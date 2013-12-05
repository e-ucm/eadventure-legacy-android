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

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadandroid.common.data.HasTargetId;
import es.eucm.eadandroid.common.data.adaptation.AdaptationProfile;
import es.eucm.eadandroid.common.data.adventure.ChapterSummary;
import es.eucm.eadandroid.common.data.assessment.AssessmentProfile;
import es.eucm.eadandroid.common.data.chapter.book.Book;
import es.eucm.eadandroid.common.data.chapter.conditions.GlobalState;
import es.eucm.eadandroid.common.data.chapter.conversation.Conversation;
import es.eucm.eadandroid.common.data.chapter.effects.Macro;
import es.eucm.eadandroid.common.data.chapter.elements.Atrezzo;
import es.eucm.eadandroid.common.data.chapter.elements.Item;
import es.eucm.eadandroid.common.data.chapter.elements.NPC;
import es.eucm.eadandroid.common.data.chapter.elements.Player;
import es.eucm.eadandroid.common.data.chapter.scenes.Cutscene;
import es.eucm.eadandroid.common.data.chapter.scenes.GeneralScene;
import es.eucm.eadandroid.common.data.chapter.scenes.Scene;

/**
 * This class hold the data of a chapter in eAdventure.
 */
public class Chapter extends ChapterSummary implements HasTargetId {

    /**
     * Identifier of the initial scene.
     */
    private String initialScene;

    /**
     * List of playable scenes.
     */
    private List<Scene> scenes;
    
    /** 
     * list of gpsrules
     */
    private List<GpsRule> Gpsrules;

    
    /** 
     * list of qrcodes
     */
    private List<QrcodeRule> qrrules;
    /**
     * List of cutscenes.
     */
    private List<Cutscene> cutscenes;

    /**
     * List of books.
     */
    private List<Book> books;

    /**
     * List of items (objects).
     */
    private List<Item> items;

    /**
     * List of atrezzo items (non interactive objects)
     */
    private List<Atrezzo> atrezzo;

    /**
     * The player.
     */
    private Player player;

    /**
     * List of characters.
     */
    private List<NPC> characters;

    /**
     * List of conversations.
     */
    private List<Conversation> conversations;

    /**
     * The list of timers (advanced options)
     */
    private List<Timer> timers;

    /**
     * List of the flags present in the game
     */
    
  

    /**
     * List of the flags present in the game
     */
    
    
    private List<String> flags;

    /**
     * List of the vars present in the game
     */
    private List<String> vars;

    /**
     * List of global states
     */
    private List<GlobalState> globalStates;

    /**
     * List of macros
     */
    private List<Macro> macros;

    /**
     * Empty constructor. Sets values to null and creates empty lists.
     */
    public Chapter( ) {

        super( );
        // Create lists
        scenes = new ArrayList<Scene>( );
        cutscenes = new ArrayList<Cutscene>( );
        books = new ArrayList<Book>( );
        items = new ArrayList<Item>( );
        atrezzo = new ArrayList<Atrezzo>( );
        player = new Player( );
        characters = new ArrayList<NPC>( );
        conversations = new ArrayList<Conversation>( );
        timers = new ArrayList<Timer>( );
        flags = new ArrayList<String>( );
        vars = new ArrayList<String>( );
        globalStates = new ArrayList<GlobalState>( );
        macros = new ArrayList<Macro>( );
        Gpsrules = new ArrayList<GpsRule>( );
        qrrules = new ArrayList<QrcodeRule>( );
    }

    
    
    
    
    public List<QrcodeRule> getQrrules() {
		return qrrules;
	}


    public void addQrRule(QrcodeRule qr )
    {
    	this.qrrules.add(qr);
    }
  





	/**
     * Returns the list of Gpsrules in the game
     * 
     * @return the list of Gpsrules in the game
     */
    public List<GpsRule> getGpsRules( ) {

        return this.Gpsrules;
    }
    
    /**
     *Adds a gps rule to the list of gpsrules in the game.  
     * @param gps
     */
     public void addGpsRule(GpsRule gps)
     {
     	this.Gpsrules.add(gps);
     }
    
    
    
    /**
     * Constructor with title for the chapter. Sets empty values and creates
     * empty lists, plus one scene.
     * 
     * @param title
     *            Title for the chapter
     * @param sceneId
     *            Identifier for the scene
     */
    public Chapter( String title, String sceneId ) {

        super( title );
        initialScene = sceneId;

        // Create lists
        scenes = new ArrayList<Scene>( );
        cutscenes = new ArrayList<Cutscene>( );
        books = new ArrayList<Book>( );
        items = new ArrayList<Item>( );
        atrezzo = new ArrayList<Atrezzo>( );
        player = new Player( );
        characters = new ArrayList<NPC>( );
        conversations = new ArrayList<Conversation>( );
        timers = new ArrayList<Timer>( );
        // Add the scene
        scenes.add( new Scene( sceneId ) );
        globalStates = new ArrayList<GlobalState>( );
        macros = new ArrayList<Macro>( );
        flags = new ArrayList<String>( );
        vars = new ArrayList<String>( );
    }

