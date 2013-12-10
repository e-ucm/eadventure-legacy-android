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
package es.eucm.eadandroid.ecore.control.functionaldata.functionalactions;

import java.util.Timer;
import java.util.TimerTask;

import es.eucm.eadandroid.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadandroid.common.data.chapter.Action;
import es.eucm.eadandroid.common.data.chapter.elements.NPC;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.ecore.control.ActionManager;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.Options;
import es.eucm.eadandroid.ecore.control.animations.Animation;
import es.eucm.eadandroid.ecore.control.animations.AnimationState;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;

/**
 * The action to speak a line
 * 
 * @author Eugenio Marchiori
 * 
 */
public class FunctionalSpeak extends FunctionalAction {

    /**
     * The text to be spoken
     */
    private String[] text;

    /**
     * The id of the spoken audio
     */
    private long audioId = -1;

    /**
     * This is an Voice object of FreeTTS, that is used to synthesize the sound
     * of a conversation line.
     */
 //VOICE   private Voice voice;

    /**
     * The speech must be launched in another thread
     */
    private TTask task;

    /**
     * Check if tts synthesizer is been used
     */
    private boolean ttsInUse;

    /**
     * Time spent in this state
     */
    private long totalTime;

    /**
     * The time the character will be talking
     */
    private int timeTalking;
    
    /**
     * Keep showing the current line until user skip it
     */
    private boolean keepShowing;

    /**
     * Constructor with the original action and the text to speak
     * 
     * @param action
     *            The original action
     * @param text
     *            The text to speak
     */
    public FunctionalSpeak( Action action, String text, boolean keepShowing ) {

        super( action );
        type = ActionManager.ACTION_TALK;
        setText( text );
        ttsInUse = false;
        this.keepShowing = keepShowing;
    }

