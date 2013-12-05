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
 * Data representation of a random effect. According to a probability defined by
 * user, the system chooses between two effects which is to be launched So the
 * behaviour is: PROBABILITY times POSITIVE EFFECT is triggered 100-PROBABILITY
 * times NEGATIVE EFFECT is triggered
 * 
 * @author Javier
 */
public class RandomEffect extends AbstractEffect {

    /**
     * Effect to be triggered PROBABILITY% of the times
     */
    private AbstractEffect positiveEffect;

    /**
     * Effect to be triggered 100-PROBABILITY% of the times
     */
    private AbstractEffect negativeEffect;

    /**
     * Probability in range 0%-100%
     */
    private int probability;

    /**
     * Constructor
     * 
     * @param probability
     */
    public RandomEffect( int probability ) {

        super( );
        this.probability = probability;
    }

    /**
     * Default constructor. Sets probability to 50%
     */
    public RandomEffect( ) {

        this( 50 );
    }

    @Override
    public int getType( ) {

        return Effect.RANDOM_EFFECT;
    }

    /**
     * @param positiveEffect
     *            the positiveEffect to set
     */
    public void setPositiveEffect( AbstractEffect positiveEffect ) {

        this.positiveEffect = positiveEffect;
    }

    /**
     * @param negativeEffect
     *            the negativeEffect to set
     */
    public void setNegativeEffect( AbstractEffect negativeEffect ) {

        this.negativeEffect = negativeEffect;
    }

    /**
     * @return the probability
     */
    public int getProbability( ) {

        return probability;
    }

    /**
     * @param probability
     *            the probability to set
     */
    public void setProbability( int probability ) {

        this.probability = probability;
    }

    /**
     * @return the positiveEffect
     */
    public AbstractEffect getPositiveEffect( ) {

        return positiveEffect;
    }

    /**
     * @return the negativeEffect
     */
    public AbstractEffect getNegativeEffect( ) {

        return negativeEffect;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        RandomEffect re = (RandomEffect) super.clone( );
        re.negativeEffect = ( negativeEffect != null ? (AbstractEffect) negativeEffect.clone( ) : null );
        re.positiveEffect = ( positiveEffect != null ? (AbstractEffect) positiveEffect.clone( ) : null );
        re.probability = probability;
        return re;
    }
}