    /**
     * Returns the initial scene identifier.
     * 
     * @return Initial scene identifier
     */
    public String getTargetId( ) {

        return initialScene;
    }

    /**
     * Returns the initial scene
     * 
     * @return the initial scene
     */
    public GeneralScene getInitialGeneralScene( ) {

        GeneralScene initialGeneralScene = null;
        if( initialScene != null ) {
            initialGeneralScene = getGeneralScene( initialScene );
        }
        if( initialGeneralScene == null ) {
            // Return the FIRST initial scene stored
            for( int i = 0; i < getGeneralScenes( ).size( ) && initialGeneralScene == null; i++ )
                if( getGeneralScenes( ).get( i ).isInitialScene( ) )
                    initialGeneralScene = getGeneralScenes( ).get( i );

            // If there is no initial scene, return the first scene
            if( initialGeneralScene == null )
                initialGeneralScene = getGeneralScenes( ).get( 0 );
        }

        return initialGeneralScene;
    }

    /**
     * Returns the list of playable scenes in the game.
     * 
     * @return List of playable scenes
     */
    public List<Scene> getScenes( ) {

        return scenes;
    }

    /**
     * Returns the list of cutscenes in the game.
     * 
     * @return List of cutscenes
     */
    public List<Cutscene> getCutscenes( ) {

        return cutscenes;
    }

    /**
     * Returns the list of books in the game
     * 
     * @return the list of books in the game
     */
    public List<Book> getBooks( ) {

        return books;
    }

    /**
     * Returns the list of items in the game
     * 
     * @return the list of items in the game
     */
    public List<Item> getItems( ) {

        return items;
    }

    /**
     * Returns the list of atrezzo items in the game
     * 
     * @return the list of atrezzo items in the game
     */
    public List<Atrezzo> getAtrezzo( ) {

        return atrezzo;
    }

    /**
     * Returns the player of the game
     * 
     * @return the player of the gam
     */
    public Player getPlayer( ) {

        return player;
    }

    /**
     * Returns the list of characters in the game
     * 
     * @return the list of characters in the game
     */
    public List<NPC> getCharacters( ) {

        return characters;
    }

    /**
     * Returns the list of conversations in the game
     * 
     * @return the list of conversations in the game
     */
    public List<Conversation> getConversations( ) {

        return conversations;
    }

    /**
     * Changes the initial scene of the chapter.
     * 
     * @param initialScene
     *            New initial scene identifier
     */
    public void setTargetId( String initialScene ) {

        this.initialScene = initialScene;
    }

    /**
     * Adds a scene to the list of playable scenes in the game.
     * 
     * @param scene
     *            the scene to add
     */
    public void addScene( Scene scene ) {

        scenes.add( scene );
    }

    /**
     * Adds a cutscene to the list of cutscenes in the game.
     * 
     * @param cutscene
     *            The cutscene to add
     */
    public void addCutscene( Cutscene cutscene ) {

        cutscenes.add( cutscene );
    }

    /**
     * Adds a book to the list of book in the game
     * 
     * @param book
     *            the book to add
     */
    public void addBook( Book book ) {

        books.add( book );
    }

    /**
     * Adds an item to the list of items in the game
     * 
     * @param item
     *            the item to add
     */
    public void addItem( Item item ) {

        items.add( item );
    }

    /**
     * Adds an atrezzo item to the list of atrezzo items in the game
     * 
     * @param atrezzo
     *            the atrezzo item to add
     */
    public void addAtrezzo( Atrezzo atrezzo ) {

        this.atrezzo.add( atrezzo );
    }

    /**
     * Adds a global state to the list of global states in the game
     * 
     * @param globalState
     *            the global state to add
     */
    public void addGlobalState( GlobalState globalState ) {

        globalStates.add( globalState );
    }

    /**
     * Adds a macro to the list of macros in the game
     * 
     * @param macro
     *            the macro to add
     */
    public void addMacro( Macro macro ) {

        macros.add( macro );
    }

    /**
     * Changes the player in the game
     * 
     * @param player
     *            the new player
     */
    public void setPlayer( Player player ) {

        this.player = player;
    }

    /**
     * Adds a character to the list of characters in the game
     * 
     * @param npc
     *            the new character
     */
    public void addCharacter( NPC npc ) {

        characters.add( npc );
    }

