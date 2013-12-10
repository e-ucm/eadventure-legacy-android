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
package es.eucm.eadandroid.ecore.control.animations;



import android.graphics.Bitmap;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * This class implements an Animation, based on an ImageSet.
 */
public class ImageAnimation extends ImageSet {

    /**
     * Time per animation frame.
     */
    private static final int TIME_PER_FRAME = 100;

    /**
     * Accumulated played time of the animation.
     */
    private long accumulatedAnimationTime;

    /**
     * Stores if the animation is has looped or not.
     */
    private boolean playingFirstTime;

    public ImageAnimation( ) {

        super( );
        accumulatedAnimationTime = 0;
        playingFirstTime = true;
    }

    /**
     * Starts this animation over from the beginning.
     */
    @Override
    public synchronized void start( ) {

        super.start( );
        accumulatedAnimationTime = 0;
        playingFirstTime = true;
    }

    /**
     * Returns if the animation is being played for first time or not.
     * 
     * @return True if the animation reached to and end and started over again,
     *         false otherwise
     */
    @Override
    public boolean isPlayingForFirstTime( ) {

        return playingFirstTime;
    }

    /**
     * Updates this animation's current image (frame) if neccesary.
     */
    @Override
    public void update( long elapsedTime ) {

        // Add the elapsed time to the accumulated
        accumulatedAnimationTime += elapsedTime;

        if( imageSet.length > 1 ) {

            // Skip frame for every TIME_PER_FRAME miliseconds
            while( accumulatedAnimationTime > TIME_PER_FRAME ) {
                accumulatedAnimationTime -= TIME_PER_FRAME;
                currentFrameIndex++;
            }

            if( currentFrameIndex >= imageSet.length ) {
                currentFrameIndex %= imageSet.length;
                playingFirstTime = false;
            }
        }
        else if( accumulatedAnimationTime > TIME_PER_FRAME ) {
            playingFirstTime = false;
        }
    }

    @Override
    protected Bitmap[] getNoAnimationAvailableImageSet( ) {

        Bitmap[] array = new Bitmap[ 1 ];
        array[0] = MultimediaManager.getInstance( ).loadImage( ResourceHandler.DEFAULT_ANIMATION, MultimediaManager.IMAGE_SCENE );
        return array;
    }
}
