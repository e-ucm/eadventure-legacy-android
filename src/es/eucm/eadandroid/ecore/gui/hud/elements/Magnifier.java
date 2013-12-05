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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.os.Vibrator;
import es.eucm.eadandroid.ecore.control.ContextServices;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.gui.GUI;

public class Magnifier {

	/**
	 * MAGNIFIER SATIC DESC
	 */

	private Bitmap magBmp;
	private Canvas canvasMag;
	private Bitmap bmpmagaux;

	Paint pFrame;

	/**
	 * MAGNIFIER DINAMIC DESC
	 */

	private boolean showing;
	private Rect magBounds;
	private int radius;
	public static int CENTER_OFFSET;

	/**
	 * FINGER REGION
	 */

	private static final int FINGER_REGION = (int) (60f * GUI.DISPLAY_DENSITY_SCALE);
	private static final int F_RADIUS = FINGER_REGION / 2;

	private Rect fBounds;

	/**
	 * MAGNIFIED BITMAP
	 */
	private Bitmap bmpsrc;
	private Matrix matrix;
	float zoom;
	Path mPath = new Path();
	private Rect magBoundsIntersected;

	/**
	 * WINDOW BOUNDS
	 */

	Rect windowBounds;

	/**
	 * ITEM DESCRIPTION
	 */

	Paint textP;
	Paint pointPaint;
	
	int elementColor = Color.parseColor("#F4FA58");
	int combinedElementColor = Color.parseColor("#5858FA");
	int exitColor = Color.parseColor("#81F781");
	
	boolean vibrateOnFocus;
	

	public Magnifier(int not_scaled_radius, int not_scaled_frameWidth, float zoom, Bitmap bmp) {

		this.radius = (int) (not_scaled_radius * GUI.DISPLAY_DENSITY_SCALE);
		CENTER_OFFSET = (int) (30 * GUI.DISPLAY_DENSITY_SCALE);

		magBounds = new Rect(0, 0, radius * 2, radius * 2);
		magBoundsIntersected = new Rect(0, 0, radius * 2, radius * 2);

		fBounds = new Rect(0, 0, FINGER_REGION, FINGER_REGION);

		windowBounds = new Rect(0, 0, GUI.FINAL_WINDOW_WIDTH,
				GUI.FINAL_WINDOW_HEIGHT);

		this.showing = false;
		this.bmpsrc = bmp;
		this.matrix = new Matrix();
		this.zoom = zoom;
		
		bmpmagaux = null;

		pFrame = new Paint(Paint.ANTI_ALIAS_FLAG);
		pFrame.setColor(0xFF000000);
		pFrame.setStyle(Paint.Style.STROKE);
		pFrame.setStrokeWidth(not_scaled_frameWidth * GUI.DISPLAY_DENSITY_SCALE);

		textP = new Paint(Paint.ANTI_ALIAS_FLAG);
		textP.setColor(0xFFFFFFFF);
		textP.setShadowLayer(4f, 0, 0, Color.BLACK);
		textP.setTextSize(20 * GUI.DISPLAY_DENSITY_SCALE);
		textP.setTypeface(Typeface.SANS_SERIF);
		textP.setTextAlign(Align.CENTER);
		
		pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setColor(0xFF000000);
		pointPaint.setStrokeWidth(4f * GUI.DISPLAY_DENSITY_SCALE);

		vibrateOnFocus=true;	
		
		createMagnifier();

	}

	private void createMagnifier() {

		magBmp = Bitmap.createBitmap(magBounds.width(), magBounds.height(),
				Bitmap.Config.RGB_565);

		float t = (zoom * magBounds.width() - magBounds.width()) / 2;

		matrix.preTranslate(-t, -t);
		matrix.preScale(zoom, zoom);

		canvasMag = new Canvas(magBmp);
	}

	public void doDraw(Canvas c) {

		if (showing) {
			updateMagPicture();
			paintMagBmp(c);

		}
	}

