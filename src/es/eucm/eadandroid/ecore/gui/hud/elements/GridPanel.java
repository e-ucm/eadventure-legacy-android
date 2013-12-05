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
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Paint.Style;
import android.graphics.drawable.GradientDrawable;
import android.os.Vibrator;
import android.util.Log;
import es.eucm.eadandroid.ecore.control.ContextServices;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;

public class GridPanel {
	
	
	public static final int DEFAULT_ICON_HEIGHT = 48;
	public static final int DEFAULT_ICON_WIDTH = 80;
	
	
	private static final float PRESSED_SCALE = 0.9f;
	
	private static final int MILI = 1000;
	private static final float DECCELERATION = 0.007f;

	/** ICON_HEIGHT in dips **/

	private int ICON_HEIGHT ;
	private int ICON_WIDTH ;

	
	private Rect normalIconBounds;
	private Rect pressedIconBounds;
	
	private int TEXT_HEIGHT = (int) (13 * GUI.DISPLAY_DENSITY_SCALE);
	
	private static int MAX_TEXT_WIDTH ;

	private int HORIZONTAL_ICON_SEPARATION = (int) (20 * GUI.DISPLAY_DENSITY_SCALE);

	private int VERTICAL_ICON_SEPARATION = (int) (10 * GUI.DISPLAY_DENSITY_SCALE);

	private int VERTICAL_TEXT_SEPARATION = (int) (5 * GUI.DISPLAY_DENSITY_SCALE);

	private int GRADIENT_TRANSPARENCY_WIDTH = (int) (15 * GUI.DISPLAY_DENSITY_SCALE);

	public  int VERTICAL_ICON_SPACE ;

	public  int HORIZONTAL_ICON_SPACE ;

	private float HORIZONTAL_SEP_PERCENTAGE ;

	private float VERTICAL_SEP_PERCENTAGE ;

	private int numRows;
	private int numColums;
	private int centerOffset;

	private Rect bounds;

	private int height;
	private int width;

	private int leftLimit;
	private int rightLimit;

	Paint textP;

	GradientDrawable rightGrad, leftGrad;

	/** DRAGGING ANIMATION */
	
	private boolean dragging=false;
	
	/** SWIPE ANIMATION **/

	boolean swipeAnimating;
	int currentVelocityX;
	long elapsedSwipeTime;
	int direction;
	int swipeCorner;
	int lastDistance;

	/** ITEM SELECTION ANIMATION */

	RectF selectedItem;
	boolean itemSelected;
	int selColumn, selRow;
	Paint pSelItem;
	int itemFocusIndex;

	/** DATA MODEL **/

	DataSet dataSet;

	private float ROUNDED_SELECT_ITEM_RADIO = 10f * GUI.DISPLAY_DENSITY_SCALE;


