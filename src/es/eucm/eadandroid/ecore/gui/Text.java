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

import android.graphics.Canvas;

class Text {

	/**
	 * Array string
	 */
	private String[] text;

	/**
	 * X coordinate
	 */
	private int x;

	/**
	 * Y coordinate
	 */
	private int y;

	/**
	 * Color of the text
	 */
	private int textColor;

	/**
	 * Color of the borde of the text
	 */
	private int borderColor;

	private int bubbleBkgColor;

	private int bubbleBorderColor;

	private boolean showArrow = true;

	private boolean showBubble = false;

	/**
	 * Constructor of the class
	 * 
	 * @param text
	 *            Array string
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param textColor
	 *            Color of the text
	 * @param borderColor
	 *            Color of the borde of the text
	 */
	public Text(String[] text, int x, int y, int textColor, int borderColor) {

		this.text = text;
		this.x = x;
		this.y = y;
		this.textColor = textColor;
		this.borderColor = borderColor;
	}

	/**
	 * Constructor of the class
	 * 
	 * @param text
	 *            Array string
	 * @param x
	 *            X coordinate
	 * @param y
	 *            Y coordinate
	 * @param textColor
	 *            Color of the text
	 * @param borderColor
	 *            Color of the borde of the text
	 */
	public Text(String[] text, int x, int y, int textColor,
			int borderColor, int bubbleBkgColor, int bubbleBorderColor
			) {

		this.text = text;
		this.x = x;
		this.y = y;
		this.textColor = textColor;
		this.borderColor = borderColor;
		this.showBubble = true;
		this.bubbleBkgColor = bubbleBkgColor;
		this.bubbleBorderColor = bubbleBorderColor;
		this.showArrow = false;
	}

	/**
	 * Draw the text onto the position
	 * 
	 * @param g
	 *            Graphics2D to draw the text
	 */
	public void draw(Canvas c) {
		
		this.processType();

		if (showBubble)
			
			GUI.drawStringOnto(c, text, x, y, textColor, borderColor,bubbleBkgColor, bubbleBorderColor, showArrow);
		else
			GUI.drawStringOnto(c, text, x, y, textColor, borderColor);
	}

	/**
	 * Returns the Y coordinate
	 * 
	 * @return Y coordinate
	 */
	public int getY() {

		return y;
	}
	
	private void processType( ) {

		String text = this.text[0];
        if( text != null && !text.equals( "" ) && text.charAt( 0 ) == '#' ) {
            String tag = text.substring( 0, text.indexOf( ' ' ) );
            if( tag != "" ) 
                this.text[0] = text.substring( tag.length( ) );               
            
        }

    }
}
