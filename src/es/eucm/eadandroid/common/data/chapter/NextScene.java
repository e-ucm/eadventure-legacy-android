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
package es.eucm.eadandroid.common.data.chapter;

import es.eucm.eadandroid.common.data.HasTargetId;
import es.eucm.eadandroid.common.data.Positioned;
import es.eucm.eadandroid.common.data.chapter.ExitLook;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;

/**
 * This class holds the data of a next scene in eAdventure
 */
public class NextScene implements Cloneable, HasTargetId, Positioned {

    public static final int NO_TRANSITION = 0;

    public static final int TOP_TO_BOTTOM = 1;

    public static final int BOTTOM_TO_TOP = 2;

    public static final int LEFT_TO_RIGHT = 3;

    public static final int RIGHT_TO_LEFT = 4;

    public static final int FADE_IN = 5;

    /**
     * Id of the target scene
     */
    private String nextSceneId;

    /**
     * X position in which the player should appear in the new scene
     */
    private int destinyX;

    /**
     * Y position in which the player should appear in the new scene
     */
    private int destinyY;

    /**
     * Conditions of the next scene
     */
    private Conditions conditions;

    /**
     * Effects triggered before exiting the current scene
     */
    private Effects effects;

    /**
     * Post effects triggered after exiting the current scene
     */
    private Effects postEffects;

    /**
     * A list of assets. Required to store, when desired, customized cursors for
     * the exits
     */
    private ExitLook look;

    private int transitionType;

    private int transitionTime;

    /**
     * Creates a new NextScene
     * 
     * @param nextSceneId
     *            the id of the next scene
     */
    public NextScene( String nextSceneId ) {

        this.nextSceneId = nextSceneId;

        destinyX = Integer.MIN_VALUE;
        destinyY = Integer.MIN_VALUE;
        conditions = new Conditions( );
        effects = new Effects( );
        postEffects = new Effects( );
        transitionType = NO_TRANSITION;
        transitionTime = 0;
    }

    /**
     * Creates a new nextScene
     * 
     * @param nextScene
     *            the id of the next scene
     * @param positionX
     *            the horizontal position of the player when he/she gets into
     *            this scene
     * @param positionY
     *            the vertical position of the player when he/she gets into this
     *            scene
     */
    public NextScene( String nextScene, int positionX, int positionY ) {

        this.nextSceneId = nextScene;

        this.destinyX = positionX;
        this.destinyY = positionY;
        conditions = new Conditions( );
        effects = new Effects( );
        postEffects = new Effects( );
        transitionType = NO_TRANSITION;
        transitionTime = 0;
    }

    /**
     * Returns the id of the next scene
     * 
     * @return the id of the next scene
     */
    public String getTargetId( ) {

        return nextSceneId;
    }

    /**
     * Returns whether this scene has been assigned a position for a player that
     * just came in
     * 
     * @return true if this scene has a position assigned, false otherwise
     */
    public boolean hasPlayerPosition( ) {

        return ( destinyX != Integer.MIN_VALUE ) && ( destinyY != Integer.MIN_VALUE );
    }

    /**
     * Returns the horizontal position of the player when he/she gets into this
     * scene
     * 
     * @return the horizontal position of the player when he/she gets into this
     *         scene
     */
    public int getPositionX( ) {

        return destinyX;
    }

    /**
     * Returns the vertical position of the player when he/she gets into this
     * scene
     * 
     * @return the verticalal position of the player when he/she gets into this
     *         scene
     */
    public int getPositionY( ) {

        return destinyY;
    }

    /**
     * Returns the conditions for this next scene
     * 
     * @return the conditions for this next scene
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    /**
     * Returns the effects for this next scene
     * 
     * @return the effects for this next scene
     */
    public Effects getEffects( ) {

        return effects;
    }

    /**
     * Returns the post-effects for this next scene
     * 
     * @return the post-effects for this next scene
     */
    public Effects getPostEffects( ) {

        return postEffects;
    }

    /**
     * Sets a new next scene id.
     * 
     * @param nextSceneId
     *            New next scene id
     */
    public void setTargetId( String nextSceneId ) {

        this.nextSceneId = nextSceneId;
    }

    /**
     * Sets the new destiny position for the next scene.
     * 
     * @param positionX
     *            X coordinate of the destiny position
     * @param positionY
     *            Y coordinate of the destiny position
     */
    public void setDestinyPosition( int positionX, int positionY ) {

        setPositionX( positionX );
        setPositionY( positionY );
    }

    /**
     * Sets the new destiny position X for the next scene.
     * 
     * @param positionX
     *            X coordinate of the destiny position
     */
    public void setPositionX( int positionX ) {

        this.destinyX = positionX;
    }

    /**
     * Sets the new destiny position Y for the next scene.
     * 
     * @param positionY
     *            Y coordinate of the destiny position
     */
    public void setPositionY( int positionY ) {

        this.destinyY = positionY;
    }

    /**
     * Changes the conditions for this next scene
     * 
     * @param conditions
     *            The new conditions
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    /**
     * Changes the effects for this next scene
     * 
     * @param effects
     *            The new effects
     */
    public void setEffects( Effects effects ) {

        this.effects = effects;
    }

    /**
     * Changes the post-effects for this next scene
     * 
     * @param postEffects
     *            The new post-effects
     */
    public void setPostEffects( Effects postEffects ) {

        this.postEffects = postEffects;
    }

    /**
     * @return the exitText
     */
    public String getExitText( ) {

        String exitText = null;
        //if (new FunctionalConditions(getConditions( )).allConditionsOk( ) && look!=null){
        if( look != null ) {
            exitText = look.getExitText( );
        }
        return exitText;
    }

    /**
     * Returns the cursor of the first resources block which all conditions are
     * met
     * 
     * @return the cursor. Null is returned if no look had its conditions
     *         satisfied, or if no one was set
     */
    public String getCursorPath( ) {

        String cursorPath = null;
        //if (new FunctionalConditions(getConditions( )).allConditionsOk( ) && look!=null){
        if( look != null ) {
            cursorPath = look.getCursorPath( );
        }
        return cursorPath;
    }

    public ExitLook getExitLook( ) {

        return this.look;
    }

    public void setExitLook( ExitLook exitLook ) {

        look = exitLook;
    }

    public int getTransitionType( ) {

        return transitionType;
    }

    public int getTransitionTime( ) {

        return transitionTime;
    }

    public void setTransitionType( int transitionType ) {

        this.transitionType = transitionType;
    }

    public void setTransitionTime( int transitionTime ) {

        this.transitionTime = transitionTime;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        NextScene ns = (NextScene) super.clone( );
        ns.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        ns.destinyX = destinyX;
        ns.destinyY = destinyY;
        ns.effects = ( effects != null ? (Effects) effects.clone( ) : null );
        ns.look = ( look != null ? (ExitLook) look.clone( ) : null );
        ns.nextSceneId = ( nextSceneId != null ? new String( nextSceneId ) : null );
        ns.postEffects = ( postEffects != null ? (Effects) postEffects.clone( ) : null );
        return ns;
    }

}
