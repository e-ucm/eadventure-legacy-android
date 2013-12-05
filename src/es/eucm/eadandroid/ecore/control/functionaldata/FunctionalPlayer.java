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
package es.eucm.eadandroid.ecore.control.functionaldata;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;
import android.os.Debug;
import android.util.Log;
import es.eucm.eadandroid.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadandroid.common.data.chapter.Action;
import es.eucm.eadandroid.common.data.chapter.CustomAction;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.common.data.chapter.InfluenceArea;
import es.eucm.eadandroid.common.data.chapter.elements.Element;
import es.eucm.eadandroid.common.data.chapter.elements.NPC;
import es.eucm.eadandroid.common.data.chapter.elements.Player;
import es.eucm.eadandroid.common.data.chapter.resources.Asset;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.data.chapter.scenes.Scene;
import es.eucm.eadandroid.ecore.control.ActionManager;
import es.eucm.eadandroid.ecore.control.DebugLog;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.animations.Animation;
import es.eucm.eadandroid.ecore.control.animations.AnimationState;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalAction;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalCustom;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalCustomInteract;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalDragTo;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalExamine;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalGive;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalGoTo;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalGrab;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalLook;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalNullAction;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalSpeak;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalTalk;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalUse;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalUseWith;
import es.eucm.eadandroid.ecore.data.GameText;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * The player
 */
public class FunctionalPlayer extends FunctionalElement implements TalkingElement {

    /**
     * Default speed of the player.
     */
    public static final float DEFAULT_SPEED = 120.0f;

    public static final float SPEED_TRANSPARENT_MODE = 5000;

    /**
     * Speed in the X coordinate.
     */
    protected float speedX;

    /**
     * Speed in the Y coordinate.
     */
    protected float speedY;

    /**
     * Player containing the data
     */
    private Player player;

    /**
     * Front color of the player's text
     */
    private int textFrontColor;

    /**
     * Border color of the player's text
     */
    private int textBorderColor;

    private int bubbleBkgColor;

    private int bubbleBorderColor;

    /**
     * Resources being used by the character
     */
    private Resources resources;

    /**
     * The exit where the player is heading
     */
    private Exit targetExit;

    /**
     * Current player's direction
     */
    private int currentDirection = -1;

    public List<FunctionalAction> actionPool;

    public List<Animation[]> animationPool;

    private boolean isTransparent = false;

    private float oldScale = -1;

    private Bitmap oldImage = null;

    private Bitmap oldOriginalImage = null;
    
    private boolean keepShowingGlobal;
   

    /**
     * @return the isTransparent
     */
    public boolean isTransparent( ) {

        return isTransparent;
    }

    /**
     * @param isTransparent
     *            the isTransparent to set
     */
    public void setTransparent( boolean isTransparent ) {

        this.isTransparent = isTransparent;
    }

    /**
     * Creates a new FunctionalPlayer
     * 
     * @param player
     *            the player's data
     */
    public FunctionalPlayer( Player player ) {
        super( 0, 0 );
        
    	/* DEBUG MEMORY ALLOCATION */
    	
    	long playerMem =  Debug.getNativeHeapAllocatedSize();
    	
    	/////////////////////////////////////////////////////////
        
        this.player = player;
        speedX = 0;
        speedY = 0;
        layer = -1;
        scale = 1;
        currentDirection = AnimationState.EAST;
        // Select the resources block
        resources = createResourcesBlock( );

        actionPool = new ArrayList<FunctionalAction>( );
        animationPool = new ArrayList<Animation[]>( );
        
        Log.e("PlayerAnimAntes",String.valueOf(Debug.getNativeHeapAllocatedSize()));
        
        animationPool.add( loadPlayerAnimations() );
        
        Log.e("PlayerAnimDespues",String.valueOf(Debug.getNativeHeapAllocatedSize()));

        textFrontColor = generateColor( player.getTextFrontColor( ) );
        textBorderColor = generateColor( player.getTextBorderColor( ) );
        bubbleBkgColor = generateColor( player.getBubbleBkgColor( ) );
        bubbleBorderColor = generateColor( player.getBubbleBorderColor( ) );
        keepShowingGlobal = Game.getInstance( ).getGameDescriptor( ).isKeepShowing( );
        
        
    	/////////////////////////////////////////////////////////
    	
    	playerMem =  Debug.getNativeHeapAllocatedSize() - playerMem;
    	
    	Log.e("PlayerMem",String.valueOf(playerMem /1048576)+"MB");
    	
    	
    	/////////////////////////////////////////////////////////
        
    }
    
