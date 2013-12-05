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

import org.xml.sax.Attributes;

import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadandroid.common.data.chapter.effects.ActivateEffect;
import es.eucm.eadandroid.common.data.chapter.effects.CancelActionEffect;
import es.eucm.eadandroid.common.data.chapter.effects.ConsumeObjectEffect;
import es.eucm.eadandroid.common.data.chapter.effects.DeactivateEffect;
import es.eucm.eadandroid.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;
import es.eucm.eadandroid.common.data.chapter.effects.GenerateObjectEffect;
import es.eucm.eadandroid.common.data.chapter.effects.HighlightItemEffect;
import es.eucm.eadandroid.common.data.chapter.effects.IncrementVarEffect;
import es.eucm.eadandroid.common.data.chapter.effects.MacroReferenceEffect;
import es.eucm.eadandroid.common.data.chapter.effects.MoveNPCEffect;
import es.eucm.eadandroid.common.data.chapter.effects.MoveObjectEffect;
import es.eucm.eadandroid.common.data.chapter.effects.MovePlayerEffect;
import es.eucm.eadandroid.common.data.chapter.effects.PlayAnimationEffect;
import es.eucm.eadandroid.common.data.chapter.effects.PlaySoundEffect;
import es.eucm.eadandroid.common.data.chapter.effects.RandomEffect;
import es.eucm.eadandroid.common.data.chapter.effects.SetValueEffect;
import es.eucm.eadandroid.common.data.chapter.effects.ShowTextEffect;
import es.eucm.eadandroid.common.data.chapter.effects.SpeakCharEffect;
import es.eucm.eadandroid.common.data.chapter.effects.SpeakPlayerEffect;
import es.eucm.eadandroid.common.data.chapter.effects.TriggerBookEffect;
import es.eucm.eadandroid.common.data.chapter.effects.TriggerConversationEffect;
import es.eucm.eadandroid.common.data.chapter.effects.TriggerCutsceneEffect;
import es.eucm.eadandroid.common.data.chapter.effects.TriggerLastSceneEffect;
import es.eucm.eadandroid.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadandroid.common.data.chapter.effects.WaitTimeEffect;

/**
 * Class to subparse effects
 */
public class EffectSubParser extends SubParser {

    /* Constants */
    /**
     * Constant for no subparsing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing conditions
     */
    private static final int SUBPARSING_CONDITION = 1;

    /* Attributes */

    /**
     * The current subparser being used
     */
    private SubParser subParser;

    /**
     * Indicates the current element being subparsed
     */
    private int subParsing;

    /**
     * Stores the current id target
     */
    private String currentCharIdTarget;

    /**
     * Stores the effects being parsed
     */
    private Effects effects;

    /**
     * Atributes for show-text effects
     */

    int x = 0;

    int y = 0;

    int frontColor = 0;

    int borderColor = 0;

    /**
     * Constants for reading random-effect
     */
    private boolean positiveBlockRead = false;

    private boolean readingRandomEffect = false;

    private RandomEffect randomEffect;

    /**
     * Stores the current conditions being read
     */
    private Conditions currentConditions;

    /**
     * CurrentEffect. Stores the last created effect to add it later the
     * conditions
     */
    private AbstractEffect currentEffect;

    /**
     * New effects
     */
    private AbstractEffect newEffect;

    /* Methods */

    /**
     * Constructor
     * 
     * @param effects
     *            Structure in which the effects will be placed
     * @param chapter
     *            Chapter data to store the read data
     */
    public EffectSubParser( Effects effects, Chapter chapter ) {

        super( chapter );
        this.effects = effects;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        newEffect = null;

        // If it is a cancel-action tag
        if( sName.equals( "cancel-action" ) ) {
            newEffect = new CancelActionEffect( );
        }

        // If it is a activate tag
        else if( sName.equals( "activate" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "flag" ) ) {
                    newEffect = new ActivateEffect( attrs.getValue( i ) );
                    chapter.addFlag( attrs.getValue( i ) );
                }
        }

