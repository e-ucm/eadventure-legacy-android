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

/**
 * Stores an adaptation profile. Each profile contains the path of the xml file
 * where it is stored (relative to the zip file), along with the list of
 * adaptation rules and initial state defined in the profile
 * 
 * @author Javier
 * 
 */
public class AdaptationProfile implements Cloneable, ContainsAdaptedState {

    /**
     * Constants to identify the comparison operations
     */
    public static final String EQUALS = "eq";

    public static final String GRATER = "gt";

    public static final String LESS = "lt";

    public static final String GRATER_EQ = "ge";

    public static final String LESS_EQ = "le";

    /**
     * The Name of the adaptation profile
     */
    // Also is the path of the assessment profile for old game version. In new game version, there arent separate files for assessment,
    // the assessment info is in chapter.xml
    private String name;

    /**
     * The list of adaptation rules
     */
    private List<AdaptationRule> rules;

    /**
     * Initial state defined in the profile
     */
    private AdaptedState initialState;

    /**
     * List of referenced flags
     */
    private List<String> flags;

    /**
     * List of referenced vars
     */
    private List<String> vars;

    /**
     * Store if adaptation profile is for scorm2004
     */
    private boolean scorm2004;

    /**
     * Store if adaptation profile is for scorm 1.2
     */
    private boolean scorm12;

    /**
     * @param name
     * @param rules
     * @param initialState
     * @param scorm12
     * @param scorm2004
     */
    public AdaptationProfile( List<AdaptationRule> rules, AdaptedState initialState, String name, boolean scorm12, boolean scorm2004 ) {

        this.name = name;
        this.rules = rules;
        if( initialState == null )
            this.initialState = new AdaptedState( );
        else
            this.initialState = initialState;
        flags = new ArrayList<String>( );
        vars = new ArrayList<String>( );
        this.scorm2004 = scorm2004;
        this.scorm12 = scorm12;
    }

    /**
     * Empty constructor
     */
    public AdaptationProfile( ) {

        name = null;
        rules = new ArrayList<AdaptationRule>( );
        scorm2004 = false;
        scorm12 = false;
        flags = new ArrayList<String>( );
        vars = new ArrayList<String>( );
        initialState = new AdaptedState( );
    }

    /**
     * @param path
     */
    public AdaptationProfile( String name ) {

        this( );
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName( ) {

        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName( String name ) {

        this.name = name;
    }

    /**
     * @return the rules
     */
    public List<AdaptationRule> getRules( ) {

        return rules;
    }

    /**
     * Adds a new rule to the structure
     */
    public void addRule( AdaptationRule rule ) {

        this.rules.add( rule );
    }

    /**
     * Adds a new rule to the structure
     */
    public void addRule( AdaptationRule rule, int index ) {

        this.rules.add( index, rule );
    }

    /**
     * @return the initialState
     */
    public AdaptedState getAdaptedState( ) {

        return initialState;
    }

    /**
     * @param initialState
     *            the initialState to set
     */
    public void setAdaptedState( AdaptedState initialState ) {

        this.initialState = initialState;
    }

    /**
     * Adds a new flag
     * 
     * @param flag
     */
    public void addFlag( String flag ) {

        if( !flags.contains( flag ) ) {
            flags.add( flag );
        }
    }

    /**
     * Adds a new var
     * 
     * @param var
     */
    public void addVar( String var ) {

        if( !vars.contains( var ) ) {
            vars.add( var );
        }
    }

    /**
     * @return the flags
     */
    public List<String> getFlags( ) {

        return flags;
    }

    /**
     * @return the vars
     */
    public List<String> getVars( ) {

        return vars;
    }

    /**
     * @param flags
     *            the flags to set
     */
    public void setFlags( List<String> flags ) {

        this.flags = flags;
    }

    /**
     * @param vars
     *            the vars to set
     */
    public void setVars( List<String> vars ) {

        this.vars = vars;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AdaptationProfile ap = (AdaptationProfile) super.clone( );
        ap.flags = new ArrayList<String>( );
        for( String s : flags )
            ap.flags.add( ( s != null ? new String( s ) : null ) );
        ap.initialState = (AdaptedState) initialState.clone( );
        ap.name = ( name != null ? new String( name ) : null );
        ap.rules = new ArrayList<AdaptationRule>( );
        for( AdaptationRule ar : rules )
            ap.rules.add( (AdaptationRule) ar.clone( ) );
        ap.vars = new ArrayList<String>( );
        for( String s : vars )
            ap.vars.add( ( s != null ? new String( s ) : null ) );
        ap.scorm12 = scorm12;
        ap.scorm2004 = scorm2004;
        return ap;
    }

    /**
     * @return the scorm2004
     */
    public Boolean isScorm2004( ) {

        return scorm2004;
    }

    /**
     * Changes to scorm2004 profile
     */
    public void changeToScorm2004Profile( ) {

        scorm2004 = true;
        scorm12 = false;
    }

    /**
     * @return the scorm12
     */
    public Boolean isScorm12( ) {

        return scorm12;
    }

    /**
     * Changes to scorm12 profile
     */
    public void changeToScorm12Profile( ) {

        scorm2004 = false;
        scorm12 = true;
    }

    /**
     * Changes to scorm2004 profile
     */
    public void changeToNormalProfile( ) {

        scorm2004 = false;
        scorm12 = false;
    }

    /**
     * Returns all operation representation
     * 
     * @return
     */
    public static String[] getOperations( ) {

        return new String[] { "=", ">", "<", ">=", "<=" };
    }

    /**
     * Gets the operation name from an operation representation
     * 
     * @param ope
     *            the representation of the operation
     * @return the name of the operation
     */
    public static String getOpName( Object ope ) {

        String op = new String( "" );
        if( ope.equals( "=" ) ) {
            op = EQUALS;
        }
        else if( ope.equals( ">" ) ) {
            op = GRATER;
        }
        else if( ope.equals( "<" ) ) {
            op = LESS;
        }
        else if( ope.equals( ">=" ) ) {
            op = GRATER_EQ;
        }
        else if( ope.equals( "<=" ) ) {
            op = LESS_EQ;
        }
        return op;
    }

    /**
     * Gets the operation representation from an operation name
     * 
     * @param ope
     *            the name of the operation
     * @return the representation of the operation
     */
    public static String getOpRepresentation( Object ope ) {

        String op = new String( "" );
        if( ope.equals( EQUALS ) ) {
            op = "=";
        }
        else if( ope.equals( GRATER ) ) {
            op = ">";
        }
        else if( ope.equals( LESS ) ) {
            op = "<";
        }
        else if( ope.equals( GRATER_EQ ) ) {
            op = ">=";
        }
        else if( ope.equals( LESS_EQ ) ) {
            op = "<=";
        }
        return op;
    }

    /**
     * @param scorm2004
     *            the scorm2004 to set
     */
    public void setScorm2004( boolean scorm2004 ) {

        this.scorm2004 = scorm2004;
    }

    /**
     * @param scorm12
     *            the scorm12 to set
     */
    public void setScorm12( boolean scorm12 ) {

        this.scorm12 = scorm12;
    }

    /**
     * @param initialState
     *            the initialState to set
     */
    public void setInitialState( AdaptedState initialState ) {

        this.initialState = initialState;
    }

    /**
     * @return the initialState
     */
    public AdaptedState getInitialState( ) {

        return initialState;
    }

    /**
     * @param rules
     *            the rules to set
     */
    public void setRules( List<AdaptationRule> rules ) {

        this.rules = rules;
    }

}