	public GridPanel(Rect r, int iconHeight, int iconWidth) {
		
		Log.e("Grid","alto "+String.valueOf(iconHeight)+" ancho "+String.valueOf(iconWidth));
		
		ICON_HEIGHT = (int) (iconHeight * GUI.DISPLAY_DENSITY_SCALE);
		ICON_WIDTH = (int) (iconWidth * GUI.DISPLAY_DENSITY_SCALE);
		
		MAX_TEXT_WIDTH = ICON_WIDTH;
		
		normalIconBounds = new Rect(0,0,ICON_WIDTH,ICON_HEIGHT);
		
		int pressedCenterOffsetX = (ICON_WIDTH-(int)(ICON_WIDTH*PRESSED_SCALE));
		int pressedCenterOffsetY = (ICON_HEIGHT-(int)(ICON_HEIGHT*PRESSED_SCALE));
		
		
		
		pressedIconBounds = new Rect(pressedCenterOffsetX,pressedCenterOffsetY,(int)(ICON_WIDTH*PRESSED_SCALE),(int)(ICON_HEIGHT*PRESSED_SCALE));
		
		VERTICAL_ICON_SPACE = 2 * VERTICAL_ICON_SEPARATION
		+ ICON_HEIGHT + TEXT_HEIGHT + VERTICAL_TEXT_SEPARATION;

		HORIZONTAL_ICON_SPACE = 2
		* HORIZONTAL_ICON_SEPARATION + ICON_WIDTH;

		HORIZONTAL_SEP_PERCENTAGE = (float) HORIZONTAL_ICON_SEPARATION
		/ (float) HORIZONTAL_ICON_SPACE;

		VERTICAL_SEP_PERCENTAGE = (float) VERTICAL_ICON_SEPARATION
		/ (float) VERTICAL_ICON_SPACE;

		bounds = r;
		this.height = r.bottom - r.top;
		this.width = r.right - r.left;
		swipeAnimating = false;
		currentVelocityX = 0;
		direction = 0;
		lastDistance = 0;
		selectedItem = new RectF();
		itemSelected = false;
		itemFocusIndex=-1;

		pSelItem = new Paint();
		pSelItem.setColor(Color.argb(190, 255, 255, 255));
		pSelItem.setStyle(Style.FILL);
		pSelItem.setAntiAlias(true);

		calculateGrid();

		textP = new Paint();
		textP.setColor(Color.WHITE);
		textP.setStrokeWidth(6);
		textP.setStyle(Style.FILL);
		textP.setAntiAlias(true);
		textP.setTextSize(TEXT_HEIGHT);
		textP.setTextAlign(Paint.Align.CENTER);
		textP
				.setTypeface(Typeface.create(Typeface.SANS_SERIF,
						Typeface.NORMAL));

		rightGrad = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
						Color.argb(0, 0, 0, 0), Color.argb(255, 0, 0, 0) });
		rightGrad.setShape(GradientDrawable.RECTANGLE);
		rightGrad.setBounds(0, 0, GRADIENT_TRANSPARENCY_WIDTH, height);

		leftGrad = new GradientDrawable(
				GradientDrawable.Orientation.RIGHT_LEFT, new int[] {
						Color.argb(0, 0, 0, 0), Color.argb(255, 0, 0, 0) });
		leftGrad.setShape(GradientDrawable.RECTANGLE);
		leftGrad.setBounds(0, 0, GRADIENT_TRANSPARENCY_WIDTH, height);

	}
	
	private void calculateGrid() {
		leftLimit = 0;
		//XXX For some reason, numRows takes value 0 on Nexus 10. It should always be >=1 Iguess
		numRows = Math.max(height / VERTICAL_ICON_SPACE, 1);
		centerOffset = (height - numRows * VERTICAL_ICON_SPACE) / 2;
		rightLimit = 0;
	}

	public void setDataSet(DataSet ds) {
		dataSet = ds;
	}

	public Rect getBounds() {
		return bounds;
	}

	public void draw(Canvas c) {

		if (dataSet != null && dataSet.getItemCount() > 0) {

			c.clipRect(0, 0, width, height);
			if (Game.getInstance().getOptions().isDebugActive()){
				Paint boundsPaint = new Paint();
				boundsPaint.setColor(Color.MAGENTA);
				c.drawRect(0, 0, width, height, boundsPaint);
			}
//			c.drawARGB(250,244,250,88);
//			c.drawRoundRect(selectedItem, ROUNDED_SELECT_ITEM_RADIO,
//					ROUNDED_SELECT_ITEM_RADIO, pSelItem);
			c.save();
			c.translate(leftLimit, 0);
			if (Game.getInstance().getOptions().isDebugActive()){
				Paint leftLimitPaint = new Paint();
				leftLimitPaint.setColor(Color.CYAN);
				c.drawLine(0, -3000, 0, 3000, leftLimitPaint);
				c.drawLine(rightLimit-leftLimit, -3000, rightLimit-leftLimit, 3000, leftLimitPaint);
			}
			c.save();
			c.translate(0, centerOffset);
			if (Game.getInstance().getOptions().isDebugActive()){
				Paint leftLimitPaint = new Paint();
				leftLimitPaint.setColor(Color.CYAN);
				c.drawLine(-3000, 0, 3000, 0, leftLimitPaint);
			}
			c.save();

			numColums = (dataSet.getItemCount() / numRows) + 1;
			boolean moreElems = dataSet.getItemCount() > 0;

			for (int j = 0; j < numColums; j++) {
				int i = 0;
				c.translate(HORIZONTAL_ICON_SEPARATION, 0);

				while (moreElems && i < numRows) {

					c.translate(0, VERTICAL_ICON_SEPARATION);
					
					int index = (j * numRows) + i;
					
					if (index == itemFocusIndex) 					
						c.drawBitmap(dataSet.getPressedImageIcon(index),null,pressedIconBounds, null);
					else c.drawBitmap(dataSet.getNormalImageIcon(index),null,normalIconBounds, null);

					c.translate(ICON_WIDTH / 2, ICON_HEIGHT
							+ VERTICAL_TEXT_SEPARATION + TEXT_HEIGHT);
//					c.drawText(dataSet.getItemName(index), 0, 0,
//							textP);
					new AnimText(MAX_TEXT_WIDTH,dataSet.getItemName(index),textP).draw(c);

					c.translate(-(ICON_WIDTH / 2), VERTICAL_ICON_SEPARATION);

					i++;
					moreElems = ((j * numRows) + i) < dataSet.getItemCount();

				}
				c.restore();
				c.translate((ICON_WIDTH + 2 * HORIZONTAL_ICON_SEPARATION), 0);
				c.save();

			}
			c.restore();
			c.restore();
			c.restore();
			if (Game.getInstance().getOptions().isDebugActive()){
				Paint boundsPaint = new Paint();
				boundsPaint.setColor(Color.YELLOW);
				
				c.drawRect(leftGrad.getBounds(), boundsPaint);
			}
			leftGrad.draw(c);
			c.translate(width - GRADIENT_TRANSPARENCY_WIDTH, 0);
			if (Game.getInstance().getOptions().isDebugActive()){
				Paint boundsPaint2 = new Paint();
				boundsPaint2.setColor(Color.WHITE);

				c.drawRect(rightGrad.getBounds(), boundsPaint2);
			}
			rightGrad.draw(c);
			//c.restore();
		}

	}

	public void updateDragging(int x) {

		swipeAnimating = false;

		currentVelocityX = 0;

		int colWidth = numColums * (HORIZONTAL_ICON_SPACE);
		
		if (colWidth > bounds.width())
			dragging = true;

		if ((leftLimit + x) > 0)
			leftLimit = 0;
		else if ((rightLimit + x) < width) {
			rightLimit = width;
			leftLimit = Math.min(rightLimit - colWidth, 0);
		} else {
			leftLimit += x;
		}

		System.out.println("LEFT LIMIT="+leftLimit);
		
		updateSelectedItem();

	}

	public void swipe(long initialTime, int velocityX) {

		swipeAnimating = true;
		dragging = false;
		currentVelocityX = velocityX;

		if (velocityX >= 0)
			direction = 1;
		else
			direction = -1;

		elapsedSwipeTime = 0;

		swipeCorner = leftLimit;
		lastDistance = 0;

	}

	/** Formula => D = v0 * t +1/2 (a * t^2) **/

	public void update(long elapsedTime) {

		int colWidth = numColums * (HORIZONTAL_ICON_SPACE);

		rightLimit = Math.max(leftLimit + colWidth, width);

		if (swipeAnimating) {

			elapsedSwipeTime += elapsedTime;

			int distance = (int) ((currentVelocityX / MILI) * elapsedSwipeTime + (-direction
					* DECCELERATION * elapsedSwipeTime * elapsedSwipeTime) / 2);

			if (distance >= lastDistance) {
				if (direction == -1)
					swipeAnimating = false;
			} else if (direction == 1)
				swipeAnimating = false;

			if (swipeAnimating)
				if ((swipeCorner + distance) > 0) {
					leftLimit = 0;
					swipeAnimating = false;
				} else if ((swipeCorner + distance + colWidth) < width) {
					rightLimit = width;
					leftLimit = Math.min(rightLimit - colWidth, 0);
					swipeAnimating = false;
				} else
					leftLimit = swipeCorner + distance;
			System.out.println("LEFT LIMIT="+leftLimit);
			lastDistance = distance;

			updateSelectedItem();

		}
	}
	
	private void updateSelectedItem() {
		int left = ((selColumn - 1) * HORIZONTAL_ICON_SPACE) + leftLimit;
		int right = (left + HORIZONTAL_ICON_SPACE);
		int top = (selRow - 1) * VERTICAL_ICON_SPACE + centerOffset;
		int bottom = top + VERTICAL_ICON_SPACE;
		selectedItem.set(left, top, right, bottom);

	}

	public Object selectItem(int posX, int posY) {

		Object item = null;
		
		dragging=false;
		
		if (dataSet != null && dataSet.getItemCount() > 0 && itemFocusIndex >-1) {
			item = dataSet.getItem(itemFocusIndex);
			Log.d("ITEM",String.valueOf(itemFocusIndex));
			itemFocusIndex = -1;
			foundedVibration();
		}
		
		return item;
	}

	public void setItemFocus(int posX, int posY) {

		boolean resetIndex = true;

		if (!dragging && dataSet != null && dataSet.getItemCount() > 0) {

			posX -= bounds.left;
			posY -= bounds.top;

			int absoluteX = -leftLimit + posX;
			int absoluteY = posY;

			int column = (absoluteX / HORIZONTAL_ICON_SPACE) + 1;

			Log.w("column", String.valueOf(column));

			float precisionX = (absoluteX % HORIZONTAL_ICON_SPACE)
					/ (float) (HORIZONTAL_ICON_SPACE);

			if (precisionX >= HORIZONTAL_SEP_PERCENTAGE
					&& precisionX <= 1f - HORIZONTAL_SEP_PERCENTAGE) {

				int row = (absoluteY / VERTICAL_ICON_SPACE) + 1;

				if (row > 0 && row <= numRows) {

					float precisionY = (absoluteY % VERTICAL_ICON_SPACE)
							/ (float) (VERTICAL_ICON_SPACE);

					Log.w("row", String.valueOf(row));

					if (precisionY >= VERTICAL_SEP_PERCENTAGE
							&& precisionY <= 1f - VERTICAL_SEP_PERCENTAGE) {

						int index = (column - 1) * numRows + (row - 1);

						Log.w("index", String.valueOf(index));

						if (index < dataSet.getItemCount()) {

							if (itemFocusIndex==-1)
								foundedVibration();
							
							selColumn = column;
							selRow = row;
							updateSelectedItem();
							itemSelected = true;
							itemFocusIndex = index;
							resetIndex = false;
							
							
						}
					}

				}

			}

		}
		
		if (resetIndex)
			itemFocusIndex=-1;

	}
	
	private void foundedVibration() {
		if (Game.getInstance().getOptions().isVibrationActive()){
			// Get instance of Vibrator from current Context
			Vibrator v = ContextServices.getInstance().getServiceVibrator(); 
			// Vibrate for 300 milliseconds
			v.vibrate(40);
		}

	}

	public void resetItemFocus() {
		itemFocusIndex = -1;		
	}

	
	public Point pack(){
		//Get the number of buttons/items
		int nItems = this.dataSet.getItemCount();
		// Calculate the number of rows and columns
		this.numColums = nItems;
		this.numRows=1;
		if (nItems>4){
			this.numColums=4;
			this.numRows=(int)Math.ceil(nItems/4F);
		}
		int totalRowWidth= HORIZONTAL_ICON_SPACE*numColums;//+2*HORIZONTAL_ICON_SEPARATION;
		while (GUI.FINAL_WINDOW_WIDTH<totalRowWidth) {
			this.numColums=(int)Math.ceil(numColums/2F);
			//this.numColums/=2;
			this.numRows*=2;
			totalRowWidth= HORIZONTAL_ICON_SPACE*numColums;//+2*HORIZONTAL_ICON_SEPARATION;
			
		} 
		width = totalRowWidth;
		height = numRows*(VERTICAL_ICON_SPACE+TEXT_HEIGHT);
		centerOffset = (height - numRows * VERTICAL_ICON_SPACE) / 2;
		leftGrad.setBounds(0, 0, GRADIENT_TRANSPARENCY_WIDTH, height-VERTICAL_ICON_SEPARATION);
		rightGrad.setBounds(0, 0, GRADIENT_TRANSPARENCY_WIDTH, height-VERTICAL_ICON_SEPARATION);
		leftLimit=0;
		rightLimit=0;
		//leftLimit=x;
		//rightLimit=x+totalRowWidth;
		
		return new Point(width+HORIZONTAL_ICON_SEPARATION*2,height+VERTICAL_ICON_SEPARATION*2);
		
	}
	
	public void setLeftTop(int x, int y){
		bounds = new Rect(x,y,width+x,height+y);
	}
	

}
