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
package es.eucm.eadandroid.common.loader.subparsers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadandroid.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadandroid.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadandroid.common.data.chapter.conversation.node.DialogueConversationNode;
import es.eucm.eadandroid.common.data.chapter.conversation.node.OptionConversationNode;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;

/**
 * Class to subparse graph conversations
 */
public class GraphConversationSubParser extends SubParser {

    /* Attributes */

    /**
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing effect tag
     */
    private static final int SUBPARSING_EFFECT = 1;

    /**
     * Constant for subparsing conditions
     */
    private static final int SUBPARSING_CONDITION = 2;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * Name of the conversation
     */
    private String conversationName;

    /**
     * Stores the current node
     */
    private ConversationNode currentNode;

    /**
     * Set of nodes for the graph
     */
    private List<ConversationNode> graphNodes;

    /**
     * Stores the current set of links (of the current node)
     */
    private List<Integer> currentLinks;

    /**
     * Bidimensional vector for storing the links between nodes (the first
     * dimension holds the nodes, the second one the links)
     */
    private List<List<Integer>> nodeLinks;

    /**
     * Current effect (of the current node)
     */
    private Effects currentEffects;

    /**
     * Subparser for the effect
     */
    private SubParser subParser;

    /**
     * Name of the last non-player character read, "NPC" is no name were found
     */
    private String characterName;

    /**
     * Path of the audio track for a conversation line
     */
    private String audioPath;

    /**
     * Check if the options in option node may be random
     */
    private boolean random;
    
    /**
     * Check if the previous line will be showed at options node
     */
    private boolean keepShowing;
    
    /**
     * Keep showing for each conversation line
     */
    private boolean keepShowingLine;
    
    /**
     * Check if the user's response will be showed
     */
    private boolean showUserOption;
    
    /**
     * Check if each conversation line will wait until user interacts
     */
    private boolean keepShowingDialogue;

    /**
     * Check if a conversation line must be synthesize
     */
    private Boolean synthesizerVoice;

    /**
     * Stores the current conditions being read
     */
    private Conditions currentConditions;

    /**
     * Store the current conversation line
     */
    private ConversationLine conversationLine;

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public GraphConversationSubParser( Chapter chapter ) {

        super( chapter );
    }