	private void updateMagPicture() {
		
		 bmpmagaux = Bitmap.createBitmap(bmpsrc,
				magBoundsIntersected.left, magBoundsIntersected.top,
				magBoundsIntersected.width(), magBoundsIntersected.height());

		canvasMag.save();
		canvasMag.drawARGB(255, 0, 0, 0);
		canvasMag.translate(magBoundsIntersected.left - magBounds.left,
				magBoundsIntersected.top - magBounds.top);
		canvasMag.drawBitmap(bmpmagaux, matrix, null);
		canvasMag.restore();
		bmpmagaux = null;

	}

	private void paintMagBmp(Canvas c) {

		
		c.save();
		c.translate(magBounds.left, magBounds.top);

		c.save();

		c.translate(magBounds.width() / 2, -5 * GUI.DISPLAY_DENSITY_SCALE);

		FunctionalElement fe = Game.getInstance().getActionManager()
				.getElementOver();

		String exit = Game.getInstance().getActionManager().getExit();
		
		FunctionalElement feInCursor = Game.getInstance().getActionManager().getElementInCursor();

		
		
		if (feInCursor!=null && fe!=null) {
			if (vibrateOnFocus)
				foundedVibration();
			
			pFrame.setColor(combinedElementColor);
			pointPaint.setColor(combinedElementColor);
		    c.drawText(feInCursor.getElement().getName()+" + "+fe.getElement().getName(), 0, 0, textP);
			
		}
		
		else if (fe != null) {
			if (vibrateOnFocus)
				foundedVibration();
			
			pFrame.setColor(elementColor);
			pointPaint.setColor(elementColor);
			c.drawText(fe.getElement().getName(), 0, 0, textP);
		}

		else if (exit != null && exit !="") {
			if (vibrateOnFocus)
				foundedVibration();
			
			pFrame.setColor(exitColor);
			pointPaint.setColor(exitColor);
			c.drawText(exit, 0, 0, textP);
		}

		else {
			vibrateOnFocus=true;
			pFrame.setColor(Color.BLACK);
			pointPaint.setColor(Color.BLACK);
//			pFrame.setShadowLayer(4f, -4, 4, Color.BLACK);
		}

		c.restore();

		mPath.reset();
		mPath.addCircle(radius, radius, radius, Path.Direction.CCW);
		c.clipPath(mPath, Region.Op.REPLACE);

		c.save();
		c.translate(magBoundsIntersected.left - magBounds.left,
				magBoundsIntersected.top - magBounds.top);
		c.drawBitmap(magBmp, 0, 0, null);
		c.restore();

		c.drawCircle(radius, radius, radius, pFrame);
		c.drawPoint(radius, radius, pointPaint);

		c.restore();
	}

	private void foundedVibration() {
		if (Game.getInstance().getOptions().isVibrationActive()){
			// Get instance of Vibrator from current Context
			Vibrator v = ContextServices.getInstance().getServiceVibrator(); 
			// Vibrate for 300 milliseconds
			v.vibrate(40);
			vibrateOnFocus = false;
		}

	}

	public void updateMagPos(int xfocus, int yfocus) {

		//magBounds.set(xfocus - radius, yfocus + 30 - 2 * radius, xfocus
				//+ radius, yfocus + 30);
		
		//magBounds.set(xfocus - radius, yfocus + 10 - 2 * radius, xfocus
				//+ radius, yfocus + 10 + radius);
		
		magBounds.set(xfocus - radius, yfocus - radius - Magnifier.CENTER_OFFSET, xfocus
				+ radius, yfocus + radius - Magnifier.CENTER_OFFSET);

		magBoundsIntersected.set(magBounds);
		magBoundsIntersected.intersect(windowBounds);

		// fBounds.set(xfocus-F_RADIUS, yfocus- 2*F_RADIUS - radius , xfocus +
		// F_RADIUS, yfocus - radius);

	}

	public void toggle() {

		showing = !showing;

	}

	public void show() {

		showing = true;
		vibrateOnFocus = true;

	}

	public void hide() {
		showing = false;
		vibrateOnFocus=false;
	}

	public boolean isShown() {
		return showing;
	}

}