    private Animation[] loadPlayerAnimations(){
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        
        Animation[] animations = new Animation[ 4 ];
        
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION )
                && !resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa" ) )
            animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ), true, MultimediaManager.IMAGE_PLAYER );
      
        if( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ) != null && !resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION )
                && !resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ).equals( SpecialAssetPaths.ASSET_EMPTY_ANIMATION + ".eaa" ))
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
        else
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
       
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_STAND_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
        
        return animations;
    }

    public boolean isAlwaysSynthesizer( ) {

        return player.isAlwaysSynthesizer( );
    }

    public String getPlayerVoice( ) {

        return player.getVoice( );
    }

    /**
     * Updates the resources of the npc (if the current resources and the new
     * one are different)
     */
    public void updateResources( ) {

        // Get the new resources
        Resources newResources = createResourcesBlock( );

        // If the resources have changed, load the new one
        if( resources != newResources ) {
            resources = newResources;

            // Flush the past resources from the images cache
            MultimediaManager.getInstance( ).flushImagePool( MultimediaManager.IMAGE_PLAYER );

            this.animationPool.clear( );
            this.animationPool.add( loadPlayerAnimations() );
        }
    }

    @Override
    public Element getElement( ) {

        return player;
    }

    @Override
    public int getWidth( ) {

        return getCurrentAnimation( ).getImage( ).getWidth(  );
    }

    @Override
    public int getHeight( ) {

        return getCurrentAnimation( ).getImage( ).getHeight(  );
    }

    /**
     * Adds a new action to the pool. The walking action will be added as
     * needed.
     * 
     * @param action
     *            The action to add to the pool
     * @param functionalElement
     *            The element on with the action is performed
     */
    public void addAction( FunctionalAction action ) {

        actionPool.add( action );
    }

    /**
     * Cancel all actions currently being performed
     */
    public void cancelActions( ) {

        getCurrentAction( ).stop( );
        actionPool.clear( );
        cancelAnimations( );
    }

    public FunctionalAction getCurrentAction( ) {

        if( actionPool.size( ) > 0 )
            return actionPool.get( actionPool.size( ) - 1 );
        else
            return new FunctionalNullAction( );
    }

    public void popAction( ) {

        if( actionPool.size( ) > 0 )
            actionPool.remove( actionPool.size( ) - 1 );
    }

    /**
     * Adds a new animation to the animation pool, the exact result depends on
     * whether the animation is to be repeated indefinitely or a limited number
     * of times.
     * 
     * @param animation
     * @param repeat
     */
    public void setAnimation( Animation[] animations, int repeat ) {

        //TODO check behavior
        if( repeat != -1 ) {
            animationPool.add( animations );
        }
        else {
            if( animationPool.size( ) > 1 )
                animationPool.remove( animationPool.size( ) - 1 );
            animationPool.add( animations );
        }
    }

    /**
     * When an action is completed, it is likely it would like to remove the
     * animation it set
     */
    public void popAnimation( ) {

        if( animationPool.size( ) > 1 ) {
            animationPool.remove( animationPool.size( ) - 1 );
        }
    }

    public Animation getCurrentAnimation( ) {

        if( currentDirection >= 0 && currentDirection < 4 )
            return animationPool.get( animationPool.size( ) - 1 )[currentDirection];
        else
            return animationPool.get( animationPool.size( ) - 1 )[0];
    }

    public void cancelAnimations( ) {

        if( animationPool.size( ) > 0 ) {
            Animation[] temp = animationPool.get( 0 );
            animationPool.clear( );
            animationPool.add( temp );
        }
    }

    /**
     * Performs the given action with the given element
     * 
     * @param element
     *            the element that will receive the action
     * @param actionSelected
     *            the action to be performed
     */
    public void performActionInElement( FunctionalElement element ) {

        Game game = Game.getInstance( );
        int actionSelected = Game.getInstance( ).getActionManager( ).getActionSelected( );
        if( actionSelected == ActionManager.ACTION_LOOK ) {
            addAction( new FunctionalLook( element ) );
            return;
        }
        
        FunctionalAction nextAction = new FunctionalNullAction( );
        switch( actionSelected ) {
            case ActionManager.ACTION_EXAMINE:
                cancelActions( );
                nextAction = new FunctionalExamine( null, element );
                break;
            case ActionManager.ACTION_GIVE:
                if( element.canPerform( actionSelected ) ) {
                    if( element.isInInventory( ) ) {
                        cancelActions( );
                        nextAction = new FunctionalGive( null, element );
                        game.getActionManager( ).setActionSelected( ActionManager.ACTION_GIVE_TO );
                    }
                    else {
                        if( player.isAlwaysSynthesizer( ) )
                            speakWithFreeTTS( GameText.getTextGiveObjectNotInventory( ), player.getVoice( ) );
                        else
                            speak( GameText.getTextGiveObjectNotInventory( ), keepShowingGlobal );
                    }
                }
                else {
                    if( player.isAlwaysSynthesizer( ) )
                        speakWithFreeTTS( GameText.getTextGiveNPC( ), player.getVoice( ) );
                    else
                        speak( GameText.getTextGiveNPC( ), keepShowingGlobal );
                }
                break;
            case ActionManager.ACTION_GIVE_TO:
                if( element.canPerform( actionSelected ) ) {
                    if( getCurrentAction( ).getType( ) == ActionManager.ACTION_GIVE ) {
                        nextAction = getCurrentAction( );
                        popAction( );
                        ( (FunctionalGive) nextAction ).setAnotherElement( element );
                    }
                }
                else {
                    popAction( );
                    speak( GameText.getTextGiveCannot( ), keepShowingGlobal );
                }
                break;
            case ActionManager.ACTION_GRAB:
                cancelActions( );
                if( element.canPerform( actionSelected ) ) {
                    if( !element.isInInventory( ) ) {
                        nextAction = new FunctionalGrab( null, element );
                    }
                    else
                        speak( GameText.getTextGrabObjectInventory( ), keepShowingGlobal );
                }
                else
                    speak( GameText.getTextGrabNPC( ), keepShowingGlobal );
                break;
            case ActionManager.ACTION_TALK:
                cancelActions( );
                if( element.canPerform( actionSelected ) ) {
                    nextAction = new FunctionalTalk( null, element );
                }
                else
                    speak( GameText.getTextTalkObject( ), keepShowingGlobal );
                break;
            case ActionManager.ACTION_USE:
                if( element.canPerform( actionSelected ) ) {
                    if( element.canBeUsedAlone( ) ) {
                        cancelActions( );
                        nextAction = new FunctionalUse( element );
                    }
                    else {
                        cancelActions( );
                        nextAction = new FunctionalUseWith( null, element );
                        game.getActionManager( ).setActionSelected( ActionManager.ACTION_USE_WITH );
                    }
                }
                else {
                    popAction( );
                    speak( GameText.getTextUseNPC( ), keepShowingGlobal );
                }
                break;
            case ActionManager.ACTION_DRAG_TO:
                if (getCurrentAction().getType( ) == Action.DRAG_TO) {
                    nextAction = getCurrentAction();
                    popAction();
                    if (nextAction != null && element != null && nextAction instanceof FunctionalDragTo) {
                        ((FunctionalDragTo) nextAction).setAnotherElement(element);
                    }
                }
                else {
                    nextAction = new FunctionalDragTo( element );
                    game.getActionManager( ).setActionSelected( ActionManager.ACTION_DRAG_TO );
                }
                break;
            case ActionManager.ACTION_CUSTOM:
                nextAction = new FunctionalCustom( element, Game.getInstance( ).getActionManager( ).getCustomActionName( ) );
                break;
            case ActionManager.ACTION_CUSTOM_INTERACT:
                if( getCurrentAction( ).getType( ) == Action.CUSTOM_INTERACT ) {
                    nextAction = getCurrentAction( );
                    popAction( );
                    if( nextAction != null && element != null && nextAction instanceof FunctionalCustomInteract ) {
                        ( (FunctionalCustomInteract) nextAction ).setAnotherElement( element );
                    }
                }
                else {
                    nextAction = new FunctionalCustomInteract( element, Game.getInstance( ).getActionManager( ).getCustomActionName( ) );
                }
                break;
            case ActionManager.ACTION_USE_WITH:
                if( element.canPerform( actionSelected ) ) {
                    if( getCurrentAction( ).getType( ) == ActionManager.ACTION_USE_WITH ) {
                        nextAction = getCurrentAction( );
                        popAction( );
                        ( (FunctionalUseWith) nextAction ).setAnotherElement( element );
                    }
                }
                else {
                    popAction( );
                    speak( GameText.getTextUseNPC( ), keepShowingGlobal );
                }
                break;
        }

        if( nextAction.isNeedsGoTo( ) && !this.isTransparent ) {
            FunctionalGoTo functionalGoTo = new FunctionalGoTo( null, (int) element.getX( ), (int) element.getY( ), this, element );
            if( functionalGoTo.canGetTo( ) ) {
                addAction( nextAction );
                functionalGoTo.setKeepDistance( nextAction.getKeepDistance( ) );
                addAction( functionalGoTo );
            }
            /*else {
                addAction( functionalGoTo ); this avoids a lock of player
            }*/
            return;
        }
        else
            addAction( nextAction );
        return;

    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    public void draw( ) {

        if( !isTransparent ) {
            Bitmap image = getCurrentAnimation( ).getImage( );

            int realX = (int) ( x - ( image.getWidth(  ) * scale / 2 ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ) );
            int realY = (int) ( y - ( image.getHeight(  ) * scale ) );

            if( image == oldOriginalImage && scale == oldScale ) {
                image = oldImage;
            }
            else if( scale != 1 ) {
                oldOriginalImage = image;  
                image = Bitmap.createScaledBitmap(oldOriginalImage, Math.round( oldOriginalImage.getWidth(  ) * scale ),  Math.round( oldOriginalImage.getHeight(  ) * scale ), true);
                /*image = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( Math.round( image.getWidth(  ) * scale ),  Math.round( image.getHeight(  ) * scale ), true );
                Canvas c = new Canvas(image);
                Matrix m = new Matrix();
                m.setScale(scale,scale);
                c.drawBitmap(oldOriginalImage, m, null);*/                
            }
            else {
                oldOriginalImage = image;
            }

            oldScale = scale;
            oldImage = image;

            if( layer == Scene.PLAYER_WITHOUT_LAYER || layer == Scene.PLAYER_NO_ALLOWED )
                GUI.getInstance( ).addPlayerToDraw( image, realX, realY, Math.round( y ), Math.round( y ) );
            else
                GUI.getInstance( ).addElementToDraw( image, realX, realY, layer, Math.round( y ), null, null );

        }
        
        if( getCurrentAction( ).isStarted( ) && !getCurrentAction( ).isFinished( ) )
            getCurrentAction( ).drawAditionalElements( );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#update(long)
     */
    public void update( long elapsedTime ) {

        while( getCurrentAction( ).isFinished( ) ) {
            this.popAction( );
        }
        if( !getCurrentAction( ).isStarted( ) ) {
            getCurrentAction( ).start( this );
        }
        else {
            getCurrentAction( ).update( elapsedTime );
        }
        //TODO check if the animation must be pop because it repeated as indicated
        getCurrentAnimation( ).update( elapsedTime );
    }

    @Override
    public boolean isPointInside( float x, float y ) {

        return ( this.x - getWidth( ) / 2 < x ) && ( x < this.x + getWidth( ) / 2 ) && ( this.y - getHeight( ) < y ) && ( y < this.y );
    }

    @Override
    public boolean canPerform( int action ) {

        boolean canPerform = false;

        switch( action ) {
            case ActionManager.ACTION_LOOK:
            case ActionManager.ACTION_EXAMINE:
            case ActionManager.ACTION_CUSTOM:
                canPerform = true;
                break;

            case ActionManager.ACTION_GRAB:
            case ActionManager.ACTION_USE:
            case ActionManager.ACTION_GIVE:
            case ActionManager.ACTION_GOTO:
            case ActionManager.ACTION_USE_WITH:
            case ActionManager.ACTION_GIVE_TO:
            case ActionManager.ACTION_TALK:
            case ActionManager.ACTION_CUSTOM_INTERACT:
                canPerform = false;
                break;
        }

        return canPerform;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.TalkingElement#speak(java.lang.String)
     */
    public void speak( String text, boolean keepShowing ) {

    	text = processName(text);
        if( text != null ) {
            DebugLog.player( "Player says " + text );
            FunctionalSpeak functionalSpeak = new FunctionalSpeak( null, text, keepShowing );
            addAction( functionalSpeak );
        }
    }
    
    public void speak( String text){
        speak( text, false);
    }

    public void speak( String text, String audioPath, boolean keepShowing ) {

    	text = processName(text);
        DebugLog.player( "Player says " + text + " with audio" );
        FunctionalSpeak functionalSpeak = new FunctionalSpeak( null, text, audioPath, keepShowing );
        addAction( functionalSpeak );
    }
    
    public void speak( String text, String audioPath) {
        speak( text, audioPath, false );
    }

    /**
     * 
     */
    public void speakWithFreeTTS( String text, String voice, boolean keepShowing ) {

    	text = processName(text);
        if( text != null ) {
            DebugLog.player( "Player speaks with text-to-speech" );
            FunctionalSpeak functionalSpeak = new FunctionalSpeak( null, text, keepShowing );
            if (voice != null && !voice.equals( "" ))
                functionalSpeak.setSpeakFreeTTS( text, voice );
            
            addAction( functionalSpeak );
            
        }
    }
    
    /**
     * Speak with TTS without keep showing the line
     * @param text
     * @param voice
     */
    public void speakWithFreeTTS( String text, String voice ) {
        speakWithFreeTTS( text, voice, false);
    }
    
    /**
     * Look for [] tag to add the name of the speaker
     * 
     * @param text
     * @return
     */
    public String processName(String text){
        
        if (text!= null && text.startsWith( "[]" ))
           text = text.replaceFirst( "\\[\\]", "[ " + Player.IDENTIFIER + " ]");
           
        return text;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.TalkingElement#stopTalking()
     */
    public void stopTalking( ) {

        if( getCurrentAction( ).getType( ) == ActionManager.ACTION_TALK ) {
            getCurrentAction( ).stop( );
            this.cancelActions( );
        }
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.TalkingElement#isTalking()
     */
    public boolean isTalking( ) {

        return getCurrentAction( ).getType( ) == ActionManager.ACTION_TALK;
    }

    /**
     * Returns if the player is walking
     * 
     * @return true if the player is walking, false otherwise
     */
    public boolean isWalking( ) {

        return getCurrentAction( ).getType( ) == ActionManager.ACTION_GOTO;
    }

    /**
     * Changes the player's target exit
     * 
     * @param exit
     *            the new target exit
     */
    public void setTargetExit( Exit exit ) {

        targetExit = exit;
    }

    /**
     * Returns the player's target exit
     * 
     * @return the player's target exit
     */
    public Exit getTargetExit( ) {

        return targetExit;
    }

    /**
     * Changes the player's current direction
     * 
     * @param direction
     *            the new player's direction
     */
    public void setDirection( int direction ) {

        currentDirection = direction;
    }

    /**
     * Return the las direction set
     */
    public int getDirection( ) {

        return currentDirection;
    }

    /**
     * Returns the X coordinate speed.
     * 
     * @return X coordinate speed
     */
    public float getSpeedX( ) {

        return speedX;
    }

    /**
     * Sets the new X coordinate speed.
     * 
     * @param speedX
     *            New X coordinate speed
     */
    public void setSpeedX( float speedX ) {

        if( !isTransparent )
            this.speedX = speedX;
        else if( speedX < 0 ) {
            this.speedX = -SPEED_TRANSPARENT_MODE;
        }
        else if( speedX > 0 ) {
            this.speedX = SPEED_TRANSPARENT_MODE;
        }
        else {
            this.speedX = 0;
        }

    }

    /**
     * Returns the Y coordinate speed.
     * 
     * @return Y coordinate speed
     */
    public float getSpeedY( ) {

        return speedY;
    }

    /**
     * Sets the new Y coordinate speed.
     * 
     * @param speedY
     *            New Y coordinate speed
     */
    public void setSpeedY( float speedY ) {

        this.speedY = speedY;
    }

    /**
     * Returns the player's data
     * 
     * @return the player's data
     */
    public Player getPlayer( ) {

        return player;
    }

    /**
     * Returns the front color of the player's text
     * 
     * @return Front color of the text
     */
    public int getTextFrontColor( ) {

        return textFrontColor;
    }

    /**
     * Returns the border color of the player's text
     * 
     * @return Border color of the text
     */
    public int getTextBorderColor( ) {

        return textBorderColor;
    }

    /**
     * Returns the resources of the npc
     * 
     * @return Resources of the npc
     */
    public Resources getResources( ) {

        return resources;
    }

    /**
     * Creates the current resource block to be used
     */
    public Resources createResourcesBlock( ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < player.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( player.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = player.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if( newResources == null ) {
            newResources = new Resources( );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_SPEAK_DOWN, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_SPEAK_UP, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_SPEAK_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_WALK_DOWN, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_WALK_UP, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_WALK_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_USE_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_STAND_UP, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_STAND_DOWN, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_STAND_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
        }
        return newResources;
    }

    @Override
    public Action getFirstValidAction( int actionType ) {

        return null;
    }

    @Override
    public CustomAction getFirstValidCustomAction( String actionName ) {

        return null;
    }

    @Override
    public CustomAction getFirstValidCustomInteraction( String actionName ) {

        return null;
    }

    @Override
    public InfluenceArea getInfluenceArea( ) {

        return null;
    }

    public int getBubbleBkgColor( ) {

        return bubbleBkgColor;
    }

    public int getBubbleBorderColor( ) {

        return bubbleBorderColor;
    }

    public boolean getShowsSpeechBubbles( ) {

        return player.getShowsSpeechBubbles( );
    }

}
