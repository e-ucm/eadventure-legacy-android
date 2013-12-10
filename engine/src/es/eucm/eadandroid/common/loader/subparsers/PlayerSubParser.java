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
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.elements.Player;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;

/**
 * Class to subparse objetcs
 */
public class PlayerSubParser extends SubParser {

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
     * Player being parsed
     */
    private Player player;

    /**
     * Current resources being parsed
     */
    private Resources currentResources;

    /**
     * Current conditions being parsed
     */
    private Conditions currentConditions;

    /**
     * Subparser for conditions
     */
    private SubParser conditionSubParser;

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public PlayerSubParser( Chapter chapter ) {

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

            // If it is a player tag, create the player
            if( sName.equals( "player" ) ) {
                player = new Player( );
            }

            // If it is a resources tag, create new resources
            else if( sName.equals( "resources" ) ) {
                currentResources = new Resources( );
                
                for (int i = 0; i < attrs.getLength( ); i++) {
                    if (attrs.getLocalName( i ).equals( "name" ))
                        currentResources.setName( attrs.getValue( i ) );
                }

            }

            // If it is a condition tag, create new conditions, new subparser and switch the state
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

                // If the asset is not an special one
                //if( !AssetsController.isAssetSpecial( path ) )
                currentResources.addAsset( type, path );
            }

            // If it is a frontcolor or bordercolor tag, pick the color
            else if( sName.equals( "frontcolor" ) || sName.equals( "bordercolor" ) ) {
                String color = "";

                // Pick the color
                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getLocalName( i ).equals( "color" ) )
                        color = attrs.getValue( i );

                // Set the color in the player
                if( sName.equals( "frontcolor" ) )
                    player.setTextFrontColor( color );
                if( sName.equals( "bordercolor" ) )
                    player.setTextBorderColor( color );
            }

            else if( sName.equals( "textcolor" ) ) {
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "showsSpeechBubble" ) )
                        player.setShowsSpeechBubbles( attrs.getValue( i ).equals( "yes" ) );
                    if( attrs.getLocalName( i ).equals( "bubbleBkgColor" ) )
                        player.setBubbleBkgColor( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "bubbleBorderColor" ) )
                        player.setBubbleBorderColor( attrs.getValue( i ) );
                }
            }

            // If it is a voice tag, take the voice and the always synthesizer option
            else if( sName.equals( "voice" ) ) {
                String voice = new String( "" );
                String response;
                boolean alwaysSynthesizer = false;

                // Pick the voice and synthesizer option
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "name" ) )
                        voice = attrs.getValue( i );
                    if( attrs.getLocalName( i ).equals( "synthesizeAlways" ) ) {
                        response = attrs.getValue( i );
                        if( response.equals( "yes" ) )
                            alwaysSynthesizer = true;
                    }

                }
                player.setAlwaysSynthesizer( alwaysSynthesizer );
                player.setVoice( voice );

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

            // If it is a player tag, store the player in the game data
            if( sName.equals( "player" ) ) {
                chapter.setPlayer( player );
            }

            // If it is a documentation tag, hold the documentation in the player
            else if( sName.equals( "documentation" ) ) {
                player.setDocumentation( currentString.toString( ).trim( ) );
            }

            // If it is a resources tag, add the resources to the player
            else if( sName.equals( "resources" ) ) {
                player.addResources( currentResources );
            }

            // If it is a name tag, add the name to the player
            else if( sName.equals( "name" ) ) {
                player.setName( currentString.toString( ).trim( ) );
            }

            // If it is a brief tag, add the brief description to the player
            else if( sName.equals( "brief" ) ) {
                player.setDescription( currentString.toString( ).trim( ) );
            }

            // If it is a detailed tag, add the detailed description to the player
            else if( sName.equals( "detailed" ) ) {
                player.setDetailedDescription( currentString.toString( ).trim( ) );
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            conditionSubParser.endElement( namespaceURI, sName, sName );

            // If the condition tag is being closed, add the condition to the resources, and switch the state
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

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If a condition is being subparsed, spread the call
        else if( subParsing == SUBPARSING_CONDITION )
            conditionSubParser.characters( buf, offset, len );
    }
}
