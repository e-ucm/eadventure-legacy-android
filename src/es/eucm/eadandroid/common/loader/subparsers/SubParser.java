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
package es.eucm.eadandroid.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadandroid.common.data.chapter.Chapter;

/**
 * Abstract class for subparsing elements of the script
 */
public abstract class SubParser {

    /* Attributes */

    /**
     * String to store the current string in the XML file.
     */
    protected StringBuffer currentString;

    /**
     * Chapter in which the data will be stored.
     */
    protected Chapter chapter;

    /* Methods */

    /**
     * Constructor.
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public SubParser( Chapter chapter ) {

        this.chapter = chapter;
        currentString = new StringBuffer( );
    }

    /**
     * Receive notification of the start of an element.
     * 
     * @param namespaceURI
     *            The Namespace URI, or the empty string if the element has no
     *            Namespace URI or if Namespace processing is not being
     *            performed
     * @param sName
     *            The local name (without prefix), or the empty string if
     *            Namespace processing is not being performed
     * @param sName
     *            The qualified name (with prefix), or the empty string if
     *            qualified names are not available
     * @param attrs
     *            The attributes attached to the element. If there are no
     *            attributes, it shall be an empty Attributes object
     */
    public abstract void startElement( String namespaceURI, String sName, String qName, Attributes attrs );

    /**
     * Receive notification of the end of an element.
     * 
     * @param namespaceURI
     *            The Namespace URI, or the empty string if the element has no
     *            Namespace URI or if Namespace processing is not being
     *            performed
     * @param sName
     *            The local name (without prefix), or the empty string if
     *            Namespace processing is not being performed
     * @param sName
     *            The qualified name (with prefix), or the empty string if
     *            qualified names are not available
     */
    public abstract void endElement( String namespaceURI, String sName, String qName );

    /**
     * Receive notification of character data inside an element.
     * 
     * @param buf
     *            The characters
     * @param offset
     *            The start position in the character array
     * @param len
     *            The number of characters to use from the character array
     */
    public void characters( char[] buf, int offset, int len ) {

        // Append the new characters
        currentString.append( new String( buf, offset, len ) );
    }
}
