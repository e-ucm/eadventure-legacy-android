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
package es.eucm.eadandroid.common.data.chapter.effects;

/**
 * An effect that show text in an specific zone in scene
 */
public class ShowTextEffect extends AbstractEffect {

    /**
     * The text which will be showed
     */
    private String text;

    /**
     * The x position in scene
     */
    private int x;

    /**
     * The y position in scene
     */
    private int y;

    /**
     * The text front color in RGB format
     */
    private int rgbFrontColor;

    /**
     * The text border color in RGB fotrmat
     */
    private int rgbBorderColor;

    /**
     * Constructor
     * 
     * @param text
     * @param x
     * @param y
     * @param front
     * @param border
     */
    public ShowTextEffect( String text, int x, int y, int front, int border ) {

        super( );
        this.text = text;
        this.x = x;
        this.y = y;
        this.rgbFrontColor = front;
        this.rgbBorderColor = border;
    }

    /**
     * @return the text
     */
    public String getText( ) {

        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText( String text ) {

        this.text = text;
    }

    /**
     * @return the x
     */
    public int getX( ) {

        return x;
    }

    /**
     * @return the y
     */
    public int getY( ) {

        return y;
    }

    /**
     * Sets the new text position
     * 
     * @param x
     *            New text position X coordinate
     * @param y
     *            New text position Y coordinate
     */
    public void setTextPosition( int x, int y ) {

        this.x = x;
        this.y = y;
    }

    /**
     * Return the effect type
     */
    @Override
    public int getType( ) {

        return SHOW_TEXT;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        ShowTextEffect ste = (ShowTextEffect) super.clone( );
        ste.text = ( text != null ? new String( text ) : null );
        ste.x = x;
        ste.y = y;
        ste.rgbBorderColor = rgbBorderColor;
        ste.rgbFrontColor = rgbFrontColor;
        return ste;
    }

    /**
     * @return the rgbFrontColor
     */
    public int getRgbFrontColor( ) {

        return rgbFrontColor;
    }

    /**
     * @param rgbFrontColor
     *            the rgbFrontColor to set
     */
    public void setRgbFrontColor( int rgbFrontColor ) {

        this.rgbFrontColor = rgbFrontColor;
    }

    /**
     * @return the rgbBorderColor
     */
    public int getRgbBorderColor( ) {

        return rgbBorderColor;
    }

    /**
     * @param rgbBorderColor
     *            the rgbBorderColor to set
     */
    public void setRgbBorderColor( int rgbBorderColor ) {

        this.rgbBorderColor = rgbBorderColor;
    }
}
