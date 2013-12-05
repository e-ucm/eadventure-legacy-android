/*******************************************************************************
 * <e-Adventure> Mobile for Android(TM) is a port of the <e-Adventure> research project to 	the Android(TM) platform.
 *        
 *          Copyright 2009-2012 <e-UCM> research group.
 *        
 *          <e-UCM> is a research group of the Department of Software Engineering
 *           and Artificial Intelligence at the Complutense University of Madrid
 *           (School of Computer Science).
 *        
 *           C Profesor Jose Garcia Santesmases sn,
 *           28040 Madrid (Madrid), Spain.
 *       
 *           For more info please visit:  <http://e-adventure.e-ucm.es/android> or
 *           <http://www.e-ucm.es>
 *        
 *        	 *Android is a trademark of Google Inc.
 *       	
 *        ****************************************************************************
 *     	 This file is part of <e-Adventure> Mobile, version 1.0.
 *     
 *    	 Main contributors - Roberto Tornero
 *     
 *     	 Former contributors - Alvaro Villoria 
 *     						    Juan Manuel de las Cuevas
 *     						    Guillermo Martin 	
 *    
 *     	 Directors - Baltasar Fernandez Manjon
 *     				Eugenio Marchiori
 *     
 *         	 You can access a list of all the contributors to <e-Adventure> Mobile at:
 *                	http://e-adventure.e-ucm.es/contributors
 *        
 *        ****************************************************************************
 *             <e-Adventure> Mobile is free software: you can redistribute it and/or modify
 *            it under the terms of the GNU Lesser General Public License as published by
 *            the Free Software Foundation, either version 3 of the License, or
 *            (at your option) any later version.
 *        
 *            <e-Adventure> Mobile is distributed in the hope that it will be useful,
 *            but WITHOUT ANY WARRANTY; without even the implied warranty of
 *            MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *            GNU Lesser General Public License for more details.
 *        
 *            See <http://www.gnu.org/licenses/>
 ******************************************************************************/
package es.eucm.eadandroid.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.data.chapter.book.Book;
import es.eucm.eadandroid.common.data.chapter.book.BookPage;
import es.eucm.eadandroid.common.data.chapter.book.BookParagraph;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;

/**
 * Class to sub-parse books
 */
public class BookSubParser extends SubParser {

    /* Attributes */

    /**
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing condition tag
     */
    private static final int SUBPARSING_CONDITION = 1;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * The book being read
     */
    private Book book;

    /**
     * Current resources being read
     */
    private Resources currentResources;

    /**
     * Current conditions being read
     */
    private Conditions currentConditions;

    /**
     * Subparser for the conditions
     */
    private SubParser conditionSubParser;

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public BookSubParser( Chapter chapter ) {

        super( chapter );
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a book tag, store the id of the book
            if( sName.equals( "book" ) ) {
                String bookId = "";

                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getLocalName( i ).equals( "id" ) )
                        bookId = attrs.getValue( i );

                book = new Book( bookId );
            }

            // If it is a resources tag, create the new resources
            else if( sName.equals( "resources" ) ) {
                currentResources = new Resources( );
                
                for (int i = 0; i < attrs.getLength( ); i++) {
                    if (attrs.getLocalName( i ).equals( "name" ))
                        currentResources.setName( attrs.getValue( i ) );
                }

            }

            // If it is a documentation tag, hold the documentation in the book
            else if( sName.equals( "documentation" ) ) {
                book.setDocumentation( currentString.toString( ).trim( ) );
            }

            // If it is a condition tag, create a new subparser
            else if( sName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                conditionSubParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }

