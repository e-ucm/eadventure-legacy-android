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

import java.util.List;

import es.eucm.eadandroid.common.data.HasId;
import es.eucm.eadandroid.common.data.chapter.conditions.*;

/**
 * Rule for the assesment engine
 */
public class AssessmentRule implements Cloneable, HasId {

    /**
     * Number of different importance values
     */
    public static final int N_IMPORTANCE_VALUES = 5;

    /**
     * Importance value for very low
     */
    public static final int IMPORTANCE_VERY_LOW = 0;

    /**
     * Importance value for low
     */
    public static final int IMPORTANCE_LOW = 1;

    /**
     * Importance value for normal
     */
    public static final int IMPORTANCE_NORMAL = 2;

    /**
     * Importance value for high
     */
    public static final int IMPORTANCE_HIGH = 3;

    /**
     * Importance value for very high
     */
    public static final int IMPORTANCE_VERY_HIGH = 4;

    /**
     * String values for the different importance values
     */
    public static final String IMPORTANCE_VALUES[] = { "verylow", "low", "normal", "high", "veryhigh" };

    /**
     * Id of the rule
     */
    protected String id;

    /**
     * Importance of the rule, stored as an integer
     */
    protected int importance;

    /**
     * Concept of the rule
     */
    protected String concept;

    /**
     * Conditions for the rule to trigger
     */
    protected Conditions conditions;

    /**
     * The effect of the rule
     */
    protected AssessmentEffect effect;
    
    /**
     * If it is active, the assessment rule can be executed more than 1 times
     */
    protected boolean repeatRule;

    /**
     * Default constructor
     * 
     * @param id
     *            Id of the rule
     * @param importance
     *            Importance of the rule
     */
    public AssessmentRule( String id, int importance, boolean repeatRule ) {

        this.id = id;
        this.importance = importance;
        concept = null;
        conditions = new Conditions( );
        effect = new AssessmentEffect( );
        this.repeatRule = repeatRule;
    }

    /**
     * Sets the concept of the rule
     * 
     * @param concept
     *            Concept of the rule
     */
    public void setConcept( String concept ) {

        this.concept = concept;
    }

    /**
     * Sets the conditions of the rule
     * 
     * @param conditions
     *            Conditions of the rule
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    /**
     * Sets the text of the rule
     * 
     * @param text
     *            Text of the rule
     */
    public void setText( String text ) {

        effect.setText( text );
    }

    /**
     * Adds a new assessment property
     * 
     * @param property
     *            Assessment property to be added
     */
    public void addProperty( AssessmentProperty property ) {

        effect.getAssessmentProperties( ).add( property );
    }

    /**
     * Return the rule's id
     * 
     * @return Id of the rule
     */
    public String getId( ) {

        return id;
    }

    /**
     * Return the rule's importance
     * 
     * @return Importance of the rule
     */
    public Integer getImportance( ) {

        return importance;
    }

    /**
     * Returns the rule's concept
     * 
     * @return Concept of the rule
     */
    public String getConcept( ) {

        return concept;
    }

    /**
     * Returns the rule's text (if present)
     * 
     * @return Text of the rule if present, null otherwise
     */
    public String getText( ) {

        return effect.getText( );
    }

    public List<AssessmentProperty> getAssessmentProperties( ) {

        return effect.getAssessmentProperties( );
    }

    /**
     * @param importance
     *            the importance to set
     */
    public void setImportance( Integer importance ) {

        this.importance = importance;
    }

    /**
     * @return the conditions
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    public void setId( String assRuleId ) {

        this.id = assRuleId;
    }
    
    /**
     * 
     * @return if the rule can be executed one or more times
     */
    public Boolean isRepeatRule( ) {
    
        return repeatRule;
    }

    
    public void setRepeatRule( Boolean repeatRule ) {
    
        this.repeatRule = repeatRule;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AssessmentRule ar = (AssessmentRule) super.clone( );
        ar.concept = ( concept != null ? new String( concept ) : null );
        if( conditions != null )
            ar.conditions = (Conditions) conditions.clone( );
        if( effect != null )
            ar.effect = (AssessmentEffect) effect.clone( );
        ar.id = ( id != null ? new String( id ) : null );
        ar.importance = importance;
        ar.repeatRule = repeatRule;
        return ar;
    }
}
