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
package es.eucm.eadandroid.common.data.chapter;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadandroid.common.data.Named;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;

/**
 * An customizable action that can be done during the game
 * 
 * @author Eugenio Marchiori
 * 
 */
public class CustomAction extends Action implements Named {

    /**
     * Name of the action
     */
    private String name;

    /**
     * Resources used by the action (such as icons for the button)
     */
    private List<Resources> resources;

    /**
     * Default constructor for actions that only need one object
     * 
     * @param type
     *            The type of the custom action
     */
    public CustomAction( int type ) {

        super( type );
        resources = new ArrayList<Resources>( );
    }

    /**
     * Constructor with id, for actions that need two objects
     * 
     * @param type
     *            the type of the custom action
     * @param idTarget
     *            the id of the other object
     */
    public CustomAction( int type, String idTarget ) {

        super( type, idTarget );
        resources = new ArrayList<Resources>( );
    }

    /**
     * Constructor with conditions and effects, for actions that only need one
     * object
     * 
     * @param type
     *            the type of the action
     * @param conditions
     *            the conditions of the action
     * @param effects
     *            the effects of the action
     * @param notEffects
     *            The effects of the action when the conditions aren't OK (must
     *            not be null)
     */
    public CustomAction( int type, Conditions conditions, Effects effects, Effects notEffects ) {

        super( type, conditions, effects, notEffects );
        resources = new ArrayList<Resources>( );
    }

    /**
     * Constructor with conditions and effects, for actions that need two
     * objects
     * 
     * @param type
     *            the type of the action
     * @param idTarget
     *            the id of the other object
     * @param conditions
     *            the conditions of the action
     * @param effects
     *            the effects of the action
     * @param notEffects
     *            The effects of the action when the conditions aren't OK (must
     *            not be null)
     */
    public CustomAction( int type, String idTarget, Conditions conditions, Effects effects, Effects notEffects ) {

        super( type, idTarget, conditions, effects, notEffects );
        resources = new ArrayList<Resources>( );
    }

    /**
     * Constructor that uses a default action
     * 
     * @param action
     *            a normal action
     */
    public CustomAction( Action action ) {

        super( action.getType( ), action.getTargetId( ), action.getConditions( ), action.getEffects( ), action.getNotEffects( ) );
        resources = new ArrayList<Resources>( );
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName( String name ) {

        this.name = name;
    }

    /**
     * @return the name value
     */
    public String getName( ) {

        return name;
    }

    /**
     * @param resources
     *            the resources to add
     */
    public void addResources( Resources resources ) {

        this.resources.add( resources );
    }

    /**
     * @return the list of resources
     */
    public List<Resources> getResources( ) {

        return resources;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        CustomAction ca = (CustomAction) super.clone( );
        ca.name = ( name != null ? new String( name ) : null );
        if( resources != null ) {
            ca.resources = new ArrayList<Resources>( );
            for( Resources r : resources )
                ca.resources.add( (Resources) r.clone( ) );
        }
        return ca;
    }

}
