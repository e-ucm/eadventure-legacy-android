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
package es.eucm.eadandroid.common.loader.parsers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadandroid.common.data.adaptation.AdaptationRule;
import es.eucm.eadandroid.common.data.adaptation.AdaptedState;
import es.eucm.eadandroid.common.data.adaptation.UOLProperty;
import es.eucm.eadandroid.common.loader.InputStreamCreator;

/**
 * This handler reads the initial values for the adaptation engine. This class
 * is only used for specific xml adaptation files, that is a past
 * characteristic, to preserve past game version. In new versions, the
 * adaptation info is in chapter.xml file.For this reason the parsing of
 * assessment is now in chapter parsing (ChapterHandler)
 */
public class AdaptationHandler extends DefaultHandler {

    /* Constants */
    private static final int NONE = 0;

    private static final int INITIAL_STATE = 1;

    private static final int ADAPTATION_RULE = 2;

    private int parsing = NONE;

    /**
     * Adaptation data
     */
    private AdaptedState initialState;

    private List<AdaptationRule> externalRules;

    private AdaptationRule rule_temp;

    /**
     * List of flags involved in this assessment script
     */
    private List<String> flags;

    /**
     * List of vars involved in this assessment script
     */
    private List<String> vars;

    /**
     * String to store the current string in the XML file
     */
    private StringBuffer currentString;

    /**
     * InputStreamCreator used in resolveEntity to find dtds (only required in
     * Applet mode)
     */
    private InputStreamCreator isCreator;

    /**
     * Boolean to check if it is an scorm 1.2 profile
     */
    private boolean scorm12;

    /**
     * Boolean to check if it is an scorm 2004 profile
     */
    private boolean scorm2004;

    /**
     * Default constructor
     */
    public AdaptationHandler( InputStreamCreator isCreator, List<AdaptationRule> rules, AdaptedState iState ) {

        initialState = iState;
        if( rules == null )
            externalRules = new ArrayList<AdaptationRule>( );
        else
            externalRules = rules;
        currentString = new StringBuffer( );
        vars = new ArrayList<String>( );
        flags = new ArrayList<String>( );
        this.isCreator = isCreator;
    }

    private void addFlag( String flag ) {

        if( !flags.contains( flag ) ) {
            flags.add( flag );
        }
    }

    private void addVar( String var ) {

        if( !vars.contains( var ) ) {
            vars.add( var );
        }
    }

    /**
     * Returns the initial state
     * 
     * @return initial state
     */
    public AdaptedState getInitialState( ) {

        return initialState;
    }

