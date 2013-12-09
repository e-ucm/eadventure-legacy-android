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
import android.graphics.Paint;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalhighlights.FunctionalHighlight;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.pathdirectory.Paths;


class ElementImage {

	/**
	 * Image
	 */
	protected Bitmap image;

	/**
	 * X coordinate
	 */
	protected int x;

	/**
	 * Y coordinate
	 */
	protected int y;

	/**
	 * Original y, without pertinent transformations to fir the original
	 * image to scene reference image.
	 */
	protected int originalY;

	/**
	 * Depth of the image (to be painted).
	 */
	private int depth;

	private FunctionalHighlight highlight;

	private FunctionalElement functionalElement;


	/**
	 * Constructor of the class
	 * 
	 * @param image
	 *            Image
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param depth
	 *            Depth to draw the image
	 */
	
	public ElementImage(Bitmap image, int x, int y, int depth, int originalY) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.depth = depth;
		this.originalY = originalY;
		this.highlight = null;
	}

	public ElementImage(Bitmap image, int x, int y, int depth,
			int originalY, FunctionalHighlight highlight,
			FunctionalElement fe) {
		this(image, x, y, depth, originalY);
		this.highlight = highlight;
		this.functionalElement = fe;
	}

	/**
	 * Draw the image in the position
	 * 
	 * @param g
	 *            Graphics2D to draw the image
	 */
	public void draw(Canvas c) {
		if (highlight != null) {
			c.drawBitmap(highlight.getHighlightedImage(image), x
					+ highlight.getDisplacementX(), y
					+ highlight.getDisplacementY(), null);
			// OLDc.drawImage( highlight.getHighlightedImage( image ), x +
			// highlight.getDisplacementX( ), y +
			// highlight.getDisplacementY( ), null );
		} else {
			if (this.functionalElement != null && this.functionalElement.getDragging()){
				final Bitmap hand = MultimediaManager.getInstance( ).loadImage(Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/drag.png" , MultimediaManager.IMAGE_SCENE);
				Bitmap temp = Bitmap.createScaledBitmap(image, (int)(this.functionalElement.getWidth()*this.getFunctionalElement().getScale()*1.1), (int)(this.functionalElement.getHeight()*this.getFunctionalElement().getScale()*1.1), true);
				final Bitmap dest = temp.copy(Bitmap.Config.ARGB_4444, true);
				Paint p = new Paint(Paint.DITHER_FLAG * Paint.ANTI_ALIAS_FLAG);
				p.setAlpha(150); 
		        c.drawBitmap(dest, x, y, p);
		        c.drawBitmap(hand, x + 10 * GUI.DISPLAY_DENSITY_SCALE, y + (3 * this.functionalElement.getHeight()*this.getFunctionalElement().getScale())/4, null);
		        temp = null;
			}				
			else c.drawBitmap(image, x, y, null);
		}
		
	}

	/**
	 * Returns the depth of the element.
	 * 
	 * @return Depth of the element
	 */
	public int getDepth() {

		return depth;
	}

	/**
	 * Changes the element´s depth
	 * 
	 * @param depth
	 */
	public void setDepth(int depth) {

		this.depth = depth;
	}



	/**
	 * @return the functionalElement
	 */
	public FunctionalElement getFunctionalElement() {

		return functionalElement;
	}

	public int getOriginalY() {
		// TODO Auto-generated method stub
		return this.originalY;
	}
}
