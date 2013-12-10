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

import es.eucm.eadandroid.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;

/**
 * An effect that makes the player to speak a line of text.
 */
public class FunctionalSpeakPlayerEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalSpeakPlayerEffect.
     * 
     * @param line
     *            the text to be spoken
     */
    public FunctionalSpeakPlayerEffect( SpeakPlayerEffect effect ) {

        super( effect );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {

        FunctionalConditions cond = new FunctionalConditions( effect.getConditions( ) );
        if( cond.allConditionsOk( ) ) {
            FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );
            if( player.isAlwaysSynthesizer( ) )
                player.speakWithFreeTTS( ( (SpeakPlayerEffect) effect ).getLine( ), player.getPlayerVoice( ), Game.getInstance( ).getGameDescriptor( ).isKeepShowing( ) );
            else
                player.speak( ( (SpeakPlayerEffect) effect ).getLine( ), Game.getInstance( ).getGameDescriptor( ).isKeepShowing( ) );
            Game.getInstance( ).setCharacterCurrentlyTalking( player );
        }
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    @Override
    public boolean isInstantaneous( ) {

        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    @Override
    public boolean isStillRunning( ) {

        if( Game.getInstance( ).getCharacterCurrentlyTalking( ) != null && !Game.getInstance( ).getCharacterCurrentlyTalking( ).isTalking( ) )
            Game.getInstance( ).setCharacterCurrentlyTalking( null );

        return Game.getInstance( ).getCharacterCurrentlyTalking( ) != null;
    }
    
    @Override
    public boolean canSkip( ) {
        return true;
    }

    @Override
    public void skip( ) {
        if( Game.getInstance( ).getCharacterCurrentlyTalking( ) != null && Game.getInstance( ).getCharacterCurrentlyTalking( ).isTalking( ) )
            Game.getInstance( ).getCharacterCurrentlyTalking( ).stopTalking( );
        Game.getInstance( ).setCharacterCurrentlyTalking( null );
    }


}
