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
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;

/**
 * This abstract class represents an action that can be taken by the player.
 * Every class that extends from it must implement the methods to create a new
 * action, set the necessary animations and update the state until the action is
 * finished.
 * 
 * @author Eugenio Marchiori
 */
public abstract class FunctionalAction {

    /**
     * The original action in the data structure that took to the creation of
     * this functional action.
     */
    protected Action originalAction;

    /**
     * The functional player in the game
     */
    protected FunctionalPlayer functionalPlayer;

    /**
     * True if the action has finished being taken
     */
    protected boolean finished = false;

    /**
     * True if the action needs another element to be taken (ie. Use-with or
     * custom-interact)
     */
    protected boolean requiersAnotherElement = false;

    /**
     * True if the action needs the player to reach the element over with it
     * takes place
     */
    protected boolean needsGoTo = false;

    /**
     * The type of the action
     */
    protected int type;

    /**
     * The distance to keep between player and element in the case the former
     * must reach the later to be performed
     */
    protected int keepDistance = 0;

    /**
     * Default constructor, taking an original action.
     * 
     * @param action
     *            The original action
     */
    public FunctionalAction( Action action ) {

        originalAction = action;
    }

    /**
     * Start the action, the player is already positioned to take it.
     * 
     * @param functionalPlayer
     *            The functional player of the game
     */
    public abstract void start( FunctionalPlayer functionalPlayer );

    /**
     * Returns true if the action has already started
     * 
     * @return True if the action has already started
     */
    public boolean isStarted( ) {

        return functionalPlayer != null;
    }

    /**
     * Returns true if the action requires another element to be taken
     * 
     * @return True if the action requires another element
     */
    public boolean isRequiersAnotherElement( ) {

        return requiersAnotherElement;
    }

    /**
     * Returns true if the action has finished
     * 
     * @return True if the action has finished
     */
    public boolean isFinished( ) {

        return finished;
    }

    /**
     * Returns true if the action needs the player to go up to the element (or
     * its influence area if the scene has trajectories) to perform it.
     * 
     * @return True if the player needs to get to the element
     */
    public boolean isNeedsGoTo( ) {

        return needsGoTo;
    }

    /**
     * Update the state of the action after a certain time.
     * 
     * @param elapsedTime
     *            The time elapsed since the last updated
     */
    public abstract void update( long elapsedTime );

    /**
     * Set the other element in case it was needed by the action.
     * 
     * @param element
     *            The other element needed by the action
     */
    public abstract void setAnotherElement( FunctionalElement element );

    /**
     * Returns the type of the action
     * 
     * @return The type of the action
     */
    public int getType( ) {

        return type;
    }

    /**
     * Stop the execution of the action
     */
    public abstract void stop( );

    /**
     * Draw additional element needed by the action in the scene
     */
    public abstract void drawAditionalElements( );

    /**
     * Get the distance that must be kept between player and the element.
     * 
     * @return The distance to keep between player and element
     */
    public int getKeepDistance( ) {

        return keepDistance;
    }

    /**
     * Set the distance that must be kept between player and the element.
     * 
     * @param keepDistance
     *            The new distance to keep between player and element
     */
    public void setKeepDistance( int keepDistance ) {

        this.keepDistance = keepDistance;
    }

    /**
     * Get the other element of the action.
     * 
     * @return The other element of the action.
     */
    public FunctionalElement getAnotherElement( ) {

        return null;
    }
}
