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

import android.hardware.SensorEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.KeyPadListener;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.TouchListener;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.TrackBallListener;

/**
 * A state of the game main loop
 */
public abstract class GameState {

	/**
	 * Instance of game
	 */
	 protected Game game;


	protected TouchListener touchListener;
	protected KeyPadListener keyListener;
	protected TrackBallListener tballListener;

	/**
	 * Creates a new GameState
	 */
	public GameState() {


		 this.game = Game.getInstance();
	}

	/**
	 * Perform an iteration of the game main loop
	 * 
	 * @param elapsedTime
	 *            the elapsed time from the last iteration
	 * @param fps
	 *            current frames per second
	 */
	public abstract void mainLoop(long elapsedTime, int fps);

	/**
	 * Called to process key events.
	 * 
	 * @param event
	 * @return
	 */
	public boolean processKeyEvent(KeyEvent event) {

		if (keyListener != null)
			return keyListener.processKeyEvent(event);
		else
			return false;
	}

	/**
	 * Called to process touch screen events.
	 * 
	 * @param event
	 * @return
	 */
	public boolean processTouchEvent(MotionEvent event) {
		if (touchListener != null)
			return touchListener.processTouchEvent(event);
		return false;
	}

	/**
	 * Called to process trackball events.
	 * 
	 * @param event
	 * @return
	 */
	public boolean processTrackballEvent(MotionEvent event) {
		if (tballListener != null)
			return tballListener.processTrackballEvent(event);
		else
			return false;
	}

	public boolean processSensorEvent(SensorEvent e) {

		return false;

	}

	public void registerTouchListener(TouchListener t) {
		this.touchListener = t;
		
	}
	

}
