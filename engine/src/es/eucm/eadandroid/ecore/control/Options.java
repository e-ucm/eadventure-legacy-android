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

import android.content.SharedPreferences;
import es.eucm.eadandroid.ecore.data.GameText;
import es.eucm.eadandroid.homeapp.preferences.PreferencesActivity;

/**
 * This class stores the options of the game
 */
public class Options {
	
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
    
    private SharedPreferences prefs;
    
    private boolean audioEnabled=true;
    private boolean debugEnabled=true;
    private boolean vibrateEnabled=true;
    
 

    /**
     * Constructor, sets the default options
     */
    public Options(SharedPreferences prefs) {
    	
    	this.prefs = prefs;
    	audioEnabled = prefs.getBoolean(PreferencesActivity.AUDIO_PREF, true);
    	debugEnabled = prefs.getBoolean(PreferencesActivity.DEBUG_PREF, false);
    	vibrateEnabled = prefs.getBoolean(PreferencesActivity.VIBRATE_PREF, true);
    }

   

    /**
     * Returns the state of the music
     * 
     * @return True if the music is active, false otherwise
     */
    public boolean isMusicActive( ) {
    	
        return audioEnabled;
    }
    
    /**
     * Returns the state of the music
     * 
     * @return True if the music is active, false otherwise
     */
    public boolean isDebugActive( ) {
    	
        return debugEnabled;
    }
    
    /**
     * Returns the state of the vibration
     * 
     * @return True if the music is active, false otherwise
     */
    public boolean isVibrationActive( ) {
    	
        return vibrateEnabled;
    }

    /**
     * Returns the state of the effects
     * 
     * @return True if the effects are active, false otherwise
     */
    public boolean isEffectsActive( ) {

      //  return prefs.getProperty( "FunctionalEffects" ).equals( "On" );
  	
    	return true;
    }

    /**
     * Returns the speed of the text
     * 
     * @return TEXT_SLOW, TEXT_NORMAL or TEXT_FAST
     */
    public int getTextSpeed( ) {

//        return Integer.parseInt( prefs.getProperty( "TextSpeed" ) );
    	return TEXT_NORMAL;
    }

    /**
     * Sets the state of the music
     * 
     * @param active
     *            True if the music must be activated, false if deactivated
     */
    public void setMusicActive( boolean active ) {

    	audioEnabled = active;
    }

    /**
     * Sets the state of the effects
     * 
     * @param active
     *            True if the effects must be activated, false if deactivated
     */
    public void setEffectsActive( boolean active ) {

    //    prefs.setProperty( "FunctionalEffects", ( active ? "On" : "Off" ) );
 
    }

    /**
     * Sets the speed of the text
     * 
     * @param textSpeed
     *            TEXT_SLOW, TEXT_NORMAL or TEXT_FAST
     */
    public void setTextSpeed( int textSpeed ) {

    //    prefs.setProperty( "TextSpeed", String.valueOf( textSpeed ) );
    }
}
