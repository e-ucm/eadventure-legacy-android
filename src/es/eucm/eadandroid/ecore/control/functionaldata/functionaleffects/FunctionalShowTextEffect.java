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


import java.util.Timer;
import java.util.TimerTask;

import es.eucm.eadandroid.common.data.chapter.effects.ShowTextEffect;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.Options;
import es.eucm.eadandroid.ecore.gui.GUI;

/**
 * An effect that shows the given text in current scene.
 */
public class FunctionalShowTextEffect extends FunctionalEffect {

    /**
     * The text that will be shown
     */
    private String[] text;

    /**
     * The time that text will be shown
     */
    private int timeShown;

    /**
     * Boolean to control is effect is running
     */
    private boolean isStillRunning;

    /**
     * The timer which controls the time that text is shown
     */
    private Timer timer;
    
    private TimerTask task;
    
    /**
     * Value to control when the user skip the text
     */
    private boolean skipByUser;

    /**
     * Constructor
     * 
     * @param effect
     */
    FunctionalShowTextEffect( ShowTextEffect effect ) {

        super( effect );
        // split the text if don't fit in the screen
        this.text = GUI.getInstance( ).splitText( effect.getText( ) );

        // obtain the text speed
        float multiplier = 1;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_SLOW )
            multiplier = 1.5f;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_FAST )
            multiplier = 0.8f;

        // calculate the time that text will be shown
        timeShown = (int) ( 300 * effect.getText( ).split( " " ).length * multiplier );
        if( timeShown < (int) ( 1400 * multiplier ) )
            timeShown = (int) ( 1400 * multiplier );

        this.isStillRunning = false;
        this.skipByUser = false;
    }

    @Override
    public boolean isInstantaneous( ) {

        return false;
    }

    @Override
    public boolean isStillRunning( ) {

        return isStillRunning;
    }

    @Override
    public void triggerEffect( ) {

        // FIXME: Convendría cambiar esto para que no se use un timer
    	
    	timer = new Timer();
    	
    	task = new TimerTask() {

			@Override
			public void run() {			
                finish();				
			}
    		
    	};
    	
    	timer.schedule(task, timeShown * 2);    	

        isStillRunning = true;

    }

    public void draw( ) {

        GUI.getInstance( ).addTextToDraw( text, ( (ShowTextEffect) effect ).getX( ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), ( (ShowTextEffect) effect ).getY( ), ( (ShowTextEffect) effect ).getRgbFrontColor( ) , ((ShowTextEffect) effect ).getRgbBorderColor( ) );

    }
    
    private void finish() {
    	
        if (!Game.getInstance( ).getGameDescriptor( ).isKeepShowing( ) || skipByUser){
        	isStillRunning = false;
        	timer.cancel();
        }
        
        else triggerEffect();
        
    }
    
    @Override
    public boolean canSkip( ) {
        return true;
    }

    @Override
    public void skip( ) {
       skipByUser = true;
       finish();
    }
}
