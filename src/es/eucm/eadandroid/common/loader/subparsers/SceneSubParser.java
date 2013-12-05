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


import java.util.ArrayList;

import org.xml.sax.Attributes;

import android.graphics.Point;

import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.data.chapter.ElementReference;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.common.data.chapter.ExitLook;
import es.eucm.eadandroid.common.data.chapter.InfluenceArea;
import es.eucm.eadandroid.common.data.chapter.NextScene;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.data.chapter.scenes.Scene;

/**
 * Class to subparse scenes
 */
public class SceneSubParser extends SubParser {

    /* Attributes */

    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading resources tag
     */
    private static final int READING_RESOURCES = 1;

    /**
     * Constant for reading exit tag
     */
    private static final int READING_EXIT = 2;

    /**
     * Constant for reading next-scene tag
     */
    private static final int READING_NEXT_SCENE = 3;

    /**
     * Constant for reading element reference tag
     */
    private static final int READING_ELEMENT_REFERENCE = 4;

    /**
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing condition tag
     */
    private static final int SUBPARSING_CONDITION = 1;

    /**
     * Constant for subparsing effect tag
     */
    private static final int SUBPARSING_EFFECT = 2;

    /**
     * Constant for subparsing active area
     */
    private static final int SUBPARSING_ACTIVE_AREA = 3;

    /**
     * Constant for subparsing active area
     */
    private static final int SUBPARSING_BARRIER = 4;

    private static final int SUBPARSING_TRAJECTORY = 5;
 

    /**
     * Stores the current element being parsed
     */
    private int reading = READING_NONE;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * Stores the element being parsed
     */
    private Scene scene;

    /**
     * Stores the current resources being parsed
     */
    private Resources currentResources;

    /**
     * Stores the current exit being used
     */
    private Exit currentExit;

    /**
     * Stores the current exit look being used
     */
    private ExitLook currentExitLook;

    /**
     * Stores the current next-scene being used
     */
    private NextScene currentNextScene;

    private Point currentPoint;

    /**
     * Stores the current element reference being used
     */
    private ElementReference currentElementReference;

    /**
     * Stores the current conditions being parsed
     */
    private Conditions currentConditions;

    /**
     * Stores the current effects being parsed
     */
    private Effects currentEffects;

