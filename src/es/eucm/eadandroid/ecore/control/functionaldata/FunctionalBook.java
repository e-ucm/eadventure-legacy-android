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



import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import es.eucm.eadandroid.common.data.chapter.book.Book;
import es.eucm.eadandroid.common.data.chapter.resources.Asset;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * This class manages the eGame "bookscenes".
 */

public abstract class FunctionalBook {

    /**
     * Position of the upper left corner of the next page button
     */
    protected Point nextPage;

    /**
     * Position of the upper left corner of the previous page button
     */
    protected Point previousPage;

    /**
     * Book with the information
     */
    protected Book book;

    /**
     * Current page.
     */
    protected int currentPage;

    /**
     * Image for background
     */
    protected Bitmap background;

    /**
     * Number of pages.
     */
    protected int numPages;

   

    protected FunctionalBook( Book b ) {

        this.book = b;
        // Create necessaries resources to display the book
        Resources r = createResourcesBlock( book );
        // Load images and positions
        loadImages( r );

        // Load arrows position
        this.previousPage = book.getPreviousPagePoint( );
        this.nextPage = book.getNextPagePoint( );

    }

   

    /**
     * Returns the book's data (text and images)
     * 
     * @return the book's data
     */
    public Book getBook( ) {

        return book;
    }

    /**
     * Returns whether the book is in its last page
     * 
     * @return true if the book is in its last page, false otherwise
     */
    public abstract boolean isInLastPage( );

    /**
     * Returns whether the book is in its first page
     * 
     * @return true if the book is in its first page, false otherwise
     */
    public abstract boolean isInFirstPage( );

    /**
     * Changes the current page to the next one
     */
    public abstract void nextPage( );

    /**
     * Changes the current page to the previous one
     */ 
    public abstract void previousPage( );

    /**
     * Load the necessaries images for displaying the book. This method is
     * pretty much the same as "loadImages" from BookPagePreviewPanel.
     */
    protected void loadImages( Resources r ) {

        background = MultimediaManager.getInstance( ).loadImage( r.getAssetPath( Book.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );
    }


    /**
     * Creates the current resource block to be used
     */
    protected Resources createResourcesBlock( Book b ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < b.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( b.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = b.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if( newResources == null ) {
            newResources = new Resources( );
            newResources.addAsset( new Asset( Book.RESOURCE_TYPE_BACKGROUND, ResourceHandler.DEFAULT_BACKGROUND ) );
        }
        return newResources;
    }

    public void draw( Canvas c ) {
    	
    	c.drawBitmap(background, 0, 0, null);        

    }
    
    public void clearBookBitmap() {
    	
    	this.background.recycle();
    }

}
