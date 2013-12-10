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

import java.util.HashMap;

import es.eucm.eadandroid.common.data.Named;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;

/**
 * The list of resources of an element in the game under certain conditions.
 */
public class Resources implements Cloneable, Named {

    /**
     * Conditions of the resource
     */
    private Conditions conditions;

    /**
     * List of single assets stored in the resources structure
     */
    private HashMap<String, String> assets;

    private String name;
    
    /**
     * Creates a new Resources.
     */
    public Resources( ) {
        assets = new HashMap<String, String>( );
        conditions = new Conditions( );
        name = "No name";
    }

    /**
     * Inserts a new asset in the resources.
     * 
     * @param type
     *            Identifier of the asset
     * @param path
     *            Path of the asset
     */
    public boolean addAsset( String type, String path ) {
        boolean alreadyExists = existAsset( type ) && getAssetPath( type ).equals( path );
        // Remove the asset (if it was present), and insert the new one
        deleteAsset( type );
        assets.put( type, path );
        return !alreadyExists;
    }

    /**
     * Adds an asset to the resources.
     * 
     * @param asset
     *            the asset to be added
     */
    public boolean addAsset( Asset asset ) {
        return addAsset( asset.getType( ), asset.getPath( ) );
    }

    /**
     * Deletes the given asset from the resources block.
     * 
     * @param type
     *            Identifier of the asset
     */
    public void deleteAsset( String type ) {
        assets.remove( type );
    }

    /**
     * Returns the number of assets contained.
     * 
     * @return Number of assets
     */
    public int getAssetCount( ) {

        return assets.size( );
    }

    /**
     * Returns an array with all the types of the resources.
     * 
     * @return Array with the resources types
     */
    public String[] getAssetTypes( ) {

        return assets.keySet( ).toArray( new String[] {} );
    }

    /**
     * Returns an array with all the values of the resources.
     * 
     * @return Array with the resources values
     */
    public String[] getAssetValues( ) {

        return assets.values( ).toArray( new String[] {} );
    }

    /**
     * Returns the path associated with the given type.
     * 
     * @param type
     *            Identifier of the asset
     * @return Path to the asset if present, null otherwise
     */
    public String getAssetPath( String type ) {

        return assets.get( type );
    }

    /**
     * Returns the conditions to the resources.
     * 
     * @return the conditions to the resources
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    /**
     * Sets the conditions to the resources.
     * 
     * @param conditions
     *            the new conditions.
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    /**
     * Returns whether exists an asset of the given type.
     * 
     * @param type
     *            the type of the asset
     * @return whether exists an asset of the given type
     */
    public boolean existAsset( String type ) {

        boolean existAsset = false;

        existAsset = assets.containsKey( type ) && assets.get( type ) != null;

        return existAsset;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Resources r = (Resources) super.clone( );
        if( assets != null ) {
            r.assets = new HashMap<String, String>( );
            for( String s : assets.keySet( ) ) {
                String s2 = ( assets.get( s ) != null ? new String( assets.get( s ) ) : null );
                r.assets.put( new String( s ), s2 );
            }
        }
        r.name = ( name != null ? new String(name) : null);
        r.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        return r;
    }

    /**
     * Deletes all the resources in the structure. Needed for undo tool
     * 
     * @return
     */
    public void clearAssets( ) {

        assets.clear( );
    }

}
