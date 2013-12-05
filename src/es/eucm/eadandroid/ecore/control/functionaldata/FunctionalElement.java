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
package es.eucm.eadandroid.ecore.control.functionaldata;



import android.graphics.Color;
import android.util.Log;
import es.eucm.eadandroid.common.data.chapter.Action;
import es.eucm.eadandroid.common.data.chapter.CustomAction;
import es.eucm.eadandroid.common.data.chapter.InfluenceArea;
import es.eucm.eadandroid.common.data.chapter.elements.Element;
import es.eucm.eadandroid.ecore.control.functionaldata.Renderable;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalhighlights.FunctionalHighlight;


/**
 * This class defines the common behaviour from the characters and the objects
 * in the game
 */
public abstract class FunctionalElement implements Renderable {

    /**
     * Horizontal position of the element
     */
    protected float x;

    /**
     * Vertical position of the element
     */
    protected float y;

    protected float scale;

    /**
     * The position in which will be painted this element
     */
    protected int layer;

    protected FunctionalHighlight highlight;
    
    protected boolean dragging;
    
    /**
     * Creates a new FunctionalElement
     * 
     * @param x
     *            horizontal position of the element
     * @param y
     *            vertical position of the element
     */
    public FunctionalElement( int x, int y ) {

        this.x = x;
        this.y = y;
        this.scale = 1;
        this.layer = -1;
        this.highlight = null;
        this.dragging = false;
    }

    /**
     * Returns this element's data
     * 
     * @return this element's data
     */
    public abstract Element getElement( );

    /**
     * Returns the horizontal position of this element
     * 
     * @return the horizontal position of this element
     */
    public float getX( ) {

        return x;
    }

    /**
     * Returns the vertical position of this element
     * 
     * @return the vertical position of this element
     */
    public float getY( ) {

        return y;
    }

    public float getScale( ) {

        return scale;
    }

    /**
     * Gets this Sprite's width, based on the size of the current image.
     */
    public abstract int getWidth( );

    /**
     * Gets this Sprite's height, based on the size of the current image.
     */
    public abstract int getHeight( );

    /**
     * Sets the horizontal position of this element
     * 
     * @param x
     *            the new horizontal position
     */
    public void setX( float x ) {

        this.x = x;
    }

    /**
     * Sets the vertical position of this element
     * 
     * @param y
     *            the new vertical position
     */
    public void setY( float y ) {

        this.y = y;
    }

    public void setScale( float scale ) {

        this.scale = scale;
    }

    /**
     * Returns whether the given point is inside this element
     * 
     * @param x
     *            the horizontal position of the point
     * @param y
     *            the vertical position of the point
     * @return true if the given point is inside this element, false otherwise
     */
    public abstract boolean isPointInside( float x, float y );

    /**
     * Returns whether this element can perform the given action
     * 
     * @param action
     *            the action to be performed
     * @return true if the action can be performed, false otherwise
     */
    public abstract boolean canPerform( int action );

    /**
     * Returns if the element can be used alone, without any other element
     * 
     * @return True if the element can be used, false otherwise
     */
    public boolean canBeUsedAlone( ) {

        return false;
    }
    
    /**
     * Triggers the examining action associated with the element
     * 
     * @return True if the element was examined, false otherwise
     */
    public boolean examine( ) {

        return false;
    }

    /**
     * Returns whether this element is in the player's inventory
     * 
     * @return true if this is in the inventory, false otherwise
     */
    public boolean isInInventory( ) {

        return false;
    }

    /**
     * Returns a color from the given string
     * 
     * Calls to this method were substituted by 
     *  android.graphics.Color.parseColor(string)
     *
     * @param colorText
     *            Color in format "#RRGGBB"
     * @return Described color
     * */
     
    protected int generateColor( String colorText ) {
    	
    	try {
    		int color=Color.parseColor(colorText);
    		return color;
    	}
    	catch(Exception e) {
    		Log.d("el color peta", "dentro de functionalElement generacolor()  ");
    	}  	
		return Color.WHITE;
    }
    

    public abstract Action getFirstValidAction( int actionType );

    public abstract CustomAction getFirstValidCustomAction( String actionName );

    public abstract CustomAction getFirstValidCustomInteraction( String actionName );

    public int getLayer( ) {

        return layer;
    }

    public void setLayer( int layer ) {

        this.layer = layer;
    }

    public abstract InfluenceArea getInfluenceArea( );

    public boolean use( ) {

        return false;
    }

    public void setHighlight( FunctionalHighlight b ) {
        this.highlight = b;
    }
    
    public FunctionalHighlight getHighlight() {
        return highlight;
    }

    /**
     * Returns true if the element can be dragged
     * 
     * @return True if the element can be dragged, false otherwise
     */
    public boolean canBeDragged( ) {
        return false;
    }
    
    public void setDragging( boolean b ) {
        this.dragging = b;
    }
    
    public boolean getDragging() {
        return this.dragging;
    }
}
