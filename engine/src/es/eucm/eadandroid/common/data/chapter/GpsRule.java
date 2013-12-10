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
package es.eucm.eadandroid.common.data.chapter;

import android.graphics.Color;
import es.eucm.eadandroid.common.data.Documented;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;




public class GpsRule implements Cloneable, Documented {


	public static final double DEFAULT_LATITUDE = 0;
	public static final double DEFAULT_LONGITUD = 0;

 //   private long seconds;
    
    private double latitude;
    private double longitude;

    private Conditions initCond;

    private Conditions endCond;

    private Effects effect;



    private String documentation;

    private int Radio;

    /**
     * to denotate which gps we are getting;
     */
    private String sceneName;

    public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String id) {
		sceneName = id;
	}

	public int getRadio() {
		return Radio;
	}

	public void setRadio(int radio) {
		Radio = radio;
	}

	private int fontColor = Color.BLACK;

    private int borderColor = Color.WHITE;

    public GpsRule( double latitud,double longitud, Conditions init, Conditions end, Effects effect ) {

        this.latitude = latitud;
        this.longitude = longitud;
        this.initCond = init;
        this.endCond = end;
        this.effect = effect;

        Radio=50;
       
        
        
        
        
    }

    public GpsRule( double latitud, double longitud ) {

        this( latitud,longitud, new Conditions( ), new Conditions( ), new Effects( ) );
    }

    public GpsRule( ) {

        this(0,0);
    }




    public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
     * @return the initCond
     */
    public Conditions getInitCond( ) {

        return initCond;
    }
    

    

    /**
     * @param initCond
     *            the initCond to set
     */
    public void setInitCond( Conditions initCond ) {

        this.initCond = initCond;
    }

    /**
     * @return the endCond
     */
    public Conditions getEndCond( ) {

        return endCond;
    }

    /**
     * @param endCond
     *            the endCond to set
     */
    public void setEndCond( Conditions endCond ) {

        this.endCond = endCond;
    }

    /**
     * @return the effect
     */
    public Effects getEffects( ) {

        return effect;
    }

    /**
     * @param effect
     *            the effect to set
     */
    public void setEffects( Effects effect ) {

        this.effect = effect;
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

   
    /**
     * @return the fontColor
     */
    public int getFontColor( ) {

        return fontColor;
    }

    /**
     * @param fontColor
     *            the fontColor to set
     */
    public void setFontColor( int fontColor ) {

        this.fontColor = fontColor;
    }

    /**
     * @return the borderColor
     */
    public int getBorderColor( ) {

        return borderColor;
    }

    /**
     * @param borderColor
     *            the borderColor to set
     */
    public void setBorderColor( int borderColor ) {

        this.borderColor = borderColor;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

    	GpsRule t = (GpsRule) super.clone( );
        t.documentation = ( documentation != null ? new String( documentation ) : null );
        t.effect = ( effect != null ? (Effects) effect.clone( ) : null );
        t.endCond = ( endCond != null ? (Conditions) endCond.clone( ) : null );
        t.initCond = ( initCond != null ? (Conditions) initCond.clone( ) : null );
 
        return t;
    }

}
