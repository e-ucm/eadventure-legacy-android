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
package es.eucm.eadandroid.common.data.chapter.conversation.line;

import es.eucm.eadandroid.common.auxiliar.AllElementsWithAssets;
import es.eucm.eadandroid.common.data.Named;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.elements.Player;

/**
 * This class stores a single conversation line, along with the name of the
 * speaker character.
 */
public class ConversationLine implements Cloneable, Named {

    /**
     * Constant for the player identifier for the lines.
     */
    public static final String PLAYER = Player.IDENTIFIER;

    /**
     * String that holds the name of the character.
     */
    private String name;

    /**
     * Sentence said by the character.
     */
    private String text;

    /**
     * Path for the audio track where the line is recorded. Its use is optional.
     */
    private String audioPath;

    /**
     * Tell if the line has to be read by synthesizer
     */
    private boolean synthesizerVoice;

    /**
     * Conditions associated to this line
     */
    private Conditions conditions;
    
    /**
     * Keep line showing until user interacts
     */
    private boolean keepShowing;

    /**
     * Constructor.
     * 
     * @param name
     *            Name of the character
     * @param text
     *            Sentence
     */
    public ConversationLine( String name, String text ) {

        this.name = name;
        this.text = text;
        this.synthesizerVoice = false;
        this.keepShowing = false;
        conditions = new Conditions( );
    }

    /**
     * Returns the name of the character.
     * 
     * @return The name of the character
     */
    public String getName( ) {

        return name;
    }

    /**
     * Returns the text of the converstational line.
     * 
     * @return The text of the conversational line
     */
    public String getText( ) {

        return text;
    }

    /**
     * Returns if the line belongs to the player.
     * 
     * @return True if the line belongs to the player, false otherwise
     */
    public boolean isPlayerLine( ) {

        return name.equals( PLAYER );
    }

    /**
     * Sets the new name of the line.
     * 
     * @param name
     *            New name
     */
    public void setName( String name ) {

        this.name = name;
    }

    /**
     * Sets the new text of the line.
     * 
     * @param text
     *            New text
     */
    public void setText( String text ) {

        this.text = text;
    }

    /**
     * @return the audioPath
     */
    public String getAudioPath( ) {

        return audioPath;
    }

    /**
     * @param audioPath
     *            the audioPath to set
     */
    public void setAudioPath( String audioPath ) {

        this.audioPath = audioPath;
        
      //if audioPath is not null, store the conversation line 
        if (audioPath != null)
            AllElementsWithAssets.addAsset( this );
    }

    /**
     * Returns true if the audio path is valid. That is when it is not null and
     * different to ""
     */
    public boolean isValidAudio( ) {

        return audioPath != null && !audioPath.equals( "" );
    }

    /**
     * Returns if the line has to be read by synthesizer
     * 
     * @return if this line has to be read by synthesizer
     */
    public Boolean getSynthesizerVoice( ) {

        return synthesizerVoice;
    }

    /**
     * Set if the line to be read by synthesizer
     * 
     * @param synthesizerVoice
     *            true for to be read by synthesizer
     */
    public void setSynthesizerVoice( Boolean synthesizerVoice ) {

        this.synthesizerVoice = synthesizerVoice;
    }

    /**
     * @return the conditions
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    /**
     * @param conditions
     *            the conditions to set
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }
    
    public boolean isKeepShowing( ) {

        return keepShowing;
    }

    public void setKeepShowing( boolean keepShowing ) {

        this.keepShowing = keepShowing;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        ConversationLine cl = (ConversationLine) super.clone( );
        cl.audioPath = ( audioPath != null ? new String( audioPath ) : null );
        cl.name = ( name != null ? new String( name ) : null );
        cl.synthesizerVoice = synthesizerVoice;
        cl.text = ( text != null ? new String( text ) : null );
        cl.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        cl.keepShowing = keepShowing;
        return cl;
    }

}
