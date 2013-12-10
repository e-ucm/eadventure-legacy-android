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
package es.eucm.eadandroid.ecore.control;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import android.content.SharedPreferences;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import es.eucm.eadandroid.assessment.AssessmentEngine;
import es.eucm.eadandroid.common.auxiliar.SpecialAssetPaths;
import es.eucm.eadandroid.common.data.adaptation.AdaptedState;
import es.eucm.eadandroid.common.data.adventure.ChapterSummary;
import es.eucm.eadandroid.common.data.adventure.DescriptorData;
import es.eucm.eadandroid.common.data.adventure.DescriptorData.Perspective;
import es.eucm.eadandroid.common.data.assessment.AssessmentProfile;
import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.common.data.chapter.InfluenceArea;
import es.eucm.eadandroid.common.data.chapter.Timer;
import es.eucm.eadandroid.common.data.chapter.book.Book;
import es.eucm.eadandroid.common.data.chapter.conversation.Conversation;
import es.eucm.eadandroid.common.data.chapter.elements.NPC;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.data.chapter.scenes.Cutscene;
import es.eucm.eadandroid.common.data.chapter.scenes.Scene;
import es.eucm.eadandroid.common.gui.JOptionPane;
import es.eucm.eadandroid.common.loader.Loader;
import es.eucm.eadandroid.common.loader.incidences.Incidence;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.GameThread;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalItem;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalNPC;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalPlayer;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalScene;
import es.eucm.eadandroid.ecore.control.functionaldata.TalkingElement;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffect;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.ecore.control.gamestate.GameState;
import es.eucm.eadandroid.ecore.control.gamestate.GameStateBook;
import es.eucm.eadandroid.ecore.control.gamestate.GameStateConversation;
import es.eucm.eadandroid.ecore.control.gamestate.GameStateLoading;
import es.eucm.eadandroid.ecore.control.gamestate.GameStateNextScene;
import es.eucm.eadandroid.ecore.control.gamestate.GameStatePlaying;
import es.eucm.eadandroid.ecore.control.gamestate.GameStateRunEffects;
import es.eucm.eadandroid.ecore.control.gamestate.GameStateSlidescene;
import es.eucm.eadandroid.ecore.control.gamestate.GameStateVideoscene;
import es.eucm.eadandroid.ecore.control.gamestate.scene.SceneTouchListener;
import es.eucm.eadandroid.ecore.data.GameText;
import es.eucm.eadandroid.ecore.data.SaveGame;
import es.eucm.eadandroid.ecore.data.SaveGameException;
import es.eucm.eadandroid.ecore.data.SaveTimer;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.ecore.gui.hud.states.DraggingState;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class Game implements TimerEventListener , SpecialAssetPaths{

	public static final String TAG ="Game";
	
	/* GAME GLOBAL STATES */
	private static final int STATE_PAUSE = 0;
	private static final int STATE_RUNNING = 1;
	private int globalState = STATE_RUNNING;

	private static Game instance = null;

	
	 /**
     * Constant for loading state
     */
    public static final int STATE_LOADING = 0;

    /**
     * Constant for playing state
     */
    public static final int STATE_PLAYING = 1;

    /**
     * Constant for slidescene state
     */
    public static final int STATE_SLIDE_SCENE = 2;

    /**
     * Constant for next scene state
     */
    public static final int STATE_NEXT_SCENE = 3;

    /**
     * Constant for videoscene state
     */
    public static final int STATE_VIDEO_SCENE = 4;

    /**
     * Constant for running effects state
     */
    public static final int STATE_RUN_EFFECTS = 5;

    /**
     * Constant for running effects state from a conversation
     */
    public static final int STATE_RUN_EFFECTS_FROM_CONVERSATION = 9;

    /**
     * Constant for book state
     */
    public static final int STATE_BOOK = 6;

    /**
     * Constant for conversation state
     */
    public static final int STATE_CONVERSATION = 7;

    /**
     * Constant for options state
     */
    public static final int STATE_OPTIONS = 8;
    /**
     * Constant for options state
     */
    public static final int STATE_VIDEO = 10;

    /**
     * Path of the file containing the adventure
     */
    private String adventurePath;

    /**
     * Name of the file containing the adventure
     */
    private String adventureName;

    /**
     * Descriptor info of the adventure
     */
    private DescriptorData gameDescriptor;

    /**
     * Game data of the adventure
     */
    private Chapter gameData;

    /**
     * Flag summary
     */
    private FlagSummary flags;

    /**
     * Var summary
     */
    private VarSummary vars;
/**
     * Assessment engine
     */
    private AssessmentEngine assessmentEngine;
    
    /*
     * if the game is going to be loaded from a saved game
     */
    private boolean LoadedGame=false;
    private String LoadingGame;

    /*
     */ 
    /**
     * Adaptation engine
     *//*
    private AdaptationEngine adaptationEngine;
*/
    /**
     * The adapted state to be executed. It holds a null value if no adapted
     * state must be executed.
     */
    private AdaptedState adaptedStateToExecute;

    /**
     * Item summary
     */
    private ItemSummary itemSummary;

    /**
     * Atrezzo item summary
     */
    private AtrezzoSummary atrezzoSummary;

    /**
     * Inventory
     */
    private Inventory inventory;

    /**
     * Options of the game
     */
    private Options options;

    /**
     * The next scene that will be loaded
     */
    private Exit nextScene;

    /**
     * The last scene that was loaded
     */
    private Exit lastNextScene;

    /**
     * Functional scene being played
     */
    private FunctionalScene functionalScene;

    /**
     * Functional player of the game
     */
    private FunctionalPlayer functionalPlayer;

    /**
     * State of the game
     */
    
    private  GameState currentState;
    
    private SharedPreferences prefs;

    public SharedPreferences getPrefs() {
		return prefs;
	}

	public void setPrefs(SharedPreferences prefs) {
		this.prefs = prefs;
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}

	/**
     * Store if each arraylist of effects in effectsQueue comes from a
     * conversation, to manage stackOfStates properly
     */
    private Stack<Boolean> isConvEffectsBlock;

    /**
     * Store the number of blocks of effects created in conversations are in
     * effectsQueue
     */
    private int numberConv;

    /**
     * LIFO of Queues of effects to be performed
     */
    private Stack<List<FunctionalEffect>> effectsQueue;

    /**
     * Stores the character currently talking
     */
    private TalkingElement characterCurrentlyTalking;

    /**
     * Stores if the game is over or not
     */
    private boolean gameOver = false;

    /**
     * Stores whether the current chapter has finished
     */
    private boolean nextChapter = false;

    /**
     * The number of the current chapter
     */
    private int currentChapter;

    /**
     * Time elapsed of game
     */
    private long totalTime = 0;

    /**
     * Book to be displayed
     */
    private Book book;

    /**
     * Conversation to be played
     */
    private Conversation conversation;

    /**
     * Current non player character selected by default in a conversation
     */
    private FunctionalNPC currentNPC;

    /**
     * Controls the actions in the game
     */
    private ActionManager actionManager;

    /**
     * Controls the timers in the game (normal timers and assessment timers)
     */
    private TimerManager timerManager;

    /**
     * Structure for game timers. The key is the id returned by TimerManager
     */
    private HashMap<Integer, Timer> gameTimers;

    /**
     * Stack to store each conversation nested
     */
    private Stack<GameState> stackOfState;


    /**
     * Temp Variable that is used to store the state of the FSM that controls
     * the removal of "fake drags".
     */
    private String state = "";
    
    /* ************************ */
    
    /** UIEVENT LISTENERS */
    

    private SceneTouchListener sceneTouchListener;
    
    /*******/
    
    private int totalElapsedTime=0 ;

	private Game(String LoadingGame) {
		
		createUIEventListeners();
		
		if (LoadingGame!=null)
		{
			this.LoadedGame=true;
			this.LoadingGame=LoadingGame;
		}
			
		
	}
	
	public void createUIEventListeners() {
		
		this.sceneTouchListener = new SceneTouchListener();
		
	}

	public static void create(String LoadingGame) {

		instance = new Game(LoadingGame);
	}

	public static void delete() {
		
		staticStop( );
		instance = null;
	}

	public static Game getInstance() {

		return instance;
	}

	public void setAdventurePath(String advPath) {
		this.adventurePath = advPath;
	}

	public String getAdventurePath() {
		return this.adventurePath;
	}
	
    /**
     * Sets the adventure name
     * 
     * @param adventureName
     *            The name of the adventure
     */
    public void setAdventureName( String adventureName ) {

        this.adventureName = adventureName;
    }
    
    /**
     * Init the game parameters 
     */
    private void loadCurrentChapter() {

        Log.d(TAG,"Loading chapter" );

        // Reset the image cache
        MultimediaManager.getInstance( ).flushImagePool( MultimediaManager.IMAGE_SCENE );
        MultimediaManager.getInstance( ).flushImagePool( MultimediaManager.IMAGE_PLAYER );
        MultimediaManager.getInstance( ).flushImagePool( MultimediaManager.IMAGE_MENU );
        MultimediaManager.getInstance( ).flushAnimationPool( );
        System.gc();
        System.runFinalization();

        // REset game strings
        GameText.reloadStrings( );        

        // Extract the chapter
        ChapterSummary chapter = gameDescriptor.getChapterSummaries( ).get( currentChapter );

        // Load the script data
        gameData = Loader.loadChapterData( ResourceHandler.getInstance( ), chapter.getChapterPath( ), new ArrayList<Incidence>( ), true );

        // Create the flags & vars summaries and the assessment engine
        flags = new FlagSummary( gameData.getFlags( ), false );
        vars = new VarSummary( gameData.getVars( ), false );

        // Init the time manager
        timerManager = TimerManager.getInstance( );
        timerManager.reset( );     
        
        if (gameData.getAssessmentName()!="")
        chapter.setAssessmentName(gameData.getAssessmentName());
        
        if (gameData.hasAssessmentProfile())
        	assessmentEngine.loadAssessmentRules( gameData.getSelectedAssessmentProfile() );
        	
        if (!gameData.hasAssessmentProfile()&&chapter.hasAssessmentProfile())
            assessmentEngine.loadAssessmentRules( chapter.getSelectedAssessmentProfile() );
     
        // Initialize the required elements of the game
        actionManager = new ActionManager( );
        itemSummary = new ItemSummary( gameData.getItems( ) );
        atrezzoSummary = new AtrezzoSummary( gameData.getAtrezzo( ) );
        inventory = new Inventory( );

        // Initialize the stack of queue of effects
        effectsQueue = new Stack<List<FunctionalEffect>>( );
        effectsQueue.push( new ArrayList<FunctionalEffect>( ) );

        //Initialize the stack that store if each list of effects keep in effectsQueue is made in a conversation or not
        isConvEffectsBlock = new Stack<Boolean>( );

        numberConv = 0;

        // Initialize the stack of states (used to keep the conversations and can throw its effects)
        stackOfState = new Stack<GameState>( );

        // By default, set the initial scene taking it from the XML script
        //GeneralScene initialScene = gameData.getInitialGeneralScene( );
        Exit firstScene = new Exit( true, 0, 0, 40, 40 );
        firstScene.setNextSceneId( gameData.getInitialGeneralScene( ).getId( ) );

        // Set the next scene
        setNextScene( firstScene );

        // Create the functional player
        functionalPlayer = new FunctionalPlayer( gameData.getPlayer( ) );
        functionalPlayer.setTransparent( gameDescriptor.getPlayerMode( ) == DescriptorData.MODE_PLAYER_1STPERSON );

        // Add timers to the TimerManager
        this.gameTimers = new HashMap<Integer, Timer>( );
        for( Timer timer : gameData.getTimers( ) ) {
            int id = timerManager.addTimer( timer, this, timer.getTime( ) );
            gameTimers.put( new Integer( id ), timer );
        }
        
        if (this.gameData.getGpsRules().size()>0){	
        	if ( GpsManager.getInstance() !=null)	
        		GpsManager.getInstance().addallgpsrules(this.gameData.getGpsRules());
		
		}
        
        if (this.gameData.getQrrules().size()>0){
        	if (QrcodeManager.getInstance()==null)
        		QrcodeManager.create();        	
        	QrcodeManager.getInstance().addAllQRRules(this.gameData.getQrrules());           	
        }
        currentState = new GameStateNextScene( );

        nextChapter = false;

        Log.d(TAG,"Chapter loaded" );
    }

	
	public void start() {

		try {
			// DEBUG
			Log.e("InicioGame", String.valueOf(Debug
					.getNativeHeapAllocatedSize()));

			this.timerManager = TimerManager.getInstance();
			totalTime = 0;
			long elapsedTime = 0;
			long oldTime = System.currentTimeMillis();
			long lastFps = 0;
			long time;
			int fps = 0;
			int oldFps = 0;

			// Load the game descriptor (it holds the info of the GUI and the
			// player)
			gameDescriptor = Loader.loadDescriptorData(ResourceHandler
					.getInstance());

			if (gameDescriptor == null) {
				// TODO possibly add dialog to tell player the game couldn't get
				// loaded
				return;
			}
			gameDescriptor.setProjectName(adventureName);

			currentState = new GameStateLoading();

			// Load the options
			options = new Options(prefs);
			
			GUI.getInstance().initHUD(); 
			
			if (options.isDebugActive()) 
				GUI.getInstance().enableDebugOverlay();

			// Init the assessment and adaptation engines
			// adaptationEngine = new AdaptationEngine( );
			assessmentEngine = new AssessmentEngine();

			currentChapter = 0;

			boolean needsName = false;

			for (ChapterSummary chapter : gameDescriptor.getChapterSummaries()) {
				AssessmentProfile ap = AssessmentEngine
						.loadAssessmentProfile(chapter.getAssessmentName());
				if (!needsName && ap != null && ap.isSendByEmail())
					needsName = true;
			}

			if (needsName) {
				// ASSESSMENT
				String name = "player1";
				gameDescriptor.setPlayerName(name);
				assessmentEngine.setPlayerName(name);
			}


			Log.e("InicioGame1", String.valueOf(Debug
					.getNativeHeapAllocatedSize()));
			

			while (!gameOver) {

				loadCurrentChapter();
				if (this.LoadedGame) {
					this.LoadedGame = false;
					this.load(this.LoadingGame);
				}

				finishloadingdialog();

				Log.e("InicioGame2", String.valueOf(Debug
						.getNativeHeapAllocatedSize()));

				while (!nextChapter && !gameOver) {

					if (this.globalState == STATE_RUNNING) {
						time = System.currentTimeMillis();
						elapsedTime = time - oldTime;
						oldTime = time;
						totalTime += elapsedTime;
						if (time - lastFps < 1000) {
							fps++;
						} else {
							lastFps = time;
							oldFps = fps;
							fps = 1;
						}

						GUI.getInstance().updateDebugInfo(oldFps, elapsedTime);

						currentState.mainLoop(elapsedTime, oldFps);

						MultimediaManager.getInstance().update();

						try {
							Thread.sleep(Math.max((10 - (System
									.currentTimeMillis() - time)), 0));
						} catch (InterruptedException e) {

						}
					} else {
						Thread.sleep(1000);		
					}

				}
				
				GUI.getInstance().clearScreen();
				functionalScene.stopBackgroundMusic();

				// If there is an assessment profile, show the "Save Report"
			
				while (!assessmentEngine.isEndOfChapterFeedbackDone()) {
					Thread.sleep(100);
				}

				if (currentChapter == gameDescriptor.getChapterSummaries().size())
					gameOver = true;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		try {
			stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	 private void finishloadingdialog() {
		 
		 Handler handler=GameThread.getInstance().getHandler();
	     Message msg = handler.obtainMessage();
	     Bundle b = new Bundle();
	     b.putInt("dialog", 1);
				
		 msg.what = ActivityHandlerMessages.FINISH_DIALOG;
		 msg.setData(b);
		 msg.sendToTarget();
		
	}

	private void writeMemoryUsageLog(FileWriter file,long elapsedTime) {

		 final long refresh = 1000;
		 
		 totalElapsedTime+=elapsedTime;
		 
		 if (totalElapsedTime>refresh) {
			 
			totalElapsedTime=0; 
		 
	        PrintWriter pw = null;
	        try
	        {
	            pw = new PrintWriter(file);

				String memorylog = currentState.toString()+","+Long.toString(Debug.getNativeHeapFreeSize())+
				","+Long.toString(Debug.getNativeHeapAllocatedSize())+
				","+Long.toString(Debug.getNativeHeapSize());

	                pw.println(memorylog);

	                Log.i("Memory",memorylog);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
		 }
		
	}

	
    
    private void preLoadAnimations( ) {

        MultimediaManager multimedia = MultimediaManager.getInstance( );
        for( Resources r : gameData.getPlayer( ).getResources( ) ) {
            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            if( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ) != null && !r.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ).equals( ASSET_EMPTY_ANIMATION ) )
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
            else
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_UP ), false, MultimediaManager.IMAGE_PLAYER );
            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_DOWN ), false, MultimediaManager.IMAGE_PLAYER );

            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            if( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ) != null && !r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ).equals( ASSET_EMPTY_ANIMATION ) )
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
            else
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_UP ), false, MultimediaManager.IMAGE_PLAYER );
            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );

            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            if( r.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ) != null && !r.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ).equals( ASSET_EMPTY_ANIMATION ) )
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
            else
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );

            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            if( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ) != null && !r.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ).equals( ASSET_EMPTY_ANIMATION ) )
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
            else
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_UP ), false, MultimediaManager.IMAGE_PLAYER );
            multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
        }
        for( NPC npc : gameData.getCharacters( ) ) {
            for( Resources r : npc.getResources( ) ) {
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
                if( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ) != null && !r.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ).equals( ASSET_EMPTY_ANIMATION ) )
                    multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_LEFT ), false, MultimediaManager.IMAGE_SCENE );
                else
                    multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_RIGHT ), true, MultimediaManager.IMAGE_SCENE );
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_UP ), false, MultimediaManager.IMAGE_SCENE );
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_STAND_DOWN ), false, MultimediaManager.IMAGE_SCENE );

                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
                if( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ) != null && !r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ).equals( ASSET_EMPTY_ANIMATION ) )
                    multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_LEFT ), false, MultimediaManager.IMAGE_SCENE );
                else
                    multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), true, MultimediaManager.IMAGE_SCENE );
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_UP ), false, MultimediaManager.IMAGE_SCENE );
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_DOWN ), false, MultimediaManager.IMAGE_SCENE );

                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
                if( r.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ) != null && !r.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ).equals( ASSET_EMPTY_ANIMATION ) )
                    multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_USE_LEFT ), false, MultimediaManager.IMAGE_SCENE );
                else
                    multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_SCENE );

                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
                if( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ) != null && !r.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ).equals( ASSET_EMPTY_ANIMATION ) )
                    multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_LEFT ), false, MultimediaManager.IMAGE_SCENE );
                else
                    multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_SCENE );
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_UP ), false, MultimediaManager.IMAGE_SCENE );
                multimedia.loadAnimation( r.getAssetPath( NPC.RESOURCE_TYPE_WALK_DOWN ), false, MultimediaManager.IMAGE_SCENE );
            }
        }

    }
    
    /**
     * Processes the adapted state
     * 
     * @param firstScene
     *            to add it a new scene
     * @param adaptedState
     */
    private void processAdaptedState( Exit firstScene, AdaptedState adaptedState ) {

        // If there is an adapted state to be executed
        if( adaptedState != null ) {

            // If it has an initial scene, set it
            if( adaptedState.getTargetId( ) != null ){
                boolean found = false;
                // check the scene is in chapter
                for( Scene scene : gameData.getScenes( ) ) {
                    if( scene.getId( ).equals( adaptedState.getTargetId( ) ) ){
                        firstScene.setNextSceneId( adaptedState.getTargetId( ) );
                        found = true;
                    }
                    
                }
                if (!found){
                // check the scene is a cutscene
                for( Cutscene cutscene : gameData.getCutscenes( ) ) {
                    if( cutscene.getId( ).equals( adaptedState.getTargetId( ) ) )
                        firstScene.setNextSceneId( adaptedState.getTargetId( ) );
                
                      }
                 }
            }
            // Set the flags
            for( String flag : adaptedState.getActivatedFlags( ) )
                if( flags.existFlag( flag ) )
                    flags.activateFlag( flag );
            for( String flag : adaptedState.getDeactivatedFlags( ) )
                if( flags.existFlag( flag ) )
                    flags.deactivateFlag( flag );
            // Set the vars
            List<String> adaptedVars = new ArrayList<String>( );
            List<String> adaptedValues = new ArrayList<String>( );
            adaptedState.getVarsValues( adaptedVars, adaptedValues );
            for( int i = 0; i < adaptedVars.size( ); i++ ) {
                String varName = adaptedVars.get( i );
                String varValue = adaptedValues.get( i );
                // check if it is a "set value" operation
                if( AdaptedState.isSetValueOp( varValue ) ) {
                    String val = AdaptedState.getSetValueData( varValue );
                    if( val != null )
                        vars.setVarValue( varName, Integer.parseInt( val ) );
                }
                // it is "increment" or "decrement" operation, for both of them is necessary to 
                // get the current value of referenced variable
                else {
                    if( vars.existVar( varName ) ) {
                        int currentValue = vars.getValue( varName );
                        int operationValue = Integer.parseInt( varValue.substring( varValue.indexOf( " " ) + 1 ) );
                        if( AdaptedState.isIncrementOp( varValue.substring( 0, varValue.indexOf( " " ) ) ) ) {
                            vars.setVarValue( varName, currentValue + operationValue );
                        }
                        else if( AdaptedState.isDecrementOp( varValue.substring( 0, varValue.indexOf( " " ) ) ) ) {
                            if( currentValue - operationValue >= 0 )
                                vars.setVarValue( varName, currentValue - operationValue );
                            else
                                vars.setVarValue( varName, 0 );
                        }
                    }
                }
            }

        }
    }

	
	private void stop() {
		  //Stop the music (if it is playing) and the adaptation clock
        if( functionalScene != null )
            functionalScene.stopBackgroundMusic( );
        // ADAPTATION
//        if( adaptationEngine != null )
//            adaptationEngine.stopAdaptationClock( );

        // Stop the communication 
        /*if( comm.getCommType( ) == CommManagerApi.SCORMV12_TYPE ) {
            comm.disconnect( null );
        }*/
        staticStop( );
		
	}
	
	  /**
     * Stops all sounds and music, the gui, etc
     */
    private static void staticStop( ) {
    
    		// deletes gps if active
		if (GpsManager.getInstance() != null) {
			GpsManager.getInstance().finishgps();
		}

        //Delete all sounds
        if( MultimediaManager.getInstance( ) != null )
            MultimediaManager.getInstance( ).deleteSounds( );

        //Hide the GUI
        if( GUI.getInstance( ) != null) {
            // Delete the GUI
            GUI.delete( );
        }
    }
	
	// GENERAL METHODS //
	
    public String processText( String text ) {
        return flags.processText( vars.processText( text ) );
    }
    
    /**
     * Clears all the effects from the effects queue
     */
    public void flushEffectsQueue( ) {

        effectsQueue.clear( );
    }
    
    public void evaluateState( ) {

        if( numberConv < stackOfState.size( ) ) {
            currentState = stackOfState.pop( );
            // set the game attribute conversation to stored conversation
            setConversation( ( (GameStateConversation) currentState ).getConvID( ) );

        }
        else if( !isEmptyFIFOinStack( ) )
            setState( STATE_RUN_EFFECTS );
        else
            setState( STATE_PLAYING );
    }
    
    public void goToNextChapter( ) {

        currentChapter++;
        nextChapter = true;
    }
    
    public void goToChapter( int chapter ) {

        currentChapter = chapter;
        nextChapter = true;
    }
    
    // FunctoinalEffectsMethods //
    
    /**
     * Update the data pending from the flags. This include the resources of the
     * game, and the rules processed.
     */
    public synchronized void updateDataPendingFromState( boolean notifyTimerCycles ) {

        timerManager.update( notifyTimerCycles );
        functionalScene.updateScene( );
        if( gameData.hasAssessmentProfile( ) )
        	assessmentEngine.processRules( );

    }
    
    /**
     * Removes an item from the inventory, and counts it as a consumed item
     * 
     * @param itemId
     *            Id of the item
     */
    public void consumeItem( String itemId ) {

        // Remove the FunctionalItem from the inventory
        if( itemSummary.isItemGrabbed( itemId ) ) {
            itemSummary.consumeItem( itemId );
            inventory.consumeItem( itemId );
        }
    }
    
    /**
     * Stores a series of effects in the queue, and changes the state of the
     * game
     * 
     * @param effects
     *            List of effects to be stored
     * @param fromConversation
     *            Distinguish when the State run effects are called from a
     *            conversation, to manage the stack of states which only stores
     *            conversation states.
     */
    public void storeEffectsInQueue( List<FunctionalEffect> effects, boolean fromConversation ) {

        isConvEffectsBlock.push( fromConversation );
        effectsQueue.push( new ArrayList<FunctionalEffect>( ) );
        for( int i = 0; i < effects.size( ); i++ )
            effectsQueue.peek( ).add( i, effects.get( i ) );

        if( fromConversation )
            numberConv++;

        if( !( currentState instanceof GameStateRunEffects ) ) {
            setState( STATE_RUN_EFFECTS );
        }
    }
    
    /**
     * Places an item in the inventory (if its previous state was normal)
     * 
     * @param itemId
     *            Id of the item
     */
    public void generateItem( String itemId ) {

        if( itemSummary.isItemNormal( itemId ) ) {
           // 23/11/2010 the object has to disappear from the scene when it's generated (aba)
            // inventory.storeItem( new FunctionalItem( gameData.getItem( itemId ), (InfluenceArea) null ) );
           // itemSummary.grabItem( itemId );
            grabItem(itemId);
        }
        else if( itemSummary.isItemConsumed( itemId ) ) {
            // 23/11/2010 in this case it is not necesary because the item is not being showhed in the secene
            itemSummary.regenerateItem( itemId );
            inventory.storeItem( new FunctionalItem( gameData.getItem( itemId ), (InfluenceArea) null ) );
            itemSummary.grabItem( itemId );
        }
    }
    
    // Functionaldata methods //
    
    /**
     * Removes an item from the scene (if placed there) and moves it to the
     * inventory
     * 
     * @param itemId
     *            Id of the item
     */
    public void grabItem( String itemId ) {

        // Remove the FunctionalItem from the scene and store it into the inventory,  
        FunctionalItem grabbedItem = null;

        for( FunctionalItem currentItem : functionalScene.getItems( ) )
            // If we found the item we wanted
            if( currentItem.getItem( ).getId( ).equals( itemId ) )
                grabbedItem = currentItem;
                    
        // if the element is not in the scene, take it from the data model
        if (grabbedItem == null){
            grabbedItem = new FunctionalItem( gameData.getItem( itemId ), (InfluenceArea) null );
        } 
        // if the element is in the scene
        else {
            
            // Delete the item from the scene
            functionalScene.getItems( ).remove( grabbedItem );
        }

        
        // Insert the item in the inventory
        inventory.storeItem( grabbedItem );

        // Count the item as grabbed
        itemSummary.grabItem( itemId );
    }
    
    /**
     * Returns the atrezzo item summary
     * 
     * @return Atrezzo item summary
     */
    public AtrezzoSummary getAtrezzoItemSummary( ) {

        return atrezzoSummary;
    }
	
    

 
 
    /**
     * Push in the state stack the GameState gs
     * 
     * @param gs
     *            GameState to store
     */
    // this method is only used with GameStateConversation
    public void pushCurrentState( GameState gs ) {

        stackOfState.push( gs );

        // store the name of the conversation for future conversation restoring. It will be needed to
        // restore the effects in nodes of this conversation.
        ( (GameStateConversation) currentState ).setConvID( conversation.getId( ) );

    }
    
    public void endConversation( ) {

        if( !isEmptyFIFOinStack( ) )
            setState( STATE_RUN_EFFECTS );
        else if( !stackOfState.isEmpty( ) )
            evaluateState( );
        else
            setState( STATE_PLAYING );

    }
    
    // SAVE & LOAD METHODS //
    
    public void save( String saveFile ) {

        SaveGame saveGame = new SaveGame( );
        saveGame.setVersionNumber( Integer.parseInt( gameDescriptor.getVersionNumber( ) ) );
        saveGame.setProjectName( gameDescriptor.getProjectName( ) );
        saveGame.setTitle( gameDescriptor.getTitle( ) );
        saveGame.setChapter( currentChapter );
        Calendar calendar = new GregorianCalendar( );
        saveGame.setSaveTime( calendar.get( Calendar.DAY_OF_MONTH ) + "/" + ( calendar.get( Calendar.MONTH ) + 1 ) + "/" + calendar.get( Calendar.YEAR ) + " " + calendar.get( Calendar.HOUR_OF_DAY ) + ":" + calendar.get( Calendar.MINUTE ) );
        saveGame.setTotalTime( totalTime );
        saveGame.setFlags( flags );
        saveGame.setVars( vars );
        saveGame.setIdScene( functionalScene.getScene( ).getId( ) );
        saveGame.setItemSummary( itemSummary );
        saveGame.setPlayerX( functionalPlayer.getX( ) );
        saveGame.setPlayerY( functionalPlayer.getY( ) );
        saveGame.setTimers( timerManager );
        if( !saveGame.saveTxt( saveFile ) )
            System.err.println( "* Error: There has been an error, savegame ''savedgame.egame'' not saved." );
    }
    
    public void load( String saveFile ) {

        SaveGame saveGame = new SaveGame( );
        try {
            if( saveGame.loadTxt( saveFile ) ) {
                setState( STATE_LOADING );

                if( gameDescriptor.getTitle( ).equals( saveGame.getTitle( ) ) ) {

                    currentChapter = saveGame.getChapter( );

                    if( gameDescriptor.getChapterSummaries( ).get( currentChapter ) != null ) {
                        ChapterSummary chapter = gameDescriptor.getChapterSummaries( ).get( currentChapter );
                        gameData = Loader.loadChapterData( ResourceHandler.getInstance( ), chapter.getChapterPath( ), new ArrayList<Incidence>( ), true );
                    }
                    totalTime = saveGame.getTotalTime( );
                    if( saveGame.getFlags( ) != null )
                        flags = saveGame.getFlags( );
                    if( saveGame.getVars( ) != null )
                        vars = saveGame.getVars( );

                    itemSummary = saveGame.getItemSummary( );

                    //functionalPlayer.setDestiny( 0, 0 );

                    // TODO check that the following line isn't necessary
                    //functionalPlayer.setState( FunctionalPlayer.IDLE );
                    functionalPlayer.cancelActions( );
                    functionalPlayer.cancelAnimations( );

                    if( gameData.getGeneralScene( saveGame.getIdScene( ) ) != null )
                        functionalScene = new FunctionalScene( (Scene) gameData.getGeneralScene( saveGame.getIdScene( ) ), functionalPlayer );

                    functionalPlayer.setX( saveGame.getPlayerX( ) );
                    functionalPlayer.setY( saveGame.getPlayerY( ) );

                    inventory = new Inventory( );
                    ArrayList<String> grabbedItems = itemSummary.getGrabbedItems( );

                    for( String item : grabbedItems )
                        inventory.storeItem( new FunctionalItem( gameData.getItem( item ), (InfluenceArea) null ) );

                    SaveTimer st = new SaveTimer( );
                    String[] timers = saveGame.getLoadTimers( );
                    if( timers != null ) {
                        for( int i = 0; i < timers.length; i++ ) {

                            // take the correct values for each timer
                            String timer = timers[i];
                            String[] aux = timer.split( "-" );
                            boolean isAssessment = Integer.valueOf( aux[3] ).intValue( ) == 0;
                            st.setState( Integer.valueOf( aux[0] ).intValue( ) );
                            if( timerManager.isRunningState( Integer.valueOf( aux[0] ).intValue( ) ) ) {
                                st.setLastUpdate( System.currentTimeMillis( ) / 1000 );
                                if( !isAssessment )
                                    st.setTimeUpdate( Integer.valueOf( aux[1] ).longValue( ) - Integer.valueOf( aux[2] ).longValue( ) );
                            }
                            else {
                                st.setLastUpdate( 0 );
                                if( !isAssessment )
                                    st.setTimeUpdate( Integer.valueOf( aux[1] ).longValue( ) );
                            }
                            // change this values in the current TimerManager
                            int check = timerManager.changeValueOfTimer( i, st );
                            if( check >= 0 ) {
                                // Put this changes in gameTimers
                                //gameTimers.get(new Integer(i)).
                            }
                            else {
                                System.out.println( "* Error: There has been an error, savegame ''savedgame.egame'' not propperly loaded." );
                            }
                            // If it is assessment timer, set the correct values in assessmentEngine
                            if( isAssessment ) {
                                // current time - the time in second that has been
                             if( assessmentEngine.getTimedAssessmentRule( new Integer( i ) ) != null )
                                  assessmentEngine.getTimedAssessmentRule( new Integer( i ) ).setStartTime( System.currentTimeMillis( ) / 1000 - Integer.valueOf( aux[2] ).longValue( ) );
                            }

                        }

                     
                    }

                    setState( STATE_PLAYING );
                }
                else {
                    System.out.println( "* Error: There has been an error, savegame ''savedgame.egame'' not loaded." );
                }
            }
        }
        catch( SaveGameException e ) {
        	Log.i("Game.load","There was an error while loading the selected adventure");
            JOptionPane.showMessageDialog( null, "There was an error while loading the selected adventure.\nPlease check that no configuration file is missing or incorrect", "Error loading adventure", JOptionPane.ERROR_MESSAGE );
            JOptionPane.showMessageDialog( null, "The first chapter will be reloaded", "Error loading adventure", JOptionPane.ERROR_MESSAGE );
        //    Graphics2D g = GUI.getInstance( ).getGraphics( );
            currentChapter = 0;
            loadCurrentChapter(  );
        }

    }
    
    /**
     * Saves the options to a file
     */
    public void saveOptions( ) {

   //     options.saveOptions( adventurePath, adventureName );
    }
    
    
	
	// GETTERS //
	
    /**
     * Returns the game descriptor
     * 
     * @return Game descriptor
     */
    public DescriptorData getGameDescriptor( ) {

        return gameDescriptor;
    }
    
    /**
     * Returns the functional scene being played
     * 
     * @return Functional scene
     */
    public FunctionalScene getFunctionalScene( ) {

        return functionalScene;
    }
    
    /**
     * Returns the flag summary
     * 
     * @return Flag summary
     */
    public FlagSummary getFlags( ) {

        return flags;
    }

    /**
     * Returns the var summary
     * 
     * @return Var summary
     */
    public VarSummary getVars( ) {

        return vars;
    }
    
    /**
     * Returns the game data
     * 
     * @return Game data
     */
    public Chapter getCurrentChapterData( ) {

        return gameData;
    }

    /**
     * Returns the options of the game
     * 
     * @return Options of the game
     */
    public Options getOptions( ) {

        return options;
    }
    
    /**
     * Returns the functional player
     * 
     * @return Functional player
     */
    public FunctionalPlayer getFunctionalPlayer( ) {

        return functionalPlayer;
    }
    
    /**
     * Returns the current character currently talking
     * 
     * @return Character currently talking
     */
    public TalkingElement getCharacterCurrentlyTalking( ) {

        return characterCurrentlyTalking;
    }
    
    /**
     * Returns the last next scene
     * 
     * @return Next scene
     */
    public Exit getLastScene( ) {

        return lastNextScene;
    }
    
    /**
     * Returns the item summary
     * 
     * @return Item summary
     */
    public ItemSummary getItemSummary( ) {

        return itemSummary;
    }
    

    /**
     * Returns the inventory
     * 
     * @return Inventory
     */
    public Inventory getInventory( ) {

        return inventory;
    }
    
    /**
     * Returns the action manager
     * 
     * @return Action manager
     */
    public ActionManager getActionManager( ) {

        return actionManager;
    }
    
    /**
     * Returns the current next scene
     * 
     * @return Next scene
     */
    public Exit getNextScene( ) {

        return nextScene;
    }
    

    public int getTime( ) {

        return (int) totalTime / 1000;
    }
    
    /**
     * Returns the book
     * 
     * @return Book
     */
    public Book getBook( ) {

        return book;
    }
    
    /**
     * Returns the next conversation to be played
     * 
     * @return Conversation
     */
    public Conversation getConversation( ) {

        return conversation;
    }
    
    /**
     * Returns the current npc stored to perform conversations by default
     * 
     * @return Functional character
     */
    public FunctionalNPC getCurrentNPC( ) {

        return currentNPC;
    }
    
    /**
     * Gets the adventure name
     * 
     * @return the name of the adventure
     */
    public String getAdventureName( ) {

        return adventureName;
    }
    
    /**
     * Returns the adapted state that must be executed.
     * 
     * @return The adapted state to be executed, null if there is none
     */
    public AdaptedState getAdaptedStateToExecute( ) {

        return adaptedStateToExecute;
    }
    
    /**
     * Gets the first element of the top of the stack
     */
    public FunctionalEffect getFirstElementOfTop( ) {

        FunctionalEffect toReturn = null;
        if( effectsQueue.size( ) > 1 && effectsQueue.peek( ).isEmpty( ) ) {
            effectsQueue.pop( );
            if( isConvEffectsBlock.pop( ) )
                numberConv--;
        }
        else {
            if( effectsQueue.peek( ).size( ) != 0 ) {
                toReturn = effectsQueue.peek( ).remove( 0 );
                if( effectsQueue.size( ) > 1 && effectsQueue.peek( ).isEmpty( ) ) {
                    effectsQueue.pop( );
                    if( isConvEffectsBlock.pop( ) )
                        numberConv--;
                }
            }
        }
        return toReturn;

        //Con esto que esta comentado no solo avanzo en la cola, sino en la pila tb
        /*if (effectsQueue.peek().size()==1 && effectsQueue.size()!=1){
        	FunctionalEffect fe = effectsQueue.peek().remove(0);
        	popEffectsStack();
        	return fe;
        }else
        	return effectsQueue.peek().remove(0);*/
        //Solo avanzamos en la cola

    }

    
    /**
     * Returns true if the current player mode is Transparent. False otherwise
     * 
     * @return
     */
    public boolean isTransparent( ) {

        return getGameDescriptor( ).getPlayerMode( ) == DescriptorData.MODE_PLAYER_1STPERSON;
    }
    
    /**
     * Returns whether there is an active communication link with a Learning
     * Management System
     * 
     * @return true If the communication is active
     *//*
    public boolean isConnected( ) {

        if( comm != null ) {
            return comm.isConnected( );
        }
        return false;
    }*/
    
    //SETTERS //
    
    /**
     * Sets the current npc stored to perform conversations by default
     * 
     * @param currentNPC
     *            New functional character
     */
    public void setCurrentNPC( FunctionalNPC currentNPC ) {

        this.currentNPC = currentNPC;
    }
    
    /**
     * Sets the next scene to be loaded
     * 
     * @param nextScene
     *            New next scene structure
     */
    public void setNextScene( Exit nextScene ) {

        this.lastNextScene = this.nextScene;
        this.nextScene = nextScene;
    }
    
    /**
     * Sets the new state for the game
     * 
     * @param state
     *            New game state
     */
    public void setState( int state ) {
    	
    	
        switch( state ) {
            case STATE_LOADING:
                currentState = new GameStateLoading( );
                break;
            case STATE_PLAYING:
                currentState = new GameStatePlaying( );
                currentState.registerTouchListener(sceneTouchListener);
                break;
            case STATE_SLIDE_SCENE: 
               currentState = new GameStateSlidescene( );
               currentState.registerTouchListener(sceneTouchListener);
                break;
            case STATE_NEXT_SCENE:
                currentState = new GameStateNextScene( );
                break;
            case STATE_VIDEO_SCENE:
               currentState = new GameStateVideoscene( );
                break;
            case STATE_RUN_EFFECTS:
               currentState = new GameStateRunEffects( this.isConvEffectsBlock.peek( ) );
                break;
            case STATE_RUN_EFFECTS_FROM_CONVERSATION:
               currentState = new GameStateRunEffects( true );
                break;
                           
            case STATE_BOOK:
               currentState = new GameStateBook( );
               currentState.registerTouchListener(sceneTouchListener);

                break;
            case STATE_CONVERSATION:
                currentState = new GameStateConversation( );
                currentState.registerTouchListener(sceneTouchListener);
                break;
                
            case STATE_VIDEO:
                currentState = new GameStateBook( );
                currentState.registerTouchListener(sceneTouchListener);
                 break;    

        }
    }
    
    /**
     * Sets the character currently talking
     * 
     * @param characterCurrentlyTalking
     *            New character currently talking
     */
    public void setCharacterCurrentlyTalking( TalkingElement characterCurrentlyTalking ) {

        this.characterCurrentlyTalking = characterCurrentlyTalking;
    }
    
    /**
     * Sets the book to be displayed
     * 
     * @param bookId
     *            Book id
     */
    public void setBook( String bookId ) {

        this.lastNextScene = this.getNextScene( );
        book = gameData.getBook( bookId );
    }
    
    /**
     * Sets the next conversation to be played
     * 
     * @param conversationId
     *            New conversation
     */
    public void setConversation( String conversationId ) {

        conversation = gameData.getConversation( conversationId );
    }
    
    public void setPlayerLayer( int layer ) {

        functionalPlayer.setLayer( layer );

    }
    
    /**
     * Sets the functional scene
     * 
     * @param scene
     *            New functional scene
     */
    public void setFunctionalScene( FunctionalScene scene ) {
    	
    	if (this.functionalScene != null) 
            functionalScene.freeMemory();
        
    	functionalScene = null;
    	System.gc();

        this.functionalScene = scene;
    }

    public void setGameOver( ) {

        gameOver = true;
    }
	// HANDLING GLOBAL STATE //

	private void setGlobalState(int state) {
	   this.globalState=state;
	}
	
	public void unpause(SurfaceHolder canvasHolder) {
		GUI.getInstance().setCanvasSurfaceHolder(canvasHolder);
		
		setGlobalState(STATE_RUNNING);
	}
	
	public void unpause() {
		setGlobalState(STATE_RUNNING);
	}

	public void pause() {
		setGlobalState(STATE_PAUSE);
	}
	
	public boolean ispause() {
		boolean pause=false;
		
		if (globalState==STATE_PAUSE)
			pause=true;
		return pause;
	}

	public Bundle saveState(Bundle map) {
		return null;
	}

	public synchronized void restoreState(Bundle savedState) {
		setGlobalState(STATE_RUNNING);
	}

	
	// TIMER EVENT LISTENER INTERFACE IMPLEMENTATION//

    public void cycleCompleted( int timerId, long elapsedTime ) {
    	
    	if (GUI.getInstance().getHUD().getState() instanceof DraggingState){
        	((DraggingState) GUI.getInstance().getHUD().getState()).clearDraggingElement();
        }
    	GUI.getInstance().getHUD().reset();
        
        this.getFunctionalPlayer().cancelActions();

        //System.out.println("Timer " + timerId + " expired, executing effects.");
        Timer timer = gameTimers.get( new Integer( timerId ) );
        FunctionalEffects.storeAllEffects( timer.getEffects( ) );
    }

    public void timerStarted( int timerId, long currentTime ) {

        //System.out.println("Timer " + timerId + " starting");
        // Do nothing
    }

    public void timerStopped( int timerId, long currentTime ) {
    	
    	if (GUI.getInstance().getHUD().getState() instanceof DraggingState){
        	((DraggingState) GUI.getInstance().getHUD().getState()).clearDraggingElement();
        }
    	GUI.getInstance().getHUD().reset();
        
        this.getFunctionalPlayer().cancelActions();

        //System.out.println("Timer " + timerId + " was stopped, executing effects");
        Timer timer = gameTimers.get( new Integer( timerId ) );
        FunctionalEffects.storeAllEffects( timer.getPostEffects( ) );
        //timerManager.deleteTimer( timerId );
    }
	
    /**
     * Check if the Stack only have one empty FIFO
     */
    public boolean isEmptyFIFOinStack( ) {

        return effectsQueue.peek( ).isEmpty( );
    }
    
    // Assessment methods //
    /**
     * Returns the assessment engine
     * 
     * @return Assessment engine
     */
  
    public AssessmentEngine getAssessmentEngine( ) {

        return assessmentEngine;
    }
    
	public void finish() {
		
		this.gameOver=true;
	}
    
	
	public boolean processTouchEvent(MotionEvent e) {
		return currentState.processTouchEvent(e);
	}
	
	public boolean processTrackballEvent(MotionEvent e) {
		return currentState.processTrackballEvent(e);
	}
	
	public boolean processKeyEvent(KeyEvent e) {
		return currentState.processKeyEvent(e);
	}
		
	public boolean processSensorEvent(SensorEvent e) {
		return currentState.processSensorEvent(e);
	}

	public void setvideostatefinish() {
		((GameStateVideoscene) currentState).setstop(true);
		
	}
	
	 public boolean isIsometric() {
	        return gameDescriptor.getPerspective( ) == Perspective.ISOMETRIC;
	    }

}
