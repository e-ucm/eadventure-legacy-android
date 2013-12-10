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
package es.eucm.eadandroid.common.data.chapter.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.eucm.eadandroid.common.data.HasId;
import es.eucm.eadandroid.common.data.chapter.conversation.node.ConversationNode;

/**
 * Implements Tree and Graph conversations
 */
public abstract class Conversation implements Cloneable, HasId {

    /**
     * Constant for tree conversations.
     */
    public static final int TREE = 0;

    /**
     * Constant for graph conversations.
     */
    public static final int GRAPH = 1;

    /* Attributes */

    /**
     * Type of the conversation.
     */
    private int conversationType;

    /**
     * Reference name of the conversation
     */
    private String conversationId;

    /**
     * Root of the conversation
     */
    private ConversationNode root;

    /* Methods */

    /**
     * Constructor
     * 
     * @param conversationType
     *            Type of the conversation
     * @param conversationId
     *            Identifier of the conversation
     * @param root
     *            Root node (start) of the conversation
     */
    protected Conversation( int conversationType, String conversationId, ConversationNode root ) {

        this.conversationType = conversationType;
        this.conversationId = conversationId;
        this.root = root;
    }

    /**
     * Returns the type of the conversation.
     * 
     * @return Conversation's type
     */
    public int getType( ) {

        return conversationType;
    }

    /**
     * Returns the name of the conversation.
     * 
     * @return Conversation's name
     */
    public String getId( ) {

        return conversationId;
    }

    /**
     * Returns the initial node of the conversation, the one which starts the
     * conversation.
     * 
     * @return First node of the conversation
     */
    public ConversationNode getRootNode( ) {

        return root;
    }

    /**
     * Sets the a new identifier for the conversation.
     * 
     * @param id
     *            New identifier
     */
    public void setId( String id ) {

        this.conversationId = id;
    }

    public List<ConversationNode> getAllNodes( ) {

        List<ConversationNode> nodes = new ArrayList<ConversationNode>( );
        getAllNodes( root, nodes );
        return nodes;
    }

    private void getAllNodes( ConversationNode firstNode, List<ConversationNode> nodes ) {

        for( int i = -1; i < firstNode.getChildCount( ); i++ ) {
            ConversationNode child = null;
            if( i == -1 )
                child = firstNode;
            else
                child = firstNode.getChild( i );
            // Check the child is not in the list yet
            boolean isInList = false;
            for( ConversationNode aNode : nodes ) {
                if( aNode == child ) {
                    isInList = true;
                    break;
                }

            }
            if( !isInList ) {
                nodes.add( child );
                getAllNodes( child, nodes );
            }
        }
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Conversation c = (Conversation) super.clone( );
        c.conversationId = ( conversationId != null ? new String( conversationId ) : null );
        c.conversationType = conversationType;

        HashMap<ConversationNode, ConversationNode> clonedNodes = new HashMap<ConversationNode, ConversationNode>( );

        c.root = ( root != null ? (ConversationNode) root.clone( ) : null );

        clonedNodes.put( root, c.root );
        List<ConversationNode> nodes = new ArrayList<ConversationNode>( );
        List<ConversationNode> visited = new ArrayList<ConversationNode>( );
        nodes.add( root );

        while( !nodes.isEmpty( ) ) {
            ConversationNode temp = nodes.get( 0 );
            ConversationNode cloned = clonedNodes.get( temp );
            nodes.remove( 0 );
            visited.add( temp );

            for( int i = 0; i < temp.getChildCount( ); i++ ) {
                ConversationNode tempCloned = clonedNodes.get( temp.getChild( i ) );
                if( tempCloned == null ) {
                    tempCloned = (ConversationNode) temp.getChild( i ).clone( );
                    clonedNodes.put( temp.getChild( i ), tempCloned );
                }
                cloned.addChild( tempCloned );

                if( !visited.contains( temp.getChild( i ) ) && !nodes.contains( temp.getChild( i ) ) )
                    nodes.add( temp.getChild( i ) );
            }
        }
        return c;
    }
}
