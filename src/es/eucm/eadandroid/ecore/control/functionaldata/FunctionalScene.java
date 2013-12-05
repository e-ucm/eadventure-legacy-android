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
package es.eucm.eadandroid.ecore.control.functionaldata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.graphics.Bitmap;
import android.os.Debug;
import android.util.Log;
import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.data.chapter.ElementReference;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.common.data.chapter.elements.ActiveArea;
import es.eucm.eadandroid.common.data.chapter.elements.Atrezzo;
import es.eucm.eadandroid.common.data.chapter.elements.Barrier;
import es.eucm.eadandroid.common.data.chapter.elements.Item;
import es.eucm.eadandroid.common.data.chapter.elements.NPC;
import es.eucm.eadandroid.common.data.chapter.resources.Asset;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.data.chapter.scenes.Scene;
import es.eucm.eadandroid.ecore.control.ActionManager;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.ItemSummary;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalExit;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalGoTo;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;


/**
 * A scene in the game
 */
public class FunctionalScene implements Renderable {

    /**
     * Margins of the scene (for use in the scroll)
     */
    private final static int MAX_OFFSET_X = 300;    

    /**
     * Scene data
     */
    private Scene scene;

    /**
     * Resources being used in the scene
     */
    private Resources resources;

    /**
     * Background image for the scene
     */
    private Bitmap background;

    /**
     * Foreground image for the scene
     */
    private Bitmap foreground;

    /**
     * Background music
     */
    private long backgroundMusicId = -1;

    /**
     * Functional player present in the scene
     */
    private FunctionalPlayer player;

    /**
     * Functional items present in the scene
     */
    private ArrayList<FunctionalItem> items;

    /**
     * Functional characters present in the scene
     */
    private ArrayList<FunctionalNPC> npcs;

    /**
     * Functional areas present in the scene
     */
    private ArrayList<FunctionalActiveArea> areas;

    /**
     * Functional barriers present in the scene;
     */
    private ArrayList<FunctionalBarrier> barriers;

    /**
     * Functional atrezzo items present in the scene
     */
    private ArrayList<FunctionalAtrezzo> atrezzo;

    private FunctionalTrajectory trajectory;

    /**
     * Offset of the scroll.
     */
    private int offsetX;

    private boolean moveOffsetRight = false;

    private boolean moveOffsetLeft = false;

    private boolean showsOffsetArrows = false;

    /**
     * Creates a new FunctionalScene loading the background music.
     * 
     * @param scene
     *            the scene's data
     * @param player
     *            the reference to the player
     */
    public FunctionalScene( Scene scene, FunctionalPlayer player ) {

        this( scene, player, -1 );
    }

