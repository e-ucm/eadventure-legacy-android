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
import es.eucm.eadandroid.common.data.chapter.elements.NPC;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.ecore.control.ActionManager;
import es.eucm.eadandroid.ecore.control.DebugLog;
import es.eucm.eadandroid.ecore.control.animations.Animation;
import es.eucm.eadandroid.ecore.control.animations.AnimationState;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;
import es.eucm.eadandroid.ecore.data.GameText;
import es.eucm.eadandroid.multimedia.MultimediaManager;

/**
 * The action to use an element
 * 
 * @author Eugenio Marchiori
 */
public class FunctionalUse extends FunctionalAction {

    /**
     * The element to use
     */
    private FunctionalElement element;

    /**
     * The total elapsed time of the action
     */
    private long totalTime;

    /**
     * True if the element can be used
     */
    private boolean canUse = false;

    /**
     * Constructor with the element to use
     * 
     * @param element
     *            The element to be used
     */
    public FunctionalUse( FunctionalElement element ) {

        super( null );
        this.type = ActionManager.ACTION_USE;
        this.element = element;
        originalAction = element.getFirstValidAction( ActionManager.ACTION_USE );
        if( element.isInInventory( ) || originalAction == null ) {
            this.needsGoTo = false;
        }
        else {
            this.needsGoTo = originalAction.isNeedsGoTo( );
            this.keepDistance = originalAction.getKeepDistance( );
        }
    }

    @Override
    public void drawAditionalElements( ) {

    }

    @Override
    public void setAnotherElement( FunctionalElement element ) {

    }

    @Override
    public void start( FunctionalPlayer functionalPlayer ) {

        this.functionalPlayer = functionalPlayer;
        this.needsGoTo = true;
        this.finished = false;
        totalTime = 0;

        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );

        Animation[] animations = new Animation[ 4 ];
        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION ) )
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );

        if( functionalPlayer.getX( ) < element.getX( ) ) {
            functionalPlayer.setDirection( AnimationState.EAST );
        }
        else {
            functionalPlayer.setDirection( AnimationState.WEST );
        }
        functionalPlayer.setAnimation( animations, -1 );

    }

    @Override
    public void stop( ) {

        finished = true;
    }

    @Override
    public void update( long elapsedTime ) {

        totalTime += elapsedTime;
        if( !finished && !canUse ) {
            canUse = element.use( );
            if( !canUse ) {
                DebugLog.player( "Can't use " + element.getElement( ).getId( ) );
                if( functionalPlayer.isAlwaysSynthesizer( ) )
                    functionalPlayer.speakWithFreeTTS( GameText.getTextUseCannot( ), functionalPlayer.getPlayerVoice( ) );
                else
                    functionalPlayer.speak( GameText.getTextUseCannot( ) );
                functionalPlayer.popAnimation( );
                finished = true;
            }
        }
        else if( !finished && totalTime > 1000 ) {
            DebugLog.player( "Used " + element.getElement( ).getId( ) );
            finished = true;
            functionalPlayer.popAnimation( );
        }
    }

}
