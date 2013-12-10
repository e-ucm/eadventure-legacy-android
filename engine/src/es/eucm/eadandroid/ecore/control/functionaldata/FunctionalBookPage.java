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
import es.eucm.eadandroid.common.data.chapter.book.BookPage;
import es.eucm.eadandroid.multimedia.MultimediaManager;


public class FunctionalBookPage {

    private static final long serialVersionUID = 1L;

    private BookPage bookPage;

    private boolean isValid;

    private Bitmap background, currentArrowLeft, currentArrowRight;
    
    private Point previousPage, nextPage;

    private Bitmap image;
    
    private FunctionalStyledBook fBook;

   // private BookEditorPane editorPane;

    public FunctionalBookPage( Bitmap background ) {

        this.background = background;
    }

    public FunctionalBookPage( BookPage bookPage, FunctionalStyledBook fBook, Bitmap background, Point previousPage, Point nextPage,  boolean listenHyperLinks ) {

        
   //     editorPane = new BookEditorPane( bookPage );
        isValid = true;
        this.bookPage = bookPage;
        this.fBook = fBook;
        this.background = background;
        this.previousPage = previousPage;
        this.nextPage = nextPage;
        
        if( bookPage.getType( ) == BookPage.TYPE_IMAGE ) {
            image = MultimediaManager.getInstance( ).loadImage( bookPage.getUri( ), MultimediaManager.IMAGE_SCENE );
        }
        
    }


    public void paint( Canvas c ) {
    	
        if( image != null )
            c.drawBitmap(image, 0, 0,null);    
    }

    /**
     * @return the bookPage
     */
    public BookPage getBookPage( ) {

        return bookPage;
    }

    /**
     * @param bookPage
     *            the bookPage to set
     */
    public void setBookPage( BookPage bookPage ) {

        this.bookPage = bookPage;
    }

  

    /**
     * @param isValid
     *            the isValid to set
     */
    public void setValid( boolean isValid ) {

        this.isValid = isValid;
    }

	public boolean isValid() {
		// TODO Auto-generated method stub
		return isValid;
	}


       

      
    

   

}
