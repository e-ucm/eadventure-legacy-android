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
package es.eucm.eadandroid.ecore.control.functionaldata.functionalactions;

import es.eucm.eadandroid.ecore.control.ActionManager;
import es.eucm.eadandroid.ecore.control.DebugLog;
import es.eucm.eadandroid.ecore.control.animations.AnimationState;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;

/**
 * The action to look at an element
 * 
 * @author Eugenio Marchiori
 * 
 */
public class FunctionalLook extends FunctionalAction {

    /**
     * The element to look at
     */
    private FunctionalElement element;

    /**
     * Default constructor, with the element to look at
     * 
     * @param element
     *            The element to look at
     */
    public FunctionalLook( FunctionalElement element ) {

        super( null );
        type = ActionManager.ACTION_LOOK;
        this.element = element;
    }

    @Override
    public void drawAditionalElements( ) {

    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {

        this.functionalPlayer = functionalPlayer;
        if( element.isInInventory( ) ) {
            functionalPlayer.setDirection( AnimationState.SOUTH );
        }
        else {
            if( element.getX( ) < functionalPlayer.getX( ) )
                functionalPlayer.setDirection( AnimationState.WEST );
            else
                functionalPlayer.setDirection( AnimationState.EAST );
        }
        finished = true;
        if( functionalPlayer.isAlwaysSynthesizer( ) )
            functionalPlayer.speakWithFreeTTS( element.getElement( ).getDescription( ), functionalPlayer.getPlayerVoice( ) );
        else
            functionalPlayer.speak( element.getElement( ).getDescription( ) );

        DebugLog.player( "Look: " + element.getElement( ).getId( ) + " desc: " + element.getElement( ).getDescription( ) );
    }

    @Override
    public void stop( ) {

        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {

    }

}
