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
import java.util.List;

import es.eucm.eadandroid.common.data.HasTargetId;

/**
 * Stores the adaptation data, which includes the flag activation and
 * deactivation values, along with the initial scene of the game
 */
public class AdaptedState implements Cloneable, HasTargetId {

    /**
     * Id of the initial scene
     */
    private String initialScene;

    /**
     * Flags values
     */
    public static final String ACTIVATE = "activate";

    public static final String DEACTIVATE = "deactivate";

    /**
     * Vars values
     */
    public static final String INCREMENT = "increment";

    public static final String DECREMENT = "decrement";

    public static final String VALUE = "set-value";

    /**
     * List of all flags and vars (in order)
     */
    private List<String> allFlagsVars;

    /**
     * List of deactivate/activate for flags (and value for vars)
     */
    private List<String> actionsValues;

    /**
     * Constructor
     */
    public AdaptedState( ) {

        initialScene = null;
        allFlagsVars = new ArrayList<String>( );
        actionsValues = new ArrayList<String>( );

    }

    /**
     * Returns the id of the initial scene
     * 
     * @return Id of the initial scene, null if none
     */
    public String getTargetId( ) {

        return initialScene;
    }

    /**
     * Returns the list of flags and vars
     * 
     * @return List of the deactivated flags
     */
    public List<String> getFlagsVars( ) {

        return allFlagsVars;
    }

    /**
     * Sets the initial scene id
     * 
     * @param initialScene
     *            Id of the initial scene
     */
    public void setTargetId( String initialScene ) {

        this.initialScene = initialScene;
    }

    /**
     * Adds a new flag to be activated
     * 
     * @param flag
     *            Name of the flag
     */
    public void addActivatedFlag( String flag ) {

        allFlagsVars.add( flag );
        actionsValues.add( ACTIVATE );

    }

    /**
     * Adds a new flag to be deactivated
     * 
     * @param flag
     *            Name of the flag
     */
    public void addDeactivatedFlag( String flag ) {

        allFlagsVars.add( flag );
        actionsValues.add( DEACTIVATE );

    }

    /**
     * Adds a new var
     * 
     * @param var
     * @param value
     */
    public void addVarValue( String var, String value ) {

        allFlagsVars.add( var );
        actionsValues.add( value );
    }

    public void removeFlagVar( int row ) {

        allFlagsVars.remove( row );
        actionsValues.remove( row );
    }

    public void changeFlag( int row, String flag ) {

        int nFlags = actionsValues.size( );
        allFlagsVars.remove( row );
        if( row < nFlags - 1 )
            allFlagsVars.add( row, flag );
        else
            allFlagsVars.add( flag );

    }

    public void changeAction( int row ) {

        int nFlags = actionsValues.size( );
        if( actionsValues.get( row ).equals( ACTIVATE ) ) {
            actionsValues.remove( row );

            if( row < nFlags - 1 )
                actionsValues.add( row, DEACTIVATE );
            else
                actionsValues.add( DEACTIVATE );
        }

        else if( actionsValues.get( row ).equals( DEACTIVATE ) ) {
            actionsValues.remove( row );

            if( row < nFlags - 1 )
                actionsValues.add( row, ACTIVATE );
            else
                actionsValues.add( ACTIVATE );
        }

    }

    /**
     * Change the type of "index" position. If in that position there was a
     * flag, change it to var and vice versa. It change and set the new flag or
     * var to default value.
     * 
     * @param index
     *            the position where the change will take place
     * @param name
     *            the name of the new flag or var
     */
    public void change( int index, String name ) {

        if( isFlag( index ) ) {
            actionsValues.set( index, INCREMENT + " 0" );
            allFlagsVars.set( index, name );
        }
        else {
            actionsValues.set( index, ACTIVATE );
            allFlagsVars.set( index, name );
        }
    }

    public void changeAction( int row, String newValue ) {

        if( row >= 0 && row <= actionsValues.size( ) ) {
            actionsValues.remove( row );
            actionsValues.add( row, newValue );
        }
    }

    public String getAction( int i ) {

        return actionsValues.get( i );
    }

    public String getValueForVar( int i ) {

        String val = actionsValues.get( i );
        //if (val.contains(VALUE)){
        return Integer.toString( getValueToSet( i ) );
        //}else {
        //  return null;
        //}
    }

    /**
     * Returns the value for "VALUE" action (the value which will be set to
     * associated var)
     * 
     * @param index
     * @return
     */
    public int getValueToSet( int index ) {

        String val = actionsValues.get( index );
        int subIndex = val.indexOf( " " );
        if( subIndex != -1 ) {
            val = val.substring( subIndex + 1 );
            return Integer.parseInt( val );
        }
        else {
            return Integer.MIN_VALUE;
        }

    }

    public int getValue( int i ) {

        return Integer.parseInt( actionsValues.get( i ) );
    }

    public String getFlagVar( int i ) {

        return allFlagsVars.get( i );
    }

