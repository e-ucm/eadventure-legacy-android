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
package es.eucm.eadandroid.ecore.control.animations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import es.eucm.eadandroid.common.data.chapter.scenes.Scene;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.gui.GUI;

/**
 * Abstract class that has a set of animations, with functions to react to mouse
 * events
 */
public abstract class AnimationState {

    /**
     * The animations of this state
     */
    protected Animation[] animations;

    /**
     * The north animation: the character is heading the screen
     */
    public static final int NORTH = 0;

    /**
     * The south animation: the character is looking back the screen
     */
    public static final int SOUTH = 1;

    /**
     * The east animation: the character is looking at the right
     */
    public static final int EAST = 2;

    /**
     * The west animation: the character is looking at the left
     */
    public static final int WEST = 3;

    private float oldScale = -1;

    private Bitmap oldImage = null;

    private Bitmap oldOriginalImage = null;
    
    private Bitmap image = null;
    
    protected boolean resetAnimation=false;

    /**
     * Creates a new AnimationState
     */
    public AnimationState( ) {

        animations = new Animation[ 4 ];
    }

    /**
     * Updates the animation
     * 
     * @param elapsedTime
     *            the elapsed time from the last update
     */
    public abstract void update( long elapsedTime );

    /**
     * Initializes the animation
     */
    public abstract void initialize( );

    /**
     * Updates the animation
     */
    public void updateAnimation( ) {

    	int tempDirection = -1;

        if( getCurrentDirection( ) == -1 )
            setCurrentDirection( SOUTH );
        
        //TODO ISOMETRIC
        if (Game.getInstance( ).isIsometric( )) {
            double angle = Math.atan2( getVelocityY(), getVelocityX() );
    
            if (angle < -Math.PI / 1.2) {
                tempDirection = SOUTH;
            } else if (angle < 0) {
                tempDirection = EAST;
            } else if (angle < Math.PI / 1.2) {
                tempDirection = NORTH;
            } else {
                tempDirection = WEST;
            }
            tempDirection = WEST;
        } else {
            if( Math.abs( getVelocityY( ) ) > Math.abs( getVelocityX( ) ) ) {
                //Si baja
                //FIXME: EL = del IF se ha puesto a pelo, sin ver que pasa en juegos 3ªpersona
                if( getVelocityY( ) >= 0 ) {
                    tempDirection = SOUTH;
                }
                else if( getVelocityY( ) < 0 ) {
                    tempDirection = NORTH;
                }
                //Si es el eje Este-Oeste
            }
            else {
                //Si a la derecha
                //FIXME: EL = del IF se ha puesto a pelo, sin ver que pasa en juegos 3ªpersona
                if( getVelocityX( ) >= 0 ) {
                    tempDirection = EAST;
                }
                else if( getVelocityX( ) < 0 ) {
                    tempDirection = WEST;
                }
            }
        }
        
        
        if( Math.abs( getVelocityX( ) ) != 0 && Math.abs( getVelocityY( ) ) != 0 && tempDirection != -1 && (tempDirection != getCurrentDirection( ) ||isResetAnimation())) {
            setCurrentDirection( tempDirection );
            animations[getCurrentDirection( )].start( );
            resetAnimation = false;
        }
    }

    /**
     * Returns the current frame of the animation
     */
    public Bitmap getImage( ) {

        return getCurrentAnimation( ).getImage( );
    }

    /**
     * Returns the current animation of the character
     */
    public Animation getCurrentAnimation( ) {

        return animations[getCurrentDirection( )];
    }

    /**
     * Draws the current frame of the animation
     * 
     * @param x
     *            the horizontal position where paint the frame
     * @param y
     *            the vertical position where paint the frame
     * @param scale
     * @param depth
     *            Position where will be drawn
     */
    public void draw( int x, int y, float scale, int depth, FunctionalElement fe ) {
    	//TODO falta por hacer el draw
    	
        image = getCurrentAnimation( ).getImage( );
        int realX = (int) ( x - ( image.getWidth(  ) * scale / 2 ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ) );
        int realY = (int) ( y - ( image.getHeight(  ) * scale ) );

        if( image == oldOriginalImage && scale == oldScale ) {
            image = oldImage;
        }
        else if( scale != 1 ) {
            oldOriginalImage = image;
            image = Bitmap.createScaledBitmap(oldOriginalImage, Math.round( oldOriginalImage.getWidth(  ) * scale ),  Math.round( oldOriginalImage.getHeight(  ) * scale ), false);
            //image = image.getScaledInstance( Math.round( image.getWidth( null ) * scale ), Math.round( image.getHeight( null ) * scale ), Image.SCALE_SMOOTH );
            //Bitmap temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( Math.round( image.getWidth(  ) * scale ),  Math.round( image.getHeight(  ) * scale ), true );
          //  ((Graphics2D) temp.getGraphics( )).drawImage( image, AffineTransform.getScaleInstance( scale, scale ), null );
           
            /*Canvas c = new Canvas(temp);
            
            Matrix m = new Matrix();
            m.setScale(scale,scale);
            
            c.drawBitmap(image, m, null);            
            image = temp;
            temp = null;*/
        }
        else {
            oldOriginalImage = image;
        }

        oldScale = scale;
        oldImage = image;

        if( depth == Scene.PLAYER_WITHOUT_LAYER || depth == Scene.PLAYER_NO_ALLOWED ) {
            GUI.getInstance( ).addPlayerToDraw( image, realX, realY, Math.round( y ), Math.round( y ) );
        }
        else {
            GUI.getInstance( ).addElementToDraw( image, realX, realY, depth, Math.round( y ), null , fe);
        }
    }

    /**
     * Load the animation resources
     */
    public abstract void loadResources( );

    /**
     * Changes the animation's current direction
     * 
     * @param direction
     *            new animation's direction
     */
    protected abstract void setCurrentDirection( int direction );

    protected abstract int getCurrentDirection( );

    protected abstract float getVelocityX( );

    protected abstract float getVelocityY( );
    
    /**
     * @return the resetAnimation
     */
    public boolean isResetAnimation( ) {
    
        return resetAnimation;
    }

    
    /**
     * @param resetAnimation the resetAnimation to set
     */
    public void setResetAnimation( boolean resetAnimation ) {
    
        this.resetAnimation = resetAnimation;
    }
}
