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

import es.eucm.eadandroid.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadandroid.common.data.chapter.Action;
import es.eucm.eadandroid.common.data.chapter.elements.NPC;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.ecore.control.ActionManager;
import es.eucm.eadandroid.ecore.control.DebugLog;
import es.eucm.eadandroid.ecore.control.animations.Animation;
import es.eucm.eadandroid.ecore.control.animations.AnimationState;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalItem;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalNPC;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;
import es.eucm.eadandroid.ecore.data.GameText;
import es.eucm.eadandroid.multimedia.MultimediaManager;

/**
 * The action to give an element to an NPC
 * 
 * @author Eugenio Marchiori
 * 
 */
public class FunctionalGive extends FunctionalAction {

    /**
     * The element to give
     */
    private FunctionalElement element;

    /**
     * The other element of the action (an NPC in this case)
     */
    private FunctionalElement anotherElement;

    /**
     * The total elapsed time of the action
     */
    private long totalTime;

    /**
     * True if the element can be given to the other element of the action
     */
    private boolean canGive = false;

    /**
     * Default constructor, with the original action and the element to be given
     * 
     * @param action
     *            The original action
     * @param element
     *            The element to be given
     */
    public FunctionalGive( Action action, FunctionalElement element ) {

        super( action );
        this.element = element;
        this.type = ActionManager.ACTION_GIVE;
        originalAction = element.getFirstValidAction( Action.GIVE_TO );
        requiersAnotherElement = true;
    }

    @Override
    public void drawAditionalElements( ) {

    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {

        requiersAnotherElement = false;
        if( originalAction != null ) {
            needsGoTo = originalAction.isNeedsGoTo( );
            keepDistance = originalAction.getKeepDistance( );
        }
        else {
            needsGoTo = true;
            keepDistance = 35;
        }
        anotherElement = element;
    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {

        this.functionalPlayer = functionalPlayer;

        Resources resources = functionalPlayer.getResources( );

        MultimediaManager multimedia = MultimediaManager.getInstance( );
        if( anotherElement.getX( ) > functionalPlayer.getX( ) ) {
            functionalPlayer.setDirection( AnimationState.EAST );
        }
        else {
            functionalPlayer.setDirection( AnimationState.WEST );
        }
        Animation[] animations = new Animation[ 4 ];
        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION ) )
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        functionalPlayer.setAnimation( animations, -1 );

    }

    @Override
    public void stop( ) {

        if( functionalPlayer != null && !finished ) {
            functionalPlayer.popAnimation( );
        }
        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

        if( anotherElement != null ) {
            totalTime += elapsedTime;
            if( element instanceof FunctionalItem && anotherElement instanceof FunctionalNPC ) {
                FunctionalItem item = (FunctionalItem) element;
                FunctionalNPC npc = (FunctionalNPC) anotherElement;

                if( !finished && !canGive ) {
                    canGive = item.giveTo( npc );
                    if( !canGive ) {
                        DebugLog.player( "Can't give: " + item.getItem( ).getId( ) + " to " + npc.getNPC( ).getId( ) );
                        if( functionalPlayer.isAlwaysSynthesizer( ) )
                            functionalPlayer.speakWithFreeTTS( GameText.getTextGiveCannot( ), functionalPlayer.getPlayerVoice( ) );
                        else
                            functionalPlayer.speak( GameText.getTextGiveCannot( ) );
                        functionalPlayer.popAnimation( );
                        finished = true;
                    }
                }
                else if( !finished && totalTime > 1000 ) {
                    DebugLog.player( "Gave: " + item.getItem( ).getId( ) + " to " + npc.getNPC( ).getId( ) );
                    finished = true;
                    functionalPlayer.popAnimation( );
                }
            }
            else {
                DebugLog.player( "Invalid give..." );
                finished = true;
            }
        }

    }

    @Override
    public FunctionalElement getAnotherElement( ) {

        return anotherElement;
    }

}
