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
package es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects;

import es.eucm.eadandroid.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadandroid.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadandroid.common.data.chapter.effects.ConsumeObjectEffect;
import es.eucm.eadandroid.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadandroid.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadandroid.common.data.chapter.effects.Effect;
import es.eucm.eadandroid.common.data.chapter.effects.GenerateObjectEffect;
import es.eucm.eadandroid.common.data.chapter.effects.HighlightItemEffect;
import es.eucm.eadandroid.common.data.chapter.effects.IncrementVarEffect;
import es.eucm.eadandroid.common.data.chapter.effects.MacroReferenceEffect;
import es.eucm.eadandroid.common.data.chapter.effects.MoveNPCEffect;
import es.eucm.eadandroid.common.data.chapter.effects.MoveObjectEffect;
import es.eucm.eadandroid.common.data.chapter.effects.MovePlayerEffect;
import es.eucm.eadandroid.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadandroid.common.data.chapter.effects.PlaySoundEffect;
import es.eucm.eadandroid.common.data.chapter.effects.RandomEffect;
import es.eucm.eadandroid.common.data.chapter.effects.SetValueEffect;
import es.eucm.eadandroid.common.data.chapter.effects.ShowTextEffect;
import es.eucm.eadandroid.common.data.chapter.effects.SpeakCharEffect;
import es.eucm.eadandroid.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadandroid.common.data.chapter.effects.TriggerBookEffect;
import es.eucm.eadandroid.common.data.chapter.effects.TriggerConversationEffect;
import es.eucm.eadandroid.common.data.chapter.effects.TriggerCutsceneEffect;
import es.eucm.eadandroid.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadandroid.common.data.chapter.effects.WaitTimeEffect;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;

/**
 * This abstract class defines how a certain effect must be triggered in the
 * game engine.
 */
public abstract class FunctionalEffect {

    /**
     * The effect to be ruled
     */
    protected AbstractEffect effect;

    /**
     * Constructor
     */
    public FunctionalEffect( AbstractEffect effect ) {

        this.effect = effect;
    }

    /**
     * triggers the effect
     */
    public abstract void triggerEffect( );

    /**
     * Returns whether the effect is instantaneous.
     * 
     * @return whether the effect is instantaneous
     */
    public abstract boolean isInstantaneous( );

    /**
     * Returns whether the effect is still running
     * 
     * @return true if the effect is still running, false otherwise
     */
    public abstract boolean isStillRunning( );

    /**
     * Returns true if all conditions associated to this effect are OK
     * 
     * @return
     */
    public boolean isAllConditionsOK( ) {

        if( effect != null )
            return new FunctionalConditions( effect.getConditions( ) ).allConditionsOk( );
        else
            return true;
    }

    /**
     * Static factory constructor for FunctionalEffects. Creates a new
     * FunctionalEffect according to the type of the Effect provided as argument
     */
    public static FunctionalEffect buildFunctionalEffect( AbstractEffect effect ) {

        FunctionalEffect fe = null;
        switch( effect.getType( ) ) {
            case Effect.ACTIVATE:
                fe = new FunctionalActivateEffect( (ActivateEffect) effect );
                break;
            case Effect.DEACTIVATE:
                fe = new FunctionalDeactivateEffect( (DeactivateEffect) effect );
                break;
            case Effect.SET_VALUE:
                fe = new FunctionalSetValueEffect( (SetValueEffect) effect );
                break;
            case Effect.INCREMENT_VAR:
                fe = new FunctionalIncrementVarEffect( (IncrementVarEffect) effect );
                break;
            case Effect.DECREMENT_VAR:
                fe = new FunctionalDecrementVarEffect( (DecrementVarEffect) effect );
                break;
            case Effect.MACRO_REF:
                fe = new FunctionalMacroReferenceEffect( (MacroReferenceEffect) effect );
                break;
            case Effect.CANCEL_ACTION:
                break;
            case Effect.CONSUME_OBJECT:
                fe = new FunctionalConsumeObjectEffect( (ConsumeObjectEffect) effect );
                break;
            case Effect.GENERATE_OBJECT:
                fe = new FunctionalGenerateObjectEffect( (GenerateObjectEffect) effect );
                break;
            case Effect.MOVE_NPC:
                fe = new FunctionalMoveNPCEffect( (MoveNPCEffect) effect );
                break;
            case Effect.MOVE_PLAYER:
                fe = new FunctionalMovePlayerEffect( (MovePlayerEffect) effect );
                break;
            case Effect.PLAY_ANIMATION:
                fe = new FunctionalPlayAnimationEffect( (PlayAnimationEffect) effect );
                break;
            case Effect.PLAY_SOUND:
                fe = new FunctionalPlaySoundEffect( (PlaySoundEffect) effect );
                break;
            case Effect.RANDOM_EFFECT:
                fe = new FunctionalRandomEffect( (RandomEffect) effect );
                break;
            case Effect.SPEAK_CHAR:
                fe = new FunctionalSpeakCharEffect( (SpeakCharEffect) effect );
                break;
            case Effect.SPEAK_PLAYER:
                fe = new FunctionalSpeakPlayerEffect( (SpeakPlayerEffect) effect );
                break;
            case Effect.TRIGGER_BOOK:
                fe = new FunctionalTriggerBookEffect( (TriggerBookEffect) effect );
                break;
            case Effect.TRIGGER_CONVERSATION:
                fe = new FunctionalTriggerConversationEffect( (TriggerConversationEffect) effect );
                break;
            case Effect.TRIGGER_CUTSCENE:
                fe = new FunctionalTriggerCutsceneEffect( (TriggerCutsceneEffect) effect );
                break;
            case Effect.TRIGGER_LAST_SCENE:
                fe = new FunctionalTriggerLastSceneEffect( );
                break;
            case Effect.TRIGGER_SCENE:
                fe = new FunctionalTriggerSceneEffect( (TriggerSceneEffect) effect );
                break;
            case Effect.SHOW_TEXT:
                fe = new FunctionalShowTextEffect( (ShowTextEffect) effect );
                break;
            case Effect.WAIT_TIME:
                fe = new FunctionalWaitTimeEffect( (WaitTimeEffect) effect );
                break;
            case Effect.HIGHLIGHT_ITEM:
                fe = new FunctionalHighlightItemEffect( (HighlightItemEffect) effect );
                break;
            case Effect.MOVE_OBJECT:
                fe = new FunctionalMoveObjectEffect( (MoveObjectEffect) effect);
                break;
        }
        return fe;
    }

    public boolean canSkip( ) {
        return false;
    }

    public void skip( ) {
    }

}
