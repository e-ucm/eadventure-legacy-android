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
package es.eucm.eadandroid.common.data.animation;

/**
 * This class holds the information for an animation transition
 * 
 * @author Eugenio Marchiori
 * 
 */
public class Transition implements Cloneable, Timed {

    /**
     * The transition does nothing
     */
    public static final int TYPE_NONE = 0;

    /**
     * The transition makes the previous frame disappear while the new one
     * appears
     */
    public static final int TYPE_FADEIN = 1;

    /**
     * The transition places the new frame over the old one from left to right
     */
    public static final int TYPE_VERTICAL = 2;

    /**
     * The transition places the new frame over the old one from top to bottom
     */
    public static final int TYPE_HORIZONTAL = 3;

    /**
     * Time (duration) of the transition
     */
    private long time;

    /**
     * Type of the transition: {@link #TYPE_FADEIN}, {@link #TYPE_NONE},
     * {@link #TYPE_HORIZONTAL} or {@link #TYPE_VERTICAL}
     */
    private int type;

    /**
     * Creates a new empty transition
     */
    public Transition( ) {

        time = 0;
        type = TYPE_NONE;
    }

    /**
     * Returns the time (duration) of the transition in milliseconds
     * 
     * @return the time (duration) of the transition in milliseconds
     */
    public long getTime( ) {

        return time;
    }

    /**
     * Sets the time (duration) of the transition in milliseconds
     * 
     * @param time
     *            the new time (duration) of the transition in milliseconds
     */
    public void setTime( long time ) {

        this.time = time;
    }

    /**
     * Returns the type of the transition
     * 
     * @return the type of the transition
     */
    public int getType( ) {

        return type;
    }

    /**
     * Sets the type of the transition
     * 
     * @param type
     *            The new type of the transition
     */
    public void setType( int type ) {

        this.type = type;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Transition t = (Transition) super.clone( );
        t.time = time;
        t.type = type;
        return t;
    }

}
