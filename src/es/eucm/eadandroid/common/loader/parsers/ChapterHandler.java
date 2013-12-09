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
package es.eucm.eadandroid.common.loader.parsers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.data.chapter.conditions.GlobalState;
import es.eucm.eadandroid.common.data.chapter.effects.Macro;
import es.eucm.eadandroid.common.loader.InputStreamCreator;
import es.eucm.eadandroid.common.loader.subparsers.AdaptationSubParser;
import es.eucm.eadandroid.common.loader.subparsers.AssessmentSubParser;
import es.eucm.eadandroid.common.loader.subparsers.AtrezzoSubParser;
import es.eucm.eadandroid.common.loader.subparsers.BookSubParser;
import es.eucm.eadandroid.common.loader.subparsers.CharacterSubParser;
import es.eucm.eadandroid.common.loader.subparsers.ConditionSubParser;
import es.eucm.eadandroid.common.loader.subparsers.CutsceneSubParser;
import es.eucm.eadandroid.common.loader.subparsers.EffectSubParser;
import es.eucm.eadandroid.common.loader.subparsers.GpsSubParser;
import es.eucm.eadandroid.common.loader.subparsers.GraphConversationSubParser;
import es.eucm.eadandroid.common.loader.subparsers.ItemSubParser;
import es.eucm.eadandroid.common.loader.subparsers.PlayerSubParser;
import es.eucm.eadandroid.common.loader.subparsers.QrcodeSubParser;
import es.eucm.eadandroid.common.loader.subparsers.SceneSubParser;
import es.eucm.eadandroid.common.loader.subparsers.SubParser;
import es.eucm.eadandroid.common.loader.subparsers.TimerSubParser;
import es.eucm.eadandroid.common.loader.subparsers.TreeConversationSubParser;

/**
 * This class is the handler to parse the e-Adventure XML file
 */
public class ChapterHandler extends DefaultHandler {

    /* Attributes */

    /**
     * Constant for subparsing nothing
     */
    private static final int NONE = 0;

    /**
     * Constant for subparsing scene tag
     */
    private static final int SCENE = 1;

    /**
     * Constant for subparsing slidescene tag
     */
    private static final int CUTSCENE = 2;

    /**
     * Constant for subparsing book tag
     */
    private static final int BOOK = 3;

    /**
     * Constant for subparsing object tag
     */
    private static final int OBJECT = 4;

    /**
     * Constant for subparsing player tag
     */
    private static final int PLAYER = 5;

    /**
     * Constant for subparsing character tag
     */
    private static final int CHARACTER = 6;

    /**
     * Constant for subparsing conversation tag
     */
    private static final int CONVERSATION = 7;

    /**
     * Constant for subparsing timer tag
     */
    private static final int TIMER = 8;

    /**
     * Constant for subparsing global-state tag
     */
    private static final int GLOBAL_STATE = 9;

    /**
     * Constant for subparsing macro tag
     */
    private static final int MACRO = 10;

    /**
     * Constant for subparsing atrezzo object tag
     */
    private static final int ATREZZO = 11;

    /**
     * Constant for subparsing assessment tag
     */
    private static final int ASSESSMENT = 12;

    /**
     * Constant for subparsing adaptation tag
     */
    private static final int ADAPTATION = 13;
    
    private static final int SUBPARSING_GPS=14;
    private static final int SUBPARSING_QRCODE=15;

    /**
     * Stores the current element being parsed
     */
    private int subParsing = NONE;

    /**
     * Current subparser being used
     */
    private SubParser subParser;

    /**
     * Chapter data
     */
    private Chapter chapter;

    /**
     * InputStreamCreator used in resolveEntity to find dtds (only required in
     * Applet mode)
     */
    private InputStreamCreator isCreator;

    /**
     * Current global state being subparsed
     */
    private GlobalState currentGlobalState;

    /**
     * Current macro being subparsed
     */
    private Macro currentMacro;

    /**
     * Buffer for globalstate docs
     */
    private StringBuffer currentString;

    /* Methods */

    /**
     * Default constructor.
     * 
     * @param chapter
     *            Chapter in which the data will be stored
     */
    public ChapterHandler( InputStreamCreator isCreator, Chapter chapter ) {

        this.chapter = chapter;
        this.isCreator = isCreator;
        currentString = new StringBuffer( );
    }

    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

