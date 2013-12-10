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

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class ActionButtons implements DataSet{
	

//    public static final int MAX_BUTTON_WIDTH = (int) ( 160 * GUI.DISPLAY_DENSITY_SCALE );
//
//    public static final int MAX_BUTTON_HEIGHT = (int) ( 96 * GUI.DISPLAY_DENSITY_SCALE );
//
//    public static final int MIN_BUTTON_WIDTH = (int) ( 80 * GUI.DISPLAY_DENSITY_SCALE );
//
//    public static final int MIN_BUTTON_HEIGHT = (int) ( 48 * GUI.DISPLAY_DENSITY_SCALE );

   private List<ActionButton> buttons = new ArrayList<ActionButton>();
    

	public Object getItem(int i) {

		return buttons.get(i);
	}

	public int getItemCount() {
		return buttons.size();
	}

	public Bitmap getNormalImageIcon(int i) {
		return buttons.get(i).getButtonNormal();
	}
	
	public Bitmap getPressedImageIcon(int i) {
		return buttons.get(i).getButtonPressed();
	}

	public String getItemName(int i) {
		return buttons.get(i).getName();
	}

	public void clear() {
		buttons.clear();	
	}

	public void add(ActionButton actionButton) {
		buttons.add(actionButton);
	}

}