    /**
     * The subparser for the condition or effect tags
     */
    private SubParser subParser;

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public SceneSubParser( Chapter chapter ) {

        super( chapter );
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // If no element is being parsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a scene tag, create a new scene with its id
            if( sName.equals( "scene" ) ) {
                String sceneId = "";
                boolean initialScene = false;
                int playerLayer = -1;
                float playerScale = 1.0f;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "id" ) )
                        sceneId = attrs.getValue( i );
                    if( attrs.getLocalName( i ).equals( "start" ) )
                        initialScene = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getLocalName( i ).equals( "playerLayer" ) )
                        playerLayer = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "playerScale" ) )
                        playerScale = Float.parseFloat( attrs.getValue( i ) );
                }

                scene = new Scene( sceneId );
                scene.setPlayerLayer( playerLayer );
                scene.setPlayerScale( playerScale );
                if( initialScene )
                    chapter.setTargetId( sceneId );
            }

            // If it is a resources tag, create the new resources
            else if( sName.equals( "resources" ) ) {
                currentResources = new Resources( );
                
                for (int i = 0; i < attrs.getLength( ); i++) {
                    if (attrs.getLocalName( i ).equals( "name" ))
                        currentResources.setName( attrs.getValue( i ) );
                }

                reading = READING_RESOURCES;
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

            // If it is a default-initial-position tag, store it in the scene
            else if( sName.equals( "default-initial-position" ) ) {
                int x = Integer.MIN_VALUE, y = Integer.MIN_VALUE;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "x" ) )
                        x = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "y" ) )
                        y = Integer.parseInt( attrs.getValue( i ) );
                }

                scene.setDefaultPosition( x, y );
            }

            // If it is an exit tag, create the new exit
            else if( sName.equals( "exit" ) ) {
                int x = 0, y = 0, width = 0, height = 0;
                boolean rectangular = true;
                int influenceX = 0, influenceY = 0, influenceWidth = 0, influenceHeight = 0;
                boolean hasInfluence = false;
                String idTarget = "";
                int destinyX = Integer.MIN_VALUE, destinyY = Integer.MIN_VALUE;
                int transitionType = 0, transitionTime = 0;
                boolean notEffects = false;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "rectangular" ) )
                        rectangular = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getLocalName( i ).equals( "x" ) )
                        x = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "y" ) )
                        y = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "width" ) )
                        width = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "height" ) )
                        height = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "hasInfluenceArea" ) )
                        hasInfluence = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getLocalName( i ).equals( "influenceX" ) )
                        influenceX = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "influenceY" ) )
                        influenceY = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "influenceWidth" ) )
                        influenceWidth = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "influenceHeight" ) )
                        influenceHeight = Integer.parseInt( attrs.getValue( i ) );

                    if( attrs.getLocalName( i ).equals( "idTarget" ) )
                        idTarget = attrs.getValue( i );
                    if( attrs.getLocalName( i ).equals( "destinyX" ) )
                        destinyX = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "destinyY" ) )
                        destinyY = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "transitionType" ) )
                        transitionType = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "transitionTime" ) )
                        transitionTime = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "not-effects" ) )
                        notEffects = attrs.getValue( i ).equals( "yes" );
                }

                currentExit = new Exit( rectangular, x, y, width, height );
                currentExit.setNextSceneId( idTarget );
                currentExit.setDestinyX( destinyX );
                currentExit.setDestinyY( destinyY );
                currentExit.setTransitionTime( transitionTime );
                currentExit.setTransitionType( transitionType );
                currentExit.setHasNotEffects( notEffects );
                if( hasInfluence ) {
                    InfluenceArea influenceArea = new InfluenceArea( influenceX, influenceY, influenceWidth, influenceHeight );
                    currentExit.setInfluenceArea( influenceArea );
                }
                reading = READING_EXIT;
            }

            else if( sName.equals( "exit-look" ) ) {
                currentExitLook = new ExitLook( );
                String text = null;
                String cursorPath = null;
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "text" ) )
                        text = attrs.getValue( i );
                    if( attrs.getLocalName( i ).equals( "cursor-path" ) )
                        cursorPath = attrs.getValue( i );
                }
                currentExitLook.setCursorPath( cursorPath );
                currentExitLook.setExitText( text );
            }

            // If it is a next-scene tag, create the new next scene
            else if( sName.equals( "next-scene" ) ) {
                String idTarget = "";
                int x = Integer.MIN_VALUE, y = Integer.MIN_VALUE;
                int transitionType = 0, transitionTime = 0;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "idTarget" ) )
                        idTarget = attrs.getValue( i );
                    if( attrs.getLocalName( i ).equals( "x" ) )
                        x = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "y" ) )
                        y = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "transitionType" ) )
                        transitionType = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "transitionTime" ) )
                        transitionTime = Integer.parseInt( attrs.getValue( i ) );
                }

                currentNextScene = new NextScene( idTarget, x, y );
                currentNextScene.setTransitionType( transitionType );
                currentNextScene.setTransitionTime( transitionTime );
                reading = READING_NEXT_SCENE;
            }

            else if( sName.equals( "point" ) ) {

                int x = 0;
                int y = 0;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "x" ) )
                        x = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "y" ) )
                        y = Integer.parseInt( attrs.getValue( i ) );
                }

                currentPoint = new Point( x, y );
            }

            // If it is a object-ref or character-ref, create the new element reference
            else if( sName.equals( "object-ref" ) || sName.equals( "character-ref" ) || sName.equals( "atrezzo-ref" ) ) {
                String idTarget = "";
                int x = 0, y = 0;
                float scale = 0;
                int layer = 0;
                int influenceX = 0, influenceY = 0, influenceWidth = 0, influenceHeight = 0;
                boolean hasInfluence = false;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "idTarget" ) )
                        idTarget = attrs.getValue( i );
                    if( attrs.getLocalName( i ).equals( "x" ) )
                        x = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "y" ) )
                        y = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "scale" ) )
                        scale = Float.parseFloat( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "layer" ) )
                        layer = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "hasInfluenceArea" ) )
                        hasInfluence = attrs.getValue( i ).equals( "yes" );
                    if( attrs.getLocalName( i ).equals( "influenceX" ) )
                        influenceX = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "influenceY" ) )
                        influenceY = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "influenceWidth" ) )
                        influenceWidth = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getLocalName( i ).equals( "influenceHeight" ) )
                        influenceHeight = Integer.parseInt( attrs.getValue( i ) );
                }
                
                if (layer==-1)
                	layer=0;

                currentElementReference = new ElementReference( idTarget, x, y, layer );
                if( hasInfluence ) {
                    InfluenceArea influenceArea = new InfluenceArea( influenceX, influenceY, influenceWidth, influenceHeight );
                    currentElementReference.setInfluenceArea( influenceArea );
                }
                if( scale > 0.001 || scale < -0.001 )
                    currentElementReference.setScale( scale );
                reading = READING_ELEMENT_REFERENCE;
            }

            // If it is a condition tag, create the new condition, the subparser and switch the state
            else if( sName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }

            // If it is a effect tag, create the new effect, the subparser and switch the state
            else if( sName.equals( "effect" ) ) {
                currentEffects = new Effects( );
                subParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }

            // If it is a post-effect tag, create the new effect, the subparser and switch the state
            else if( sName.equals( "post-effect" ) ) {
                currentEffects = new Effects( );
                subParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }

            // If it is a post-effect tag, create the new effect, the subparser and switch the state
            else if( sName.equals( "not-effect" ) ) {
                currentEffects = new Effects( );
                subParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }

            // If it is a post-effect tag, create the new effect, the subparser and switch the state
            else if( sName.equals( "active-area" ) ) {
                subParsing = SUBPARSING_ACTIVE_AREA;
                subParser = new ActiveAreaSubParser( chapter, scene, scene.getActiveAreas( ).size( ) );
            }

            // If it is a post-effect tag, create the new effect, the subparser and switch the state
            else if( sName.equals( "barrier" ) ) {
                subParsing = SUBPARSING_BARRIER;
                subParser = new BarrierSubParser( chapter, scene, scene.getBarriers( ).size( ) );
            }

            else if( sName.equals( "trajectory" ) ) {
                subParsing = SUBPARSING_TRAJECTORY;
                subParser = new TrajectorySubParser( chapter, scene );
            }
            
         // Subparse GPS


        }

        // If it is subparsing an effect or condition, spread the call
        if( subParsing != SUBPARSING_NONE ) {
            subParser.startElement( namespaceURI, sName, sName, attrs );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a scene tag, store the scene in the game data
            if( sName.equals( "scene" ) ) {
                chapter.addScene( scene );
            }

            // If it is a resources tag, add the resources to the scene
            else if( sName.equals( "resources" ) ) {
                scene.addResources( currentResources );
                reading = READING_NONE;
            }

            // If it is a name tag, store the name in the scene
            else if( sName.equals( "name" ) ) {
                scene.setName( currentString.toString( ).trim( ) );
            }

            // If it is a documentation tag, hold the documentation in the current element
            else if( sName.equals( "documentation" ) ) {
                if( reading == READING_NONE )
                    scene.setDocumentation( currentString.toString( ).trim( ) );
                else if( reading == READING_EXIT )
                    currentExit.setDocumentation( currentString.toString( ).trim( ) );
                else if( reading == READING_ELEMENT_REFERENCE )
                    currentElementReference.setDocumentation( currentString.toString( ).trim( ) );
            }

            // If it is an exit tag, store the exit in the scene
            else if( sName.equals( "exit" ) ) {
                if( currentExit.getNextScenes( ).size( ) > 0 ) {
                    for( NextScene nextScene : currentExit.getNextScenes( ) ) {
                        try {
                            Exit exit = (Exit) currentExit.clone( );
                            exit.setNextScenes( new ArrayList<NextScene>( ) );
                            exit.setDestinyX( nextScene.getPositionX( ) );
                            exit.setDestinyY( nextScene.getPositionY( ) );
                            exit.setEffects( nextScene.getEffects( ) );
                            exit.setPostEffects( nextScene.getPostEffects( ) );
                            if( exit.getDefaultExitLook( ) == null )
                                exit.setDefaultExitLook( nextScene.getExitLook( ) );
                            else {
                                if( nextScene.getExitLook( ) != null ) {
                                    if( nextScene.getExitLook( ).getExitText( ) != null && !nextScene.getExitLook( ).getExitText( ).equals( "" ) )
                                        exit.getDefaultExitLook( ).setExitText( nextScene.getExitLook( ).getExitText( ) );
                                    if( nextScene.getExitLook( ).getCursorPath( ) != null && !nextScene.getExitLook( ).getCursorPath( ).equals( "" ) )
                                        exit.getDefaultExitLook( ).setCursorPath( nextScene.getExitLook( ).getCursorPath( ) );
                                }
                            }
                            exit.setHasNotEffects( false );
                            exit.setConditions( nextScene.getConditions( ) );
                            exit.setNextSceneId( nextScene.getTargetId( ) );
                            scene.addExit( exit );
                        }
                        catch( CloneNotSupportedException e ) {
                            e.printStackTrace( );
                        }
                    }
                }
                else {
                    scene.addExit( currentExit );
                }
                //scene.addExit( currentExit );
                reading = READING_NONE;
            }

            // If it is an exit look tag, store the look in the exit
            else if( sName.equals( "exit-look" ) ) {
                if( reading == READING_NEXT_SCENE )
                    currentNextScene.setExitLook( currentExitLook );
                else if( reading == READING_EXIT ) {
                    currentExit.setDefaultExitLook( currentExitLook );
                }
            }

            // If it is a next-scene tag, store the next scene in the current exit
            else if( sName.equals( "next-scene" ) ) {
                currentExit.addNextScene( currentNextScene );
                reading = READING_NONE;
            }

            else if( sName.equals( "point" ) ) {
                currentExit.addPoint( currentPoint );
            }

            // If it is a object-ref tag, store the reference in the scene
            else if( sName.equals( "object-ref" ) ) {
                scene.addItemReference( currentElementReference );
                reading = READING_NONE;
            }

            // If it is a character-ref tag, store the reference in the scene
            else if( sName.equals( "character-ref" ) ) {
                scene.addCharacterReference( currentElementReference );
                reading = READING_NONE;
            }
            // If it is a atrezzo-ref tag, store the reference in the scene
            else if( sName.equals( "atrezzo-ref" ) ) {
                scene.addAtrezzoReference( currentElementReference );
                reading = READING_NONE;
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, sName );

            // If the condition tag is being closed
            if( sName.equals( "condition" ) ) {

                // If we are parsing resources, add the condition to the current resources
                if( reading == READING_RESOURCES )
                    currentResources.setConditions( currentConditions );

                // If we are parsing a next-scene, add the condition to the current next scene
                if( reading == READING_NEXT_SCENE )
                    currentNextScene.setConditions( currentConditions );

                // If we are parsing an element reference, add the condition to the current element reference
                if( reading == READING_ELEMENT_REFERENCE )
                    currentElementReference.setConditions( currentConditions );

                if( reading == READING_EXIT )
                    currentExit.setConditions( currentConditions );

                // Switch the state
                subParsing = SUBPARSING_NONE;

            }
        }

        // If an effect is being subparsed
        else if( subParsing == SUBPARSING_EFFECT ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, sName );

            // If the effect tag is being closed, add the effects to the current next scene and switch the state
            if( sName.equals( "effect" ) ) {
                if( reading == READING_NEXT_SCENE )
                    currentNextScene.setEffects( currentEffects );
                if( reading == READING_EXIT )
                    currentExit.setEffects( currentEffects );
                subParsing = SUBPARSING_NONE;
            }

            // If the effect tag is being closed, add the post-effects to the current next scene and switch the state
            if( sName.equals( "post-effect" ) ) {
                if( reading == READING_NEXT_SCENE )
                    currentNextScene.setPostEffects( currentEffects );
                if( reading == READING_EXIT )
                    currentExit.setPostEffects( currentEffects );
                subParsing = SUBPARSING_NONE;
            }

            if( sName.equals( "not-effect" ) ) {
                currentExit.setNotEffects( currentEffects );
                subParsing = SUBPARSING_NONE;
            }
        }

        // If an active area is being subparsed
        else if( subParsing == SUBPARSING_ACTIVE_AREA ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, sName );

            if( sName.equals( "active-area" ) ) {
                subParsing = SUBPARSING_NONE;
            }
        }
        
        
        

        // If a barrier is being subparsed
        else if( subParsing == SUBPARSING_BARRIER ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, sName );

            if( sName.equals( "barrier" ) ) {
                subParsing = SUBPARSING_NONE;
            }
        }

        else if( subParsing == SUBPARSING_TRAJECTORY ) {
            subParser.endElement( namespaceURI, sName, sName );
            if( sName.equals( "trajectory" ) ) {
                subParsing = SUBPARSING_NONE;
                // next line is moved to TrayectorySubParser
                //scene.getTrajectory().deleteUnconnectedNodes();
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

        // If it is reading an effect or a condition
        else
            subParser.characters( buf, offset, len );
    }
}
