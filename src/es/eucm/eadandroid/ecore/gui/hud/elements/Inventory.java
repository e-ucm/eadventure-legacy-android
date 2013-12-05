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
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;

public class Inventory {

	/**
	 * All pixels are in Dip's -> 1dip = 1px in an android default screen HVGA
	 * 480*320 - 160dpi
	 **/

	/** Folding - Unfolding region in dips **/

	public static final int UNFOLD_REGION_POSY = (int) (20 * GUI.DISPLAY_DENSITY_SCALE);
	public static final int FOLD_REGION_POSY = (int) ((GUI.FINAL_WINDOW_HEIGHT - 20 * GUI.DISPLAY_DENSITY_SCALE));
	public static final int FOLD_REGION_POSX = (int) ((GUI.FINAL_WINDOW_WIDTH - 20 * GUI.DISPLAY_DENSITY_SCALE));

	private static final int drag_offset = (int) (15 * GUI.DISPLAY_DENSITY_SCALE);

	/** INVENTORY PAINT DEFINITION */

	Picture inventoryPicture;

	/** Inventory panel **/

	private static final int IPANEL_WIDTH = GUI.FINAL_WINDOW_WIDTH;
	private static final int IPANEL_HEIGHT = GUI.FINAL_WINDOW_HEIGHT;

	private static final int TRANSPARENT_PADDING = (int) (20 * GUI.DISPLAY_DENSITY_SCALE);

	/** Rounded rect panel */

	private static final int ROUNDED_RECT_STROKE_WIDTH = (int) (3 * GUI.DISPLAY_DENSITY_SCALE);
	private static final float ROUNDED_RECT_ROUND_RADIO = 15f * GUI.DISPLAY_DENSITY_SCALE;

	private static final int RPANEL_PADDING = (int) (10 * GUI.DISPLAY_DENSITY_SCALE);

	private RectF r;
	private Paint p;
	private Paint paintBorder;

	/** Grid panel */

	GridPanel gridPanel;


	/** INENTORY PANEL ANIMATION COORDINATES & VARIABLES */

	/** IPANEL **/

	int iPanelBottom;

	boolean animating;
	boolean anchored;

	public Inventory() {

		iPanelBottom = 0;
		animating = false;
		anchored = false;

		p = new Paint();
		p.setColor(Color.BLACK);
		p.setStyle(Style.FILL);
		p.setAntiAlias(true);

		paintBorder = new Paint();
		paintBorder.setColor(Color.WHITE);
		paintBorder.setStyle(Style.STROKE);
		paintBorder.setStrokeWidth(ROUNDED_RECT_STROKE_WIDTH);
		paintBorder.setAntiAlias(true);


		int roundRectWidth = IPANEL_WIDTH - 2 * TRANSPARENT_PADDING;
		int roundRectHeight = IPANEL_HEIGHT - 2 * TRANSPARENT_PADDING;

		r = new RectF(0, 0, roundRectWidth, roundRectHeight);

		inventoryPicture = new Picture();

		Canvas c = inventoryPicture.beginRecording(IPANEL_WIDTH, IPANEL_HEIGHT);

		c.drawARGB(150, 0, 0, 0);
		c.translate(TRANSPARENT_PADDING, TRANSPARENT_PADDING);
		c.drawRoundRect(r, ROUNDED_RECT_ROUND_RADIO, ROUNDED_RECT_ROUND_RADIO,
				p);
		c.drawRoundRect(r, ROUNDED_RECT_ROUND_RADIO, ROUNDED_RECT_ROUND_RADIO,
				paintBorder);
		c.translate(30 * GUI.DISPLAY_DENSITY_SCALE,
				30 * GUI.DISPLAY_DENSITY_SCALE);

		inventoryPicture.endRecording();

		Rect gridPanelBounds = new Rect(TRANSPARENT_PADDING + RPANEL_PADDING,
				TRANSPARENT_PADDING + RPANEL_PADDING, IPANEL_WIDTH - TRANSPARENT_PADDING -RPANEL_PADDING,
				IPANEL_HEIGHT - TRANSPARENT_PADDING-RPANEL_PADDING);

		gridPanel = new GridPanel(gridPanelBounds,GridPanel.DEFAULT_ICON_HEIGHT,GridPanel.DEFAULT_ICON_WIDTH);

	}

	public void doDraw(Canvas c) {
		if (iPanelBottom > 0){
			c.save();
			c.clipRect(0, 0, IPANEL_WIDTH, iPanelBottom);
			c.save();
			c.translate(0, iPanelBottom - IPANEL_HEIGHT);
			inventoryPicture.draw(c);
			c.restore();
			c.translate(0, iPanelBottom - IPANEL_HEIGHT);
			c.translate(TRANSPARENT_PADDING + RPANEL_PADDING, TRANSPARENT_PADDING
					+ RPANEL_PADDING);
			// FIXME estooo tiene que quitarse de aqui y ponerl en otro sitio ;D!!!
			gridPanel.setDataSet(Game.getInstance().getInventory());
			gridPanel.draw(c);
			c.restore();
		}

	}

	public void updateDraggingPos(int y) {

		iPanelBottom = Math.min(IPANEL_HEIGHT, y + drag_offset);
	}

	public boolean isAnimating() {
		if (iPanelBottom > IPANEL_HEIGHT / 2)
			animating = true;
		return animating;
	}

	public void resetPos() {

		iPanelBottom = 0;

	}

	public void update(long elapsedTime) {

		if (animating) {
			if (iPanelBottom < IPANEL_HEIGHT) {
				iPanelBottom = Math.min(IPANEL_HEIGHT, iPanelBottom + 15);
			} else {
				anchored = true;
				animating = false;
			}
		} else
			gridPanel.update(elapsedTime);

	}

	public boolean isAnchored() {
		return anchored;
	}

	public boolean pointInGrid(int x, int y) {
		return gridPanel.getBounds().contains(x, y);

	}

	public void updateDraggingGrid(int x) {
		gridPanel.updateDragging(x);

	}

	public void gridSwipe(long initialTime, int velocityX) {

		gridPanel.swipe(initialTime, velocityX);

	}

	public Object selectItemFromGrid(int x, int y) {

		return gridPanel.selectItem(x, y);

	}

	public void setItemFocus(int posX, int posY) {
		gridPanel.setItemFocus(posX, posY);

	}

	public void resetItemFocus() {
		gridPanel.resetItemFocus();

	}

}
