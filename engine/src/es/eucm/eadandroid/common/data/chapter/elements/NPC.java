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
package es.eucm.eadandroid.common.data.chapter.elements;

/**
 * This class holds the data of a non playing character (npc) in eAdventure
 */
public class NPC extends Element {

    /**
     * The tag for the standup animation
     */
    public static final String RESOURCE_TYPE_STAND_UP = "standup";

    /**
     * The tag for the standdown animation
     */
    public static final String RESOURCE_TYPE_STAND_DOWN = "standdown";

    /**
     * The tag for the standright animation
     */
    public static final String RESOURCE_TYPE_STAND_RIGHT = "standright";

    public static final String RESOURCE_TYPE_STAND_LEFT = "standleft";

    /**
     * The tag for the speakup animation
     */
    public static final String RESOURCE_TYPE_SPEAK_UP = "speakup";

    /**
     * The tag for the speakdown animation
     */
    public static final String RESOURCE_TYPE_SPEAK_DOWN = "speakdown";

    /**
     * The tag for the speakright animation
     */
    public static final String RESOURCE_TYPE_SPEAK_RIGHT = "speakright";

    public static final String RESOURCE_TYPE_SPEAK_LEFT = "speakleft";

    /**
     * The tag for the useright animation
     */
    public static final String RESOURCE_TYPE_USE_RIGHT = "useright";

    public static final String RESOURCE_TYPE_USE_LEFT = "useleft";

    /**
     * The tag for the walkup animation
     */
    public static final String RESOURCE_TYPE_WALK_UP = "walkup";

    /**
     * The tag for the walkdown animation
     */
    public static final String RESOURCE_TYPE_WALK_DOWN = "walkdown";

    /**
     * The tag for the walkright animation
     */
    public static final String RESOURCE_TYPE_WALK_RIGHT = "walkright";

    public static final String RESOURCE_TYPE_WALK_LEFT = "walkleft";

    /**
     * The front color of the text of the character
     */
    protected String textFrontColor;

    /**
     * The border color of the text of the character
     */
    protected String textBorderColor;

    protected String bubbleBkgColor;

    protected String bubbleBorderColor;

    protected Boolean showsSpeechBubbles;

    /**
     * The voice which the synthesizer uses to read text of the character
     */
    protected String voice;

    /**
     * Tells if it must be read by synthesizer all conversation lines
     */
    protected boolean alwaysSynthesizer;

    /**
     * Creates a new NPC
     * 
     * @param id
     *            the id of the npc
     */
    public NPC( String id ) {

        super( id );

        // Default colors are white for the front color, and black for the border color
        textFrontColor = "#FFFFFF";
        textBorderColor = "#000000";
        showsSpeechBubbles = false;
        bubbleBkgColor = "#FFFFFF";
        bubbleBorderColor = "#000000"; // TODO he a–adido un 0
    }

    /**
     * Returns the front color of the character's text
     * 
     * @return String with the color, in format "#RRGGBB"
     */
    public String getTextFrontColor( ) {

        return textFrontColor;
    }

    /**
     * Returns the boder color of the character's text
     * 
     * @return String with the color, in format "#RRGGBB"
     */
    public String getTextBorderColor( ) {

        return textBorderColor;
    }

    public String getBubbleBorderColor( ) {

        return bubbleBorderColor;
    }

    public String getBubbleBkgColor( ) {

        return bubbleBkgColor;
    }

    public Boolean getShowsSpeechBubbles( ) {

        return showsSpeechBubbles;
    }

    public void setShowsSpeechBubbles( Boolean showsSpeechBubbles ) {

        this.showsSpeechBubbles = showsSpeechBubbles;
    }

    /**
     * Sets the front color of the character's text
     * 
     * @param textFrontColor
     *            String with the color, in format "#RRGGBB"
     */
    public void setTextFrontColor( String textFrontColor ) {

        this.textFrontColor = textFrontColor;
    }

    /**
     * Sets the border color of the character's text
     * 
     * @param textBorderColor
     *            String with the color, in format "#RRGGBB"
     */
    public void setTextBorderColor( String textBorderColor ) {

        this.textBorderColor = textBorderColor;
    }

    public void setBubbleBorderColor( String bubbleBorderColor ) {

        this.bubbleBorderColor = bubbleBorderColor;
    }

    public void setBubbleBkgColor( String bubbleBkgColor ) {

        this.bubbleBkgColor = bubbleBkgColor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString( ) {

        StringBuffer sb = new StringBuffer( 40 );

        sb.append( "\n" );
        sb.append( super.toString( ) );

        return sb.toString( );
    }

    /**
     * Gets the voice of the character
     * 
     * @return String with the voice
     */
    public String getVoice( ) {

        return voice;
    }

    /**
     * Sets the voice of the character
     * 
     * @param voice
     *            String with the voice
     */
    public void setVoice( String voice ) {

        this.voice = voice;
    }

    /**
     * Get if the conversation lines must be read by synthesizer
     * 
     * @return True, if always read by synthesizer, false, otherwise
     */
    public Boolean isAlwaysSynthesizer( ) {

        return alwaysSynthesizer;
    }

    /**
     * Change the possibility of read all conversation lines with the
     * synthesizer
     * 
     * @param alwaysSynthesizer
     *            the new value
     */
    public void setAlwaysSynthesizer( Boolean alwaysSynthesizer ) {

        this.alwaysSynthesizer = alwaysSynthesizer;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        NPC n = (NPC) super.clone( );
        n.alwaysSynthesizer = alwaysSynthesizer;
        n.textBorderColor = ( textBorderColor != null ? new String( textBorderColor ) : null );
        n.textFrontColor = ( textFrontColor != null ? new String( textFrontColor ) : null );
        n.voice = ( voice != null ? new String( voice ) : null );
        return n;
    }
}
