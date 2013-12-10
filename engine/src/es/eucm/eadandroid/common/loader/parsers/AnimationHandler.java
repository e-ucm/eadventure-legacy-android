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

import es.eucm.eadandroid.common.data.animation.Animation;
import es.eucm.eadandroid.common.data.animation.ImageLoaderFactory;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.loader.InputStreamCreator;
import es.eucm.eadandroid.common.loader.subparsers.FrameSubParser;
import es.eucm.eadandroid.common.loader.subparsers.TransitionSubParser;

public class AnimationHandler extends DefaultHandler {

    /**
     * String to store the current string in the XML file
     */
    StringBuffer currentString;

    /**
     * Resources to store the current resources being read
     */
    Resources currentResources;

    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading transition
     */
    private static final int READING_TRANSITION = 1;

    /**
     * Constant for reading frame
     */
    private static final int READING_FRAME = 2;

    /**
     * Stores the current element being read.
     */
    private int reading = READING_NONE;

    /**
     * Current subparser being used
     */
    private DefaultHandler subParser;

    /**
     * Animation being read.
     */
    private Animation animation;

    /**
     * InputStreamCreator used in resolveEntity to find dtds (only required in
     * Applet mode)
     */
    private InputStreamCreator isCreator;
    
    private ImageLoaderFactory factory;

    public AnimationHandler( InputStreamCreator isCreator, ImageLoaderFactory imageloader ) {

        this.factory = imageloader;
        this.isCreator = isCreator;
    }

    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

        if( this.reading == READING_NONE ) {

            if( sName.equals( "animation" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "id" ) ) {
                        animation = new Animation( attrs.getValue( i ), factory );
                        animation.getFrames( ).clear( );
                        animation.getTransitions( ).clear( );
                    }

                    if( attrs.getLocalName( i ).equals( "slides" ) ) {
                        if( attrs.getValue( i ).equals( "yes" ) )
                            animation.setSlides( true );
                        else
                            animation.setSlides( false );
                    }

                    if( attrs.getLocalName( i ).equals( "usetransitions" ) ) {
                        if( attrs.getValue( i ).equals( "yes" ) )
                            animation.setUseTransitions( true );
                        else
                            animation.setUseTransitions( false );
                    }
                }
            }

            if( sName.equals( "documentation" ) ) {
                currentString = new StringBuffer( );
            }

            if( sName.equals( "resources" ) ) {
                currentResources = new Resources( );
                
                for (int i = 0; i < attrs.getLength( ); i++) {
                    if (attrs.getLocalName( i ).equals( "name" ))
                        currentResources.setName( attrs.getValue( i ) );
                }
            }

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

            if( sName.equals( "frame" ) ) {
                subParser = new FrameSubParser( animation );
                reading = READING_FRAME;
            }

            if( sName.equals( "transition" ) ) {
                subParser = new TransitionSubParser( animation );
                reading = READING_TRANSITION;
            }
        }
        if( reading != READING_NONE ) {
            subParser.startElement( namespaceURI, sName, qName, attrs );
        }

    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        if( sName.equals( "documentation" ) ) {
            if( reading == READING_NONE )
                animation.setDocumentation( currentString.toString( ).trim( ) );
        }
        else if( sName.equals( "resources" ) ) {
            animation.addResources( currentResources );
        }

        if( reading != READING_NONE ) {
            try {
                subParser.endElement( namespaceURI, sName, qName );
            }
            catch( SAXException e ) {
                e.printStackTrace( );
            }
            reading = READING_NONE;
        }

    }

    @Override
    public void error( SAXParseException exception ) throws SAXParseException {

        // On validation, propagate exception
        exception.printStackTrace( );
        throw exception;
    }

    @Override
    public void characters( char[] buf, int offset, int len ) throws SAXException {

        // Append the new characters
        currentString.append( new String( buf, offset, len ) );
    }

    public Animation getAnimation( ) {

        return animation;
    }

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
        InputStream inputStream = AnimationHandler.class.getResourceAsStream( filename );
        if( inputStream == null ) {
            try {
                inputStream = new FileInputStream( filename );
                //inputStream = isCreator.buildInputStream( filename );
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
}