    /**
     * Creates a new FunctionalScene with the given background music.
     * 
     * @param scene
     *            the scene's data
     * @param player
     *            the reference to the player
     * @param backgroundMusicId
     *            Background music identifier
     */
    public FunctionalScene( Scene scene, FunctionalPlayer player, long backgroundMusicId ) {
    	
    	/* DEBUG MEMORY ALLOCATION SCENE CREATION */
    	
    	long sceneMem = 0;
    	long bkgMem = 0;
    	long fgMem = 0;
    	long itemMem =0;
    	long npcMem =0;
    	long aareaMem = 0;
    	long barrierMem = 0;
    	long atrezzoMem = 0;
    	
    	/////////////////////////////////////////////////////////
    	   
    	sceneMem = Debug.getNativeHeapAllocatedSize();
    	
    	Log.e("Inicio creacion escena",String.valueOf(Debug.getNativeHeapAllocatedSize()));
    	
    	/////////////////////////////////////////////////////////
        
        this.scene = scene;
        this.player = player;

        // Create lists for the characters, items and active areas
        npcs = new ArrayList<FunctionalNPC>( );
        items = new ArrayList<FunctionalItem>( );
        atrezzo = new ArrayList<FunctionalAtrezzo>( );
        areas = new ArrayList<FunctionalActiveArea>( );
        barriers = new ArrayList<FunctionalBarrier>( );
        trajectory = new FunctionalTrajectory( scene.getTrajectory( ), barriers);

        // Pick the item summary
        Chapter gameData = Game.getInstance( ).getCurrentChapterData( );
        ItemSummary itemSummary = Game.getInstance( ).getItemSummary( );

        // Select the resources
        resources = createResourcesBlock( );
        
    	/////////////////////////////////////////////////////////
        
    	bkgMem = Debug.getNativeHeapAllocatedSize();
        
        Log.e("BackgroundAntes",String.valueOf(Debug.getNativeHeapAllocatedSize()));
        
    	/////////////////////////////////////////////////////////

        // Load the background image
        background = null;
        if( resources.existAsset( Scene.RESOURCE_TYPE_BACKGROUND ) )
            background = MultimediaManager.getInstance( ).loadImage( resources.getAssetPath( Scene.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );

        if( Game.getInstance( ).isTransparent( ) && background != null && background.getWidth( ) > GUI.WINDOW_WIDTH ) {
            showsOffsetArrows = true;
        }
        
    	/////////////////////////////////////////////////////////
        
    	bkgMem = Debug.getNativeHeapAllocatedSize() - bkgMem;
    	
        Log.e("BackgroundDespues",String.valueOf(Debug.getNativeHeapAllocatedSize()));
        
        
    	fgMem = Debug.getNativeHeapAllocatedSize(); 
        
        Log.e("ForegroundAntes",String.valueOf(Debug.getNativeHeapAllocatedSize()));
        
    	/////////////////////////////////////////////////////////


        // Load the foreground image
        foreground = null;
        if( background != null && resources.existAsset( Scene.RESOURCE_TYPE_FOREGROUND ) ) {
            Bitmap bufferedBackground =  background;
            Bitmap foregroundHardMap =  MultimediaManager.getInstance( ).loadImage( resources.getAssetPath( Scene.RESOURCE_TYPE_FOREGROUND ), MultimediaManager.IMAGE_SCENE );
            Bitmap bufferedForeground = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( foregroundHardMap.getWidth(  ), foregroundHardMap.getHeight(  ), true );

            for( int i = 0; i < foregroundHardMap.getWidth(  ); i++ ) {
                for( int j = 0; j < foregroundHardMap.getHeight(  ); j++ ) {
                    if( foregroundHardMap.getPixel(i, j)==0xFFFFFFFF ) //GRAPHICS (foregroundHardMap.getRGB( i, j ) == 0xFFFFFFFF )
                        bufferedForeground.setPixel(i, j, 0x00000000); //bufferedForeground.setRGB( i, j, 0x00000000 );
                    else
                        bufferedForeground.setPixel( i, j, bufferedBackground.getPixel(i, j) );
                }
            }

            foregroundHardMap = null;
            bufferedBackground = null;              
            foreground = bufferedForeground;
        }
        
    	/////////////////////////////////////////////////////////
        
    	fgMem = Debug.getNativeHeapAllocatedSize() - fgMem; 
        Log.e("ForegroundDespues",String.valueOf(Debug.getNativeHeapAllocatedSize()));

    	/////////////////////////////////////////////////////////
        
        // Load the background music (if it is not loaded)
        this.backgroundMusicId = backgroundMusicId;
        if( backgroundMusicId == -1 )
           playBackgroundMusic( );
        
    	/////////////////////////////////////////////////////////
        
    	itemMem = Debug.getNativeHeapAllocatedSize();
    	
    	/////////////////////////////////////////////////////////

        // Add the functional items
        for( ElementReference itemReference : scene.getItemReferences( ) )
            if( new FunctionalConditions( itemReference.getConditions( ) ).allConditionsOk( ) )
                if( itemSummary.isItemNormal( itemReference.getTargetId( ) ) )
                    for( Item currentItem : gameData.getItems( ) )
                        if( itemReference.getTargetId( ).equals( currentItem.getId( ) ) ) {
                            FunctionalItem fitem = new FunctionalItem( currentItem, itemReference );
                            items.add( fitem );
                        }
        
    	/////////////////////////////////////////////////////////
        
    	itemMem = Debug.getNativeHeapAllocatedSize() - itemMem;  
    	
    	npcMem = Debug.getNativeHeapAllocatedSize(); 
    	/////////////////////////////////////////////////////////
       
        // Add the functional characters
        for( ElementReference npcReference : scene.getCharacterReferences( ) )
            if( new FunctionalConditions( npcReference.getConditions( ) ).allConditionsOk( ) )
                for( NPC currentNPC : gameData.getCharacters( ) )
                    if( npcReference.getTargetId( ).equals( currentNPC.getId( ) ) ) {
                        FunctionalNPC fnpc = new FunctionalNPC( currentNPC, npcReference );
                        npcs.add( fnpc );
                    }
    	/////////////////////////////////////////////////////////
        
    	npcMem = Debug.getNativeHeapAllocatedSize() - npcMem; 
    	
    	aareaMem= Debug.getNativeHeapAllocatedSize(); 
    	/////////////////////////////////////////////////////////
        
        // Add the functional active areas
        for( ActiveArea activeArea : scene.getActiveAreas( ) )
            if( new FunctionalConditions( activeArea.getConditions( ) ).allConditionsOk( ) )
                this.areas.add( new FunctionalActiveArea( activeArea, activeArea.getInfluenceArea( ) ) );
    	///////////////////////////////////////////////////////// 
        
    	aareaMem = Debug.getNativeHeapAllocatedSize() -aareaMem; 
    	barrierMem = Debug.getNativeHeapAllocatedSize(); 
    	
    	/////////////////////////////////////////////////////////
        // Add the functional barriers
        for( Barrier barrier : scene.getBarriers( ) )
            if( new FunctionalConditions( barrier.getConditions( ) ).allConditionsOk( ) )
                this.barriers.add( new FunctionalBarrier( barrier ) );
    	///////////////////////////////////////////////////////// 
        
        barrierMem = Debug.getNativeHeapAllocatedSize() - barrierMem; 
    	atrezzoMem = Debug.getNativeHeapAllocatedSize(); 
    	
    	/////////////////////////////////////////////////////////
        // Add the functional atrezzo items
        for( ElementReference atrezzoReference : scene.getAtrezzoReferences( ) )
            if( new FunctionalConditions( atrezzoReference.getConditions( ) ).allConditionsOk( ) )
                for( Atrezzo currentAtrezzo : gameData.getAtrezzo( ) )
                    if( atrezzoReference.getTargetId( ).equals( currentAtrezzo.getId( ) ) ) {
                        FunctionalAtrezzo fatrezzo = new FunctionalAtrezzo( currentAtrezzo, atrezzoReference );
                        atrezzo.add( fatrezzo );
                    }
    	/////////////////////////////////////////////////////////
        
        atrezzoMem = Debug.getNativeHeapAllocatedSize() - atrezzoMem; 
    	
    	/////////////////////////////////////////////////////////

		/*if (GpsManager.getInstance() != null) {
			GpsManager.getInstance().changeOfScene(scene.getId());
		}
		
		if (QrcodeManager.getInstance() != null) {
			QrcodeManager.getInstance().changeOfScene(scene.getId());
		}*/



        updateOffset( );
        
    	/////////////////////////////////////////////////////////
        
    	sceneMem = Debug.getNativeHeapAllocatedSize() - sceneMem;
        
        Log.e("Fin creacion escena",String.valueOf(Debug.getNativeHeapAllocatedSize()));
        
        Log.e("Escena",String.valueOf(sceneMem/1048576)+"MB");
        Log.e("BKG",String.valueOf(bkgMem/1048576)+"MB");
        Log.e("FG",String.valueOf(fgMem/1048576)+"MB");
        Log.e("Items",String.valueOf(itemMem/1048576)+"MB");
        Log.e("Npcs",String.valueOf(npcMem/1048576)+"MB");
        Log.e("Aarea",String.valueOf(aareaMem/1048576)+"MB");
        Log.e("Barriers",String.valueOf(barrierMem/1048576)+"MB");
        Log.e("Atrezzo",String.valueOf(atrezzoMem/1048576)+"MB");
        
        String[] memInfo = {"SCENE : "+String.valueOf(sceneMem/1048576)+"MB",
        		"BKG: "+String.valueOf(bkgMem/1048576)+"MB",
        		"FG : "+String.valueOf(fgMem/1048576)+"MB",
        		"Items : "+String.valueOf(itemMem/1048576)+"MB",
        		"NPCs : "+String.valueOf(npcMem/1048576)+"MB",
        		"AAreas : "+String.valueOf(aareaMem/1048576)+"MB",
        		"Barriers : "+String.valueOf(barrierMem/1048576)+"MB",
        		"Atrezzos : "+String.valueOf(atrezzoMem/1048576)+"MB"};
        
        GUI.getInstance().setDebugMemoryAllocInfo(memInfo);
        
       
    	/////////////////////////////////////////////////////////
    }

    /**
     * Creates a new FunctionalScene only with a given background music.
     * 
     * @param backgroundMusicId
     *            Background music identifier
     */
    public FunctionalScene( long backgroundMusicId ) {

        // Load the background music (if it is not loaded)
        this.backgroundMusicId = backgroundMusicId;
        if( backgroundMusicId == -1 )
            playBackgroundMusic( );
    }

    /**
     * Update the resources and elements of the scene, depending on the state of
     * the flags.
     */
    public void updateScene( ) {

        // Update the resources and the player's resources
        updateResources( );
        player.updateResources( );

        // Pick the game data
        Chapter gameData = Game.getInstance( ).getCurrentChapterData( );

        // Check the item references of the scene
        for( ElementReference itemReference : scene.getItemReferences( ) ) {

            // For every item that should be there
            if( new FunctionalConditions( itemReference.getConditions( ) ).allConditionsOk( ) ) {
                boolean found = false;

                // If the functional item is present, update its resources
                for( FunctionalItem currentItem : items ) {
                    if( itemReference == currentItem.getReference( ) ) {
                        currentItem.updateResources( );
                        found = true;
                    }
                }

                // If it was not found, search for it and add it
                if( !found ) {
                    if( Game.getInstance( ).getItemSummary( ).isItemNormal( itemReference.getTargetId( ) ) ) {
                        for( Item currentItem : gameData.getItems( ) ) {
                            if( itemReference.getTargetId( ).equals( currentItem.getId( ) ) ) {
                                FunctionalItem fItem = new FunctionalItem( currentItem, itemReference );
                                items.add( fItem );
                            }
                        }
                    }
                }
            }
            else {
                FunctionalItem remove = null;
                for( FunctionalItem currentItem : items ) {
                    if( currentItem.getReference( ) == itemReference )
                        remove = currentItem;
                }
                if( remove != null )
                    items.remove( remove );
            }
        }

        // Check the character references of the scene
        for( ElementReference npcReference : scene.getCharacterReferences( ) ) {
            // For every item that should be there
            if( new FunctionalConditions( npcReference.getConditions( ) ).allConditionsOk( ) ) {
                boolean found = false;

                // If the functional character is present, update its resources
                for( FunctionalNPC currentNPC : npcs ) {
                    if( npcReference == currentNPC.getReference( ) ) {
                        currentNPC.updateResources( );
                        found = true;
                    }
                }

                // If it was not found, search for it and add it
                if( !found ) {
                    for( NPC currentNPC : gameData.getCharacters( ) ) {
                        if( npcReference.getTargetId( ).equals( currentNPC.getId( ) ) ) {
                            FunctionalNPC fNPC = new FunctionalNPC( currentNPC, npcReference );
                            npcs.add( fNPC );
                        }
                    }
                }
            }
            else {
                FunctionalNPC remove = null;
                for( FunctionalNPC currentNPC : npcs ) {
                    if( currentNPC.getReference( ) == npcReference )
                        remove = currentNPC;
                }
                if( remove != null )
                    npcs.remove( remove );
            }
        }

        // Check the active areas of the scene
        for( ActiveArea activeArea : scene.getActiveAreas( ) ) {

            // For every item that should be there
            if( new FunctionalConditions( activeArea.getConditions( ) ).allConditionsOk( ) ) {
                boolean found = false;

                // If the functional item is present, update its resources
                for( FunctionalActiveArea currentFunctionalActiveArea : areas ) {
                    if( activeArea.getId( ).equals( currentFunctionalActiveArea.getItem( ).getId( ) ) ) {
                        found = true;
                        break;
                    }
                }

                // If it was not found, search for it and add it
                if( !found ) {
                    areas.add( new FunctionalActiveArea( activeArea, activeArea.getInfluenceArea( ) ) );
                }
            }
        }

        // Check the barriers of the scene
        barriers.clear( );
        for( Barrier barrier : scene.getBarriers( ) ) {
            // For every barrier that should be there
            if( new FunctionalConditions( barrier.getConditions( ) ).allConditionsOk( ) ) {
                barriers.add( new FunctionalBarrier( barrier ) );
            }
        }

        // Check the atrezzo item references of the scene
        for( ElementReference atrezzoReference : scene.getAtrezzoReferences( ) ) {

            // For every atrezzo item that should be there
            if( new FunctionalConditions( atrezzoReference.getConditions( ) ).allConditionsOk( ) ) {
                boolean found = false;

                // If the functional atrezzo item is present, update its resources
                for( FunctionalAtrezzo currentAtrezzo : atrezzo ) {
                    if( atrezzoReference == currentAtrezzo.getReference( ) ) {
                        currentAtrezzo.updateResources( );
                        found = true;
                    }
                }

                // If it was not found, search for it and add it
                if( !found ) {
                    for( Atrezzo currentAtrezzo : gameData.getAtrezzo( ) ) {
                        if( atrezzoReference.getTargetId( ).equals( currentAtrezzo.getId( ) ) ) {
                            FunctionalAtrezzo fAtrezzo = new FunctionalAtrezzo( currentAtrezzo, atrezzoReference );
                            atrezzo.add( fAtrezzo );
                        }
                    }
                }
            }
            else {
                FunctionalAtrezzo remove = null;
                for( FunctionalAtrezzo currentAtrezzo : atrezzo ) {
                    if( currentAtrezzo.getReference( ) == atrezzoReference )
                        remove = currentAtrezzo;
                }
                if( remove != null )
                    atrezzo.remove( remove );
            }

        }

        // Create a list with the active areas to remove
        ArrayList<FunctionalActiveArea> activeAreasToRemove = new ArrayList<FunctionalActiveArea>( );
        for( FunctionalActiveArea currentActiveArea : areas ) {
            boolean keepActiveArea = false;

            // For every present item, check if it must be kept
            for( ActiveArea activeArea : scene.getActiveAreas( ) ) {
                if( activeArea.getId( ).equals( currentActiveArea.getItem( ).getId( ) ) && new FunctionalConditions( activeArea.getConditions( ) ).allConditionsOk( ) ) {
                    keepActiveArea = true;
                }
            }

            // If it must not be kept, add it to the remove list
            if( !keepActiveArea )
                activeAreasToRemove.add( currentActiveArea );
        }

        // Remove the elements
        for( FunctionalActiveArea areaToRemove : activeAreasToRemove )
            areas.remove( areaToRemove );

    }

    /**
     * Updates the resources of the scene (if the current resources and the new
     * one are different)
     */
    public void updateResources( ) {

        // Get the new resources
        Resources newResources = createResourcesBlock( );

        // If the resources have changed, load the new one
        if( resources != newResources ) {
            resources = newResources;
            showsOffsetArrows = false;

            background = null;
            if( resources.existAsset( Scene.RESOURCE_TYPE_BACKGROUND ) )
                background = MultimediaManager.getInstance( ).loadImage( resources.getAssetPath( Scene.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );

            if( Game.getInstance( ).isTransparent( ) && background != null && background.getWidth(  ) > GUI.WINDOW_WIDTH ) {
                showsOffsetArrows = true;
            }

            // If there was a foreground, delete it
            if( foreground != null )
                foreground.recycle();

            // Load the foreground image
            foreground = null;
            if( background != null && resources.existAsset( Scene.RESOURCE_TYPE_FOREGROUND ) ) {
                Bitmap bufferedBackground =  background;
                Bitmap foregroundHardMap =  MultimediaManager.getInstance( ).loadImage( resources.getAssetPath( Scene.RESOURCE_TYPE_FOREGROUND ), MultimediaManager.IMAGE_SCENE );
                Bitmap bufferedForeground = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( foregroundHardMap.getWidth(  ), foregroundHardMap.getHeight(  ), true );

                for( int i = 0; i < foregroundHardMap.getWidth(  ); i++ ) {
                    for( int j = 0; j < foregroundHardMap.getHeight(  ); j++ ) {
                        if( foregroundHardMap.getPixel(i, j) == 0xFFFFFFFF )
                            bufferedForeground.setPixel(i, j, 0x00000000);
                        else
                            bufferedForeground.setPixel(i, j, bufferedBackground.getPixel(i, j));
                    }
                }
                
                bufferedBackground =  null;
                foregroundHardMap = null;
                foreground = bufferedForeground;
                bufferedForeground = null;
            }

            playBackgroundMusic( );
        }
    }

    /**
     * Returns the contained scene
     * 
     * @return Contained scene
     */
    public Scene getScene( ) {

        return scene;
    }

    /**
     * Returns the npc with the given id
     * 
     * @param npcId
     *            the id of the npc
     * @return the npc with the given id
     */
    public FunctionalNPC getNPC( String npcId ) {

        FunctionalNPC npc = null;

        if( npcId != null ) {
            for( FunctionalNPC currentNPC : npcs )
                if( currentNPC.getElement( ).getId( ).equals( npcId ) )
                    npc = currentNPC;
        }

        return npc;
    }

    /**
     * Returns the list of npcs in this scene
     * 
     * @return the list of npcs in this scene
     */
    public ArrayList<FunctionalNPC> getNPCs( ) {

        return npcs;
    }

    /**
     * Returns the list of items in this scene
     * 
     * @return the list of items in this scene
     */
    public ArrayList<FunctionalItem> getItems( ) {

        return items;
    }

    /**
     * Returns the list of items in this scene
     * 
     * @return the list of items in this scene
     */
    public ArrayList<FunctionalActiveArea> getActiveAreas( ) {

        return areas;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.functionaldata.Renderable#update(long)
     */
    public void update( long elapsedTime ) {

        playBackgroundMusic( );

        // Update the items
        for( FunctionalItem item : items )
            item.update( elapsedTime );

        // Update the active areas
        for( FunctionalActiveArea activeArea : areas )
            activeArea.update( elapsedTime );

        // Update the characters
        for( FunctionalNPC npc : npcs )
            npc.update( elapsedTime );

        // Update the player
        player.update( elapsedTime );
        
        // Update the offset
        updateOffset( );

    }

    /**
     * Returns the offset of the scroll.
     * 
     * @return Offset of the scroll
     */
    public int getOffsetX( ) {

        return offsetX;
    }

    /**
     * Updates the offset value of the screen.
     * 
     * @return True if the offset has changed, false otherwise
     */
    private boolean updateOffset( ) {

        // TODO Francis: Comentar
        if( Game.getInstance( ).isTransparent( ) )
            return false;
        boolean updated = false;

        // Scroll
        int iw = background.getWidth(  );
        if( player.getX( ) - offsetX > ( GUI.WINDOW_WIDTH - MAX_OFFSET_X ) ) {
            updated = true;
            offsetX += player.getX( ) - offsetX - ( GUI.WINDOW_WIDTH - MAX_OFFSET_X );
            if( offsetX + GUI.WINDOW_WIDTH > iw )
                offsetX = iw - GUI.WINDOW_WIDTH;
        }

        else if( player.getX( ) - offsetX < MAX_OFFSET_X ) {
            updated = true;
            offsetX -= MAX_OFFSET_X - player.getX( ) + offsetX;
            if( offsetX < 0 )
                offsetX = 0;
        }

        return updated;
    }

    public void updateOffset( boolean right ) {

        int iw = background.getWidth(  );
        if( right ) {
            offsetX += 10;
            if( offsetX + GUI.WINDOW_WIDTH > iw )
                offsetX = iw - GUI.WINDOW_WIDTH;
        }
        else {
            offsetX -= 10;
            if( offsetX < 0 )
                offsetX = 0;
        }
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    public void draw( ) {

        GUI.getInstance( ).addBackgroundToDraw( background, offsetX );

        for( FunctionalItem item : items )
        		item.draw( );

        for( FunctionalNPC npc : npcs )
        		npc.draw( );

        for( FunctionalAtrezzo at : atrezzo )
        		at.draw( );

        player.draw( );

        if( foreground != null )
            GUI.getInstance( ).addForegroundToDraw( foreground, offsetX );

        GUI.getInstance( ).setShowsOffestArrows( showsOffsetArrows, moveOffsetRight, moveOffsetLeft );
    }

    /**
     * Returns the element in the given position. If there is no element, null
     * is returned
     * 
     * @param x
     *            the horizontal position
     * @param y
     *            the vertical position
     * @return the element in the given position
     */
    public FunctionalElement getElementInside( int x, int y, FunctionalElement exclude ) {
    
    	FunctionalElement element = null;
        if( isInsideOffsetArrow( x, y ) )
            return null;

           
           List<FunctionalElement> er = GUI.getInstance( ).getElementsToInteract( ); 
           int i=er.size( )-1; 
           while( i>=0 && element == null ) {
            FunctionalElement currentElement = er.get( i );
            i--;
            if( currentElement != exclude && currentElement.isPointInside( x + Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), y ) )
                element = currentElement;
        }

        Iterator<FunctionalActiveArea> ita = areas.iterator( );
        while( ita.hasNext( ) && element == null ) {
            FunctionalActiveArea currentActiveArea = ita.next( );
            if( currentActiveArea != exclude && currentActiveArea.isPointInside( x + Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), y ) )
                element = currentActiveArea;
        }
        
        return element;
    }

    public boolean isInsideOffsetArrow( int x, int y ) {

        moveOffsetRight = false;
        moveOffsetLeft = false;

        if( showsOffsetArrows ) {
            int ypos = (GUI.WINDOW_HEIGHT / 2);
            if( y >= ypos - GUI.OFFSET_ARROW_AREA_RADIUS && y <= ypos + GUI.OFFSET_ARROW_AREA_RADIUS ) {
                int max_x = (int) Math.ceil( Math.sqrt( GUI.OFFSET_ARROW_AREA_RADIUS * GUI.OFFSET_ARROW_AREA_RADIUS - Math.pow( y - ypos, 2 ) ) );
                if( x <= max_x )
                    moveOffsetLeft = true;
                if( x >= GUI.WINDOW_WIDTH - max_x )
                    moveOffsetRight = true;
            } 
        }

        return moveOffsetLeft || moveOffsetRight;
    }

    public FunctionalTrajectory getTrajectory( ) {

        return trajectory;
    }

    /**
     * Returns the exit in the given position. If there is no exit, null is
     * returned.
     * 
     * @param x
     *            the horizontal position
     * @param y
     *            the vertical position
     * @return the exit in the given position
     */
    public Exit getExitInside( int x, int y ) {

        if( this.isInsideOffsetArrow( x, y ) )
            return null;

        for( Exit exit : scene.getExits( ) ) {
            if( exit.isPointInside( x + offsetX, y ) && ( new FunctionalConditions( exit.getConditions( ) ).allConditionsOk( ) || exit.isHasNotEffects( ) ) )
                return exit;
        }
        return null;
    }

    /**
	 * Notify that the user has unpressed on the scene
	 * 
	 * @param x
	 *            the horizontal position of the click
	 * @param y
	 *            the vertical position of the click
	 * @param actionSelected
	 *            the current action selected (use, give, grab, look, ...)
	 */
	public void unpressed(int x, int y) {

        FunctionalElement element = getElementInside( x + offsetX, y , null);
        if( Game.getInstance( ).getActionManager( ).getActionSelected( ) == ActionManager.ACTION_GOTO || element == null ) {
            int destX = x + offsetX;
            int destY = y;
            FunctionalGoTo functionalGoTo = new FunctionalGoTo( null, destX, destY );
            int finalX = functionalGoTo.getPosX( );
            int finalY = functionalGoTo.getPosY( );
            Exit exit = getExitInside( finalX - offsetX, finalY );
            player.cancelActions( );
            if( exit != null) {
                if( !player.isTransparent( ) && this.getTrajectory( ).hasTrajectory( ) ) {
                    functionalGoTo = new FunctionalGoTo( null, destX, destY, Game.getInstance( ).getFunctionalPlayer( ), new FunctionalExitArea( exit, exit.getInfluenceArea( ) ) );
                    if( functionalGoTo.canGetTo( ) ) {
                        player.addAction( new FunctionalExit( exit ) );
                        player.addAction( functionalGoTo );
                    }
                }
                else {
                    if( !player.isTransparent( ) ) {
                        player.addAction( new FunctionalExit( exit ) );
                        player.addAction( functionalGoTo );
                    }
                    else if( player.isTransparent( ) ) {
                        player.addAction( new FunctionalExit( exit ) );
                    }
                }
            }
            Game.getInstance( ).getActionManager( ).setActionSelected( ActionManager.ACTION_GOTO );
        }
        else { //if (element != null){
            Game.getInstance( ).getFunctionalPlayer( ).performActionInElement( element );
        }
	}

	public void tap(int x, int y) {

        if( isInsideOffsetArrow( x, y ) ) {
            System.out.println( "Is inside offset arrow" );
            if( moveOffsetRight )
                updateOffset( true );
            if( moveOffsetLeft )
                updateOffset( false );
        }

        FunctionalElement element = getElementInside( x + offsetX, y , null);
        if( Game.getInstance( ).getActionManager( ).getActionSelected( ) == ActionManager.ACTION_GOTO || element == null ) {
            int destX = x + offsetX;
            int destY = y;
            FunctionalGoTo functionalGoTo = new FunctionalGoTo( null, destX, destY );
            player.cancelActions( );
            if(!player.isTransparent( )) 
            	player.addAction(functionalGoTo);
        }   
        else if (element != null) {
			Game.getInstance().getFunctionalPlayer().performActionInElement(element);
		}
        Game.getInstance( ).getActionManager( ).setActionSelected( ActionManager.ACTION_GOTO );
        
	}

    public int[] checkPlayerAgainstBarriers( int destX, int destY ) {

        int[] finalPos = new int[ 2 ];
        finalPos[0] = destX;
        finalPos[1] = destY;
        for( FunctionalBarrier barrier : barriers ) {
            int[] newDest = barrier.checkPlayerAgainstBarrier( player, finalPos[0], finalPos[1] );
            finalPos[0] = newDest[0];
            finalPos[1] = newDest[1];
        }
        return finalPos;
    }

    /**
     * Returns the identifier of the backgrounds music.
     * 
     * @return Identifier number of the background music
     */
    public long getBackgroundMusicId( ) {

        return backgroundMusicId;
    }

    /**
     * Stops the background music of the scene
     */
    public void stopBackgroundMusic( ) {

    	MultimediaManager.getInstance( ).stopPlaying( backgroundMusicId );
    	
    }

    /**
     * Load and play the background music if is not active. If its the first
     * time it loads, it obtains the ID of the background music to be able to
     * identify itself from other sounds.
     */
    public void playBackgroundMusic( ) {

        if( Game.getInstance( ).getOptions( ).isMusicActive( ) ) {
            if( backgroundMusicId != -1 ) {
                if( !MultimediaManager.getInstance( ).isPlaying( backgroundMusicId ) ) {
                    backgroundMusicId = MultimediaManager.getInstance( ).loadMusic( resources.getAssetPath( Scene.RESOURCE_TYPE_MUSIC ), true );
                    MultimediaManager.getInstance( ).startPlaying( backgroundMusicId );
                }
            }
            else {
                if( resources.existAsset( Scene.RESOURCE_TYPE_MUSIC ) ) {
                    backgroundMusicId = MultimediaManager.getInstance( ).loadMusic( resources.getAssetPath( Scene.RESOURCE_TYPE_MUSIC ), true );
                    MultimediaManager.getInstance( ).startPlaying( backgroundMusicId );
                }
                else {
                    MultimediaManager.getInstance( ).stopPlayingMusic( );
                }
            }
        }
    }

    /**
     * Creates the current resource block to be used
     */
    public Resources createResourcesBlock( ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < scene.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( scene.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = scene.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if( newResources == null ) {
            newResources = new Resources( );
            newResources.addAsset( new Asset( Scene.RESOURCE_TYPE_BACKGROUND, ResourceHandler.DEFAULT_BACKGROUND ) );
            newResources.addAsset( new Asset( Scene.RESOURCE_TYPE_FOREGROUND, ResourceHandler.DEFAULT_FOREGROUND ) );
            newResources.addAsset( new Asset( Scene.RESOURCE_TYPE_HARDMAP, ResourceHandler.DEFAULT_HARDMAP ) );
        }
        return newResources;
    }
    
    public void freeMemory() {
        this.resources = null;
        this.background = null;
        this.foreground = null;
        this.trajectory = null;
        this.areas = null;
        this.atrezzo = null;
        this.barriers = null;
        this.items = null;
        this.npcs = null;
    }
    
}
