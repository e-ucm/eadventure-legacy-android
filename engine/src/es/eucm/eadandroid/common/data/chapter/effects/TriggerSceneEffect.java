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
 * An effect that triggers a scene
 */
public class TriggerSceneEffect extends AbstractEffect implements HasTargetId {

    /**
     * Id of the cutscene to be played
     */
    private String targetSceneId;

    /**
     * X position of the player in the scene.
     */
    private int x;

    /**
     * Y position of the player in the scene.
     */
    private int y;

    /**
     * Creates a new TriggerSceneEffect
     * 
     * @param targetSceneId
     *            the id of the cutscene to be triggered
     * @param x
     *            X position of the player in the new scene
     * @param y
     *            Y position of the player in the new scene
     */
    public TriggerSceneEffect( String targetSceneId, int x, int y ) {

        super( );
        this.targetSceneId = targetSceneId;
        this.x = x;
        this.y = y;
    }

    @Override
    public int getType( ) {

        return TRIGGER_SCENE;
    }

    /**
     * Returns the targetSceneId
     * 
     * @return String containing the targetSceneId
     */
    public String getTargetId( ) {

        return targetSceneId;
    }

    /**
     * Sets the new targetSceneId
     * 
     * @param targetSceneId
     *            New targetSceneId
     */
    public void setTargetId( String targetSceneId ) {

        this.targetSceneId = targetSceneId;
    }

    /**
     * Returns the X destiny position of the player.
     * 
     * @return X destiny position
     */
    public int getX( ) {

        return x;
    }

    /**
     * Returns the Y destiny position of the player.
     * 
     * @return Y destiny position
     */
    public int getY( ) {

        return y;
    }

    /**
     * Sets the new insertion position for the player
     * 
     * @param x
     *            X coord of the position
     * @param y
     *            Y coord of the position
     */
    public void setPosition( int x, int y ) {

        this.x = x;
        this.y = y;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        TriggerSceneEffect tse = (TriggerSceneEffect) super.clone( );
        tse.targetSceneId = ( targetSceneId != null ? new String( targetSceneId ) : null );
        tse.x = x;
        tse.y = y;
        return tse;
    }

}