    /**
     * Constructor with the original action, the text to speak and the audio to
     * use
     * 
     * @param action
     *            The original action
     * @param text
     *            The text to speak
     * @param audioPath
     *            The path of the audio
     */
    public FunctionalSpeak( Action action, String text, String audioPath, boolean keepShowing ) {

        super( action );
        type = ActionManager.ACTION_TALK;
        setText( text );
        setAudio( audioPath );
        ttsInUse = false;
        this.keepShowing = keepShowing;
    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {

        this.functionalPlayer = functionalPlayer;
        totalTime = 0;

        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        Animation[] animations = new Animation[ 4 ];
        
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION )
                && !resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa"))
            animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ), true, MultimediaManager.IMAGE_PLAYER );

        if( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION )
                && !resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa" ))
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );

        functionalPlayer.setAnimation( animations, -1 );
    }

    @Override
    public void update( long elapsedTime ) {

        totalTime += elapsedTime;

        if( !keepShowing && totalTime > timeTalking && ( audioId == -1 || !MultimediaManager.getInstance( ).isPlaying( audioId ) ) && ( !ttsInUse ) ) {
            finished = true;
            functionalPlayer.popAnimation( );
            stopTTSTalking( );
        }
    }

    /**
     * Set the text to be displayed. This is what the player is saying
     * 
     * @param text
     *            the text to be displayed
     */
    public void setText( String text2 ) {

        String text = Game.getInstance( ).processText( text2 );

        this.text = GUI.getInstance( ).splitText( text );

        float multiplier = 1;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_SLOW )
            multiplier = 1.5f;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_FAST )
            multiplier = 0.8f;

        timeTalking = (int) ( 300 * text.split( " " ).length * multiplier );
        if( timeTalking < (int) ( 1400 * multiplier ) )
            timeTalking = (int) ( 1400 * multiplier );
    }

    /**
     * Set the audio used by the action
     * 
     * @param audioPath
     *            The path of the audio
     */
    public void setAudio( String audioPath ) {

        if( audioPath == null ) {
            if( audioId != -1 ) {
                MultimediaManager.getInstance( ).stopPlaying( audioId );
                while( MultimediaManager.getInstance( ).isPlaying( audioId ) ) {
                    try {
                        Thread.sleep( 1 );
                    }
                    catch( InterruptedException e ) {
                    }
                }
                audioId = -1;
            }
        }
        else {
            if( audioId != -1 ) {
                MultimediaManager.getInstance( ).stopPlaying( audioId );
                while( MultimediaManager.getInstance( ).isPlaying( audioId ) ) {
                    try {
                        Thread.sleep( 1 );
                    }
                    catch( InterruptedException e ) {
                    }
                }
            }

            //Gap between audios: 0.5s
            try {
                Thread.sleep( 500 );
            }
            catch( InterruptedException e ) {
            }

            audioId = MultimediaManager.getInstance( ).loadSound( audioPath, false );
            MultimediaManager.getInstance( ).startPlaying( audioId );
            while( !MultimediaManager.getInstance( ).isPlaying( audioId ) ) {
                try {
                    Thread.sleep( 1 );
                }
                catch( InterruptedException e ) {
                }
            }
        }
    }

    /**
     * Set the parameters to speak with freeTTS.
     * 
     * @param text2
     *            The text that must be said
     * @param voice
     *            The voice of the player
     */
    public void setSpeakFreeTTS( String text2, String voice ) {

        String text = Game.getInstance( ).processText( text2 );

        task = new TTask( voice, text );
        Timer timer = new Timer( );
        ttsInUse = true;
        timer.schedule( task, 0 );
    }

    /**
     * Stop the freetts speech
     */
    public void stopTTSTalking( ) {

        if( task != null ) {
            task.cancel( );
            ttsInUse = false;
        }
    }

    /**
     * The timertask that plays the freetts speech in the background
     */
    public class TTask extends TimerTask {

        private String text;

        private boolean deallocate;

        public TTask( String voiceText, String text ) {
            System.setProperty( "freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory" );
            this.text = text;
            this.deallocate = false;
//   //VOICE         VoiceManager voiceManager = VoiceManager.getInstance( );
//            voice = voiceManager.getVoice( voiceText );
//            // this call could throw a java.io.IOException if voiceText is not a valid voice (for example "")
//            // now, in the editor we only can choose a valid void or no voice. Both behaviors are controled.
//            voice.allocate( );

        }

        @Override
        public void run( ) {

            try {

     //VOICE           voice.speak( text );
                ttsInUse = false;

            }
            catch( IllegalStateException e ) {
                System.out.println( "TTS found one word which can not be processated." );
            }

        }

        @Override
        public boolean cancel( ) {

            if( !deallocate ) {
     //VOICE           voice.deallocate( );
                deallocate = true;
            }
            return true;

        }
    }

    @Override
    public void drawAditionalElements( ) {

        if( text != null && ( text.length > 1 || !text[0].equals( "" ) ) ) {
            int posX;
            int posY;
            if( functionalPlayer != null && !functionalPlayer.isTransparent( ) ) {
                posX = (int) functionalPlayer.getX( ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
                posY = (int) ( functionalPlayer.getY( ) - functionalPlayer.getHeight( ) * functionalPlayer.getScale( ) );
            }
            else {
                posX = Math.round( GUI.WINDOW_WIDTH / 2.0f + Game.getInstance( ).getFunctionalScene( ).getOffsetX( ) );
                posY = Math.round( GUI.WINDOW_HEIGHT * 1.0f / 6.0f + ( functionalPlayer != null ? functionalPlayer.getHeight( ) : 0 ) );
            }
            if( functionalPlayer.getShowsSpeechBubbles( ) ) {
                GUI.getInstance( ).addTextToDraw( text, posX, posY - 15, functionalPlayer.getTextFrontColor( ), functionalPlayer.getTextBorderColor( ), functionalPlayer.getBubbleBkgColor( ), functionalPlayer.getBubbleBorderColor( ) );
            }
            else
                GUI.getInstance( ).addTextToDraw( text, posX, posY, functionalPlayer.getTextFrontColor( ), functionalPlayer.getTextBorderColor( ) );
        }
    }

    @Override
    public void stop( ) {

        if( this.isStarted( ) ){
            stopTTSTalking( );
        	if( audioId != -1 ) 
        		MultimediaManager.getInstance( ).stopPlaying( audioId );
        }
    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {

    }

}
