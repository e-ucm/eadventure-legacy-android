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

import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadandroid.common.data.adventure.ChapterSummary;
import es.eucm.eadandroid.common.data.adventure.DescriptorData;
import es.eucm.eadandroid.common.data.adventure.DescriptorData.Perspective;
import es.eucm.eadandroid.common.loader.InputStreamCreator;

/**
 * This class is the handler to parse the e-Adventure descriptor file.
 * 
 * @author Javier Torrente
 */
public class DescriptorHandler extends DefaultHandler {

    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading a chapter
     */
    private static final int READING_CHAPTER = 1;

    /**
     * String to store the current string in the XML file
     */
    private StringBuffer currentString;

    /**
     * Stores the game descriptor being read
     */
    private DescriptorData gameDescriptor;

    /**
     * Stores the element which is being read
     */
    private int reading = READING_NONE;

    /**
     * Chapter being currently read
     */
    private ChapterSummary currentChapter;

    /**
     * InputStreamCreator used in resolveEntity to find dtds (only required in
     * Applet mode)
     */
    private InputStreamCreator isCreator;

    /**
     * Constructor
     */
    public DescriptorHandler( InputStreamCreator isCreator ) {

        currentString = new StringBuffer( );
        gameDescriptor = new DescriptorData( );
        this.isCreator = isCreator;
    }

    /**
     * Returns the game descriptor read
     * 
     * @return Game descriptor
     */
    public DescriptorData getGameDescriptor( ) {

        return gameDescriptor;
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

        if( sName.equals( "game-descriptor" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "versionNumber" ) ) {
                    gameDescriptor.setVersionNumber( attrs.getValue( i ) );
                }
        }
        
