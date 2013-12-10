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

import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.ecore.control.DebugLog;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalNextSceneEffect;

/**
 * The action to exit a scene
 * 
 * @author Eugenio Marchiori
 * 
 */
public class FunctionalExit extends FunctionalAction {

    /**
     * The exit through which the player leaves the scene
     */
    private Exit exit;

    /**
     * Default constructor, with the exit to use.
     * 
     * @param exit
     *            The exit through which the player leaves the scene.
     */
    public FunctionalExit( Exit exit ) {

        super( null );
        this.exit = exit;
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
        if( exit != null ) {
            if( new FunctionalConditions( exit.getConditions( ) ).allConditionsOk( ) ) {
                FunctionalEffects effects = new FunctionalEffects( exit.getEffects( ) );
                effects.addEffect( new FunctionalNextSceneEffect( exit ) );
                FunctionalEffects.storeAllEffects( effects );
            }
            else if( exit.isHasNotEffects( ) ) {
                FunctionalEffects.storeAllEffects( exit.getNotEffects( ) );
            }
        }
        finished = true;

        DebugLog.player( "Exit scene to: " + ( exit != null ? exit.getNextSceneId( ) : "none" ) );
    }

    @Override
    public void stop( ) {

        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

    }

}
