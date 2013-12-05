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

import android.graphics.Canvas;

import es.eucm.eadandroid.common.data.chapter.book.Book;
import es.eucm.eadandroid.common.data.chapter.book.BookPage;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;



public class FunctionalStyledBook extends FunctionalBook {

    /**
     * List of functional pages.
     */
    private ArrayList<FunctionalBookPage> functionalPages;

    public FunctionalStyledBook( Book b ) {
        super( b );
        //Create EditorPanes
        functionalPages = new ArrayList<FunctionalBookPage>( );

        //Get the background image
        Resources resources = null;
        for( int i = 0; i < book.getResources( ).size( ) && resources == null; i++ )
            if( new FunctionalConditions( book.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                resources = book.getResources( ).get( i );

        //Image background = MultimediaManager.getInstance( ).loadImageFromZip( resources.getAssetPath( Book.RESOURCE_TYPE_BACKGROUND ), MultimediaManager.IMAGE_SCENE );

        //ADD the functional pages: only those valid are used 
        for( BookPage pageURL : book.getPageURLs( ) ) {
            FunctionalBookPage newFPage = new FunctionalBookPage( pageURL, this, background, previousPage, nextPage, false );

            if( newFPage.isValid( ) ) {
                functionalPages.add( newFPage );
                //System.out.println( "[LOG] FunctionalStyledBook - Constructor - Page added" );
            }
            else {
                //System.out.println( "[LOG] FunctionalStyledBook - Constructor - Page NOT added" );
            }
        }

        this.numPages = functionalPages.size( );

        //If no valid pages were found a blank page is added
        if( numPages == 0 ) {
            functionalPages.add( new FunctionalBookPage( background ) );
        }
        this.currentPage = 0;

        //System.out.println( "[LOG] FunctionalStyledBook - Constructor - TOTAL PAGES: "+numPages );
        //Show the first page
       // GUI.getInstance( ).showComponent( functionalPages.get( currentPage ) );

    }

    @Override
    public boolean isInLastPage( ) {

        return currentPage == numPages - 1;
    }
    
    @Override
    public boolean isInFirstPage( ) {

        return currentPage == 0;
    }

    @Override
    public void nextPage( ) {

        if( currentPage < numPages - 1 ) {
            // We put the normal arrow image. If we don't put this method, when we return
            // to this page, the image for the arrow is still the over image
         //   functionalPages.get( currentPage ).setCurrentArrowRight( arrowRightNormal );
            currentPage++;
          //  GUI.getInstance( ).showComponent( functionalPages.get( currentPage ) );
          //  functionalPages.get( currentPage ).updateUI( );
            //System.out.println( "NEXT PAGE" );
        }
    }

    @Override
    public void previousPage( ) {

        if( currentPage > 0 ) {
            // We put the normal arrow image. If we don't put this method, when we return
            // to this page, the image for the arrow is still the over image
        //    functionalPages.get( currentPage ).setCurrentArrowLeft( arrowLeftNormal );
            currentPage--;
       //     GUI.getInstance( ).showComponent( functionalPages.get( currentPage ) );
         //   functionalPages.get( currentPage ).updateUI( );
            //System.out.println( "PREVIOUS PAGE" );
        }
    }
    
    
    @Override
	public void draw( Canvas c ) {
    	
    	//GUI.getInstance( ).addBackgroundToDraw( background, 0 );

    	
    	
    	c.drawBitmap(background, 0, 0, null);
    	
    	functionalPages.get(currentPage).paint(c);
    	
        //g.drawImage( background, 0, 0, background.getWidth( null ), background.getHeight( null ), null );

    }

}
    
   /* @Override
    public void mouseOverPreviousPage( boolean mouseOverPreviousPage ){
        if ( mouseOverPreviousPage ){
            functionalPages.get( currentPage ).setCurrentArrowLeft( arrowLeftOver );
        }
        else
            functionalPages.get( currentPage ).setCurrentArrowLeft( arrowLeftNormal );
    }
    
    @Override
    public void mouseOverNextPage( boolean mouseOverNextPage ){
        if ( mouseOverNextPage ){
            functionalPages.get( currentPage ).setCurrentArrowRight( arrowRightOver );
        }
        else
            functionalPages.get( currentPage ).setCurrentArrowRight( arrowRightNormal );
    }*/
    

