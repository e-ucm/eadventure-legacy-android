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
package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Align;
import android.graphics.drawable.GradientDrawable;
import es.eucm.eadandroid.ecore.gui.GUI;

public class AnimText {

	
	/**
	 *  MISC CONTENT
	 */
	
	int width;
	int MAX_WIDTH;
	Path path;
	
	/**
	 * TEXT PROPS
	 */
	
	String text;	
	
	/** PAINTING PROPS
	 */
	
	Paint textP;
	GradientDrawable rightGrad;
	
	private int GRADIENT_WIDTH = (int)(20*GUI.DISPLAY_DENSITY_SCALE);
	
	public AnimText(int max_width, String text, float size, int color, Align align) {
		super();
		this.text = text;
		this.MAX_WIDTH = max_width;
		
		this.textP = new Paint();
		textP.setColor(color);
		textP.setTextSize(size * GUI.DISPLAY_DENSITY_SCALE);
		textP.setTextAlign(align);
		
		this.width = Math.min((int) textP.measureText(text, 0, text.length()),max_width);
		
		init();
		

	
	}
	

	public AnimText(int max_width, String text, Paint textP) {
		super();
		this.text = text;
		this.textP = textP;
		this.MAX_WIDTH = max_width;
		this.width = Math.min((int) textP.measureText(text, 0, text.length()),max_width);
		
		init();
	}
	
	private void init() {
		
		rightGrad = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
						Color.argb(0, 0, 0, 0), Color.argb(255,0, 0, 0) });
		rightGrad.setShape(GradientDrawable.RECTANGLE);
		rightGrad.setBounds(0, 0, GRADIENT_WIDTH, (int) textP.getTextSize());
		
		path = new Path();
		path.lineTo(width,0);
	}
	
	public void draw(Canvas c) {
		
		c.save();
		
		switch (textP.getTextAlign()) {
			
		case LEFT : 
	        c.drawTextOnPath(text,path, 0, 0,textP);	
			break; 
		case CENTER:
			c.translate(- width/2,0);	
	        c.drawTextOnPath(text,path, 0, 0,textP);	
			break;
		}
	      
		c.restore();
		
		c.save();
		
		switch (textP.getTextAlign()) {
		
		case LEFT : 
			c.translate(MAX_WIDTH -GRADIENT_WIDTH,- (int) textP.getTextSize());
			break; 
		case CENTER:
			c.translate(MAX_WIDTH/2 -GRADIENT_WIDTH,- (int) textP.getTextSize());
			break;
		}
	      
		rightGrad.draw(c);
		
		c.restore();

	}





	
}