    /**
     * Returns a list of adaptation rules
     * 
     * @return adaptation rules
     */
    public List<AdaptationRule> getAdaptationRules( ) {

        return externalRules;
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

        // Check if it is an scorm adaptation profile
        if( sName.equals( "adaptation" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "scorm12" ) ) {
                    scorm12 = attrs.getValue( i ).equals( "yes" );
                }
                if( attrs.getLocalName( i ).equals( "scorm2004" ) ) {
                    scorm2004 = attrs.getValue( i ).equals( "yes" );
                }
            }
        }

        //Start parsing the initial state
        if( sName.equals( "initial-state" ) ) {
            parsing = INITIAL_STATE;
        }

        //Start parsing an adaptation rule
        else if( sName.equals( "adaptation-rule" ) ) {
            parsing = ADAPTATION_RULE;
            rule_temp = new AdaptationRule( );
        }

        //Initial scene
        else if( sName.equals( "initial-scene" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "idTarget" ) ) {
                    if( parsing == INITIAL_STATE ) {
                        initialState.setTargetId( attrs.getValue( i ) );
                    }
                    else {
                        rule_temp.setInitialScene( attrs.getValue( i ) );
                    }
                }
            }
        }

        // If the tag activates a flag
        else if( sName.equals( "activate" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "flag" ) ) {
                    if( parsing == INITIAL_STATE ) {
                        initialState.addActivatedFlag( attrs.getValue( i ) );
                    }
                    else {
                        rule_temp.addActivatedFlag( attrs.getValue( i ) );
                    }
                    addFlag( attrs.getValue( i ) );
                }
            }
        }

        // If the tag deactivates a flag
        else if( sName.equals( "deactivate" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "flag" ) ) {
                    if( parsing == INITIAL_STATE ) {
                        initialState.addDeactivatedFlag( attrs.getValue( i ) );
                    }
                    else {
                        rule_temp.addDeactivatedFlag( attrs.getValue( i ) );
                    }
                    addFlag( attrs.getValue( i ) );
                }
            }
        }

        // If the tag set-value a var
        else if( sName.equals( "set-value" ) ) {
            String var = null;
            String value = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "value" ) ) {
                    value = attrs.getValue( i );
                }
            }

            if( parsing == INITIAL_STATE ) {
                initialState.addVarValue( var, AdaptedState.VALUE + " " + value );
            }
            else {
                rule_temp.addVarValue( var, AdaptedState.VALUE + " " + value );
            }
            addVar( var );

        }

        // If the tag increment a var
        else if( sName.equals( "increment" ) ) {
            String var = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }

            }

            if( parsing == INITIAL_STATE ) {
                initialState.addVarValue( var, AdaptedState.INCREMENT );
            }
            else {
                rule_temp.addVarValue( var, AdaptedState.INCREMENT );
            }
            addVar( var );

        }

        // If the tag decrement a var
        else if( sName.equals( "decrement" ) ) {
            String var = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }

            }

            if( parsing == INITIAL_STATE ) {
                initialState.addVarValue( var, AdaptedState.DECREMENT );
            }
            else {
                rule_temp.addVarValue( var, AdaptedState.DECREMENT );
            }
            addVar( var );

        }

        //Property from the UoL
        else if( sName.equals( "property" ) ) {
            String id = null;
            String value = null;
            String op = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "id" ) ) {
                    id = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "value" ) ) {
                    value = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "operation" ) ) {
                    op = attrs.getValue( i );
                }
            }
            rule_temp.addUOLProperty( new UOLProperty( id, value, op ) );
        }

    }

    @Override
    public void endElement( String namespaceURI, String localName, String sName ) throws SAXException {

        //Finish parsing the initial state
        if( sName.equals( "initial-state" ) ) {
            parsing = NONE;
        }

        else if( sName.equals( "description" ) ) {
            this.rule_temp.setDescription( currentString.toString( ).trim( ) );
        }

        //Finish parsing an adaptation rule
        else if( sName.equals( "adaptation-rule" ) ) {
            parsing = NONE;
            externalRules.add( rule_temp );
        }

        // Reset the current string
        currentString = new StringBuffer( );
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    @Override
    public void error( SAXParseException exception ) throws SAXParseException {

        throw exception;
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) throws SAXException {

        // Append the new characters
        currentString.append( new String( buf, offset, len ) );

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

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
    public InputSource resolveEntity( String publicId, String systemId ) {

        // Take the name of the file SAX is looking for
        int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );

        // Build and return a input stream with the file (usually the DTD): 
        // 1) First try looking at main folder
        InputStream inputStream = AdaptationHandler.class.getResourceAsStream( filename );
        if( inputStream == null ) {
            try {
                inputStream = new FileInputStream( filename );
            }
            catch( FileNotFoundException e ) {
                inputStream = null;
            }
        }

        // 2) Secondly use the inputStreamCreator
        if( inputStream == null )
            inputStream = isCreator.buildInputStream( filename );

        return new InputSource( inputStream );
    }

    /**
     * @return the scorm12
     */
    public boolean isScorm12( ) {

        return scorm12;
    }

    /**
     * @return the scorm2004
     */
    public boolean isScorm2004( ) {

        return scorm2004;
    }

}
