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

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import android.graphics.Canvas;
import es.eucm.eadandroid.common.data.adaptation.AdaptedState;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.GUI;

/**
 * A game main loop during the normal game
 */
public class GameStatePlaying extends GameState {

    /**
     * Constructor.
     */
    public GameStatePlaying( ) {
        super( );
        
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    @Override
    public synchronized void mainLoop( long elapsedTime, int fps ) {
 

    	handleUIEvents();

        // Update the time elapsed in the functional scene and in the GUI
        game.getFunctionalScene( ).update( elapsedTime );
        GUI.getInstance( ).update( elapsedTime );

        // Get the graphics and paint the whole screen in black
        Canvas c = GUI.getInstance( ).getGraphics( );
        // TODO check, though it should give no problems with non-transparent backgrounds
        //g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );

        // Draw the functional scene, and then the GUI
        game.getFunctionalScene( ).draw( );
        
        GUI.getInstance( ).drawScene( c, elapsedTime );
        
        //GUI.getInstance( ).drawHUD( g );
        

        // If there is an adapted state to be executed
        if( game.getAdaptedStateToExecute( ) != null ) {

            // If it has an initial scene, set it
            if( game.getAdaptedStateToExecute( ).getTargetId( ) != null ) {
                // check the scene is in chapter
                if( game.getCurrentChapterData( ).getScenes( ).contains( game.getAdaptedStateToExecute( ).getTargetId( ) ) ) {
                    game.setNextScene( new Exit( game.getAdaptedStateToExecute( ).getTargetId( ) ) );
                    game.setState( Game.STATE_NEXT_SCENE );
                    game.flushEffectsQueue( );
                }
            }

            // Set the flag values
            for( String flag : game.getAdaptedStateToExecute( ).getActivatedFlags( ) )
                if( game.getFlags( ).existFlag( flag ) )
                    game.getFlags( ).activateFlag( flag );
            for( String flag : game.getAdaptedStateToExecute( ).getDeactivatedFlags( ) )
                if( game.getFlags( ).existFlag( flag ) )
                    game.getFlags( ).deactivateFlag( flag );

            // Set the vars
            List<String> adaptedVars = new ArrayList<String>( );
            List<String> adaptedValues = new ArrayList<String>( );
            game.getAdaptedStateToExecute( ).getVarsValues( adaptedVars, adaptedValues );
            for( int i = 0; i < adaptedVars.size( ); i++ ) {
                String varName = adaptedVars.get( i );
                String varValue = adaptedValues.get( i );
                // check if it is a "set value" operation
                if( AdaptedState.isSetValueOp( varValue ) ) {
                    String val = AdaptedState.getSetValueData( varValue );
                    if( val != null )
                        game.getVars( ).setVarValue( varName, Integer.parseInt( val ) );
                }
                // it is "increment" or "decrement" operation, for both of them is necessary to 
                // get the current value of referenced variable
                else {
                    if( game.getVars( ).existVar( varName ) ) {
                        int currentValue = game.getVars( ).getValue( varName );
                        if( AdaptedState.isIncrementOp( varValue ) ) {
                            game.getVars( ).setVarValue( varName, currentValue + 1 );
                        }
                        else if( AdaptedState.isDecrementOp( varValue ) ) {
                            game.getVars( ).setVarValue( varName, currentValue - 1 );
                        }
                    }
                }
            }
            adaptedVars.clear();
            adaptedValues.clear();
        }


        
        // Update the data pending from the flags
        game.updateDataPendingFromState( true );        

        // Ends the draw process
        GUI.getInstance( ).endDraw( );
  
    }

	private void handleUIEvents() {
		UIEvent e;
		Queue<UIEvent> vEvents = touchListener.getEventQueue();
		while ((e = vEvents.poll()) != null) {
			switch (e.getAction()) {
			case UIEvent.PRESSED_ACTION:
				
				game.getActionManager().setExitCustomized(null);
		        game.getActionManager( ).setElementOver( null );
				if (!GUI.getInstance().processPressed(e))
					game.getActionManager( ).pressed(e);
				break;
			case UIEvent.SCROLL_PRESSED_ACTION:

				
				game.getActionManager().setExitCustomized(null);
		        game.getActionManager( ).setElementOver( null );
				
				if (!GUI.getInstance().processScrollPressed(e)) 
					game.getActionManager( ).pressed(e);
				break;	
			case UIEvent.UNPRESSED_ACTION:

				if (!GUI.getInstance().processUnPressed(e))
					 game.getActionManager( ).unPressed(e);
				break;		
			case UIEvent.FLING_ACTION:

				GUI.getInstance().processFling(e);
				break;	
			case UIEvent.TAP_ACTION: 
				
				if (!GUI.getInstance().processTap(e)) {
					game.getActionManager().tap(e);
				}
				break;
			case UIEvent.ON_DOWN_ACTION:				
				GUI.getInstance().processOnDown(e);	
				break;
			case UIEvent.LONG_PRESSED_ACTION:				
				GUI.getInstance().processLongPress(e);		
			}

		}
	}
    
}
