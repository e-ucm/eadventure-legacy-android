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


import java.util.Queue;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;
import es.eucm.eadandroid.common.data.chapter.resources.Asset;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.data.chapter.scenes.Cutscene;
import es.eucm.eadandroid.common.data.chapter.scenes.Scene;
import es.eucm.eadandroid.common.data.chapter.scenes.Slidescene;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.animations.Animation;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * A game main loop while a "slidescene" is being displayed
 */
public class GameStateSlidescene extends GameState {

    /**
     * Animation of the slidescene
     */
    private Animation slides;

    /**
     * Slidescene being played
     */
    private Slidescene slidescene;

    /**
     * Flag to control that we jump to the next chapter at most once
     */
    private boolean yetSkipped = false;

    private Bitmap bkg ;

    private Canvas canvas ;
    

    
    /**
     * Creates a new GameStateSlidescene
     */
    public GameStateSlidescene( ) {

        super( );
        
        
        bkg = Bitmap.createBitmap( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, Bitmap.Config.RGB_565);
        canvas = new Canvas(bkg);
        
        slidescene = (Slidescene) game.getCurrentChapterData( ).getGeneralScene( game.getNextScene( ).getNextSceneId( ) );

        // Select the resources
        Resources resources = createResourcesBlock( );

        // Create a background music identifier to not replay the music from the start
        long backgroundMusicId = -1;

        // If there is a funcional scene
        if( game.getFunctionalScene( ) != null ) {
            // Take the old and the new music path
            String oldMusicPath = null;
            for( int i = 0; i < game.getFunctionalScene( ).getScene( ).getResources( ).size( ) && oldMusicPath == null; i++ )
                if( new FunctionalConditions( game.getFunctionalScene( ).getScene( ).getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                    oldMusicPath = game.getFunctionalScene( ).getScene( ).getResources( ).get( i ).getAssetPath( Scene.RESOURCE_TYPE_MUSIC );
            String newMusicPath = null;
            for( int i = 0; i < slidescene.getResources( ).size( ) && newMusicPath == null; i++ )
                if( new FunctionalConditions( slidescene.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                    newMusicPath = slidescene.getResources( ).get( i ).getAssetPath( Scene.RESOURCE_TYPE_MUSIC );

            // If the music paths are the same, take the music identifier
            if( oldMusicPath != null && newMusicPath != null && oldMusicPath.equals( newMusicPath ) )
                backgroundMusicId = game.getFunctionalScene( ).getBackgroundMusicId( );
            else
                game.getFunctionalScene( ).stopBackgroundMusic( );
        }
        if( Game.getInstance( ).getOptions( ).isMusicActive( ) ) {
            if( backgroundMusicId != -1 ) {
                if( !MultimediaManager.getInstance( ).isPlaying( backgroundMusicId ) ) {
                    backgroundMusicId = MultimediaManager.getInstance( ).loadMusic( resources.getAssetPath( Scene.RESOURCE_TYPE_MUSIC ), true );
                    MultimediaManager.getInstance( ).startPlaying( backgroundMusicId );
                }
            }
            else {
                if( resources.existAsset( Scene.RESOURCE_TYPE_MUSIC ) ) {
                    backgroundMusicId = MultimediaManager.getInstance( ).loadMusic( resources.getAssetPath( Scene.RESOURCE_TYPE_MUSIC ), true );
                    MultimediaManager.getInstance( ).startPlaying( backgroundMusicId );
                }
            }
        }

        // Create the set of slides and start it
        slides = MultimediaManager.getInstance( ).loadSlidesReference( resources.getAssetPath( Slidescene.RESOURCE_TYPE_SLIDES ), MultimediaManager.IMAGE_SCENE );
        slides.start( );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    @Override
    public void mainLoop( long elapsedTime, int fps ) {

    	handleUIEvents();
    	
        // Paint the current slide
      
        canvas.clipRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
        slides.update( elapsedTime );
        if( !slides.isPlayingForFirstTime( ) )
            finishedSlides( );
        canvas.drawBitmap( slides.getImage( ), 0, 0, null );

        GUI.getInstance( ).addBackgroundToDraw( bkg, 0 );
        //        GUI.getInstance().addElementToDraw(slides.getImage(), 0, 0, 0, 0);
        GUI.getInstance( ).endDraw( );
        GUI.getInstance( ).drawScene( GUI.getInstance( ).getGraphics( ), elapsedTime );
        

    }

    
    private void finishedSlides( ) {

        // If it is a endscene, go to the next chapter
        if( !yetSkipped && slidescene.getNext( ) == Cutscene.ENDCHAPTER ) {
            yetSkipped = true;
            game.goToNextChapter( );
        }
        else if( slidescene.getNext( ) == Cutscene.NEWSCENE ) {
            Exit exit = new Exit( slidescene.getTargetId( ) );
            exit.setDestinyX( slidescene.getPositionX( ) );
            exit.setDestinyY( slidescene.getPositionY( ) );
            exit.setPostEffects( slidescene.getEffects( ) );
            exit.setTransitionTime( slidescene.getTransitionTime( ) );
            exit.setTransitionType( slidescene.getTransitionType( ) );
            game.setNextScene( exit );
            game.setState( Game.STATE_NEXT_SCENE );
        }
        else {
            if( game.getFunctionalScene( ) == null ) {
            	yetSkipped = true;
                game.goToNextChapter( );
            }
            FunctionalEffects.storeAllEffects( new Effects( ) );
        }
    }

    /**
     * Creates the current resource block to be used
     */
    private Resources createResourcesBlock( ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < slidescene.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( slidescene.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = slidescene.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if( newResources == null ) {
            newResources = new Resources( );
            newResources.addAsset( new Asset( Slidescene.RESOURCE_TYPE_SLIDES, ResourceHandler.DEFAULT_SLIDES ) );
        }
        return newResources;
    }

	
	private void handleUIEvents() {
		
		UIEvent e;
		Queue<UIEvent> vEvents = touchListener.getEventQueue();
		while ((e = vEvents.poll()) != null) {
			switch (e.getAction()) {
			case UIEvent.TAP_ACTION: 
		        // Display the next slide 
		        boolean endSlides = slides.nextImage( );
		        // If the slides have ended
		        if( endSlides ) {
		            finishedSlides( );
		        }
			}

		}
	}
		
	
}
