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
package es.eucm.eadandroid.common.data.chapter.conversation.node;

import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.conversation.line.ConversationLine;

public interface ConversationNodeView extends Cloneable {

    /**
     * Constant for dialogue node.
     */
    public static final int DIALOGUE = 0;

    /**
     * Constant for option node.
     */
    public static final int OPTION = 1;

    /**
     * Returns the type of the current node.
     * 
     * @return DIALOGUE if dialogue node, OPTION if option node
     */
    public int getType( );

    /**
     * Returns if the node is terminal (has no children).
     * 
     * @return True if the node is terminal, false otherwise
     */
    public boolean isTerminal( );

    /**
     * Returns the children's number of the node.
     * 
     * @return The number of children
     */
    public int getChildCount( );

    /**
     * Returns the view conversation node in the given position.
     * 
     * @param index
     *            Index of the child
     * @return Selected reduced child
     */
    public ConversationNodeView getChildView( int index );

    /**
     * Returns the lines' number of the node.
     * 
     * @return The number of lines
     */
    public abstract int getLineCount( );

    /**
     * Returns whether the given line belongs to the player or not.
     * 
     * @param index
     *            Index of the line
     * @return True if the line belongs to the player, false otherwise
     */
    public abstract boolean isPlayerLine( int index );

    /**
     * Returns the name of the line in the given index.
     * 
     * @param index
     *            Index of the line
     * @return Name of the line
     */
    public abstract String getLineName( int index );

    /**
     * Returns the text of the line in the given index.
     * 
     * @param index
     *            Index of the line
     * @return Text of the line
     */
    public abstract String getLineText( int index );

    /**
     * Returns the path of the audio for the given index.
     * 
     * @param index
     *            Index of the line
     * @return Text of the line
     */
    public abstract String getAudioPath( int index );

    /**
     * Checks whether the line for the given index has a valid audio path
     * 
     * @param index
     *            Index of the line
     * 
     * @return True if has audio path, false otherwise
     */
    public abstract boolean hasAudioPath( int index );

    /**
     * Returns if the node has a valid effect set.
     * 
     * @return True if the node has a set of effects, false otherwise
     */
    public boolean hasEffects( );

    /**
     * Returns the conditions of the line in the given index.
     * 
     * @param index
     *            Index of the line
     * @return Conditions of the line
     */
    public Conditions getLineConditions( int index );

    /**
     * Returns the conversation line in the given index.
     * 
     * @param index
     *            Index of the line
     * @return Conversation Line
     */
    public ConversationLine getConversationLine( int index );
}
