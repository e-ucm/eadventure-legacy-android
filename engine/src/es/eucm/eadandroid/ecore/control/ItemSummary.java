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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadandroid.common.data.chapter.elements.Item;

/**
 * Summary of the items in the adventure
 */
public class ItemSummary implements Serializable {

    /**
     * Required by Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * List of normal items
     */
    private ArrayList<String> normalItems;

    /**
     * List of grabbed items
     */
    private ArrayList<String> grabbedItems;

    /**
     * List of consumed items
     */
    private ArrayList<String> consumedItems;

    /**
     * Default constructor
     * 
     * @param items
     *            List of items, which will be stored as normal items
     */
    public ItemSummary( List<Item> items ) {

        normalItems = new ArrayList<String>( );
        grabbedItems = new ArrayList<String>( );
        consumedItems = new ArrayList<String>( );

        for( Item item : items ) {
            normalItems.add( item.getId( ) );
        }
    }

    /**
     * Returns the list of normal items
     * 
     * @return List of normal items
     */
    public ArrayList<String> getNormalItems( ) {

        return normalItems;
    }

    /**
     * Returns the list of grabbed items
     * 
     * @return List of grabbed items
     */
    public ArrayList<String> getGrabbedItems( ) {

        return grabbedItems;
    }

    /**
     * Returns the list of consumed items
     * 
     * @return List of consumed items
     */
    public ArrayList<String> getConsumedItems( ) {

        return consumedItems;
    }

    /**
     * Returns if an item is normal
     * 
     * @param itemId
     *            Id of the item
     * @return True if the item is normal, false otherwise
     */
    public boolean isItemNormal( String itemId ) {

        return normalItems.contains( itemId );
    }

    /**
     * Returns if an item has been grabbed
     * 
     * @param itemId
     *            Id of the item
     * @return True if the item has been grabbed, false otherwise
     */
    public boolean isItemGrabbed( String itemId ) {

        return grabbedItems.contains( itemId );
    }

    /**
     * Returns if an item has been consumed
     * 
     * @param itemId
     *            Id of the item
     * @return True if the item has been consumed, false otherwise
     */
    public boolean isItemConsumed( String itemId ) {

        return consumedItems.contains( itemId );
    }

    /**
     * Mark an item as grabbed, that item must be marked as normal
     * 
     * @param itemId
     *            Id of the item
     */
    public void grabItem( String itemId ) {

        if( normalItems.contains( itemId ) ) {
            normalItems.remove( itemId );
            grabbedItems.add( itemId );
        }
    }

    /**
     * Mark an item as grabbed, that item must be marked as normal
     * 
     * @param itemId
     *            Id of the item
     */
    public void regenerateItem( String itemId ) {

        if( consumedItems.contains( itemId ) ) {
            consumedItems.remove( itemId );
            normalItems.add( itemId );
        }
    }

    /**
     * Mark an item as consumed, that item must be marked as grabbed
     * 
     * @param itemId
     *            Id of the item
     */
    public void consumeItem( String itemId ) {

        if( grabbedItems.contains( itemId ) ) {
            grabbedItems.remove( itemId );
            consumedItems.add( itemId );
        }
    }
}
