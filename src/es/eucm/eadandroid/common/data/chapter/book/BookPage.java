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
package es.eucm.eadandroid.common.data.chapter.book;

/**
 * Main class for the Page (HTML or RTF document) of a book
 * 
 * @author Javier Torrente
 * 
 */
public class BookPage implements Cloneable {

    /**
     * The page takes the document from a url, which can be on the internet
     */
    public static final int TYPE_URL = 0;

    /**
     * The page takes the document as a resource, from the zip file
     */
    public static final int TYPE_RESOURCE = 1;

    /**
     * The page takes the document as an image from the zip file
     */
    public static final int TYPE_IMAGE = 2;

    /**
     * The url/resource path
     */
    private String uri;

    /**
     * The type of the page: {@link #TYPE_URL} or {@link #TYPE_RESOURCE}
     */
    private int type;

    /**
     * Space to be left between the border of the background image and the book
     * page
     */
    private int margin;

    private int marginEnd;

    private int marginTop;

    private int marginBottom;

    /**
     * Determines whether the page must be in a scroll pane (Scroll bars shown)
     */
    private boolean scrollable;

    /**
     * @return the uri
     */
    public String getUri( ) {

        return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri( String uri ) {

        this.uri = uri;
    }

    /**
     * @return the type
     */
    public int getType( ) {

        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType( int type ) {

        this.type = type;
    }

    public BookPage( String uri, int type, int margin, boolean scrollable ) {

        this.uri = uri;
        this.type = type;
        this.margin = margin;
        this.scrollable = scrollable;
    }

    public BookPage( String uri, int type, int margin, int marginEnd, int marginTop, int marginBottom, boolean scrollable ) {

        this.uri = uri;
        this.type = type;
        this.margin = margin;
        this.marginEnd = marginEnd;
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
        this.scrollable = scrollable;
    }

    /**
     * @param uri
     * @param type
     */
    public BookPage( String uri, int type, int margin ) {

        this( uri, type, margin, false );
    }

    public BookPage( String uri, int type ) {

        this( uri, type, 0 );
    }

    /**
     * @return the margin
     */
    public int getMargin( ) {

        return margin;
    }

    /**
     * @param margin
     *            the margin to set
     */
    public void setMargin( int margin ) {

        this.margin = margin;
    }

    /**
     * @return the margin
     */
    public int getMarginStart( ) {

        return margin;
    }

    /**
     * @param margin
     *            the margin to set
     */
    public void setMarginStart( int margin ) {

        this.margin = margin;
    }

    /**
     * @return the marginEnd
     */
    public int getMarginEnd( ) {

        return marginEnd;
    }

    /**
     * @param marginEnd
     *            the marginEnd to set
     */
    public void setMarginEnd( int marginEnd ) {

        this.marginEnd = marginEnd;
    }

    /**
     * @return the marginTop
     */
    public int getMarginTop( ) {

        return marginTop;
    }

    /**
     * @param marginTop
     *            the marginTop to set
     */
    public void setMarginTop( int marginTop ) {

        this.marginTop = marginTop;
    }

    /**
     * @return the marginBottom
     */
    public int getMarginBottom( ) {

        return marginBottom;
    }

    /**
     * @param marginBottom
     *            the marginBottom to set
     */
    public void setMarginBottom( int marginBottom ) {

        this.marginBottom = marginBottom;
    }

    public boolean getScrollable( ) {

        return scrollable;
    }

    public void setScrollable( boolean scrollable ) {

        this.scrollable = scrollable;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        BookPage bp = (BookPage) super.clone( );
        bp.margin = margin;
        bp.marginBottom = marginBottom;
        bp.marginEnd = marginEnd;
        bp.marginTop = marginTop;
        bp.scrollable = scrollable;
        bp.type = type;
        bp.uri = ( uri != null ? new String( uri ) : null );
        return bp;
    }
}