            // If it is an asset tag, read it and add it to the current resources
            else if( sName.equals( "asset" ) ) {
                String type = "";
                String path = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "type" ) )
                        type = attrs.getValue( i );
                    if( attrs.getLocalName( i ).equals( "uri" ) )
                        path = attrs.getValue( i );
                }

                currentResources.addAsset( type, path );
            }

            else if( sName.equals( "text" ) ) {
                book.setType( Book.TYPE_PARAGRAPHS );
            }

            else if( sName.equals( "pages" ) ) {
                book.setType( Book.TYPE_PAGES );
            }

            else if( sName.equals( "page" ) ) {
                String uri = "";
                int type = BookPage.TYPE_URL;
                int margin = 0;
                int marginEnd = 0;
                int marginTop = 0;
                int marginBottom = 0;
                boolean scrollable = false;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "uri" ) )
                        uri = attrs.getValue( i );

                    if( attrs.getLocalName( i ).equals( "type" ) ) {
                        if( attrs.getValue( i ).equals( "resource" ) )
                            type = BookPage.TYPE_RESOURCE;
                        if( attrs.getValue( i ).equals( "image" ) )
                            type = BookPage.TYPE_IMAGE;
                    }

                    if( attrs.getLocalName( i ).equals( "scrollable" ) )
                        if( attrs.getValue( i ).equals( "yes" ) )
                            scrollable = true;

                    if( attrs.getLocalName( i ).equals( "margin" ) ) {
                        try {
                            margin = Integer.parseInt( attrs.getValue( i ) );
                        }
                        catch( Exception e ) {
                        }
                    }

                    if( attrs.getLocalName( i ).equals( "marginEnd" ) ) {
                        try {
                            marginEnd = Integer.parseInt( attrs.getValue( i ) );
                        }
                        catch( Exception e ) {
                        }
                    }

                    if( attrs.getLocalName( i ).equals( "marginTop" ) ) {
                        try {
                            marginTop = Integer.parseInt( attrs.getValue( i ) );
                        }
                        catch( Exception e ) {
                        }
                    }

                    if( attrs.getLocalName( i ).equals( "marginBottom" ) ) {
                        try {
                            marginBottom = Integer.parseInt( attrs.getValue( i ) );
                        }
                        catch( Exception e ) {
                        }
                    }

                }
                book.addPage( uri, type, margin, marginEnd, marginTop, marginBottom, scrollable );

            }

            // If it is a title or bullet tag, store the previous text in the book
            else if( sName.equals( "title" ) || sName.equals( "bullet" ) ) {
                // Add the new text paragraph
                if( currentString != null && currentString.toString( ).trim( ).replace( "\t", "" ).replace( "\n", "" ).length( ) > 0 )
                    book.addParagraph( new BookParagraph( BookParagraph.TEXT, currentString.toString( ).trim( ).replace( "\t", "" ) ) );
                currentString = new StringBuffer( );
            }

            // If it is an image tag, store the image in the book
            else if( sName.equals( "img" ) ) {

                // Add the new text paragraph
                if( currentString.toString( ).trim( ).replace( "\t", "" ).replace( "\n", "" ).length( ) > 0 ) {
                    book.addParagraph( new BookParagraph( BookParagraph.TEXT, currentString.toString( ).trim( ).replace( "\t", "" ) ) );
                    currentString = new StringBuffer( );
                }

                String path = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "src" ) )
                        path = attrs.getValue( i );
                }

                // Add the new image paragraph
                book.addParagraph( new BookParagraph( BookParagraph.IMAGE, path ) );
            }
        }

        // If a condition is being subparsed, spread the call
        if( subParsing == SUBPARSING_CONDITION ) {
            conditionSubParser.startElement( namespaceURI, sName, sName, attrs );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a book tag, add the book to the game data
            if( sName.equals( "book" ) ) {
                chapter.addBook( book );
            }

            // If it is a resources tag, add the resources to the book
            else if( sName.equals( "resources" ) ) {
                book.addResources( currentResources );
            }

            // If it is a text tag, add the text to the book
            else if( sName.equals( "text" ) ) {
                // Add the new text paragraph
                if( currentString != null && currentString.toString( ).trim( ).replace( "\t", "" ).replace( "\n", "" ).length( ) > 0 )
                    book.addParagraph( new BookParagraph( BookParagraph.TEXT, currentString.toString( ).trim( ).replace( "\t", "" ) ) );
            }

            // If it is a title tag, add the text to the book
            else if( sName.equals( "title" ) ) {
                // Add the new title paragraph
                if( currentString != null )
                    book.addParagraph( new BookParagraph( BookParagraph.TITLE, currentString.toString( ).trim( ).replace( "\t", "" ) ) );
            }

            else if( sName.equals( "bullet" ) ) {
                // Add the new bullet paragraph
                if( currentString != null )
                    book.addParagraph( new BookParagraph( BookParagraph.BULLET, currentString.toString( ).trim( ).replace( "\t", "" ) ) );
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {

            // Spread the end element call
            conditionSubParser.endElement( namespaceURI, sName, sName );

            // If the condition is being closed, add the conditions to the resources
            if( sName.equals( "condition" ) ) {
                currentResources.setConditions( currentConditions );
                subParsing = SUBPARSING_NONE;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) {

        // If no element is being subparsed, read the characters
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If a condition is being subparsed, spread the call
        else if( subParsing == SUBPARSING_CONDITION )
            conditionSubParser.characters( buf, offset, len );
    }
}
