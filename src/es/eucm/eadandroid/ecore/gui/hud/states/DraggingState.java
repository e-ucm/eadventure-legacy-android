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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import es.eucm.eadandroid.common.data.chapter.Action;
import es.eucm.eadandroid.common.data.chapter.elements.Item;
import es.eucm.eadandroid.ecore.control.ActionManager;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalScene;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UnPressedEvent;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;

public class DraggingState extends HUDstate {
	
	public FunctionalElement draggingElement;
	public float originalDragX, originalDragY;
	private int x, y;

	public DraggingState(HUD stateContext) {
		super(stateContext);
		
	}

	@Override
	public void update(long elapsedTime) {
		
	}
	
	@Override
	public void doDraw(Canvas c) {
		
		Paint textP = new Paint(Paint.ANTI_ALIAS_FLAG);
		textP.setColor(0xFFFFFFFF);
		textP.setShadowLayer(4f, 0, 0, Color.BLACK);
		textP.setTextSize(20 * GUI.DISPLAY_DENSITY_SCALE);
		textP.setTypeface(Typeface.SANS_SERIF);
		textP.setTextAlign(Align.CENTER);
		
		FunctionalElement fe = Game.getInstance().getActionManager().getElementOver();

		if (fe!=null && fe != this.draggingElement && draggingElement != null) {
    		c.drawText("Drag to " + fe.getElement().getName(), x , y - 3 * (draggingElement.getHeight( ) * draggingElement.getScale( )) / 4, textP);
		}
	}
	

	@Override
	public boolean processScrollPressed(UIEvent e) {
				
		ScrollPressedEvent ev = (ScrollPressedEvent) e;
		
		x = (int)ev.eventDst.getX();
		y = (int)ev.eventDst.getY();
		
		FunctionalScene functionalScene = Game.getInstance().getFunctionalScene( );
        if( functionalScene != null ) {
            FunctionalElement elementInside = functionalScene.getElementInside( (int)((x - GUI.CENTER_OFFSET) / GUI.SCALE_RATIOX), (int)(y / GUI.SCALE_RATIOY), draggingElement );
            Game.getInstance().getActionManager( ).setElementOver( elementInside );
        }

        if (draggingElement != null) {
        	draggingElement.setX( ( x - GUI.CENTER_OFFSET) / GUI.SCALE_RATIOX  );
            draggingElement.setY( y / GUI.SCALE_RATIOY + draggingElement.getHeight( ) * draggingElement.getScale( ) / 4);
        }
		
		return false;					
	}
	

	@Override
	public boolean processUnPressed(UIEvent e) {
		
		UnPressedEvent ev = (UnPressedEvent) e;
		
		x = (int)ev.event.getX();
		y = (int)ev.event.getY();
		
		FunctionalScene functionalScene = Game.getInstance().getFunctionalScene( );
        if( functionalScene != null ) {
            FunctionalElement elementInside = functionalScene.getElementInside( (int)((x - GUI.CENTER_OFFSET) / GUI.SCALE_RATIOX), (int)(y / GUI.SCALE_RATIOY), draggingElement );
            Game.getInstance().getActionManager( ).setElementOver( elementInside );
        }
				
		if( draggingElement != null  && Game.getInstance().getFunctionalPlayer( ).getCurrentAction( ).getType( ) == Action.DRAG_TO) {
            processDragTo(ev);
        }
        
        stateContext.setState(HUDstate.HiddenState,null); 
		
		return true;
		
	}
	
	public boolean startDragging(FunctionalElement pressedElement) {
		
        Game.getInstance().getActionManager( ).setActionSelected( ActionManager.ACTION_DRAG_TO );
        Game.getInstance().getFunctionalPlayer( ).performActionInElement( pressedElement );
        originalDragX = pressedElement.getX( );
        originalDragY = pressedElement.getY( );
        draggingElement = pressedElement;
        return true;
    }
    
    private boolean processDragTo( UnPressedEvent e ) {
    	
    	x = (int)e.event.getX();
		y = (int)e.event.getY();
    	
        Game.getInstance().getActionManager( ).setActionSelected( ActionManager.ACTION_DRAG_TO );
        FunctionalScene functionalScene = Game.getInstance().getFunctionalScene( );
        if( functionalScene == null ) {
            clearDraggingElement();
            return false;
        }
        
        FunctionalElement elementInside = functionalScene.getElementInside( (int)((x - GUI.CENTER_OFFSET) / GUI.SCALE_RATIOX), (int)(y / GUI.SCALE_RATIOY), draggingElement );
        
        if (elementInside == null)
        	Game.getInstance().getFunctionalPlayer( ).cancelActions( );
        else 
        	Game.getInstance().getFunctionalPlayer( ).performActionInElement( elementInside );
        	
        clearDraggingElement();
        
        return true;
    }
    
    public void clearDraggingElement() {
        if(draggingElement != null) {
        	draggingElement.setDragging(false);
            if (draggingElement.getElement( ) instanceof Item) {
                if (((Item) draggingElement.getElement( )).isReturnsWhenDragged( )) {
                    draggingElement.setX( originalDragX );
                    draggingElement.setY( originalDragY );
                }
            }
        }
        draggingElement = null;
        Game.getInstance().getActionManager().resetDragElement(); 
        Game.getInstance().getActionManager().resetElementInCursor();
        Game.getInstance().getActionManager().setActionSelected( ActionManager.ACTION_GOTO );
    }

}