    /**
     * Adds a conversation to the list of conversation in the game
     * 
     * @param conversation
     *            the new conversation
     */
    public void addConversation( Conversation conversation ) {

        conversations.add( conversation );
    }

    /**
     * Adds a timer to the list of timers in the game
     * 
     * @param timer
     *            the new timer
     */
    public void addTimer( Timer timer ) {

        timers.add( timer );
    }

    /**
     * Returns a scene with the given id.
     * 
     * @param sceneId
     *            Scene id
     * @return Scene requested, null if it was not found
     */
    public Scene getScene( String sceneId ) {

        Scene selectedScene = null;

        for( Scene scene : scenes )
            if( scene.getId( ).equals( sceneId ) )
                selectedScene = scene;

        return selectedScene;
    }

    /**
     * Returns a cutscene with the given id.
     * 
     * @param sceneId
     *            Scene id
     * @return Scene requested, null if it was not found
     */
    public Cutscene getCutscene( String sceneId ) {

        Cutscene selectedScene = null;

        for( Cutscene scene : cutscenes )
            if( scene.getId( ).equals( sceneId ) )
                selectedScene = scene;

        return selectedScene;
    }

    /**
     * Returns an item with the given id.
     * 
     * @param itemId
     *            Item id
     * @return Item requested, null if it was not found
     */
    public Item getItem( String itemId ) {

        Item selectedItem = null;

        for( Item item : items )
            if( item.getId( ).equals( itemId ) )
                selectedItem = item;

        return selectedItem;
    }

    /**
     * Returns an atrezzo item with the given id.
     * 
     * @param atrezzoId
     *            Atrezzo id
     * @return Atrezzo item requested, null if it was not found
     */
    public Atrezzo getAtrezzo( String atrezzoId ) {

        Atrezzo selectedAtrezzo = null;

        for( Atrezzo at : atrezzo )
            if( at.getId( ).equals( atrezzoId ) )
                selectedAtrezzo = at;

        return selectedAtrezzo;
    }

    /**
     * Returns a character with the given id.
     * 
     * @param npcId
     *            Character id
     * @return Character requested, null if it was not found
     */
    public NPC getCharacter( String npcId ) {

        NPC selectedNPC = null;

        for( NPC npc : characters )
            if( npc.getId( ).equals( npcId ) )
                selectedNPC = npc;

        return selectedNPC;
    }

    /**
     * Returns a global state with the given id.
     * 
     * @param globalStateId
     *            Global State id
     * @return GlobalState requested, null if it was not found
     */
    public GlobalState getGlobalState( String globalStateId ) {

        GlobalState selectedGlobalState = null;

        for( GlobalState gs : globalStates )
            if( gs.getId( ).equals( globalStateId ) )
                selectedGlobalState = gs;

        return selectedGlobalState;
    }

    /**
     * Returns a macro with the given id.
     * 
     * @param macroId
     *            Macro id
     * @return Macro requested, null if it was not found
     */
    public Macro getMacro( String macroId ) {

        Macro selectedMacro = null;

        for( Macro m : macros )
            if( m.getId( ).equals( macroId ) )
                selectedMacro = m;

        return selectedMacro;
    }

    /**
     * Returns the list of timers (blocks of effects ruled by conditions which
     * will get executed each TIME seconds
     * 
     * @return The list of timers
     */
    public List<Timer> getTimers( ) {

        return timers;
    }

    /**
     * Set the list of timers
     * 
     * @param timers
     *            The new list of timers
     * @see #getTimers()
     */
    public void setTimers( List<Timer> timers ) {

        this.timers = timers;
    }

    /**
     * Returns the list of flags in the game
     * 
     * @return the list of flags in the game
     */
    public List<String> getFlags( ) {

        return flags;
    }

    /**
     * Returns the list of vars in the game
     * 
     * @return the list of vars in the game
     */
    public List<String> getVars( ) {

        return vars;
    }

    /**
     * Adds a flag to the list of flags in the game
     * 
     * @param flag
     *            the flag to add
     */
    public void addFlag( String flag ) {

        if( !flags.contains( flag ) )
            flags.add( flag );
    }

    /**
     * Adds a var to the list of Vars in the game
     * 
     * @param Var
     *            the var to add
     */
    public void addVar( String var ) {

        if( !vars.contains( var ) )
            vars.add( var );
    }

    /**
     * Returns the scene with the given id. If the scene is not found, null is
     * returned
     * 
     * @param generalSceneId
     *            the id of the scene to find
     * @return the scene with the given id
     */
    public GeneralScene getGeneralScene( String generalSceneId ) {

        GeneralScene scene = getScene( generalSceneId );
        if( scene == null )
            scene = getCutscene( generalSceneId );

        return scene;
    }