    /*
     * (non-Javadoc)
     * 
     * @see conversationaleditor.xmlparser.ConversationParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            // If it is a "graph-conversation" we pick the name, so we can build the conversation later
            if( sName.equals( "graph-conversation" ) ) {
                // Store the name
                conversationName = "";
                for( int i = 0; i < attrs.getLength( ); i++ )
                    if( attrs.getLocalName( i ).equals( "id" ) )
                        conversationName = attrs.getValue( i );

                graphNodes = new ArrayList<ConversationNode>( );
                nodeLinks = new ArrayList<List<Integer>>( );
            }

            // If it is a node, create a new node
            else if( sName.equals( "dialogue-node" ) || sName.equals( "option-node" ) ) {
                // Create the node depending of the tag
                if( sName.equals( "dialogue-node" ) ){
                	for( int i = 0; i < attrs.getLength( ); i++ ) {
                        //If there is a "waitUserInteraction" attribute, store if the lines will wait until user interacts
                        if( attrs.getQName( i ).equals( "keepShowing" ) ) {
                            if( attrs.getValue( i ).equals( "yes" ) )
                                keepShowingDialogue = true;
                            else
                                keepShowingDialogue = false;
                        }
                    
                    currentNode = new DialogueConversationNode(keepShowingDialogue);

                    }
                }
                
                if( sName.equals( "option-node" ) ) {
                    for( int i = 0; i < attrs.getLength( ); i++ ) {
                        //If there is a "random" attribute, store is the options will be random
                        if( attrs.getLocalName( i ).equals( "random" ) ) {
                            if( attrs.getValue( i ).equals( "yes" ) )
                                random = true;
                            else
                                random = false;
                        }
                      //If there is a "keepShowing" attribute, keep the previous conversation line showing
                        if( attrs.getLocalName( i ).equals( "keepShowing" ) ) {
                            if( attrs.getValue( i ).equals( "yes" ) )
                                keepShowing = true;
                            else
                                keepShowing = false;
                        }
                      //If there is a "showUserOption" attribute, identify if show the user response at option node
                        if( attrs.getLocalName( i ).equals( "showUserOption" ) ) {
                            if( attrs.getValue( i ).equals( "yes" ) )
                                showUserOption = true;
                            else
                                showUserOption = false;
                        }
                    }

                    currentNode = new OptionConversationNode( random, keepShowing, showUserOption );
                }
                // Create a new vector for the links of the current node
                currentLinks = new ArrayList<Integer>( );
            }

            // If it is a non-player character line, store the character name and audio path (if present)
            else if( sName.equals( "speak-char" ) ) {
                // Set default name to "NPC"
                characterName = "NPC";
                audioPath = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    // If there is a "idTarget" attribute, store it
                    if( attrs.getLocalName( i ).equals( "idTarget" ) )
                        characterName = attrs.getValue( i );

                    // If there is a "uri" attribute, store it as audio path
                    if( attrs.getLocalName( i ).equals( "uri" ) )
                        audioPath = attrs.getValue( i );
                    // If there is a "synthesize" attribute, store its value
                    if( attrs.getLocalName( i ).equals( "synthesize" ) ) {
                        String response = attrs.getValue( i );
                        if( response.equals( "yes" ) )
                            synthesizerVoice = true;
                        else
                            synthesizerVoice = false;
                    }
                    // If there is a "keepShowing" attribute, store its value
                    if( attrs.getLocalName( i ).equals( "keepShowing" ) ) {
                        String response = attrs.getValue( i );
                        if( response.equals( "yes" ) )
                            keepShowingLine = true;
                        else
                            keepShowingLine = false;
                    }
                }
            }

            // If it is a player character line, store the audio path (if present)
            else if( sName.equals( "speak-player" ) ) {
                audioPath = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {

                    // If there is a "uri" attribute, store it as audio path
                    if( attrs.getLocalName( i ).equals( "uri" ) )
                        audioPath = attrs.getValue( i );
                    // If there is a "synthesize" attribute, store its value
                    if( attrs.getLocalName( i ).equals( "synthesize" ) ) {
                        String response = attrs.getValue( i );
                        if( response.equals( "yes" ) )
                            synthesizerVoice = true;
                        else
                            synthesizerVoice = false;
                    }
                    
                    // If there is a "keepShowing" attribute, store its value
                    if( attrs.getLocalName( i ).equals( "keepShowing" ) ) {
                        String response = attrs.getValue( i );
                        if( response.equals( "yes" ) )
                            keepShowingLine = true;
                        else
                            keepShowingLine = false;
                    }
                
                }
            }

            // If it is a node to a child, store the number of the child node
            else if( sName.equals( "child" ) ) {
                // Look for the index of the link, and add it
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "nodeindex" ) ) {
                        // Get the child node index, and store it
                        Integer childIndex = new Integer( attrs.getValue( i ) );
                        currentLinks.add( childIndex );
                    }
                }
            }

            // If it is an effect tag
            else if( sName.equals( "effect" ) ) {
                // Create the new effects, and the subparser
                currentEffects = new Effects( );
                subParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }// If it is a condition tag, create new conditions and switch the state
            else if( sName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }
        }

        // If we are subparsing an effect, spread the call
        if( subParsing == SUBPARSING_EFFECT || subParsing == SUBPARSING_CONDITION ) {
            subParser.startElement( namespaceURI, sName, sName, attrs );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see conversationaleditor.xmlparser.ConversationParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            // If the tag ending is the conversation, create the graph conversation, with the first node of the list
            if( sName.equals( "graph-conversation" ) ) {
                setNodeLinks( );
                chapter.addConversation( new GraphConversation( conversationName, graphNodes.get( 0 ) ) );
            }

            // If a node is closed
            else if( sName.equals( "dialogue-node" ) || sName.equals( "option-node" ) ) {
                // Add the current node to the node list, and the set of children references into the node links
                graphNodes.add( currentNode );
                nodeLinks.add( currentLinks );
            }

            // If the tag is a line said by the player, add it to the current node
            else if( sName.equals( "speak-player" ) ) {
                // Store the read string into the current node, and then delete the string. The trim is performed so we
                // don't
                // have to worry with indentations or leading/trailing spaces
                conversationLine = new ConversationLine( ConversationLine.PLAYER, new String( currentString ).trim( ) );
                if( audioPath != null && !this.audioPath.equals( "" ) ) {
                    conversationLine.setAudioPath( audioPath );
                }
                if( synthesizerVoice != null )
                    conversationLine.setSynthesizerVoice( synthesizerVoice );
                
                conversationLine.setKeepShowing( keepShowingLine );

                currentNode.addLine( conversationLine );
            }

            // If the tag is a line said by a non-player character, add it to the current node
            else if( sName.equals( "speak-char" ) ) {
                // Store the read string into the current node, and then delete the string. The trim is performed so we
                // don't
                // have to worry with indentations or leading/trailing spaces
                conversationLine = new ConversationLine( characterName, new String( currentString ).trim( ) );
                if( audioPath != null && !this.audioPath.equals( "" ) ) {
                    conversationLine.setAudioPath( audioPath );
                }
                if( synthesizerVoice != null )
                    conversationLine.setSynthesizerVoice( synthesizerVoice );
                
                conversationLine.setKeepShowing( keepShowingLine );
                
                currentNode.addLine( conversationLine );
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If we are subparsing an effect
        else if( subParsing == SUBPARSING_EFFECT || subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, sName );

            // If the effect is being closed, insert the effect into the current node
            if( sName.equals( "effect" ) && subParsing == SUBPARSING_EFFECT ) {
                currentNode.setEffects( currentEffects );
                subParsing = SUBPARSING_NONE;
            }
            // If the effect is being closed, insert the effect into the current node
            else if( sName.equals( "condition" ) && subParsing == SUBPARSING_CONDITION ) {
                conversationLine.setConditions( currentConditions );
                subParsing = SUBPARSING_NONE;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) {

        // If no element is being subparsing
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If an effect is being subparsed, spread the call
        else if( subParsing == SUBPARSING_EFFECT || subParsing == SUBPARSING_CONDITION )
            subParser.characters( buf, offset, len );
    }

    /**
     * Set the links between the conversational nodes, taking the indexes of
     * their children, stored in nodeLinks
     */
    private void setNodeLinks( ) {

        // The size of graphNodes and nodeLinks should be the same
        for( int i = 0; i < graphNodes.size( ); i++ ) {
            // Extract the node and its links
            ConversationNode node = graphNodes.get( i );
            List<Integer> links = nodeLinks.get( i );

            // For each reference, insert the referenced node into the father node
            for( int j = 0; j < links.size( ); j++ )
                node.addChild( graphNodes.get( links.get( j ) ) );
        }
    }
}
