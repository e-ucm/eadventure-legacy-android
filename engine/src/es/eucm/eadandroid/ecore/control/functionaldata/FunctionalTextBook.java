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

import es.eucm.eadandroid.common.data.chapter.book.Book;
import es.eucm.eadandroid.common.data.chapter.book.BookParagraph;
import es.eucm.eadandroid.ecore.gui.GUI;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


public class FunctionalTextBook extends FunctionalBook {

    /**
     * X position of the first column of text
     */
    public static final int TEXT_X_1 = 110;

    /**
     * X position for the second column of text
     */
    public static final int TEXT_X_2 = 445;

    /**
     * Y position for both columns of text
     */
    public static final int TEXT_Y = 75;

    /**
     * Width of each column of text
     */
    public static final int TEXT_WIDTH = 250;

    /**
     * Width of each column of the bullet text
     */
    public static final int TEXT_WIDTH_BULLET = 225;

    /**
     * Height of each column of text
     */
    public static final int PAGE_TEXT_HEIGHT = 400;

    /**
     * Height of each line of text
     */
    public static final int LINE_HEIGHT = 25;

    /**
     * Height of each line of a title
     */
    public static final int TITLE_HEIGHT = 50;

    /**
     * Number of lines per column
     */
    public static final int TEXT_LINES = PAGE_TEXT_HEIGHT / LINE_HEIGHT;

    /**
     * Image of the book.
     */
    protected Bitmap imgBook;
    
    /**
     * Background image of the book
     */
    protected Bitmap background;

    /**
     * Total height of the book.
     */
    private int totalHeight;

    /**
     * List of functional paragraphs.
     */
    private ArrayList<FunctionalBookParagraph> functionalParagraphs;

    /**
     * Creates a new FunctionalBook
     * 
     * @param book
     *            the book's data
     */
    public FunctionalTextBook( Book b ) {
        super( b );

        // Create the list of paragraphs and add it
        functionalParagraphs = new ArrayList<FunctionalBookParagraph>( );
        for( BookParagraph paragraph : book.getParagraphs( ) ) {
            switch( paragraph.getType( ) ) {
                case BookParagraph.TITLE:
                    functionalParagraphs.add( new FunctionalBookTitle( paragraph ) );
                    break;
                case BookParagraph.BULLET:
                    functionalParagraphs.add( new FunctionalBookBullet( paragraph ) );
                    break;
                case BookParagraph.IMAGE:
                    functionalParagraphs.add( new FunctionalBookImage( paragraph ) );
                    break;
                case BookParagraph.TEXT:
                    functionalParagraphs.add( new FunctionalBookText( paragraph ) );
                    break;
            }
        }

        // Calculate the height of the book and the number of pages
        totalHeight = 0;
        currentPage = 0;
        for( FunctionalBookParagraph functionalParagraph : functionalParagraphs )
            totalHeight += functionalParagraph.getHeight( );
        numPages = (int) Math.ceil( (double) totalHeight / (double) PAGE_TEXT_HEIGHT );

        // Create the image of the book and extract the graphics
        int y = 0;
        imgBook = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( TEXT_WIDTH, totalHeight + PAGE_TEXT_HEIGHT, true);
        Canvas c = new Canvas(imgBook);

        // Paint each paragraph in the image
        for( FunctionalBookParagraph functionalParagraph : functionalParagraphs ) {

            // If the paragraph can't be splitted and doesn't fit in the current page, jump to the next one
            if( !functionalParagraph.canBeSplitted( ) && ( y % PAGE_TEXT_HEIGHT ) + functionalParagraph.getHeight( ) > PAGE_TEXT_HEIGHT )
                y += ( PAGE_TEXT_HEIGHT - ( y % PAGE_TEXT_HEIGHT ) );

            // Paint the entire paragraph
            functionalParagraph.draw( c, 0, y );
            y += functionalParagraph.getHeight( );
        }
    }

    /**
     * Returns whether the book is in its last page
     * 
     * @return true if the book is in its last page, false otherwise
     */
    @Override
    public boolean isInLastPage( ) {

        return ( currentPage == numPages - 1 || currentPage == numPages - 2 );
    }
    
    @Override
    public boolean isInFirstPage( ) {
        
        return ( currentPage == 0 || currentPage == 1 );
    }

    /**
     * Displays the next page
     */
    @Override
    public void nextPage( ) {

        // Jump two pages (each book has to columns)
        if( currentPage < numPages - 2 ) {
            currentPage += 2;
        }
    }

    /**
     * Displays the previous page
     */
    @Override
    public void previousPage( ) {

        // Jump two pages (each book has to columns)
        if( currentPage > 1 ) {
            currentPage -= 2;
        }
    }

    /**
     * Draws the text of the book
     * 
     * @param g
     *            the graphics where the book must be painted
     */
    @Override
	public void draw( Canvas c ) {
        
        super.draw( c );        

        Rect rD, rS;
        rD = new Rect(TEXT_X_1, TEXT_Y, TEXT_X_1 + TEXT_WIDTH, TEXT_Y + PAGE_TEXT_HEIGHT);
        rS = new Rect(0, currentPage * PAGE_TEXT_HEIGHT, TEXT_WIDTH, ( currentPage + 1 ) * PAGE_TEXT_HEIGHT);
        
        // Draw the first page
        c.drawBitmap( imgBook, rS, rD, null );

        // If there is second page, draw it
        if( currentPage < numPages - 1 ) {
        	
        	rD = new Rect(TEXT_X_2, TEXT_Y, TEXT_X_2 + TEXT_WIDTH, TEXT_Y + PAGE_TEXT_HEIGHT);
            rS = new Rect(0, ( currentPage + 1 ) * PAGE_TEXT_HEIGHT, TEXT_WIDTH, ( currentPage + 2 ) * PAGE_TEXT_HEIGHT);
            c.drawBitmap( imgBook, rS, rD, null );
        }
    }
}

