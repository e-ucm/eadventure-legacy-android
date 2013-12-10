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
package es.eucm.eadandroid.ecore.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

class SceneImage {

	/**
	 * Background image
	 */
	private Bitmap backgroundSrc;
	private Rect dst;

	/**
	 * Offset of the background
	 */
	private int offsetX;

	/**
	 * Constructor of the class
	 * 
	 * @param background
	 *            Background image
	 * @param offsetX
	 *            Offset
	 */
	public SceneImage(Bitmap background, int offsetX) {

		this.backgroundSrc = background;
		this.offsetX = offsetX;
		this.dst = new Rect(0,0,GUI.WINDOW_WIDTH,GUI.WINDOW_HEIGHT);
	}

	/**
	 * Draw the background with the offset
	 * 
	 * @param c
	 *            canvas to draw the background
	 */
	public void draw(Canvas c) {

		Rect src = new Rect(offsetX,0,offsetX+GUI.WINDOW_WIDTH,GUI.WINDOW_HEIGHT);		
	    c.drawBitmap(backgroundSrc, src, dst,null);	
	}
	

}
