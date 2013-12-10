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
package es.eucm.eadandroid.common.data.chapter.resources;

/**
 * Defines an asset of any type (background, slides, image, icon, [...], or the
 * animatinos of the characters.
 */
public class Asset implements Cloneable {

    /**
     * String with the type of the asset
     */
    private String type;

    /**
     * Path of the asset
     */
    private String path;

    /**
     * Creates a new asset
     * 
     * @param type
     *            the type of the asset
     * @param path
     *            the path of the asset
     */
    public Asset( String type, String path ) {

        this.type = type;
        this.path = path;
    }

    /**
     * Returns the type of the asset.
     * 
     * @return the type of the asset
     */
    public String getType( ) {

        return type;
    }

    /**
     * Returns the path of the asset.
     * 
     * @return the path of the asset
     */
    public String getPath( ) {

        return path;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Asset a = (Asset) super.clone( );
        a.path = ( path != null ? new String( path ) : null );
        a.type = ( type != null ? new String( type ) : null );
        return a;
    }
}
