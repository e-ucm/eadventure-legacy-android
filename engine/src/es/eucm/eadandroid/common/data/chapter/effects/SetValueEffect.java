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
 * An effect that sets a var with a given value
 */
public class SetValueEffect extends AbstractEffect implements HasTargetId {

    /**
     * Name of the var to be activated
     */
    private String idVar;

    /**
     * Value to be set
     */
    private int value;

    /**
     * Creates a new Activate effect.
     * 
     * @param idVar
     *            the id of the var to be activated
     */
    public SetValueEffect( String idVar, int value ) {

        super( );
        this.idVar = idVar;
        this.value = value;
    }

    @Override
    public int getType( ) {

        return SET_VALUE;
    }

    /**
     * Returns the idVar
     * 
     * @return String containing the idVar
     */
    public String getTargetId( ) {

        return idVar;
    }

    /**
     * Sets the new idVar
     * 
     * @param idVar
     *            New idVar
     */
    public void setTargetId( String idVar ) {

        this.idVar = idVar;
    }

    /**
     * @return the value
     */
    public int getValue( ) {

        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue( int value ) {

        this.value = value;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        SetValueEffect sve = (SetValueEffect) super.clone( );
        sve.idVar = ( idVar != null ? new String( idVar ) : null );
        sve.value = value;
        return sve;
    }
}