        if( qName.equals( "configuration" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "keepShowing" ) )
                    gameDescriptor.setKeepShowing( attrs.getValue( i ).equals( "yes" ) );
                if ( attrs.getLocalName( i ).equals( "perspective" )) {
                    if (attrs.getValue( i ).equals( "regular" ))
                        gameDescriptor.setPerspective( Perspective.REGULAR );
                    if (attrs.getValue( i ).equals( "isometric" ))
                        gameDescriptor.setPerspective( Perspective.ISOMETRIC );
                }
            }
        }

        // If the element is the GUI configuration, store the values
        if( sName.equals( "gui" ) ) {
            int guiType = DescriptorData.GUI_TRADITIONAL;
            boolean guiCustomized = false;
            int inventoryPosition = DescriptorData.INVENTORY_TOP_BOTTOM;

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                // Type of the GUI
                if( attrs.getLocalName( i ).equals( "type" ) ) {
                    if( attrs.getValue( i ).equals( "traditional" ) )
                        guiType = DescriptorData.GUI_TRADITIONAL;
                    else if( attrs.getValue( i ).equals( "contextual" ) )
                        guiType = DescriptorData.GUI_CONTEXTUAL;
                }

                // Customized GUI
                else if( attrs.getLocalName( i ).equals( "customized" ) ) {
                    guiCustomized = attrs.getValue( i ).equals( "yes" );
                }
                if( attrs.getLocalName( i ).equals( "inventoryPosition" ) ) {
                    if( attrs.getValue( i ).equals( "none" ) )
                        inventoryPosition = DescriptorData.INVENTORY_NONE;
                    else if( attrs.getValue( i ).equals( "top_bottom" ) )
                        inventoryPosition = DescriptorData.INVENTORY_TOP_BOTTOM;
                    else if( attrs.getValue( i ).equals( "top" ) )
                        inventoryPosition = DescriptorData.INVENTORY_TOP;
                    else if( attrs.getValue( i ).equals( "bottom" ) )
                        inventoryPosition = DescriptorData.INVENTORY_BOTTOM;
                }

            }

            // Set the values
            gameDescriptor.setGUI( guiType, guiCustomized );
            gameDescriptor.setInventoryPosition( inventoryPosition );
        }

        //Cursor
        if( sName.equals( "cursor" ) ) {
            String type = "";
            String uri = "";
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "type" ) ) {
                    type = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "uri" ) ) {
                    uri = attrs.getValue( i );
                }
            }
            gameDescriptor.addCursor( type, uri );
        }

        //Button
        if( sName.equals( "button" ) ) {
            String type = "";
            String uri = "";
            String action = "";
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "type" ) ) {
                    type = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "uri" ) ) {
                    uri = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "action" ) ) {
                    action = attrs.getValue( i );
                }
            }
            gameDescriptor.addButton( action, type, uri );
        }

        if( sName.equals( "arrow" ) ) {
            String type = "";
            String uri = "";
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "type" ) ) {
                    type = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "uri" ) ) {
                    uri = attrs.getValue( i );
                }
            }
            gameDescriptor.addArrow( type, uri );
        }

        if( sName.endsWith( "automatic-commentaries" ) ) {
            gameDescriptor.setCommentaries( true );
        }

        //If the element is the player mode, store value
        if( sName.equals( "mode" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "playerTransparent" ) ) {
                    if( attrs.getValue( i ).equals( "yes" ) ) {
                        gameDescriptor.setPlayerMode( DescriptorData.MODE_PLAYER_1STPERSON );
                    }
                    else if( attrs.getValue( i ).equals( "no" ) ) {
                        gameDescriptor.setPlayerMode( DescriptorData.MODE_PLAYER_3RDPERSON );
                    }
                }
            }
        }

        if( sName.equals( "graphics" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "mode" ) ) {
                    if( attrs.getValue( i ).equals( "windowed" ) ) {
                        gameDescriptor.setGraphicConfig( DescriptorData.GRAPHICS_WINDOWED );
                    }
                    else if( attrs.getValue( i ).equals( "fullscreen" ) ) {
                        gameDescriptor.setGraphicConfig( DescriptorData.GRAPHICS_FULLSCREEN );
                    }
                    else if( attrs.getValue( i ).equals( "blackbkg" ) ) {
                        gameDescriptor.setGraphicConfig( DescriptorData.GRAPHICS_BLACKBKG );
                    }
                }
            }
        }

      if( sName.equals( "type" ) ) {
    	  boolean gpsMode = false;
          boolean qrCodeMode = false;
       

          for( int i = 0; i < attrs.getLength( ); i++ ) {
              // Type of the GUI
        	  if( attrs.getLocalName( i ).equals( "barcode" ) ) {
        		  qrCodeMode = attrs.getValue( i ).equals( "yes" );
              }

              // Customized GUI
              else if( attrs.getLocalName( i ).equals( "geolocated" ) ) {
            	  gpsMode = attrs.getValue( i ).equals( "yes" );
              }
          }

          // Set the values
          
          gameDescriptor.setGpsMode(gpsMode);
          gameDescriptor.setQrCodeMode(qrCodeMode);
        
      }

        // If it is a chapter, create it and store the path
        else if( sName.equals( "chapter" ) ) {
            currentChapter = new ChapterSummary( );

            // Store the path of the chapter
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "path" ) )
                    currentChapter.setChapterPath( attrs.getValue( i ) );

            // Change the state
            reading = READING_CHAPTER;
        }

        // If it is an adaptation file, store the path
        // With last profile modifications, only old games includes that information in its descriptor file.
        // For that reason, the next "path" info is the name of the profile, and it is necessary to eliminate the path's characteristic
        // such as / and .xml

        else if( sName.equals( "adaptation-configuration" ) ) {
            // Store the path of the adaptation file
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "path" ) ) {
                    String adaptationName = attrs.getValue( i );
                    // delete the path's characteristics
                    // adaptationName = adaptationName.substring(adaptationName.indexOf("/")+1);
                    // adaptationName = adaptationName.substring(0,adaptationName.indexOf("."));
                    currentChapter.setAdaptationName( adaptationName );
                }

        }

        // If it is an assessment file, store the path
        // With last profile modifications, only old games includes that information in its descriptor file.
        // For that reason, the next "path" info is the name of the profile, and it is necessary to eliminate the path's characteristic
        // such as / and .xml
        else if( sName.equals( "assessment-configuration" ) ) {
            // Store the path of the assessment file
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "path" ) ) {
                    String assessmentName = attrs.getValue( i );
                    // delete the path's characteristics
                    // assessmentName = assessmentName.substring(assessmentName.indexOf("/")+1);
                    // assessmentName = assessmentName.substring(0,assessmentName.indexOf("."));
                    currentChapter.setAssessmentName( assessmentName );
                }
        }
    }

    /*  
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {

        // Stores the title
        if( sName.equals( "title" ) ) {
            if( reading == READING_NONE )
                gameDescriptor.setTitle( currentString.toString( ).trim( ) );
            else if( reading == READING_CHAPTER )
                currentChapter.setTitle( currentString.toString( ).trim( ) );
        }

        // Stores the description
        else if( sName.equals( "description" ) ) {
            if( reading == READING_NONE )
                gameDescriptor.setDescription( currentString.toString( ).trim( ) );
            else if( reading == READING_CHAPTER )
                currentChapter.setDescription( currentString.toString( ).trim( ) );
        }

        // Change the state if ends reading a chapter
        else if( sName.equals( "chapter" ) ) {
            // Add the new chapter and change the state
            gameDescriptor.addChapterSummary( currentChapter );
            reading = READING_NONE;
        }

        // Reset the current string
        currentString = new StringBuffer( );
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) throws SAXException {

        // Append the new characters
        currentString.append( new String( buf, offset, len ) );
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    @Override
    public void error( SAXParseException exception ) throws SAXParseException {

        // On validation, propagate exception
        exception.printStackTrace( );
        throw exception;
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity( String publicId, String systemId ) {

    	int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );
        InputStream inputStream = isCreator.buildInputStream( filename );
        return new InputSource( inputStream );
    }

}
