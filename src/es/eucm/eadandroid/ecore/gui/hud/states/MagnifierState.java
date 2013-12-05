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
package es.eucm.eadandroid.ecore.gui.hud.states;

import android.graphics.Canvas;
import android.view.MotionEvent;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.PressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.Magnifier;

public class MagnifierState extends HUDstate {	

	Magnifier magnifier;
	
	private int srcX, srcY;
	
	public MagnifierState(HUD stateContext , Magnifier mag) {
		super(stateContext);
		magnifier = mag;	
	}
	
	@Override
	public void doDraw(Canvas c) {
		magnifier.doDraw(c);
	}
	
	@Override
	public boolean processPressed(UIEvent e) {	
		
		MotionEvent m = ((PressedEvent) e).event;
		
		srcX = (int) m.getX();
		srcY = (int) m.getY();
		
		magnifier.show();
		magnifier.updateMagPos(srcX, srcY);
		
		return false;
	}

	@Override
	public boolean processScrollPressed(UIEvent e) {
		
		MotionEvent m = ((ScrollPressedEvent) e).eventDst;
		
		srcX = (int) m.getX();
		srcY = (int) m.getY();
		
		if (!magnifier.isShown())
			magnifier.show();
		magnifier.updateMagPos(srcX, srcY);		
				
		return false;
	}
	
	@Override
	public boolean processUnPressed(UIEvent e) {
		
		magnifier.hide();
		
		FunctionalElement elementOver = Game.getInstance().getActionManager().getElementOver();
		FunctionalElement elementInCursor = Game.getInstance().getActionManager().getElementInCursor();
		
		if (elementOver!=null && elementInCursor==null)  {
			stateContext.setState(HUDstate.ActionsState, elementOver);
		}
		else stateContext.setState(HUDstate.HiddenState,null);
		
		return false;
	}
	
}
