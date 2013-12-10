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
import es.eucm.eadandroid.common.data.chapter.book.BookParagraph;
import es.eucm.eadandroid.multimedia.MultimediaManager;


/**
 * This is a image that can be put in a book scene
 */
public class FunctionalBookImage extends FunctionalBookParagraph {

    /**
     * The image book
     */
    private BookParagraph bookImage;

    /**
     * The image of the image book
     */
    private Bitmap image;

    /**
     * Creates a new FunctionalBookImage
     * 
     * @param image
     *            the image to be rendered
     */
    public FunctionalBookImage( BookParagraph image ) {

        //set the book image
        this.bookImage = image;
        //and loads the image 
        //OLD(FROM ZIP???)
        this.image = MultimediaManager.getInstance( ).loadImage( bookImage.getContent( ), MultimediaManager.IMAGE_SCENE );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#canBeSplitted()
     */
    @Override
    public boolean canBeSplitted( ) {

        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#draw(java.awt.Graphics2D, int, int)
     */
    @Override
    public void draw( Canvas c, int x, int y ) {

        //This book only draw a image
        c.drawBitmap( image, x, y + 5, null );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.FunctionalBookParagraph#getHeight()
     */
    @Override
    public int getHeight( ) {

        //The height of the book is the height of the image
        return (int) Math.ceil( ( image.getHeight() + 5 ) / (double) FunctionalTextBook.LINE_HEIGHT ) * FunctionalTextBook.LINE_HEIGHT;
    }

}

