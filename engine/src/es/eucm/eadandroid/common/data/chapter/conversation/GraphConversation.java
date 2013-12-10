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
import java.util.List;

import es.eucm.eadandroid.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadandroid.common.data.chapter.conversation.node.DialogueConversationNode;

public class GraphConversation extends Conversation {

    /**
     * Graph conversation constructor.
     * 
     * @param conversationName
     *            Name of the conversation
     */
    public GraphConversation( String conversationName ) {

        super( Conversation.GRAPH, conversationName, new DialogueConversationNode( ) );
    }

    /**
     * Graph conversation constructor.
     * 
     * @param conversationName
     *            Name of the conversation
     * @param root
     *            Root of the conversation
     */
    public GraphConversation( String conversationName, ConversationNode root ) {

        super( Conversation.GRAPH, conversationName, root );
    }

    public GraphConversation( TreeConversation conversation ) {

        super( Conversation.GRAPH, conversation.getId( ), conversation.getRootNode( ) );
    }

    /**
     * Returns a list with all the nodes in the conversation.
     * 
     * @return List with the nodes of the conversation
     */
    @Override
    public List<ConversationNode> getAllNodes( ) {

        List<ConversationNode> nodes = new ArrayList<ConversationNode>( );

        nodes.add( getRootNode( ) );
        int i = 0;
        while( i < nodes.size( ) ) {
            ConversationNode temp = nodes.get( i );
            i++;
            for( int j = 0; j < temp.getChildCount( ); j++ ) {
                ConversationNode temp2 = temp.getChild( j );
                if( !nodes.contains( temp2 ) )
                    nodes.add( temp2 );
            }
        }

        return nodes;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        GraphConversation gc = (GraphConversation) super.clone( );
        return gc;
    }
}
