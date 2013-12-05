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

/**
 * An effect that plays a sound
 */
public class PlaySoundEffect extends AbstractEffect {

    /**
     * Whether the sound must be played in background
     */
    private boolean background;

    /**
     * The path to the sound file
     */
    private String path;

    /**
     * Creates a new PlaySoundEffect
     * 
     * @param background
     *            whether to play the sound in background
     * @param path
     *            path to the sound file
     */
    public PlaySoundEffect( boolean background, String path ) {

        super( );
        this.background = background;
        this.path = path;
    }

    @Override
    public int getType( ) {

        return PLAY_SOUND;
    }

    /**
     * Returns whether the sound must be played in background
     * 
     * @return True if the sound must be played in background, false otherwise
     */
    public boolean isBackground( ) {

        return background;
    }

    /**
     * Sets the value which tells if the sound must be played in background
     * 
     * @param background
     *            New value for background
     */
    public void setBackground( boolean background ) {

        this.background = background;
    }

    /**
     * Returns the path of the sound
     * 
     * @return Path of the sound
     */
    public String getPath( ) {

        return path;
    }

    /**
     * Sets the new path for the sound to be played
     * 
     * @param path
     *            New path of the sound
     */
    public void setPath( String path ) {

        this.path = path;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        PlaySoundEffect pse = (PlaySoundEffect) super.clone( );
        pse.background = background;
        pse.path = ( path != null ? new String( path ) : null );
        return pse;
    }
}
