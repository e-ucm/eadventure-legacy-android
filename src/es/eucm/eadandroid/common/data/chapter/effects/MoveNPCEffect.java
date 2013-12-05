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

import es.eucm.eadandroid.common.data.HasTargetId;

/**
 * An effect that makes a character to walk to a given position.
 */
public class MoveNPCEffect extends AbstractEffect implements HasTargetId {

    /**
     * Id of the npc who will walk
     */
    private String idTarget;

    /**
     * The destination of the npc
     */
    private int x;

    private int y;

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
    public MoveNPCEffect( String idTarget, int x, int y ) {

        super( );
        this.idTarget = idTarget;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getType( ) {

        return MOVE_NPC;
    }

    /**
     * Returns the id target.
     * 
     * @return Id target
     */
    public String getTargetId( ) {

        return idTarget;
    }

    /**
     * Sets the new id target.
     * 
     * @param idTarget
     *            New id target
     */
    public void setTargetId( String idTarget ) {

        this.idTarget = idTarget;
    }

    /**
     * Returns the destiny x position.
     * 
     * @return Destiny x coord
     */
    public int getX( ) {

        return x;
    }

    /**
     * Returns the destiny y position.
     * 
     * @return Destiny y coord
     */
    public int getY( ) {

        return y;
    }

    /**
     * Sets the new destiny position
     * 
     * @param x
     *            New destiny X coordinate
     * @param y
     *            New destiny Y coordinate
     */
    public void setDestiny( int x, int y ) {

        this.x = x;
        this.y = y;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        MoveNPCEffect npe = (MoveNPCEffect) super.clone( );
        npe.idTarget = ( idTarget != null ? new String( idTarget ) : null );
        npe.x = x;
        npe.y = y;
        return npe;
    }
}
