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
package es.eucm.eadandroid.ecore.control.functionaldata.functionalactions;

import es.eucm.eadandroid.common.data.chapter.Action;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalItem;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalNPC;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;
import es.eucm.eadandroid.ecore.data.GameText;

/**
 * A custom action defined by the user that needs to objects to be performed.
 * 
 * @author Eugenio Marchiori
 * 
 */
public class FunctionalDragTo extends FunctionalAction {

    /**
     * The first element of the action
     */
    private FunctionalElement element;

    /**
     * The other element of the action
     */
    private FunctionalElement anotherElement;

    /**
     * The total time of the action
     */
    private long totalTime;

    /**
     * Default constructor for a custom interact action
     * 
     * @param element
     *            The first element of the action
     * @param customActionName
     *            The name of the action
     */
    public FunctionalDragTo( FunctionalElement element) {
        super( null );
        this.type = Action.DRAG_TO;
        originalAction = element.getFirstValidAction( Action.DRAG_TO );
        this.element = element;
        this.requiersAnotherElement = true;
        this.needsGoTo = false;
        this.finished = false;
    }

    @Override
    public void drawAditionalElements( ) {

    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {
        this.anotherElement = element;
        this.requiersAnotherElement = false;
        if (originalAction!=null){
        	this.needsGoTo = originalAction.isNeedsGoTo( );
        	this.keepDistance = originalAction.getKeepDistance( );
        }
    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {
        if( !requiersAnotherElement ) {
            this.functionalPlayer = functionalPlayer;
            this.totalTime = 0;
        }
    }

    @Override
    public void stop( ) {
        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

        if( anotherElement != null ) {
            totalTime += elapsedTime;
            if( totalTime > 200 ) {
                
                if (element instanceof FunctionalItem)
                    spreadDrag((FunctionalItem) element);
                else if (element instanceof FunctionalNPC)
                    spreadDrag((FunctionalNPC) element);
                
                
                finished = true;
            }
        } 
    }
    
    //TODO include dragTo in FunctionalElement to avoid duplicate code
    private void spreadDrag(FunctionalItem item1){
        
        if( anotherElement instanceof FunctionalItem ) {
            FunctionalItem item2 = (FunctionalItem) anotherElement;
            if( !item1.dragTo(item2 ) ) {
                if( functionalPlayer.isAlwaysSynthesizer( ) )
                    functionalPlayer.speakWithFreeTTS( GameText.getTextCustomCannot( ), functionalPlayer.getPlayerVoice( ) );
                else
                    functionalPlayer.speak( GameText.getTextCustomCannot( ) );
            }
        }
        if( anotherElement instanceof FunctionalNPC ) {
            FunctionalNPC npc = (FunctionalNPC) anotherElement;
            if( !item1.dragTo( npc ) ) {
                if( functionalPlayer.isAlwaysSynthesizer( ) )
                    functionalPlayer.speakWithFreeTTS( GameText.getTextCustomCannot( ), functionalPlayer.getPlayerVoice( ) );
                else
                    functionalPlayer.speak( GameText.getTextCustomCannot( ) );
            }
        }
    }
    
   private void spreadDrag(FunctionalNPC item1){
        
        if( anotherElement instanceof FunctionalItem ) {
            FunctionalItem item2 = (FunctionalItem) anotherElement;
            if( !item1.dragTo(item2 ) ) {
                if( functionalPlayer.isAlwaysSynthesizer( ) )
                    functionalPlayer.speakWithFreeTTS( GameText.getTextCustomCannot( ), functionalPlayer.getPlayerVoice( ) );
                else
                    functionalPlayer.speak( GameText.getTextCustomCannot( ) );
            }
        }
        if( anotherElement instanceof FunctionalNPC ) {
            FunctionalNPC npc = (FunctionalNPC) anotherElement;
            if( !item1.dragTo( npc ) ) {
                if( functionalPlayer.isAlwaysSynthesizer( ) )
                    functionalPlayer.speakWithFreeTTS( GameText.getTextCustomCannot( ), functionalPlayer.getPlayerVoice( ) );
                else
                    functionalPlayer.speak( GameText.getTextCustomCannot( ) );
            }
        }
    }

}
