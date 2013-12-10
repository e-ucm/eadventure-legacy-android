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
package es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects;

import android.graphics.Canvas;
import es.eucm.eadandroid.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.animations.Animation;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;

/**
 * An effect that plays a sound
 */
public class FunctionalPlayAnimationEffect extends FunctionalEffect {

    private Animation animation;

    /**
     * Creates a new PlaySoundEffect
     * 
     * @param background
     *            whether to play the sound in background
     * @param path
     *            path to the sound file
     */
    public FunctionalPlayAnimationEffect( PlayAnimationEffect effect ) {

        super( effect );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {

        animation = MultimediaManager.getInstance( ).loadAnimation( ( (PlayAnimationEffect) effect ).getPath( ), false, MultimediaManager.IMAGE_SCENE );
        animation.start( );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    @Override
    public boolean isInstantaneous( ) {

        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.ecore.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    @Override
    public boolean isStillRunning( ) {

        return animation.isPlayingForFirstTime( );
    }

    public void update( long elapsedTime ) {

        animation.update( elapsedTime );
    }

    public void draw( Canvas g) {

        GUI.getInstance( ).addElementToDraw( animation.getImage( ), Math.round( ( (PlayAnimationEffect) effect ).getX( ) - ( animation.getImage( ).getWidth() / 2 ) ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), Math.round( ( (PlayAnimationEffect) effect ).getY( ) - ( animation.getImage( ).getHeight() / 2 ) ), Math.round( ( (PlayAnimationEffect) effect ).getY( ) ), Math.round( ( (PlayAnimationEffect) effect ).getY( ) ), null, null );
    }

}
