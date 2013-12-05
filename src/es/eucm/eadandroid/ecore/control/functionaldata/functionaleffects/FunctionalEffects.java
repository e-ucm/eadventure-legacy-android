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

import java.util.ArrayList;

import es.eucm.eadandroid.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;
import es.eucm.eadandroid.ecore.control.Game;

/**
 * A list of effects that can be triggered by an unique player's action during
 * the game.
 */
public class FunctionalEffects {

    /**
     * Stores if the effect cancel the normal course of the action
     */
    private boolean hasCancelAction;

    /**
     * List of effects to be triggered
     */
    private ArrayList<FunctionalEffect> functionalEffects;

    /**
     * Creates a new, empty list of FunctionalEffects.
     */
    public FunctionalEffects( ) {

        functionalEffects = new ArrayList<FunctionalEffect>( );
        hasCancelAction = false;
    }

    /**
     * Creates a new, empty list of FunctionalEffects.
     */
    public FunctionalEffects( Effects effects ) {

        this( );
        // Add a new functional effect to the list for each effect in effects structure
        for( AbstractEffect effect : effects.getEffects( ) ) {
            FunctionalEffect fe = FunctionalEffect.buildFunctionalEffect( effect );
            if( fe != null )
                functionalEffects.add( fe );
        }
        // If the effects structure has cancel action, add it
        hasCancelAction = effects.hasCancelAction( );
    }

    /**
     * Return the effect in the given position.
     * 
     * @param index
     *            the effect position
     * @return the effect in the given position
     */
    public FunctionalEffect getEffect( int index ) {

        return functionalEffects.get( index );
    }

    /**
     * Sets whether the list of effects has a cancel action.
     * 
     * @param hasCancelAction
     *            true if the list of effects has a cancel action, false
     *            otherwise
     */
    public void setHasCancelAction( boolean hasCancelAction ) {

        this.hasCancelAction = hasCancelAction;
    }

    /**
     * Returns whether the list of effects has a cancel action.
     * 
     * @return true if the list of effects has a cancel action, false otherwise
     */
    public boolean hasCancelAction( ) {

        return hasCancelAction;
    }

    /**
     * Queues the effects in the game effects queue to be done when possible.
     */
    public static void storeAllEffects( Effects effects ) {

    	synchronized(FunctionalEffects.class) {
    	Game.getInstance( ).storeEffectsInQueue( new FunctionalEffects( effects ).getEffects( ), false );
    	}
    	}

    public static void storeAllEffects( Effects effects, boolean fromConversation ) {

        Game.getInstance( ).storeEffectsInQueue( new FunctionalEffects( effects ).getEffects( ), fromConversation );
    }

    public static void storeAllEffects( FunctionalEffects functionalEffects ) {

        Game.getInstance( ).storeEffectsInQueue( functionalEffects.getEffects( ), false );
    }

    /**
     * @return the functionalEffects
     */
    public ArrayList<FunctionalEffect> getEffects( ) {

        return functionalEffects;
    }

    /**
     * Add new functional effect
     * 
     * @param functionalEffect
     */
    public void addEffect( FunctionalEffect functionalEffect ) {

        functionalEffects.add( functionalEffect );
    }

}