    /**
     * Returns the list of general scenes in the game
     * 
     * @return the list of general scenes in the game
     */
    public List<GeneralScene> getGeneralScenes( ) {

        List<GeneralScene> generalScenes = new ArrayList<GeneralScene>( );
        for( Scene scene : scenes ) {
            generalScenes.add( scene );
        }
        for( Cutscene cutscene : cutscenes ) {
            generalScenes.add( cutscene );
        }
        return generalScenes;
    }

    /**
     * Returns an book with the given id.
     * 
     * @param bookId
     *            book id
     * @return book requested, null if it was not found
     */
    public Book getBook( String bookId ) {

        Book selectedbook = null;

        for( Book book : books )
            if( book.getId( ).equals( bookId ) )
                selectedbook = book;

        return selectedbook;
    }

    /**
     * Returns a Conversation with the given id.
     * 
     * @param ConversationId
     *            Conversation id
     * @return Conversation requested, null if it was not found
     */
    public Conversation getConversation( String conversationId ) {

        Conversation selectedConversation = null;

        for( Conversation conversation : conversations )
            if( conversation.getId( ).equals( conversationId ) )
                selectedConversation = conversation;

        return selectedConversation;
    }

    /**
     * Returns true if the argumented id matches to a cutscene
     */
    public boolean isCutscene( String id ) {

        return getCutscene( id ) != null;
    }

    /**
     * @return the globalStates
     */
    public List<GlobalState> getGlobalStates( ) {

        return globalStates;
    }

    /**
     * @param globalStates
     *            the globalStates to set
     */
    public void setGlobalStates( List<GlobalState> globalStates ) {

        this.globalStates = globalStates;
    }

    /**
     * @return the macros
     */
    public List<Macro> getMacros( ) {

        return macros;
    }

    /**
     * @param macros
     *            the macros to set
     */
    public void setMacros( List<Macro> macros ) {

        this.macros = macros;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Chapter c = (Chapter) super.clone( );
        if( atrezzo != null ) {
            c.atrezzo = new ArrayList<Atrezzo>( );
            for( Atrezzo a : atrezzo )
                c.atrezzo.add( (Atrezzo) a.clone( ) );
        }
        if( books != null ) {
            c.books = new ArrayList<Book>( );
            for( Book b : books )
                c.books.add( (Book) b.clone( ) );
        }
        if( characters != null ) {
            c.characters = new ArrayList<NPC>( );
            for( NPC n : characters )
                c.characters.add( (NPC) n.clone( ) );
        }
        if( conversations != null ) {
            c.conversations = new ArrayList<Conversation>( );
            for( Conversation cc : conversations )
                c.conversations.add( (Conversation) cc.clone( ) );
        }
        if( cutscenes != null ) {
            c.cutscenes = new ArrayList<Cutscene>( );
            for( Cutscene cs : cutscenes )
                c.cutscenes.add( (Cutscene) cs.clone( ) );
        }
        if( flags != null ) {
            c.flags = new ArrayList<String>( );
            for( String s : flags )
                c.flags.add( new String( s ) );
        }
        if( globalStates != null ) {
            c.globalStates = new ArrayList<GlobalState>( );
            for( GlobalState gs : globalStates )
                c.globalStates.add( (GlobalState) gs.clone( ) );
        }
        c.initialScene = ( initialScene != null ? new String( initialScene ) : null );
        if( items != null ) {
            c.items = new ArrayList<Item>( );
            for( Item i : items )
                c.items.add( (Item) i.clone( ) );
        }
        if( macros != null ) {
            c.macros = new ArrayList<Macro>( );
            for( Macro m : macros )
                c.macros.add( (Macro) m.clone( ) );
        }
        c.player = ( player != null ? (Player) player.clone( ) : null );
        if( scenes != null ) {
            c.scenes = new ArrayList<Scene>( );
            for( Scene s : scenes )
                c.scenes.add( (Scene) s.clone( ) );
        }
        if( timers != null ) {
            c.timers = new ArrayList<Timer>( );
            for( Timer t : timers )
                c.timers.add( (Timer) t.clone( ) );
        }
        if( vars != null ) {
            c.vars = new ArrayList<String>( );
            for( String s : vars )
                c.vars.add( new String( s ) );
        }
        if( assessmentProfiles != null ) {
            c.assessmentProfiles = new ArrayList<AssessmentProfile>( );
            for( AssessmentProfile ap : assessmentProfiles )
                c.assessmentProfiles.add( ap );
        }

        if( adaptationProfiles != null ) {
            c.adaptationProfiles = new ArrayList<AdaptationProfile>( );
            for( AdaptationProfile ap : adaptationProfiles )
                c.adaptationProfiles.add( ap );
        }

        return c;
    }


}
