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
package es.eucm.eadandroid.common.data.assessment;

import java.util.ArrayList;
import java.util.List;

public class AssessmentEffect implements Cloneable {

    /**
     * Text of the effect of the rule, if present (null if not)
     */
    protected String text;

    /**
     * List of properties to be set
     */
    protected List<AssessmentProperty> properties;

    public AssessmentEffect( ) {

        text = null;
        properties = new ArrayList<AssessmentProperty>( );
    }

    /**
     * Sets the text of the rule
     * 
     * @param text
     *            Text of the rule
     */
    public void setText( String text ) {

        this.text = text;
    }

    /**
     * Adds a new assessment property
     * 
     * @param property
     *            Assessment property to be added
     */
    public void addProperty( AssessmentProperty property ) {

        properties.add( property );
    }

    /**
     * Returns the rule's text (if present)
     * 
     * @return Text of the rule if present, null otherwise
     */
    public String getText( ) {

        return text;
    }

    public List<AssessmentProperty> getAssessmentProperties( ) {

        return properties;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AssessmentEffect ae = (AssessmentEffect) super.clone( );
        if( properties != null ) {
            ae.properties = new ArrayList<AssessmentProperty>( );
            for( AssessmentProperty ap : properties )
                ae.properties.add( (AssessmentProperty) ap.clone( ) );
        }
        ae.text = ( text != null ? new String( text ) : null );
        return ae;

    }

}
