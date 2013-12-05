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
package es.eucm.eadandroid.ecore.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import android.util.Log;
import es.eucm.eadandroid.ecore.data.GameText;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class OptionsDeprecated {

	public static final String TAG = "Options";

    /**
     * Number of speeds for the text speed parameter
     */
    public static final int TEXT_NUM_SPEEDS = 3;

    /**
     * Slow speed for text display
     */
    public static final int TEXT_SLOW = 0;

    /**
     * Normal speed for text display
     */
    public static final int TEXT_NORMAL = 1;

    /**
     * Fast speed for text display
     */
    public static final int TEXT_FAST = 2;

    /**
     * Texts for the options of the text speed.
     */
    public static final String[] TEXT_SPEED_PRINT_VALUES = { GameText.TEXT_SLOW, GameText.TEXT_NORMAL, GameText.TEXT_FAST };

    /**
     * Options of the game stored in a Properties structure
     */
    private Properties options;

    /**
     * Constructor, sets the default options
     */
    public OptionsDeprecated( ) {

        options = new Properties( );

        // Set the default values
        options.setProperty( "Music", "On" );
        options.setProperty( "FunctionalEffects", "On" );
        options.setProperty( "TextSpeed", "1" );
    }

    /**
     * Loads the options from a file, if possible
     * 
     * @param optionsPath
     *            Options file path
     * @param optionsFile
     *            Options filename
     */
    public void loadOptions( String optionsPath, String optionsFile ) {

        InputStream is = ResourceHandler.getInstance( ).getResourceAsStream(optionsPath + optionsFile + "-opt.xml" );
        if( is != null ) {
            try {
                options.loadFromXML( is );
                is.close( );
            }
            catch( IOException e ) {
            		Log.d(TAG,"ReportDialog.generateReortError");
            }
        }
    }

    /**
     * Saves the options from a file, if possible
     * 
     * @param optionsPath
     *            Options file path
     * @param optionsFile
     *            Options filename
     */
    public void saveOptions( String optionsPath, String optionsFile ) {

        OutputStream os = ResourceHandler.getInstance( ).getOutputStream( optionsPath + optionsFile + "-opt.xml" );
        if( os != null ) {
            try {
                options.storeToXML( os, "eAdventure game options", "ISO-8859-1" );
            }
            catch( IOException e ) {
        		Log.d(TAG,"ReportDialog.generateReortError");
            }
        }
    }

    /**
     * Returns the state of the music
     * 
     * @return True if the music is active, false otherwise
     */
    public boolean isMusicActive( ) {

        return options.getProperty( "Music" ).equals( "On" );
    }

    /**
     * Returns the state of the effects
     * 
     * @return True if the effects are active, false otherwise
     */
    public boolean isEffectsActive( ) {

        return options.getProperty( "FunctionalEffects" ).equals( "On" );
    }

    /**
     * Returns the speed of the text
     * 
     * @return TEXT_SLOW, TEXT_NORMAL or TEXT_FAST
     */
    public int getTextSpeed( ) {

        return Integer.parseInt( options.getProperty( "TextSpeed" ) );
    }

    /**
     * Sets the state of the music
     * 
     * @param active
     *            True if the music must be activated, false if deactivated
     */
    public void setMusicActive( boolean active ) {

        options.setProperty( "Music", ( active ? "On" : "Off" ) );
    }

    /**
     * Sets the state of the effects
     * 
     * @param active
     *            True if the effects must be activated, false if deactivated
     */
    public void setEffectsActive( boolean active ) {

        options.setProperty( "FunctionalEffects", ( active ? "On" : "Off" ) );
    }

    /**
     * Sets the speed of the text
     * 
     * @param textSpeed
     *            TEXT_SLOW, TEXT_NORMAL or TEXT_FAST
     */
    public void setTextSpeed( int textSpeed ) {

        options.setProperty( "TextSpeed", String.valueOf( textSpeed ) );
    }
	
}
