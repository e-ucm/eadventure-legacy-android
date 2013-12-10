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

import java.util.Random;

import es.eucm.eadandroid.common.data.chapter.effects.RandomEffect;

/**
 * Functional class for RandomEffect
 * 
 * @author Javier
 */
public class FunctionalRandomEffect extends FunctionalEffect {

    private static final Random r = new Random( );

    /**
     * Functional positive effect
     */
    private FunctionalEffect positiveEffect;

    /**
     * Functional negative effect
     */
    private FunctionalEffect negativeEffect;

    private boolean positive;

    /**
     * Effect to be triggered: Points to positiveEffect or negativeEffect
     */
    private FunctionalEffect effectTriggered;

    public FunctionalRandomEffect( RandomEffect effect ) {

        super( effect );
        this.positiveEffect = FunctionalEffect.buildFunctionalEffect( effect.getPositiveEffect( ) );
        this.negativeEffect = FunctionalEffect.buildFunctionalEffect( effect.getNegativeEffect( ) );
    }

    private FunctionalEffect getEffectToBeTriggered( ) {

        int number = r.nextInt( 100 );
        positive = number < ( (RandomEffect) effect ).getProbability( );
        if( positive ) {
            //System.out.println(number+" < "+probability+" : Triggering positive effect" );
        }
        else {
            //System.out.println(number+" >= "+probability+" : Triggering negative effect" );
        }

        if( positive )
            return positiveEffect;
        return negativeEffect;
    }

    @Override
    public boolean isInstantaneous( ) {

        if( effectTriggered != null )
            return effectTriggered.isInstantaneous( );
        return false;
    }

    @Override
    public boolean isStillRunning( ) {

        if( effectTriggered != null )
            return effectTriggered.isStillRunning( );
        return false;
    }

    @Override
    public void triggerEffect( ) {

        effectTriggered = getEffectToBeTriggered( );
        if( effectTriggered != null ) {
            if( effectTriggered.isAllConditionsOK( ) )
                effectTriggered.triggerEffect( );
        }
    }

}
