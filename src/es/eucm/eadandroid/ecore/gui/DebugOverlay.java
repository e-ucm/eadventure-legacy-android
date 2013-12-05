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
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Debug;

public class DebugOverlay {
	
	
	private static final int REFRESH_RATIO = 50; // in ms 
	
	private int DEBUG_SCREEN_WIDTH = 0;
	private int DEBUG_SCREEN_HEIGHT = 0;
	
	private int DEBUG_INFO_SCREEN_X = 0;
	
	private long totalElapsedTime = 0;
	
	private int MAX_HEAP_INFO ;
	private static final int MAX_HEAP_SIZE = 24000000;
	
	private float nativeFree[];
	private float nativeAlloc[];
	private float nativeSize[];
	
	private int fps;
	
	private String[] memInfo = null;
	
	private int i = 0;
	
	private Paint p = new Paint();
	
	private Paint textPaint = new Paint();
	
	
	private boolean active = false;
	
	public void init() {
		
		
		DEBUG_SCREEN_WIDTH = GUI.FINAL_WINDOW_WIDTH;
		DEBUG_SCREEN_HEIGHT = GUI.FINAL_WINDOW_HEIGHT;		
		DEBUG_INFO_SCREEN_X = (int) (GUI.FINAL_WINDOW_WIDTH * 0.85f);
		MAX_HEAP_INFO = DEBUG_SCREEN_WIDTH * 2;
		
		nativeFree = new float[MAX_HEAP_INFO];
		nativeAlloc = new float[MAX_HEAP_INFO];
		nativeSize = new float[MAX_HEAP_INFO];
		
		fps = 0;
		p.setTextSize(10*GUI.DISPLAY_DENSITY_SCALE);
		textPaint.setTextSize(15*GUI.DISPLAY_DENSITY_SCALE);
	    textPaint.setColor(0XFFFFFFFF);
	
		
	}

	public void draw(Canvas canvas) {
		
		if (active) {

		canvas.clipRect(0, 0, DEBUG_SCREEN_WIDTH, DEBUG_SCREEN_HEIGHT);
		canvas.drawARGB(50, 0, 0, 0);
			
		canvas.drawText(String.valueOf((fps)), 10*GUI.DISPLAY_DENSITY_SCALE, 20*GUI.DISPLAY_DENSITY_SCALE, textPaint );

		p.setStrokeWidth(3f);

		p.setColor(Color.RED);
		canvas.drawPoints(nativeSize, p);

		int xp = 0;
		int yp = 0;

		if (nativeSize.length > 0) {

			xp = (int) nativeSize[i - 2];
			yp = (int) nativeSize[i - 1];
		}

		p.setStrokeWidth(10f);
		canvas.drawPoint(xp, yp, p);

		p.setStrokeWidth(3f);
		p.setColor(Color.GREEN);
		canvas.drawPoints(nativeAlloc, p);

		xp = 0;
		yp = 0;

		if (nativeAlloc.length > 0) {

			xp = (int) nativeAlloc[i - 2];
			yp = (int) nativeAlloc[i - 1];
		}

		p.setStrokeWidth(10f);
		canvas.drawPoint(xp, yp, p);

		long heapSize = (MAX_HEAP_SIZE / 1048576)
				- (yp * (MAX_HEAP_SIZE / 1048576)) / DEBUG_SCREEN_HEIGHT;

		canvas.drawText(String.valueOf(heapSize) + "MB", xp + 10f, yp, p);

		canvas.clipRect(DEBUG_INFO_SCREEN_X, 0, DEBUG_SCREEN_WIDTH,
				DEBUG_SCREEN_HEIGHT);

		canvas.drawARGB(100, 0, 0, 0);
		p.setColor(Color.WHITE);

		float y = 0;

		if (memInfo != null) {

			for (int i = 0; i < memInfo.length; i++) {
				canvas.drawText(memInfo[i], (float) DEBUG_INFO_SCREEN_X + 15,
						y, p);
				y += 30;
			}

		}
		
		}

	}

	public void updateMemAlloc(long elapsedTime, int calcFPS) {
 
		fps = calcFPS;
		
		totalElapsedTime += elapsedTime;

		if (totalElapsedTime > REFRESH_RATIO) {

			totalElapsedTime = 0;
			
			if (i>=DEBUG_INFO_SCREEN_X) 
				i = 0;
			
			nativeFree[i] = i;
			nativeFree[i+1] = (float) DEBUG_SCREEN_HEIGHT - (Debug.getNativeHeapFreeSize() * DEBUG_SCREEN_HEIGHT) / MAX_HEAP_SIZE;
			
			nativeAlloc[i] = i;
			nativeAlloc[i+1] = (float) DEBUG_SCREEN_HEIGHT - (Debug.getNativeHeapAllocatedSize() * DEBUG_SCREEN_HEIGHT) / MAX_HEAP_SIZE;
			
			nativeSize[i] = i;
			nativeSize[i+1] = (float) DEBUG_SCREEN_HEIGHT - (Debug.getNativeHeapSize() * DEBUG_SCREEN_HEIGHT) / MAX_HEAP_SIZE;
			
			i+=2;

		}
		
	}

	public void setMemAllocInfo(String[] memInfo) {
	
			this.memInfo = memInfo;

	}

	public void enable() {
		
		active=true;
		
	}



}
