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


public class MoveObjectEffect extends AbstractEffect implements HasTargetId {
    
    private String idTarget;
    
    private int x;
    
    private int y;
    
    private float scale;
    
    private int translateSpeed;
    
    private int scaleSpeed;
    
    private boolean animated;
        
    public MoveObjectEffect(String idTarget, int x, int y, float scale, boolean animated, int translateSpeed, int scaleSpeed) {
        super();
        this.idTarget = idTarget;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.translateSpeed = translateSpeed;
        this.scaleSpeed = scaleSpeed;
        this.animated = animated;
    }
    
    @Override
    public int getType( ) {
        return MOVE_OBJECT;
    }
    
    public String getTargetId( ) {
        return idTarget;
    }

    public void setTargetId( String id ) {
        idTarget = id;
    }
        
    public int getX( ) {
        return x;
    }

    public void setX( int x ) {
        this.x = x;
    }

    public int getY( ) {
        return y;
    }

    public void setY( int y ) {
        this.y = y;
    }

    public float getScale( ) {
        return scale;
    }

    public void setScale( float scale ) {
        this.scale = scale;
    }

    public int getTranslateSpeed( ) {
        return translateSpeed;
    }
    
    public void setTranslateSpeed( int translateSpeed ) {
        this.translateSpeed = translateSpeed;
    }

    public int getScaleSpeed( ) {
        return scaleSpeed;
    }
    
    public void setScaleSpeed( int scaleSpeed ) {
        this.scaleSpeed = scaleSpeed;
    }

    public boolean isAnimated( ) {
        return animated;
    }

    public void setAnimated( boolean animated ) {    
        this.animated = animated;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {
        MoveObjectEffect coe = (MoveObjectEffect) super.clone( );
        coe.idTarget = ( idTarget != null ? new String( idTarget ) : null );
        coe.scale = scale;
        coe.x = x;
        coe.y = y;
        coe.animated = animated;
        coe.translateSpeed = translateSpeed;
        coe.scaleSpeed = scaleSpeed;
        return coe;
    }

}
