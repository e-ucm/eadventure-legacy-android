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
package es.eucm.eadandroid.common.auxiliar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Utility for creating virtual images. That is, images that are not stored
 * physically on the hard disk.
 * 
 * @author Javier
 * 
 */
public class CreateImage {

	public static final int CENTER = 0;

	public static final int LEFT = 1;

	public static final int RIGHT = 2;

	public static final int TOP = 1;

	public static final int BOTTOM = 2;

	public static Bitmap createImage(int width, int height, String text) {

		return createImage(width, height, text, Typeface.SANS_SERIF,Typeface.NORMAL);
	}

	public static Bitmap createImage(int width, int height, String text,
			Typeface typeface , int style) {

		return createImage(width, height, Color.GREEN, 5, Color.LTGRAY, text,
				Color.RED, CENTER, CENTER, typeface , style);
	}

	public static Bitmap createImage(int width, int height,
			int backgroundColor, int borderThickness, int borderColor,
			String text, int textColor, int alignX, int alignY, Typeface typeface , int style) {

		Bitmap im = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);	
		Canvas canvas = new Canvas(im);
		
		int textThickness = 1;
		int textSize = 12;
		
		Paint mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
	
		// Paints the background
		canvas.drawColor(backgroundColor);
				
		// Paints the border
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderThickness);
        canvas.drawRect(0, 0, width, height, mPaint);
	
        //Paints the text
                
        float centerX = width * 0.5f;
        float centerY = height * 0.5f;
        
        mPaint.setColor(0x80FF0000);
        canvas.drawLine(centerX,0, centerX,2*centerY, mPaint);
        
        canvas.translate(centerX,centerY);
		mPaint.setTextSize(textSize);
		mPaint.setTypeface(Typeface.create(typeface, style));
        mPaint.setStrokeWidth(textThickness);
        mPaint.setColor(textColor);
        mPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, 0, 0, mPaint);
      
		
     /*   // TODO align text inside bitmap
             // Calculate x & y according to alignment int x = 0; int y = 0; 
        if(alignX == CENTER ) { x = (int) ( ( width - textWidth ) / 2.0 ); }
		  else if( alignX == LEFT ) { if( width > 5 ) x = 5; else x = 0; } else
		  if( alignX == RIGHT ) { if( width > textWidth + 5 ) x = (int) ( width
		  - textWidth - 5.0 ); else x = 0; }
		  
		  if( alignY == CENTER ) { y = (int) ( ( height - textHeight ) / 2.0 );
		  } else if( alignY == TOP ) { if( height > 5 ) y = 5; else y = 0; }
		  else if( alignY == BOTTOM ) { if( height > textHeight + 5 ) y = (int)
		  ( height - textHeight - 5.0 ); else y = 0; }
		  
		  gr.drawString( text, x, y ); gr.dispose( );	
		*/
        
		return im;
        
	}
}
