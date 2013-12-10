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
package es.eucm.eadandroid.ecore.control.gamestate;

import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.common.data.chapter.Trajectory.Node;
import es.eucm.eadandroid.common.data.chapter.scenes.GeneralScene;
import es.eucm.eadandroid.common.data.chapter.scenes.Scene;
import es.eucm.eadandroid.ecore.control.ActionManager;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalScene;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;

/**
 * A game main loop while a next scene is being processed
 */
public class GameStateNextScene extends GameState {

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    @Override
    public void mainLoop( long elapsedTime, int fps ) {

        // Flush the image pool and the garbage colector
        MultimediaManager.getInstance( ).flushImagePool( MultimediaManager.IMAGE_SCENE );
        MultimediaManager.getInstance().flushAnimationPool();
        
        GUI.getInstance().loading();

        // Pick the next scene, and the scene related to it
        Exit nextScene = game.getNextScene( );
        GeneralScene generalScene = game.getCurrentChapterData( ).getGeneralScene( nextScene.getNextSceneId( ) );

        // Depending on the type of the scene
        switch( generalScene.getType( ) ) {
            case GeneralScene.SCENE:

                GUI.getInstance( ).setTransition( nextScene.getTransitionTime( ), nextScene.getTransitionType( ), elapsedTime );

                if( game.getFunctionalScene( ) != null && GUI.getInstance( ).hasTransition( ) ) {
                    game.getFunctionalScene( ).draw( );
                }
                if( GUI.getInstance( ).hasTransition( ) )
                    GUI.getInstance( ).drawScene( null, elapsedTime );
                
                GUI.getInstance( ).clearBackground();

                // If it is a scene
                Scene scene = (Scene) generalScene;

                // Set the loading state
                game.setState( Game.STATE_LOADING );


                // Create a background music identifier to not replay the music from the start
                long backgroundMusicId = -1;

                // If there is a functional scene
                if( game.getFunctionalScene( ) != null ) {
                    // Take the old and the new music path
                    String oldMusicPath = null;
                    for( int i = 0; i < game.getFunctionalScene( ).getScene( ).getResources( ).size( ) && oldMusicPath == null; i++ )
                        if( new FunctionalConditions( game.getFunctionalScene( ).getScene( ).getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                            oldMusicPath = game.getFunctionalScene( ).getScene( ).getResources( ).get( i ).getAssetPath( Scene.RESOURCE_TYPE_MUSIC );
                    String newMusicPath = null;
                    for( int i = 0; i < scene.getResources( ).size( ) && newMusicPath == null; i++ )
                        if( new FunctionalConditions( scene.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                            newMusicPath = scene.getResources( ).get( i ).getAssetPath( Scene.RESOURCE_TYPE_MUSIC );

                    // If the music paths are the same, take the music identifier
                    if( oldMusicPath != null && newMusicPath != null && oldMusicPath.equals( newMusicPath ) )
                        backgroundMusicId = game.getFunctionalScene( ).getBackgroundMusicId( );
                    else
                        game.getFunctionalScene( ).stopBackgroundMusic( );
                }
                // set the player layer for this scene
                game.setPlayerLayer( scene.getPlayerLayer( ) );
                // Create the new functional scene
                FunctionalScene newScene = new FunctionalScene( scene, game.getFunctionalPlayer( ), backgroundMusicId );
                game.setFunctionalScene( newScene );

                // Set the player position

                if( nextScene.hasPlayerPosition( ) ) {
                    if( scene.getTrajectory( ) == null ) {
                        game.getFunctionalPlayer( ).setX( nextScene.getDestinyX( ) );
                        game.getFunctionalPlayer( ).setY( nextScene.getDestinyY( ) );
                        game.getFunctionalPlayer( ).setScale( scene.getPlayerScale( ) );
                    }
                    else {
                        game.getFunctionalScene( ).getTrajectory( ).changeInitialNode( nextScene.getDestinyX( ), nextScene.getDestinyY( ) );
                    }
                }
                else if( scene.getTrajectory( ) != null ) {
                    game.getFunctionalScene( ).getTrajectory( ).changeInitialNode( scene.getTrajectory( ).getInitial( ).getX( ), scene.getTrajectory( ).getInitial( ).getY( ));
                }
                else if( scene.hasDefaultPosition( ) ) {
                    // If no next scene position was defined, use the scene default
                    game.getFunctionalPlayer( ).setX( scene.getPositionX( ) );
                    game.getFunctionalPlayer( ).setY( scene.getPositionY( ) );
                    game.getFunctionalPlayer( ).setScale( scene.getPlayerScale( ) );
                }
                else {
                    // If no position was defined at all, place the player in the middle
                    game.getFunctionalPlayer( ).setX( GUI.getInstance( ).getGameAreaWidth( ) / 2 );
                    game.getFunctionalPlayer( ).setY( GUI.getInstance( ).getGameAreaHeight( ) / 2 );
                    game.getFunctionalPlayer( ).setScale( scene.getPlayerScale( ) );
                }

                // Set the state of the player and the action manager
                game.getFunctionalPlayer( ).cancelActions( );
                game.getFunctionalPlayer( ).cancelAnimations( );
                game.getActionManager( ).setActionSelected( ActionManager.ACTION_GOTO );

                // Play the post effects only if we arrive to a playable scene
                // this method also call to  game.setState( Game.STATE_RUN_EFFECTS );
                FunctionalEffects.storeAllEffects( nextScene.getPostEffects( ) );

                // Switch to run effects node
                //game.setState( Game.STATE_RUN_EFFECTS );

                break;

            case GeneralScene.SLIDESCENE:

                GUI.getInstance( ).setTransition( nextScene.getTransitionTime( ), nextScene.getTransitionType( ), elapsedTime );
                if( game.getFunctionalScene( ) != null && GUI.getInstance( ).hasTransition( ) ) {
                    game.getFunctionalScene( ).draw( );
                }
                if( GUI.getInstance( ).hasTransition( ) )
                    GUI.getInstance( ).drawScene( null, elapsedTime );
                GUI.getInstance( ).clearBackground( );

                // If it is a slidescene, load the slidescene
                game.setState( Game.STATE_SLIDE_SCENE );
                break;

            case GeneralScene.VIDEOSCENE:
                // Stop the music
                if( game.getFunctionalScene( ) != null )
                    game.getFunctionalScene( ).stopBackgroundMusic( );

                // If it is a videoscene, load the videoscene
                game.setState( Game.STATE_VIDEO_SCENE );
                break;
        }
    }


}
