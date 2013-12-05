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

import es.eucm.eadandroid.common.data.HasTargetId;

/**
 * An effect that triggers a cutscene
 */
public class TriggerCutsceneEffect extends AbstractEffect implements HasTargetId {

    /**
     * Id of the cutscene to be played
     */
    private String targetCutsceneId;

    /**
     * Creates a new TriggerCutsceneEffect
     * 
     * @param targetCutsceneId
     *            the id of the cutscene to be triggered
     */
    public TriggerCutsceneEffect( String targetCutsceneId ) {

        super( );
        this.targetCutsceneId = targetCutsceneId;
    }

    @Override
    public int getType( ) {

        return TRIGGER_CUTSCENE;
    }

    /**
     * Returns the targetCutsceneId
     * 
     * @return String containing the targetCutsceneId
     */
    public String getTargetId( ) {

        return targetCutsceneId;
    }

    /**
     * Sets the new targetCutsceneId
     * 
     * @param targetCutsceneId
     *            New targetCutsceneId
     */
    public void setTargetId( String targetCutsceneId ) {

        this.targetCutsceneId = targetCutsceneId;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        TriggerCutsceneEffect tce = (TriggerCutsceneEffect) super.clone( );
        tce.targetCutsceneId = ( targetCutsceneId != null ? new String( targetCutsceneId ) : null );
        return tce;
    }
}
