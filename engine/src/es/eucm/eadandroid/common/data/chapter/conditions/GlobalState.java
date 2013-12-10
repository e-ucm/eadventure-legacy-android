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
package es.eucm.eadandroid.common.data.chapter.conditions;

import es.eucm.eadandroid.common.data.Documented;
import es.eucm.eadandroid.common.data.HasId;

/**
 * Group of conditions named with an Id, so it can be refered to in diverse
 * points of the chapter
 * 
 * @author Javier
 * 
 */
public class GlobalState extends Conditions implements Documented, HasId {

    /**
     * Id of the Conditions group
     */
    private String id;

    /**
     * Documentation (not used in game engine)
     */
    private String documentation;

    /**
     * Constructor
     */
    public GlobalState( String id ) {

        super( );
        this.id = id;
        this.documentation = new String( );
    }

    /**
     * @return the id
     */
    public String getId( ) {

        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId( String id ) {

        this.id = id;
    }

    /**
     * @return the documentation
     */
    public String getDocumentation( ) {

        return documentation;
    }

    /**
     * @param documentation
     *            the documentation to set
     */
    public void setDocumentation( String documentation ) {

        this.documentation = documentation;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        GlobalState gs = (GlobalState) super.clone( );
        gs.documentation = ( documentation != null ? new String( documentation ) : null );
        gs.id = ( id != null ? new String( id ) : null );
        return gs;
    }
}
