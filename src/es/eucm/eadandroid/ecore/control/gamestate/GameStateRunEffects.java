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


import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffect;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalMoveObjectEffect;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalPlayAnimationEffect;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalShowTextEffect;
import es.eucm.eadandroid.ecore.gui.GUI;

/**
 * A game main loop while the effects of an action are being performed
 */
public class GameStateRunEffects extends GameState {

    /**
     * The current effect being executed
     */
    private FunctionalEffect currentExecutingEffect;

    /**
     * Distinguish when the State run effects are called from a conversation
     */
    private boolean fromConversation;

    /**
     * Skip effects bool
     */
    private boolean skip = false ;

    /**
     * Constructor
     */
    public GameStateRunEffects( boolean fromConversation ) {

        super( );
        currentExecutingEffect = null;
        this.fromConversation = fromConversation;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    @Override
    public void mainLoop( long elapsedTime, int fps ) {

        game.getActionManager( ).setElementOver( null );

        game.getActionManager( ).setExitCustomized( null);
        
        // Toggle the HUD off and set the default cursor
   //     GUI.getInstance( ).toggleHud( false );
 //       GUI.getInstance( ).setDefaultCursor( );

        if( game.getFunctionalScene( ) != null )
            game.getFunctionalScene( ).update( elapsedTime );
        GUI.getInstance( ).update( elapsedTime );

        Canvas g = GUI.getInstance( ).getGraphics( );
        
        g.clipRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );

        // Draw the scene
        if( game.getFunctionalScene( ) != null )
            game.getFunctionalScene( ).draw( );

        // If is show text effect, call to its draw method
        if( currentExecutingEffect instanceof FunctionalShowTextEffect )
            ( (FunctionalShowTextEffect) currentExecutingEffect ).draw( );
        GUI.getInstance( ).drawScene( g, elapsedTime );

      //  GUI.getInstance( ).drawHUD( g );

        // Draw the FPS
        //g.setColor( Color.WHITE );
        //g.drawString( Integer.toString( fps ), 780, 14 );

        // If no effect is being executed, or it has stopped running
        if( currentExecutingEffect == null || !currentExecutingEffect.isStillRunning( ) ) {

            // Delete the current effect
            currentExecutingEffect = null;

            /*
            // If no more effects must be executed, switch the state
            if( game.isEmptyFIFOinStack() ) {
                System.gc( );
                GUI.getInstance().toggleHud( true );
                // Look if there are some stored state, and change to correct one.               
                game.setAndPopState( );
                               
            } */

            boolean stop = false;
            // Execute effects while are instantaneous

            while( !stop /*&& !game.isEmptyFIFOinStack()*/) {
                FunctionalEffect currentEffect = game.getFirstElementOfTop( );
                if( currentEffect == null ) {
                    System.gc( );
           //         GUI.getInstance( ).toggleHud( true );
                    stop = true;
                    // Look if there are some stored conversation state, and change to correct one.               
                    game.evaluateState( );

                }
                else {
                    // Check if all conditions associated to effect are OK
                    if( currentEffect.isAllConditionsOK( ) ) {
                        currentEffect.triggerEffect( );
                        stop = !currentEffect.isInstantaneous( );
                        if( stop )
                            currentExecutingEffect = currentEffect;
                    }
                }
            }
        }

        // Special conditions for the play animation effect
        // FIXME Edu: ¿Mover esto de aqui?
        else if( currentExecutingEffect != null && currentExecutingEffect.isStillRunning( ) ) {
            // I've modified this (JAvier): I've replaced mouseClickedButton==MouseEvent.BUTTON3 
            // by ( mouseClickedButton == MouseEvent.BUTTON1
            //      || mouseClickedButton == MouseEvent.BUTTON3 )
            // Therefore you can skip effects with left button
            if ( skip && currentExecutingEffect.canSkip())  {
                Log.d("SKIPING" , "!!!!! SKIPPING :"+currentExecutingEffect.getClass( ).getName( ) );
                currentExecutingEffect.skip();
                
            }

            if( currentExecutingEffect instanceof FunctionalPlayAnimationEffect ) {
                ( (FunctionalPlayAnimationEffect) currentExecutingEffect ).draw( g );
                ( (FunctionalPlayAnimationEffect) currentExecutingEffect ).update( elapsedTime );
            }
            if (currentExecutingEffect instanceof FunctionalMoveObjectEffect) {
                ((FunctionalMoveObjectEffect) currentExecutingEffect).update(elapsedTime);
            }
        }
        
        skip = false;

        GUI.getInstance( ).endDraw( );

    }
    
	/**
	 * Called to process touch screen events.
	 * 
	 * @param event
	 * @return
	 */
    @Override
	public boolean processTouchEvent(MotionEvent event) {

    	skip = (event.getAction() == MotionEvent.ACTION_UP);
    	
    	return true;	
    	
	}
}
