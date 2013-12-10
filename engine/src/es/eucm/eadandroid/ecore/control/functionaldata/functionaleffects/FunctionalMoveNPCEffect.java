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

import es.eucm.eadandroid.common.data.chapter.effects.MoveNPCEffect;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalNPC;

/**
 * An effect that makes a character to walk to a given position.
 */
public class FunctionalMoveNPCEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalMoveNPCEffect.
     * 
     * @param idTarget
     *            the id of the character who will walk
     * @param x
     *            X final position for the NPC
     * @param y
     *            Y final position for the NPC
     */
    public FunctionalMoveNPCEffect( MoveNPCEffect effect ) {

        super( effect );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {

        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( ( (MoveNPCEffect) effect ).getTargetId( ) );
        if( npc != null ) {
            npc.setDestiny( ( (MoveNPCEffect) effect ).getX( ), ( (MoveNPCEffect) effect ).getY( ) );
            npc.setState( FunctionalNPC.WALK );
        }
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
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    @Override
    public boolean isStillRunning( ) {

        boolean stillRunning = false;

        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( ( (MoveNPCEffect) effect ).getTargetId( ) );
        if( npc != null ) {
            stillRunning = npc.isWalking( );
            // stillRunning = !(((MoveNPCEffect)effect).getX()==npc.getX()&&((MoveNPCEffect)effect).getY()==npc.getY());

        }
        return stillRunning;
    }

}
