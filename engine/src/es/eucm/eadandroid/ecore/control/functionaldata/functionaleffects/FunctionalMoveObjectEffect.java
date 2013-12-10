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

import java.util.List;

import es.eucm.eadandroid.common.data.chapter.effects.MoveObjectEffect;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalItem;

/**
 * An effect that consumes an object in the inventory
 */
public class FunctionalMoveObjectEffect extends FunctionalEffect {

    private FunctionalItem object;
    
    private MoveObjectEffect moveObjectEffect;
    
    private long time;
    
    private int initialX;
    
    private int initialY;
    
    private float scale;
    
    private boolean finished;
        
    /**
     * Creates a new FunctionalConsumeObjectEffect.
     * 
     * @param the
     *            ConsumeObjectEffect
     */
    public FunctionalMoveObjectEffect( MoveObjectEffect effect ) {
        super( effect );
        moveObjectEffect = effect;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    @Override
    public void triggerEffect( ) {
        List<FunctionalItem> items = Game.getInstance( ).getFunctionalScene( ).getItems( );
        for (FunctionalItem item : items) {
            if (item.getItem( ).getId( ).equals( moveObjectEffect.getTargetId( )))
                object = item;
        }
        if (object != null) {
            if (moveObjectEffect.isAnimated( )) {
                time = System.currentTimeMillis( );
                initialX = (int) object.getX( );
                initialY = (int) object.getY( );
                scale = object.getScale( );
                finished = false;
            } else {
                object.setX( moveObjectEffect.getX( ) );
                object.setY( moveObjectEffect.getY( ) );
                object.setScale( moveObjectEffect.getScale( ) );
                finished = true;
            }
        } else
            finished = true;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    @Override
    public boolean isInstantaneous( ) {
        return !moveObjectEffect.isAnimated( );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    @Override
    public boolean isStillRunning( ) {
        return !finished;
    }
    
    public void update( long elapsedTime ) {
        float temp = (float) (System.currentTimeMillis( ) - time) * moveObjectEffect.getTranslateSpeed( ) / 60000;
        int newX = initialX + (int) ( (moveObjectEffect.getX( ) - initialX) * temp );
        int newY = initialY + (int) ( (moveObjectEffect.getY( ) - initialY) * temp );
        temp = (float) (System.currentTimeMillis( ) - time) * moveObjectEffect.getScaleSpeed( ) / 60000;
        float newScale = scale + ((moveObjectEffect.getScale( ) - scale) * temp);
        if ((initialX < moveObjectEffect.getX( ) && newX > moveObjectEffect.getX( )) ||
                (initialX > moveObjectEffect.getX( ) && newX < moveObjectEffect.getX()) ||
                (initialY < moveObjectEffect.getY( ) && newY > moveObjectEffect.getY( )) ||
                (initialY > moveObjectEffect.getY( ) && newY < moveObjectEffect.getY()) ||
                 (initialX==moveObjectEffect.getX( ) && initialY == moveObjectEffect.getY( ) )) {
            newX = moveObjectEffect.getX( );
            newY = moveObjectEffect.getY( );
            newScale = moveObjectEffect.getScale( );
            finished = true;
        }
        object.setX( newX );
        object.setY( newY );
        object.setScale( newScale );
    }

}
