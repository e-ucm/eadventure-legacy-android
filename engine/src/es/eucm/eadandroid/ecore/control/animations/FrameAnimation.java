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

/**
 * Class that represents an animation made up of frames in the engine. It uses
 * the logic in {@link es.eucm.eadventure.common.data.animation.Animation}.
 * 
 * 
 * @author Eugenio Marchiori
 */
public class FrameAnimation implements Animation {

    /**
     * The animation with the frames and the logic
     */
    private es.eucm.eadandroid.common.data.animation.Animation animation;

    /**
     * The time accumulated in the playing of the animation
     */
    private long accumulatedTime;

    /**
     * Create a new instance using an animation.
     * 
     * @param animation
     *            The animation with the frames and the logic
     */
    public FrameAnimation( es.eucm.eadandroid.common.data.animation.Animation animation ) {

        this.animation = animation;
        accumulatedTime = 0;
    }

    public Bitmap getImage( ) {

        String sound = animation.getNewSound( );
        if( sound != null && sound != "" ) {
            long soundID = MultimediaManager.getInstance( ).loadSound( sound, false );
            MultimediaManager.getInstance( ).startPlaying( soundID );
        }
        
        return animation.getImage( accumulatedTime, es.eucm.eadandroid.common.data.animation.Animation.ENGINE );
    }

    public boolean isPlayingForFirstTime( ) {

        return !animation.finishedFirstTime( accumulatedTime );
    }

    public void start( ) {

        accumulatedTime = 0;
    }

    public void update( long elapsedTime ) {

        accumulatedTime += elapsedTime;
    }

    public boolean nextImage( ) {

        boolean noMoreFrames = false;

        accumulatedTime = animation.skipFrame( accumulatedTime );

        if( accumulatedTime >= animation.getTotalTime( ) ) {
            accumulatedTime %= animation.getTotalTime( );
            noMoreFrames = true;
        }

        return noMoreFrames;
    }

    public void setAnimation( es.eucm.eadandroid.common.data.animation.Animation animation ) {

        this.animation = animation;
    }

    public void setMirror( boolean mirror ) {

        animation.setMirror( mirror );
    }

    public void setFullscreen( boolean b ) {

        animation.setFullscreen( b );
    }
}