        // If no element is being subparsed, check if we must subparse something
        if( subParsing == NONE ) {

            //Parse eAdventure attributes
            if( sName.equals( "eAdventure" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "adaptProfile" ) ) {
                        chapter.setAdaptationName( attrs.getValue( i ) );
                    }
                    if( attrs.getLocalName( i ).equals( "assessProfile" ) ) {
                        chapter.setAssessmentName( attrs.getValue( i ) );
                    }
                }
            }
            // Subparse scene
            else if( sName.equals( "scene" ) ) {
                subParser = new SceneSubParser( chapter );
                subParsing = SCENE;
            }

            // Subparse slidescene
            else if( sName.equals( "slidescene" ) || sName.equals( "videoscene" ) ) {
                subParser = new CutsceneSubParser( chapter );
                subParsing = CUTSCENE;
            }

            // Subparse book
            else if( sName.equals( "book" ) ) {
                subParser = new BookSubParser( chapter );
                subParsing = BOOK;
            }

            // Subparse object
            else if( sName.equals( "object" ) ) {
                subParser = new ItemSubParser( chapter );
                subParsing = OBJECT;
            }

            // Subparse player
            else if( sName.equals( "player" ) ) {
                subParser = new PlayerSubParser( chapter );
                subParsing = PLAYER;
            }

            // Subparse character
            else if( sName.equals( "character" ) ) {
                subParser = new CharacterSubParser( chapter );
                subParsing = CHARACTER;
            }

            // Subparse conversacion (tree conversation)
            else if( sName.equals( "tree-conversation" ) ) {
                subParser = new TreeConversationSubParser( chapter );
                subParsing = CONVERSATION;
            }

            // Subparse conversation (graph conversation)
            else if( sName.equals( "graph-conversation" ) ) {
                subParser = new GraphConversationSubParser( chapter );
                subParsing = CONVERSATION;
            }

            // Subparse timer
            else if( sName.equals( "timer" ) ) {
                subParser = new TimerSubParser( chapter );
                subParsing = TIMER;
            }
            

            else if( sName.equals( "qrcodes" ) ) {
            	
                subParser = new QrcodeSubParser( chapter );
                subParsing = SUBPARSING_QRCODE;
                
            }
            else if( sName.equals( "gps" ) ) {
            	
                subParser = new GpsSubParser( chapter );
                subParsing = SUBPARSING_GPS;
                
            }

            // Subparse global-state
            else if( sName.equals( "global-state" ) ) {
                String id = null;
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "id" ) )
                        id = attrs.getValue( i );
                }
                currentGlobalState = new GlobalState( id );
                currentString = new StringBuffer( );
                chapter.addGlobalState( currentGlobalState );
                subParser = new ConditionSubParser( currentGlobalState, chapter );
                subParsing = GLOBAL_STATE;
            }

            // Subparse macro
            else if( sName.equals( "macro" ) ) {
                String id = null;
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "id" ) )
                        id = attrs.getValue( i );
                }
                currentMacro = new Macro( id );
                currentString = new StringBuffer( );
                chapter.addMacro( currentMacro );
                subParser = new EffectSubParser( currentMacro, chapter );
                subParsing = MACRO;
            }
            // Subparse atrezzo object
            else if( sName.equals( "atrezzoobject" ) ) {
                subParser = new AtrezzoSubParser( chapter );
                subParsing = ATREZZO;
            }// Subparse assessment profile
            else if( sName.equals( "assessment" ) ) {
                subParser = new AssessmentSubParser( chapter );
                subParsing = ASSESSMENT;
            }// Subparse adaptation profile
            else if( sName.equals( "adaptation" ) ) {
                subParser = new AdaptationSubParser( chapter );
                subParsing = ADAPTATION;
            }

        }

        // If an element is being subparsed, spread the call
        if( subParsing != NONE ) {
            //try {
            subParser.startElement( namespaceURI, sName, sName, attrs );
            //} catch (Exception e){
            //	System.out.println("Marihuanhell es muy malo pero hemos capturado la excepción");
            //e.printStackTrace();
            //}

        }
    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {

        if( sName.equals( "documentation" ) && subParsing == GLOBAL_STATE ) {
            currentGlobalState.setDocumentation( currentString.toString( ).trim( ) );
        }
        else if( sName.equals( "documentation" ) && subParsing == MACRO ) {
            currentMacro.setDocumentation( currentString.toString( ).trim( ) );
        }

        currentString = new StringBuffer( );

        // If an element is being subparsed
        if( subParsing != NONE ) {

            // Spread the end element call
            subParser.endElement( namespaceURI, sName, sName );


            // If the element is not being subparsed anymore, return to normal state
            if( sName.equals( "scene" ) && subParsing == SCENE || ( sName.equals( "slidescene" ) || sName.equals( "videoscene" ) ) && subParsing == CUTSCENE || sName.equals( "book" ) && subParsing == BOOK || sName.equals( "object" ) && subParsing == OBJECT || sName.equals( "player" ) && subParsing == PLAYER || sName.equals( "character" ) && subParsing == CHARACTER || sName.equals( "tree-conversation" ) && subParsing == CONVERSATION || sName.equals( "graph-conversation" ) && subParsing == CONVERSATION || sName.equals( "timer" ) && subParsing == TIMER || sName.equals( "qrcodes" ) && subParsing == SUBPARSING_QRCODE|| sName.equals( "gps" ) && subParsing == SUBPARSING_GPS|| sName.equals( "global-state" ) && subParsing == GLOBAL_STATE || sName.equals( "macro" ) && subParsing == MACRO || sName.equals( "atrezzoobject" ) && subParsing == ATREZZO || sName.equals( "assessment" ) && subParsing == ASSESSMENT || sName.equals( "adaptation" ) && subParsing == ADAPTATION ) {
                subParsing = NONE;
            }

        }
    }

    @Override
    public void endDocument( ) {

        // In the end of the document, if the chapter has no initial scene
        if( chapter.getTargetId( ) == null ) {
            // Set it to the first scene
            if( chapter.getScenes( ).size( ) > 0 )
                chapter.setTargetId( chapter.getScenes( ).get( 0 ).getId( ) );

            // Or to the first cutscene
            else if( chapter.getCutscenes( ).size( ) > 0 )
                chapter.setTargetId( chapter.getCutscenes( ).get( 0 ).getId( ) );
        }
    }

    @Override
    public void characters( char[] buf, int offset, int len ) throws SAXException {

        // If the SAX handler is reading an element, just spread the call to the parser
        currentString.append( new String( buf, offset, len ) );
        if( subParsing != NONE ) {
            subParser.characters( buf, offset, len );
        }
    }

    @Override
    public void error( SAXParseException exception ) throws SAXParseException {

        // On validation, propagate exception
        exception.printStackTrace( );
        throw exception;
    }

    /*	@Override
    	public InputSource resolveEntity( String publicId, String systemId ) {
    		// Take the name of the file SAX is looking for
    		int startFilename = systemId.lastIndexOf( "/" ) + 1;
    		String filename = systemId.substring( startFilename, systemId.length( ) );

    		// Create the input source to return
    		InputSource inputSource = null;

    		try {
    			// If the file is eadventure.dtd, use the one in the editor's folder
    			if( filename.toLowerCase( ).equals( "eadventure.dtd" ) )
    				inputSource = new InputSource( new FileInputStream( filename ) );

    			// If it is any other file, use the super's method
    			else
    				inputSource = super.resolveEntity( publicId, systemId );
    		} catch( FileNotFoundException e ) {
    			e.printStackTrace( );
    		} catch( IOException e ) {
    			e.printStackTrace( );
    		} catch( SAXException e ) {
    			e.printStackTrace( );
    		}

    		return inputSource;
    	}*/

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity( String publicId, String systemId ) {

        // Take the name of the file SAX is looking for
        int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );

        // Build and return a input stream with the file (usually the DTD): 
        // 1) First try looking at main folder
        InputStream inputStream = AdaptationHandler.class.getResourceAsStream( filename );
        if( inputStream == null ) {
            try {
                inputStream = new FileInputStream( filename );
            }
            catch( FileNotFoundException e ) {
                inputStream = null;
            }
        }

        // 2) Secondly use the inputStreamCreator
        if( inputStream == null )
            inputStream = isCreator.buildInputStream( filename );

        return new InputSource( inputStream );
    }

    @Override
    public void fatalError( SAXParseException e ) throws SAXException {

        //throw e;
    }
}