    public boolean isEmpty( ) {

        return allFlagsVars.size( ) == 0 && ( initialScene == null || initialScene.equals( "" ) );
    }

    /**
     * Returns the list of the activated flags
     * 
     * @return List of the activated flags
     */
    public List<String> getActivatedFlags( ) {

        List<String> activatedFlags = new ArrayList<String>( );
        for( int i = 0; i < actionsValues.size( ); i++ ) {
            if( actionsValues.get( i ).equals( ACTIVATE ) ) {
                activatedFlags.add( allFlagsVars.get( i ) );
            }
        }
        return activatedFlags;
    }

    /**
     * Returns the list of the deactivated flags
     * 
     * @return List of the deactivated flags
     */
    public List<String> getDeactivatedFlags( ) {

        List<String> deactivatedFlags = new ArrayList<String>( );
        for( int i = 0; i < actionsValues.size( ); i++ ) {
            if( actionsValues.get( i ).equals( DEACTIVATE ) ) {
                deactivatedFlags.add( allFlagsVars.get( i ) );
            }
        }
        return deactivatedFlags;
    }

    /**
     * Fills the argumented structures with the names of the vars and the values
     * they must be set with
     * 
     * @param vars
     * @param values
     */
    public void getVarsValues( List<String> vars, List<String> values ) {

        for( int i = 0; i < actionsValues.size( ); i++ ) {
            if( !actionsValues.get( i ).equals( ACTIVATE ) && !actionsValues.get( i ).equals( DEACTIVATE ) ) {
                vars.add( allFlagsVars.get( i ) );
                values.add( actionsValues.get( i ) );
            }
        }
    }

    /**
     * Joins two Adapted States. The new resulting adapted state has a merge of
     * active/inactive flags of both states, and the initial scene will be set
     * as the initial scene of the parameter state. With the vars, its do the
     * same action. The new flags/vars will be add at the end of the data
     * structure;
     * 
     * @param AdaptedState
     *            mergeState The state which will be merged with the current
     *            object
     * 
     */
    public void merge( AdaptedState mergeState ) {

        if( mergeState.initialScene != null )
            this.initialScene = mergeState.initialScene;
        if( this.allFlagsVars.size( ) == 0 ) {
            this.allFlagsVars = mergeState.allFlagsVars;
            this.actionsValues = mergeState.actionsValues;
        }
        else {
            for( int i = 0; i < mergeState.allFlagsVars.size( ); i++ ) {
                this.allFlagsVars.add( mergeState.allFlagsVars.get( i ) );
                this.actionsValues.add( mergeState.allFlagsVars.get( i ) );
            }
        }
    }

    /**
     * Check if the value in the given position is flag or variable.
     * 
     * @param index
     *            the position in allFlagsVar
     * @return true if the value in the given position has "flag" value.
     */
    public boolean isFlag( int index ) {

        String value = actionsValues.get( index );
        if( value.equals( ACTIVATE ) || value.equals( DEACTIVATE ) )
            return true;
        else
            return false;
    }

    /**
     * Check if the value with the given name is flag or variable.
     * 
     * @param name
     *            the name of the var or flag
     * @return true, if name is flag
     */
    public boolean isFlag( String name ) {

        boolean isFlag = false;
        for( int i = 0; i < allFlagsVars.size( ); i++ ) {
            if( allFlagsVars.get( i ).equals( name ) ) {
                if( isFlag( i ) )
                    return true;
                else
                    return false;
            }
        }
        return isFlag;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AdaptedState as = (AdaptedState) super.clone( );
        as.actionsValues = new ArrayList<String>( );
        for( String s : actionsValues )
            as.actionsValues.add( ( s != null ? new String( s ) : null ) );
        as.allFlagsVars = new ArrayList<String>( );
        for( String s : allFlagsVars )
            as.allFlagsVars.add( ( s != null ? new String( s ) : null ) );
        as.initialScene = ( initialScene != null ? new String( initialScene ) : null );
        return as;
    }

    /**
     * @return the actionsValues
     */
    public List<String> getActionsValues( ) {

        return actionsValues;
    }

    /**
     * Check if the given String is a "set-value" operation
     * 
     * @param op
     * @return
     */
    public static boolean isSetValueOp( String op ) {

        return op.contains( VALUE );
    }

    /**
     * Check if the given String is a "increment" operation
     * 
     * @param op
     * @return
     */
    public static boolean isIncrementOp( String op ) {

        return op.contains( INCREMENT );
    }

    /**
     * Check if the given String is a "decrement" operation
     * 
     * @param op
     * @return
     */
    public static boolean isDecrementOp( String op ) {

        return op.contains( DECREMENT );
    }

    /**
     * Take the numeric value from a "action value" that will be set as var
     * value
     * 
     * @param value
     * @return
     */
    public static String getSetValueData( String value ) {

        int subIndex = value.indexOf( " " );
        if( subIndex != -1 ) {
            value = value.substring( subIndex + 1 );
            return value;
        }
        else {
            return null;
        }
    }

}
