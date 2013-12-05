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
package es.eucm.eadandroid.common.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * This class holds all the methods to load and handle the text strings to the
 * rest of the application.
 * 
 * @author Bruno Torijano Bueno
 */
public class TC {

    public static final int NORMAL_SENTENCE = 1;

    public static final int NO_CONDITION_SENTENCE = 2;

    /**
     * Properties set containing the strings.
     */
    private static Properties guiStrings;

    /**
     * Loads the strings of the application from the given XML properties file.
     * 
     * @param languageFile
     *            Name of the file containing the text
     */
    public static void loadStrings( String languageFile ) {
        try {
            loadStrings( new FileInputStream( languageFile ) );
        }
        catch( FileNotFoundException e ) {
            guiStrings = new Properties( );
            e.printStackTrace( );
     //       JOptionPane.showMessageDialog( null, "The language file was not found, please verify that the language files are on the disk.\nAlthough you may still be able to use the adventure editor, you might get occasional problems or get some unreadable texts.\nIf you get this error again, please try reinstalling the application.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Loads the strings of the application from the given set of XML properties
     * files.
     * 
     * @param languageFiles
     *            List with the names of the file containing the text
     */
    public static void loadStrings( String[] languageFiles ) {
        if( languageFiles != null && languageFiles.length > 0 ) {
            loadStrings( languageFiles[0] );
            for( int i = 1; i < languageFiles.length; i++ )
                appendStrings( languageFiles[i] );
        }
        else {
            guiStrings = new Properties( );
        }
    }

    /**
     * Loads the strings of the application from the given set of XML properties
     * files.
     * 
     * @param languageFiles
     *            List with the names of the file containing the text
     */
    public static void loadStrings( InputStream[] languageFiles ) {

        if( languageFiles != null && languageFiles.length > 0 ) {
            loadStrings( languageFiles[0] );
            for( int i = 1; i < languageFiles.length; i++ )
                appendStrings( languageFiles[i] );
        }
        else {
            guiStrings = new Properties( );
        }
    }

    /**
     * Loads the strings of the application from the given XML properties file.
     * 
     * @param languageFile
     *            Name of the file containing the text
     */
    public static void loadStrings( InputStream languageFile ) {

        guiStrings = new Properties( );
        try {
            guiStrings.loadFromXML( languageFile );
        }

        // If the file is bad formed
        catch( InvalidPropertiesFormatException e ) {
        	e.printStackTrace();
        	// //           JOptionPane.showMessageDialog( null, "The language file is bad-formed, please try to use another language file.\nAlthough you may still be able to use the adventure editor, you might get occasional problems or get some unreadable texts.\nIf you get this error again, please try reinstalling the application.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
        }

        // If the file was not found
        catch( FileNotFoundException e ) {
        	e.printStackTrace();
        	// //           JOptionPane.showMessageDialog( null, "The language file was not found, please verify that the language files are on the disk.\nAlthough you may still be able to use the adventure editor, you might get occasional problems or get some unreadable texts.\nIf you get this error again, please try reinstalling the application.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
        }

        // If there was a I/O exception
        catch( IOException e ) {
        	e.printStackTrace();
        	//          JOptionPane.showMessageDialog( null, "There has been an error loading the language file, please check for problem accessing the files.\nAlthough you may still be able to use the adventure editor, you might get occasional problems or get some unreadable texts.\nIf you get this error again, please try reinstalling the application.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Loads the language file specified as argument but does not clear the
     * properties structure, appending both contents. The results are equivalent
     * to: properties = properties.append(propertiesFromNewFile)
     */
    public static void appendStrings( String languageFile ) {

        try {
            appendStrings( new FileInputStream( languageFile ) );
        }
        catch( FileNotFoundException e ) {
            e.printStackTrace( );
         //     JOptionPane.showMessageDialog( null, "One of the language file required was not found, please verify that the language files are on the disk.\nAlthough you may still be able to use the adventure editor, you might get occasional problems or get some unreadable texts.\nIf you get this error again, please try reinstalling the application.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
               }
    }

    /**
     * Loads the language file specified as argument but does not clear the
     * properties structure, appending both contents. The results are equivalent
     * to: properties = properties.append(propertiesFromNewFile)
     */
    public static void appendStrings( InputStream languageFile ) {

        try {
            Properties newStrings = new Properties( );
            newStrings.loadFromXML( languageFile );
            for( Object key : newStrings.keySet( ) ) {
                guiStrings.put( key, newStrings.get( key ) );
            }
        }

        // If the file is bad formed
        catch( InvalidPropertiesFormatException e ) {
        	e.printStackTrace();
        	//           JOptionPane.showMessageDialog( null, "The language file is bad-formed, please try to use another language file.\nAlthough you may still be able to use the adventure editor, you might get occasional problems or get some unreadable texts.\nIf you get this error again, please try reinstalling the application.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
        }

        // If the file was not found
        catch( FileNotFoundException e ) {
        	e.printStackTrace();
        	//           JOptionPane.showMessageDialog( null, "One of the language file required was not found, please verify that the language files are on the disk.\nAlthough you may still be able to use the adventure editor, you might get occasional problems or get some unreadable texts.\nIf you get this error again, please try reinstalling the application.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
        }

        // If there was a I/O exception
        catch( IOException e ) {
        	e.printStackTrace();
        	//           JOptionPane.showMessageDialog( null, "There has been an error loading the language file, please check for problem accessing the files.\nAlthough you may still be able to use the adventure editor, you might get occasional problems or get some unreadable texts.\nIf you get this error again, please try reinstalling the application.", "Error loading the language file", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Returns true if the string associated with the given identifier is on the
     * language file.
     * 
     * @param tag
     *            String identifier
     * @return true if found, false otherwise
     */
    public static boolean containsText( String tag ) {

        return guiStrings.containsKey( tag );
    }

    /**
     * Returns true if the string associated with the given identifier is on the
     * language file.
     * 
     * @param tag
     *            String identifier
     * @param sentence
     *            1 for normal sentence, 2 for no-condition sentence
     * @return true if found, false otherwise
     */
    public static boolean containsConditionsContextText( int element, int sentence ) {

        return containsText( "Conditions.Context." + sentence + "." + element );
    }

    /**
     * Returns the element name for the given element identifier.
     * 
     * @param element
     *            Element identifier
     * @return Element identifier, "Error" if the element was not found
     */
    public static String getElement( int element ) {

        return get( "Element.Name" + element );
    }

    /**
     * Returns the element name for the given element identifier.
     * 
     * @param element
     *            Element identifier
     * @param sentence
     *            1 for normal sentence, 2 for no-condition sentence
     * 
     */
    public static String getConditionsContextText( int element, int sentence ) {

        return get( "Conditions.Context." + sentence + "." + element );
    }

    /**
     * Returns the string associated with the given identifier.
     * 
     * @param identifier
     *            Identifier to search for the string
     * @return String retrieved from the text base, "Error" if the text was not
     *         found
     */
    public static String get( String identifier ) {

        String text = null;

        if( guiStrings != null && guiStrings.containsKey( identifier ) )
            text = guiStrings.getProperty( identifier );
        else {
            text = "Error";
            System.err.println( "Identifier \"" + identifier + "\" not found" );
        }

        return text;
    }

    /**
     * Returns the string associated with the given identifier. This method also
     * replaces occurrences in the original string with words passed through the
     * call. Every placeholder for the strings have all the form "#n", where
     * <i>n</i> is the index of the string to replace the placeholder in the
     * given array. This method takes only one string to replace.
     * 
     * @param identifier
     *            Identifier to search for the string
     * @param parameter
     *            String to replace the placeholder in the returned string
     * @return String retrieved from the text base, "Error" if the text was not
     *         found
     */
    public static String get( String identifier, String parameter ) {

        String text = null;

        if( guiStrings.containsKey( identifier ) ) {
            text = guiStrings.getProperty( identifier );
            text = text.replace( "{#0}", parameter );
        }
        else {
            text = "Error";
            System.err.println( "Identifier \"" + identifier + "\" not found" );
        }

        return text;
    }

    /**
     * Returns the string associated with the given identifier. This method also
     * replaces occurrences in the original string with words passed through the
     * call. Every placeholder for the strings have all the form "#n", where
     * <i>n</i> is the index of the string to replace the placeholder in the
     * given array. This method takes an array of strings to replace.
     * 
     * @param identifier
     *            Identifier to search for the string
     * @param parameters
     *            Array of strings to replace the placeholders in the returned
     *            string
     * @return String retrieved from the text base, "Error" if the text was not
     *         found
     */
    public static String get( String identifier, String[] parameters ) {

        String text = null;

        if( guiStrings.containsKey( identifier ) ) {
            text = guiStrings.getProperty( identifier );
            for( int i = 0; i < parameters.length; i++ )
                text = text.replace( "{#" + i + "}", parameters[i] );
        }
        else {
            text = "Error";
            System.err.println( "Identifier \"" + identifier + "\" not found" );
        }

        return text;
    }

    /**
     * Returns the default name of the edition tool given its class
     * 
     * @param c
     *            The class of the tool
     * @return The the text if any, "Error" if not found
     */
    @SuppressWarnings ( "unchecked")
    public static String getToolName( Class c ) {

        String text = null;

        if( guiStrings != null && guiStrings.containsKey( "Edition.UndoRedo.Name." + c.getSimpleName( ) ) )
            text = guiStrings.getProperty( "Edition.UndoRedo.Name." + c.getSimpleName( ) );
        else {
            text = c.getSimpleName( );
        }

        return text;
    }
}
