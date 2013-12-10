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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;

/**
 * This conversational node contains a set of lines, which represent the
 * possible options that the player can choose in a certain point of the
 * conversation. For it's correct use, there must be the same number of lines
 * and children, for each line represents an option, linked with the path the
 * conversation will follow if the option is choosed. Only DialogueNode can be
 * linked with this kind of node
 */
public class OptionConversationNode extends ConversationNode {

    /* Attributes */

    /**
     * Conversational line's vector
     */
    private List<ConversationLine> options;

    /**
     * Links to the path to follow for each option
     */
    private List<ConversationNode> optionNodes;

    private boolean effectConsumed = false;

    /**
     * Effect to be triggered when the node has finished
     */
    private Effects effects;

    /**
     * Show the options randomly
     */
    private boolean random;
    
    /**
     * Keep the last conversation line showing
     */
    private boolean keepShowing;
    
    /**
     * Show the option selected by user
     */
    private boolean showUserOption;

    /* Methods */

    public Boolean isRandom( ) {

        return random;
    }

    public void setRandom( Boolean newValue ) {

        this.random = newValue;
    }

    /**
     * Constructor
     */
    public OptionConversationNode( boolean random, boolean keepShowing, boolean showUserOption) {

        options = new ArrayList<ConversationLine>( );
        optionNodes = new ArrayList<ConversationNode>( );
        this.random = random;
        this.keepShowing = keepShowing;
        this.showUserOption = showUserOption;
        effects = new Effects( );
    }

    /**
     * Constructor
     */
    public OptionConversationNode( ) {

        this( false , false, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getType()
     */
    public int getType( ) {

        return OPTION;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#isTerminal()
     */
    public boolean isTerminal( ) {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.common.data.chapterdata.conversation.node.ConversationNodeView#getChildCount()
     */
    public int getChildCount( ) {

        return optionNodes.size( );
    }

    @Override
    public ConversationNode getChild( int index ) {

        return optionNodes.get( index );
    }

    @Override
    public void addChild( ConversationNode child ) {

        optionNodes.add( child );
    }

    @Override
    public void addChild( int index, ConversationNode child ) {

        optionNodes.add( index, child );
    }

    @Override
    public ConversationNode removeChild( int index ) {

        return optionNodes.remove( index );
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.common.data.chapterdata.conversation.node.ConversationNodeView#getLineCount()
     */
    public int getLineCount( ) {

        return options.size( );
    }

    @Override
    public ConversationLine getLine( int index ) {

        return options.get( index );
    }

    @Override
    public void addLine( ConversationLine line ) {

        options.add( line );
    }

    @Override
    public void addLine( int index, ConversationLine line ) {

        options.add( index, line );
    }

    @Override
    public ConversationLine removeLine( int index ) {

        return options.remove( index );
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.common.data.chapterdata.conversation.node.ConversationNodeView#hasEffects()
     */
    public boolean hasEffects( ) {

        return hasValidEffect( ) && !effects.isEmpty( );
    }

    @Override
    public void setEffects( Effects effects ) {

        this.effects = effects;
    }

    @Override
    public Effects getEffects( ) {

        return effects;
    }

    @Override
    public void consumeEffect( ) {

        effectConsumed = true;
    }

    @Override
    public boolean isEffectConsumed( ) {

        return effectConsumed;
    }

    @Override
    public void resetEffect( ) {

        effectConsumed = false;
    }

    @Override
    public boolean hasValidEffect( ) {

        return effects != null;
    }

    /**
     * Change randomly the position of the options.
     */
    public void doRandom( ) {

        // If option of randomly are activated
        if( random && getLineCount( ) > 0 ) {
            int cont = getLineCount( );
            Random rnd = new Random( );
            int pos;
            ArrayList<ConversationLine> op = new ArrayList<ConversationLine>( );
            ArrayList<ConversationNode> opNode = new ArrayList<ConversationNode>( );
            // Iterate the array and change randomly the position
            while( cont > 1 ) {
                pos = rnd.nextInt( cont );
                op.add( options.get( pos ) );
                opNode.add( optionNodes.get( pos ) );
                options.remove( pos );
                optionNodes.remove( pos );
                cont--;

            }
            // It must be out of loop 
            op.add( options.get( 0 ) );
            opNode.add( optionNodes.get( 0 ) );

            options = op;
            optionNodes = opNode;
        }
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        OptionConversationNode ocn = (OptionConversationNode) super.clone( );
        ocn.effectConsumed = effectConsumed;
        ocn.effects = ( effects != null ? (Effects) effects.clone( ) : null );
        /*		if (optionNodes != null) {
        			ocn.optionNodes = new ArrayList<ConversationNode>();
        			for (ConversationNode cn : optionNodes)
        				ocn.optionNodes.add((ConversationNode) cn.clone());
        		}*/
        ocn.optionNodes = new ArrayList<ConversationNode>( );
        if( options != null ) {
            ocn.options = new ArrayList<ConversationLine>( );
            for( ConversationLine cl : options )
                ocn.options.add( (ConversationLine) cl.clone( ) );
        }
        ocn.random = random;
        ocn.keepShowing = keepShowing;
        ocn.showUserOption = showUserOption;
        return ocn;
    }

    /**
     * In that case, return the conditions of the option equals to the given
     * index.
     */
    public Conditions getLineConditions( int index ) {

        return options.get( index ).getConditions( );
    }

    public ConversationLine getConversationLine( int index ) {

        return options.get( index );
    }
    
    public Boolean isKeepShowing( ) {
        
        return keepShowing;
    }

    
    public void setKeepShowing( Boolean keepShowing ) {
    
        this.keepShowing = keepShowing;
    }

    
    public Boolean isShowUserOption( ) {
    
        return showUserOption;
    }

    
    public void setShowUserOption( Boolean showUserOption ) {
    
        this.showUserOption = showUserOption;
    }
    
}
