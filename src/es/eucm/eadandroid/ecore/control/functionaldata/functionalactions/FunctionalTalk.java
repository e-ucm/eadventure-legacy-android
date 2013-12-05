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

import java.util.List;

import es.eucm.eadandroid.common.data.chapter.Action;
import es.eucm.eadandroid.ecore.control.ActionManager;
import es.eucm.eadandroid.ecore.control.DebugLog;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.animations.AnimationState;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalNPC;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.ecore.data.GameText;

/**
 * The action to talk with an npc
 * 
 * @author Eugenio Marchiori
 */
public class FunctionalTalk extends FunctionalAction {

    /**
     * The default distance to keep between player and npc
     */
    public static final int DEFAULT_DISTANCE_TO_KEEP = 35;

    /**
     * The functional npc to speak with
     */
    FunctionalNPC npc;

    /**
     * True if there is a conversation
     */
    private boolean anyConversation;

    /**
     * Constructor using a default action and the npc with which to talk
     * 
     * @param action
     *            The original action
     * @param npc
     *            The npc with which to talk
     */
    public FunctionalTalk( Action action, FunctionalElement npc ) {

        super( action );
        this.npc = (FunctionalNPC) npc;
        this.type = ActionManager.ACTION_TALK;
        if( functionalPlayer != null )
            keepDistance = this.functionalPlayer.getWidth( ) / 2;
        else
            keepDistance = DEFAULT_DISTANCE_TO_KEEP;
        keepDistance += npc.getWidth( ) / 2;

        List<Action> actions = this.npc.getNPC( ).getActions( );

        anyConversation = false;
        for( int i = 0; i < actions.size( ) && !anyConversation; i++ )
            if( actions.get( i ).getType( ) == Action.TALK_TO && new FunctionalConditions( actions.get( i ).getConditions( ) ).allConditionsOk( ) )
                anyConversation = true;

        if( anyConversation ) {
            needsGoTo = true;
        }
        else {
            needsGoTo = false;
        }
    }

    @Override
    public void drawAditionalElements( ) {

    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {

    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {

        this.functionalPlayer = functionalPlayer;

        if( anyConversation ) {
            if( npc != null ) {
                if( npc.getX( ) > functionalPlayer.getX( ) )
                    functionalPlayer.setDirection( AnimationState.EAST );
                else
                    functionalPlayer.setDirection( AnimationState.WEST );
            }
            finished = false;
        }
        else {
            if( functionalPlayer.isAlwaysSynthesizer( ) )
                functionalPlayer.speakWithFreeTTS( GameText.getTextTalkCannot( ), functionalPlayer.getPlayerVoice( ) );
            else
                functionalPlayer.speak( GameText.getTextTalkCannot( ) );
            finished = true;
        }

        DebugLog.player( "Start talk : " + npc.getNPC( ).getId( ) );
    }

    @Override
    public void stop( ) {

        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

        List<Action> actions = npc.getNPC( ).getActions( );
        boolean triggeredConversation = false;

        for( int i = 0; i < actions.size( ) && !triggeredConversation; i++ ) {
            if( actions.get( i ).getType( ) == Action.TALK_TO && new FunctionalConditions( actions.get( i ).getConditions( ) ).allConditionsOk( ) ) {
                Game.getInstance( ).setCurrentNPC( npc );
                FunctionalEffects.storeAllEffects( actions.get( i ).getEffects( ) );
                triggeredConversation = true;
            }
        }

        if( !triggeredConversation ) {
            if( functionalPlayer.isAlwaysSynthesizer( ) )
                functionalPlayer.speakWithFreeTTS( GameText.getTextTalkCannot( ), functionalPlayer.getPlayerVoice( ) );
            else
                functionalPlayer.speak( GameText.getTextTalkCannot( ) );
        }
        finished = true;
    }
}
