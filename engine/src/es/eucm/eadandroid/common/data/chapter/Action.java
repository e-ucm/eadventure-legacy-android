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

import es.eucm.eadandroid.common.data.Documented;
import es.eucm.eadandroid.common.data.HasTargetId;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;

/**
 * An action that can be done during the game.
 */
public class Action implements Cloneable, Documented, HasTargetId {

    /**
     * An action of type examine.
     */
    public static final int EXAMINE = 0;

    /**
     * An action of type grab.
     */
    public static final int GRAB = 1;

    /**
     * An action of type give-to.
     */
    public static final int GIVE_TO = 2;

    /**
     * An action of type use-with.
     */
    public static final int USE_WITH = 3;

    /**
     * An action of type use
     */
    public static final int USE = 4;

    /**
     * A custom action
     */
    public static final int CUSTOM = 5;

    /**
     * A custom interaction action
     */
    public static final int CUSTOM_INTERACT = 6;

    /**
     * An action of the type talk-to
     */
    public static final int TALK_TO = 7;
    
    /**
     * An action of type drag to
     */
    public static final int DRAG_TO = 8;

    /**
     * Stores the action type
     */
    private int type;

    /**
     * Documentation of the action.
     */
    private String documentation;

    /**
     * Id of the target of the action (in give to and use with)
     */
    private String idTarget;

    /**
     * Conditions of the action
     */
    private Conditions conditions;

    /**
     * Effects of performing the action
     */
    private Effects effects;

    /**
     * Alternative effects, when the conditions aren't OK
     */
    private Effects notEffects;

    /**
     * Activate not effects
     */
    private boolean activatedNotEffects;

    /**
     * Indicates whether the character needs to go up to the object
     */
    private boolean needsGoTo;

    /**
     * Indicates the minimum distance the character should leave between the
     * object and himself
     */
    private int keepDistance;

    /**
     * Constructor.
     * 
     * @param type
     *            The type of the action
     */
    public Action( int type ) {

        this( type, null, new Conditions( ), new Effects( ), new Effects( ) );
        switch( type ) {
            case EXAMINE:
                needsGoTo = false;
                keepDistance = 0;
                break;
            case GRAB:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case GIVE_TO:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case USE_WITH:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case USE:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case TALK_TO:
                needsGoTo = true;
                keepDistance = 35;
                break;
            case DRAG_TO:
                needsGoTo = false;
                keepDistance = 0;
                break;
            default:
                needsGoTo = false;
                keepDistance = 0;
                break;
        }
    }

    /**
     * Constructor.
     * 
     * @param type
     *            The type of the action
     * @param idTarget
     *            The target of the action
     */
    public Action( int type, String idTarget ) {

        this( type, idTarget, new Conditions( ), new Effects( ), new Effects( ) );
    }

    /**
     * Constructor
     * 
     * @param type
     *            The type of the action
     * @param conditions
     *            The conditions of the action (must not be null)
     * @param effects
     *            The effects of the action (must not be null)
     * @param notEffects
     *            The effects of the action when the conditions aren't OK (must
     *            not be null)
     */
    public Action( int type, Conditions conditions, Effects effects, Effects notEffects ) {

        this( type, null, conditions, effects, notEffects );
    }

    /**
     * Constructor
     * 
     * @param type
     *            The type of the action
     * @param idTarget
     *            The target of the action
     * @param conditions
     *            The conditions of the action (must not be null)
     * @param effects
     *            The effects of the action (must not be null)
     * @param notEffects
     *            The effects of the action when the conditions aren't OK (must
     *            not be null)
     */
    public Action( int type, String idTarget, Conditions conditions, Effects effects, Effects notEffects ) {

        this.type = type;
        this.idTarget = idTarget;
        this.conditions = conditions;
        this.effects = effects;
        this.notEffects = notEffects;
        documentation = null;
    }

    /**
     * Returns the type of the action.
     * 
     * @return the type of the action
     */
    public int getType( ) {

        return type;
    }

    /**
     * Returns the documentation of the action.
     * 
     * @return the documentation of the action
     */
    public String getDocumentation( ) {

        return documentation;
    }

    /**
     * Returns the target of the action.
     * 
     * @return the target of the action
     */
    public String getTargetId( ) {

        return idTarget;
    }

    /**
     * Returns the conditions of the action.
     * 
     * @return the conditions of the action
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    /**
     * Returns the effects of the action.
     * 
     * @return the effects of the action
     */
    public Effects getEffects( ) {

        return effects;
    }

    /**
     * Changes the documentation of this action.
     * 
     * @param documentation
     *            The new documentation
     */
    public void setDocumentation( String documentation ) {

        this.documentation = documentation;
    }

    /**
     * Changes the id target of this action.
     * 
     * @param idTarget
     *            The new id target
     */
    public void setTargetId( String idTarget ) {

        this.idTarget = idTarget;
    }

    /**
     * Changes the conditions for this next scene
     * 
     * @param conditions
     *            the new conditions
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    /**
     * Changes the effects for this next scene
     * 
     * @param effects
     *            the new effects
     */
    public void setEffects( Effects effects ) {

        this.effects = effects;
    }

    /**
     * @return the needsGoTo
     */
    public Boolean isNeedsGoTo( ) {

        return needsGoTo;
    }

    /**
     * @param needsGoTo
     *            the needsGoTo to set
     */
    public void setNeedsGoTo( Boolean needsGoTo ) {

        this.needsGoTo = needsGoTo;
    }

    /**
     * @return the keepDistance
     */
    public Integer getKeepDistance( ) {

        return keepDistance;
    }

    /**
     * @param keepDistance
     *            the keepDistance to set
     */
    public void setKeepDistance( Integer keepDistance ) {

        this.keepDistance = keepDistance;
    }

    /**
     * @return the notEffects
     */
    public Effects getNotEffects( ) {

        return notEffects;
    }

    /**
     * @param notEffects
     *            the notEffects to set
     */
    public void setNotEffects( Effects notEffects ) {

        this.notEffects = notEffects;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {
        Action a = (Action) super.clone( );
        a.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        a.documentation = ( documentation != null ? new String( documentation ) : null );
        a.effects = ( effects != null ? (Effects) effects.clone( ) : null );
        a.idTarget = ( idTarget != null ? new String( idTarget ) : null );
        a.keepDistance = keepDistance;
        a.needsGoTo = needsGoTo;
        a.type = type;
        a.notEffects = ( notEffects != null ? (Effects) notEffects.clone( ) : null );
        a.activatedNotEffects = activatedNotEffects;
        return a;
    }

    /**
     * @return the activateNotEffects
     */
    public boolean isActivatedNotEffects( ) {

        return activatedNotEffects;
    }

    /**
     * @param activateNotEffects
     *            the activateNotEffects to set
     */
    public void setActivatedNotEffects( boolean activateNotEffects ) {

        this.activatedNotEffects = activateNotEffects;
    }

}
