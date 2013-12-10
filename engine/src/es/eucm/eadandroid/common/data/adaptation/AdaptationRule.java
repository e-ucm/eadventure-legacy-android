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
package es.eucm.eadandroid.common.data.adaptation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.eucm.eadandroid.common.data.Described;

/**
 * 
 */
public class AdaptationRule implements Cloneable, Described, ContainsAdaptedState {

    //ID
    private String id;

    // GameState
    private AdaptedState gameState;

    /**
     * List of properties to be set
     */
    private List<UOLProperty> uolState;

    //Description
    private String description;

    public AdaptationRule( ) {

        uolState = new ArrayList<UOLProperty>( );
        gameState = new AdaptedState( );
    }

    /**
     * Adds a new assessment property
     * 
     * @param property
     *            Assessment property to be added
     */
    public void addUOLProperty( UOLProperty property ) {

        uolState.add( property );
    }

    /**
     * Adds a new UOL property
     * 
     * @param id
     * @param value
     * @param op
     *            Operation of comparison between the value of var id in LMS and
     *            value
     */
    public void addUOLProperty( String id, String value, String op ) {

        addUOLProperty( new UOLProperty( id, value, op ) );
    }

    public List<UOLProperty> getUOLProperties( ) {

        return uolState;
    }

    public void setInitialScene( String initialScene ) {

        gameState.setTargetId( initialScene );

    }

    public void addActivatedFlag( String flag ) {

        gameState.addActivatedFlag( flag );

    }

    public void addDeactivatedFlag( String flag ) {

        gameState.addDeactivatedFlag( flag );

    }

    public void addVarValue( String var, String value ) {

        gameState.addVarValue( var, value );
    }

    public AdaptedState getAdaptedState( ) {

        return gameState;
    }

    /**
     * @return the description
     */
    public String getDescription( ) {

        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription( String description ) {

        this.description = description;
    }

    public String getId( ) {

        return id;
    }

    public void setId( String generateId ) {

        this.id = generateId;

    }

    public Set<String> getPropertyNames( ) {

        Set<String> names = new HashSet<String>( );
        for( UOLProperty property : uolState ) {
            names.add( property.getId( ) );
        }
        return names;
    }

    /**
     * Return the value of the property with specific key
     * 
     * @param key
     * @return
     */
    public String getPropertyValue( String key ) {

        for( UOLProperty property : uolState ) {
            if( property.getId( ).equals( key ) ) {
                return property.getValue( );
            }
        }
        return new String( "" );

    }

    /**
     * Return the operation of the property with specific key
     * 
     * @param key
     * @return
     */
    public String getPropertyOp( String key ) {

        for( UOLProperty property : uolState ) {
            if( property.getId( ).equals( key ) ) {
                return property.getOperation( );
            }
        }
        return new String( "" );
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AdaptationRule ar = (AdaptationRule) super.clone( );
        ar.description = ( description != null ? new String( description ) : null );
        ar.gameState = (AdaptedState) gameState.clone( );
        ar.id = ( id != null ? new String( id ) : null );
        ar.uolState = new ArrayList<UOLProperty>( );
        for( UOLProperty uolp : uolState ) {
            ar.uolState.add( (UOLProperty) uolp.clone( ) );
        }
        return ar;
    }

    public void setAdaptedState( AdaptedState state ) {

        this.gameState = state;
    }
}
