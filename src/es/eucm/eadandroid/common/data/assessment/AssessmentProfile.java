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
package es.eucm.eadandroid.common.data.assessment;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores an assessment profile. Each profile contains the path of the xml file
 * where it is stored (relative to the zip file), along with the list of
 * assessment rules defined in the profile
 * 
 * @author Javier
 * 
 */
public class AssessmentProfile implements Cloneable {

    /**
     * Constants to identify the comparison operations
     */
    public static final String EQUALS = "eq";

    public static final String GRATER = "gt";

    public static final String LESS = "lt";

    public static final String GRATER_EQ = "ge";

    public static final String LESS_EQ = "le";

    /**
     * the name of assessment profile
     */
    // Also is the path of the assessment profile for old game version. In new game version, there arent separate files for assessment,
    // the assessment info is in chapter.xml
    private String name;

    /**
     * The list of assessment rules
     */
    private List<AssessmentRule> rules;

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

    ////////FEEDBACK
    /**
     * If true, the assessment report is shown to the student at the end of the
     * chapter
     */
    private boolean showReportAtEnd;

    /**
     * If true, the student will be asked to send an email with the report
     */
    private boolean sendByEmail;

    /**
     * The email where the student's report must be sent
     */
    private String email;

    private boolean smtpSSL;

    private String smtpServer;

    private String smtpPort;

    private String smtpUser;

    private String smtpPwd;

    /**
     * Empty constructor
     */
    public AssessmentProfile( ) {

        this( new ArrayList<AssessmentRule>( ), null );

    }

    /**
     * @param path
     */
    public AssessmentProfile( String path ) {

        this( new ArrayList<AssessmentRule>( ), path );
    }

    public AssessmentProfile( List<AssessmentRule> assessmentRules, String name ) {

        rules = assessmentRules;
        this.name = name;
        flags = new ArrayList<String>( );
        vars = new ArrayList<String>( );
        sendByEmail = false;
        email = "";
    }

    /**
     * @return the name
     */
    public String getName( ) {

        return name;
    }

    /**
     * @param path
     *            the name to set
     */
    public void setName( String name ) {

        this.name = name;
    }

    /**
     * @return the rules
     */
    public List<AssessmentRule> getRules( ) {

        return rules;
    }

    /**
     * Adds a new rule to the structure
     */
    public void addRule( AssessmentRule rule ) {

        this.rules.add( rule );
    }

    /**
     * Adds a new rule to the structure in the given position
     */
    public void addRule( AssessmentRule rule, int index ) {

        this.rules.add( index, rule );
    }

    /**
     * Set all the rules
     * 
     * @param assessmentRules
     */
    public void setRules( List<AssessmentRule> assessmentRules ) {

        this.rules = assessmentRules;
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

    /**
     * @return the showReportAtEnd
     */
    public boolean isShowReportAtEnd( ) {

        return showReportAtEnd;
    }

    /**
     * @param showReportAtEnd
     *            the showReportAtEnd to set
     */
    public void setShowReportAtEnd( boolean showReportAtEnd ) {

        this.showReportAtEnd = showReportAtEnd;
    }

    /**
     * @return the sendByEmail
     */
    public boolean isSendByEmail( ) {

        return sendByEmail;
    }

    /**
     * @param sendByEmail
     *            the sendByEmail to set
     */
    public void setSendByEmail( boolean sendByEmail ) {

        this.sendByEmail = sendByEmail;
    }

    /**
     * @return the email
     */
    public String getEmail( ) {

        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail( String email ) {

        this.email = email;
    }

    public void setSmtpSSL( boolean equals ) {

        smtpSSL = equals;
    }

    public void setSmtpPort( String value ) {

        smtpPort = value;
    }

    public void setSmtpUser( String value ) {

        smtpUser = value;
    }

    public void setSmtpPwd( String value ) {

        smtpPwd = value;
    }

    /**
     * @return the smtpSSL
     */
    public boolean isSmtpSSL( ) {

        return smtpSSL;
    }

    /**
     * @return the smtpPort
     */
    public String getSmtpPort( ) {

        return smtpPort;
    }

    /**
     * @return the smtpUser
     */
    public String getSmtpUser( ) {

        return smtpUser;
    }

    /**
     * @return the smtpPwd
     */
    public String getSmtpPwd( ) {

        return smtpPwd;
    }

    public void setSmtpServer( String value ) {

        this.smtpServer = value;
    }

    public String getSmtpServer( ) {

        return smtpServer;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AssessmentProfile ap = (AssessmentProfile) super.clone( );
        ap.email = ( email != null ? new String( email ) : null );
        if( flags != null ) {
            ap.flags = new ArrayList<String>( );
            for( String s : flags )
                ap.flags.add( ( s != null ? new String( s ) : null ) );
        }
        ap.name = ( name != null ? new String( name ) : null );
        if( rules != null ) {
            ap.rules = new ArrayList<AssessmentRule>( );
            for( AssessmentRule ar : rules )
                ap.rules.add( (AssessmentRule) ar.clone( ) );
        }
        ap.sendByEmail = sendByEmail;
        ap.showReportAtEnd = showReportAtEnd;
        ap.smtpPort = ( smtpPort != null ? new String( smtpPort ) : null );
        ap.smtpPwd = ( smtpPwd != null ? new String( smtpPwd ) : null );
        ap.smtpServer = ( smtpServer != null ? new String( smtpServer ) : null );
        ap.smtpSSL = smtpSSL;
        ap.smtpUser = ( smtpUser != null ? new String( smtpUser ) : null );
        if( vars != null ) {
            ap.vars = new ArrayList<String>( );
            for( String s : vars ) {
                ap.vars.add( ( s != null ? new String( s ) : null ) );
            }
        }
        ap.scorm12 = scorm12;
        ap.scorm2004 = scorm2004;
        return ap;
    }

    /**
     * @return the scorm2004
     */
    public boolean isScorm2004( ) {

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
    public boolean isScorm12( ) {

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
     * Don´t use this method in the editor
     * 
     * @param scorm2004
     *            the scorm2004 to set
     */
    // It is only use in AssessmentHandler to set the value during the parsing
    public void setScorm2004( boolean scorm2004 ) {

        this.scorm2004 = scorm2004;
    }

    /**
     * Don´t use this method in the editor
     * 
     * @param scorm12
     *            the scorm12 to set
     */
    // It is only use in AssessmentHandler to set the value during the parsing
    public void setScorm12( boolean scorm12 ) {

        this.scorm12 = scorm12;
    }

}
