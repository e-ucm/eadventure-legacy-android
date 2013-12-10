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
package es.eucm.eadandroid.common.data.chapter.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of effects that can be triggered by an unique player's action during
 * the game.
 */
public class Effects implements Cloneable {

    /**
     * List of effects to be triggered
     */
    private List<AbstractEffect> effects;

    /**
     * Creates a new list of Effects.
     */
    public Effects( ) {

        effects = new ArrayList<AbstractEffect>( );
    }

    /**
     * Returns whether the effects block is empty or not.
     * 
     * @return True if the block has no effects, false otherwise
     */
    public boolean isEmpty( ) {

        return effects.isEmpty( );
    }

    /**
     * Clear the list of effects.
     */
    public void clear( ) {

        effects.clear( );
    }

    /**
     * Adds a new effect to the list.
     * 
     * @param effect
     *            the effect to be added
     */
    public void add( AbstractEffect effect ) {

        effects.add( effect );
    }

    /**
     * Returns the contained list of effects
     * 
     * @return List of effects
     */
    public List<AbstractEffect> getEffects( ) {

        return effects;
    }

    /**
     * Checks if there is any cancel action effect in the list
     */
    public boolean hasCancelAction( ) {

        boolean hasCancelAction = false;
        for( Effect effect : effects ) {
            if( effect.getType( ) == Effect.CANCEL_ACTION ) {
                hasCancelAction = true;
                break;
            }
        }
        return hasCancelAction;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Effects e = (Effects) super.clone( );
        if( effects != null ) {
            e.effects = new ArrayList<AbstractEffect>( );
            for( Effect ef : effects )
                e.effects.add( (AbstractEffect) ef.clone( ) );
        }
        return e;
    }

}