        // If it is a deactivate tag
        else if( sName.equals( "deactivate" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "flag" ) ) {
                    newEffect = new DeactivateEffect( attrs.getValue( i ) );
                    chapter.addFlag( attrs.getValue( i ) );
                }
        }

        // If it is a set-value tag
        else if( sName.equals( "set-value" ) ) {
            String var = null;
            int value = 0;

            for( int i = 0; i < attrs.getLength( ); i++ ) {

                if( attrs.getLocalName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "value" ) ) {
                    value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            newEffect = new SetValueEffect( var, value );
            chapter.addVar( var );
        }

        // If it is a set-value tag
        else if( sName.equals( "increment" ) ) {
            String var = null;
            int value = 0;

            for( int i = 0; i < attrs.getLength( ); i++ ) {

                if( attrs.getLocalName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "value" ) ) {
                    value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            newEffect = new IncrementVarEffect( var, value );
            chapter.addVar( var );
        }

        // If it is a decrement tag
        else if( sName.equals( "decrement" ) ) {
            String var = null;
            int value = 0;

            for( int i = 0; i < attrs.getLength( ); i++ ) {

                if( attrs.getLocalName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getLocalName( i ).equals( "value" ) ) {
                    value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            newEffect = new DecrementVarEffect( var, value );
            chapter.addVar( var );
        }

        // If it is a macro-reference tag
        else if( sName.equals( "macro-ref" ) ) {
            // Id
            String id = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "id" ) ) {
                    id = attrs.getValue( i );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            newEffect = new MacroReferenceEffect( id );
        }

        // If it is a consume-object tag
        else if( sName.equals( "consume-object" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "idTarget" ) )
                    newEffect = new ConsumeObjectEffect( attrs.getValue( i ) );
        }

        // If it is a generate-object tag
        else if( sName.equals( "generate-object" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "idTarget" ) )
                    newEffect = new GenerateObjectEffect( attrs.getValue( i ) );
        }

        // If it is a speak-char tag
        else if( sName.equals( "speak-char" ) ) {

            // Store the idTarget, to store the effect when the tag is closed
            currentCharIdTarget = null;

            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "idTarget" ) )
                    currentCharIdTarget = attrs.getValue( i );
        }

        // If it is a trigger-book tag
        else if( sName.equals( "trigger-book" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "idTarget" ) )
                    newEffect = new TriggerBookEffect( attrs.getValue( i ) );
        }

        // If it is a trigger-last-scene tag
        else if( sName.equals( "trigger-last-scene" ) ) {
            newEffect = new TriggerLastSceneEffect( );
        }

        // If it is a play-sound tag
        else if( sName.equals( "play-sound" ) ) {
            // Store the path and background
            String path = "";
            boolean background = true;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "background" ) )
                    background = attrs.getValue( i ).equals( "yes" );
                else if( attrs.getLocalName( i ).equals( "uri" ) )
                    path = attrs.getValue( i );
            }

            // Add the new play sound effect
            newEffect = new PlaySoundEffect( background, path );
        }

        // If it is a trigger-conversation tag
        else if( sName.equals( "trigger-conversation" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "idTarget" ) )
                    newEffect = new TriggerConversationEffect( attrs.getValue( i ) );
        }

        // If it is a trigger-cutscene tag
        else if( sName.equals( "trigger-cutscene" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "idTarget" ) )
                    newEffect = new TriggerCutsceneEffect( attrs.getValue( i ) );
        }

        // If it is a trigger-scene tag
        else if( sName.equals( "trigger-scene" ) ) {
            String scene = "";
            int x = 0;
            int y = 0;
            for( int i = 0; i < attrs.getLength( ); i++ )
                if( attrs.getLocalName( i ).equals( "idTarget" ) )
                    scene = attrs.getValue( i );
                else if( attrs.getLocalName( i ).equals( "x" ) )
                    x = Integer.parseInt( attrs.getValue( i ) );
                else if( attrs.getLocalName( i ).equals( "y" ) )
                    y = Integer.parseInt( attrs.getValue( i ) );

            newEffect = new TriggerSceneEffect( scene, x, y );
        }

        // If it is a play-animation tag
        else if( sName.equals( "play-animation" ) ) {
            String path = "";
            int x = 0;
            int y = 0;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "uri" ) )
                    path = attrs.getValue( i );
                else if( attrs.getLocalName( i ).equals( "x" ) )
                    x = Integer.parseInt( attrs.getValue( i ) );
                else if( attrs.getLocalName( i ).equals( "y" ) )
                    y = Integer.parseInt( attrs.getValue( i ) );
            }

            // Add the new play sound effect
            newEffect = new PlayAnimationEffect( path, x, y );
        }

        // If it is a move-player tag
        else if( sName.equals( "move-player" ) ) {
            int x = 0;
            int y = 0;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "x" ) )
                    x = Integer.parseInt( attrs.getValue( i ) );
                else if( attrs.getLocalName( i ).equals( "y" ) )
                    y = Integer.parseInt( attrs.getValue( i ) );
            }

            // Add the new move player effect
            newEffect = new MovePlayerEffect( x, y );
        }

        // If it is a move-npc tag
        else if( sName.equals( "move-npc" ) ) {
            String npcTarget = "";
            int x = 0;
            int y = 0;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "idTarget" ) )
                    npcTarget = attrs.getValue( i );
                else if( attrs.getLocalName( i ).equals( "x" ) )
                    x = Integer.parseInt( attrs.getValue( i ) );
                else if( attrs.getLocalName( i ).equals( "y" ) )
                    y = Integer.parseInt( attrs.getValue( i ) );
            }

            // Add the new move NPC effect
            newEffect = new MoveNPCEffect( npcTarget, x, y );
        }

        // Random effect tag
        else if( sName.equals( "random-effect" ) ) {
            int probability = 0;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "probability" ) )
                    probability = Integer.parseInt( attrs.getValue( i ) );
            }

            // Add the new random effect
            randomEffect = new RandomEffect( probability );
            newEffect = randomEffect;
            readingRandomEffect = true;
            positiveBlockRead = false;
        }
        // wait-time effect
        else if( sName.equals( "wait-time" ) ) {
            int time = 0;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "time" ) )
                    time = Integer.parseInt( attrs.getValue( i ) );
            }

            // Add the new move NPC effect
            newEffect = new WaitTimeEffect( time );
        }

        // show-text effect
        else if( sName.equals( "show-text" ) ) {
            x = 0;
            y = 0;
            frontColor = 0;
            borderColor = 0;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "x" ) )
                    x = Integer.parseInt( attrs.getValue( i ) );
                else if( attrs.getLocalName( i ).equals( "y" ) )
                    y = Integer.parseInt( attrs.getValue( i ) );
                else if( attrs.getLocalName( i ).equals( "frontColor" ) )
                    frontColor = Integer.parseInt( attrs.getValue( i ) );
                else if( attrs.getLocalName( i ).equals( "borderColor" ) )
                    borderColor = Integer.parseInt( attrs.getValue( i ) );
            }

        }
        
        else if (sName.equals( "highlight-item" )) {
            int type = 0;
            boolean animated = false;
            String id = "";
            for (int i = 0; i < attrs.getLength(); i++) {
                if (attrs.getLocalName( i ).equals( "idTarget" ))
                    id = attrs.getValue( i );
                if (attrs.getLocalName( i ).equals( "animated" ))
                    animated = (attrs.getValue( i ).equals( "yes" ) ? true : false);
                if (attrs.getLocalName( i ).equals( "type" )) {
                    if (attrs.getValue( i ).equals( "none" ))
                        type = HighlightItemEffect.NO_HIGHLIGHT;
                    if (attrs.getValue( i ).equals( "green" ))
                        type = HighlightItemEffect.HIGHLIGHT_GREEN;
                    if (attrs.getValue( i ).equals( "red" ))
                        type = HighlightItemEffect.HIGHLIGHT_RED;
                    if (attrs.getValue( i ).equals( "blue" ))
                        type = HighlightItemEffect.HIGHLIGHT_BLUE;
                    if (attrs.getValue( i ).equals( "border" ))
                        type = HighlightItemEffect.HIGHLIGHT_BORDER;
                }
            }
            newEffect = new HighlightItemEffect(id, type, animated);
        }
        else if (sName.equals( "move-object" )) {
            boolean animated = false;
            String id = "";
            int x = 0;
            int y = 0;
            float scale = 1.0f;
            int translateSpeed = 20;
            int scaleSpeed = 20;
            for (int i = 0; i < attrs.getLength( ); i++) {
                if (attrs.getLocalName( i ).equals( "idTarget" ))
                    id = attrs.getValue( i );
                if (attrs.getLocalName( i ).equals( "animated" ))
                    animated = (attrs.getValue( i ).equals( "yes" ) ? true : false);
                if (attrs.getLocalName( i ).equals( "x" ))
                    x = Integer.parseInt( attrs.getValue( i ) );
                if (attrs.getLocalName( i ).equals( "y" ))
                    y = Integer.parseInt( attrs.getValue( i ) );
                if (attrs.getLocalName( i ).equals( "scale" ))
                    scale = Float.parseFloat( attrs.getValue( i ));
                if (attrs.getLocalName( i ).equals( "translateSpeed" ))
                    translateSpeed = Integer.parseInt( attrs.getValue( i ) );
                if (attrs.getLocalName( i ).equals( "scaleSpeed" ))
                    scaleSpeed = Integer.parseInt( attrs.getValue( i ) );
            }
            newEffect = new MoveObjectEffect(id, x, y, scale, animated, translateSpeed, scaleSpeed);
        }
        // If it is a condition tag, create new conditions and switch the state
        else if( sName.equals( "condition" ) ) {
            currentConditions = new Conditions( );
            subParser = new ConditionSubParser( currentConditions, chapter );
            subParsing = SUBPARSING_CONDITION;
        }

        // Not reading Random effect: Add the new Effect if not null
        if( !readingRandomEffect && newEffect != null ) {
            effects.add( newEffect );
            // Store current effect
            currentEffect = newEffect;

        }

        // Reading random effect
        if( readingRandomEffect ) {
            // When we have just created the effect, add it
            if( newEffect != null && newEffect == randomEffect ) {
                effects.add( newEffect );
            }
            // Otherwise, determine if it is positive or negative effect 
            else if( newEffect != null && !positiveBlockRead ) {
                randomEffect.setPositiveEffect( newEffect );
                positiveBlockRead = true;
            }
            // Negative effect 
            else if( newEffect != null && positiveBlockRead ) {
                randomEffect.setNegativeEffect( newEffect );
                positiveBlockRead = false;
                readingRandomEffect = false;
                randomEffect = null;
            }
            // Store current effect
            currentEffect = newEffect;

        }

        // If it is reading an effect or a condition, spread the call
        if( subParsing != SUBPARSING_NONE ) {
            subParser.startElement( namespaceURI, sName, sName, attrs );
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            newEffect = null;

            // If it is a speak-player
            if( sName.equals( "speak-player" ) ) {
                // Add the effect and clear the current string
                newEffect = new SpeakPlayerEffect( currentString.toString( ).trim( ) );
            }

            // If it is a speak-char
            else if( sName.equals( "speak-char" ) ) {
                // Add the effect and clear the current string
                newEffect = new SpeakCharEffect( currentCharIdTarget, currentString.toString( ).trim( ) );
            }// If it is a show-text
            else if( sName.equals( "show-text" ) ) {
                // Add the new ShowTextEffect
                newEffect = new ShowTextEffect( currentString.toString( ).trim( ), x, y, frontColor, borderColor );
            }

            // Not reading Random effect: Add the new Effect if not null
            if( !readingRandomEffect && newEffect != null ) {
                effects.add( newEffect );
                // Store current effect
                currentEffect = newEffect;

            }

            // Reading random effect
            if( readingRandomEffect ) {
                // When we have just created the effect, add it
                if( newEffect != null && newEffect == randomEffect ) {
                    effects.add( newEffect );
                }
                // Otherwise, determine if it is positive or negative effect 
                else if( newEffect != null && !positiveBlockRead ) {
                    randomEffect.setPositiveEffect( newEffect );
                    positiveBlockRead = true;
                }
                // Negative effect 
                else if( newEffect != null && positiveBlockRead ) {
                    randomEffect.setNegativeEffect( newEffect );
                    positiveBlockRead = false;
                    readingRandomEffect = false;
                    randomEffect = null;
                }
                // Store current effect
                currentEffect = newEffect;

            }

            // Reset the current string
            currentString = new StringBuffer( );
        }
        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, sName );

            // If the condition tag is being closed
            if( sName.equals( "condition" ) ) {
                // Store the conditions in the effect
                currentEffect.setConditions( currentConditions );

                // Switch state
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

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If it is reading an effect or a condition, spread the call
        else
            subParser.characters( buf, offset, len );
    }

}
