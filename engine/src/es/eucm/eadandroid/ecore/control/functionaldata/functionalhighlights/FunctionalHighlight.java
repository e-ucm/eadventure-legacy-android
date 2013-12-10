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
package es.eucm.eadandroid.ecore.control.functionaldata.functionalhighlights;

import android.graphics.Bitmap;



public abstract class FunctionalHighlight {
    
    protected boolean animated;
    
    protected long time = System.currentTimeMillis( );
    
    protected int displacementX = 0;
    
    protected int displacementY = 0;
    
    protected float scale = 1.0f;
    
    protected static final int TIME_CONST = 600;
    
    public abstract Bitmap getHighlightedImage(Bitmap image);
    
    protected Bitmap oldImage;
    
    protected Bitmap newImage;
    
    public int getDisplacementX() {
        return displacementX;
    }
    
    public int getDisplacementY() {
        return displacementY;
    }
    
    public boolean isAnimated() {
        return animated;
    }
    
    protected void calculateDisplacements(int width, int height) {
        long elapsedTime = System.currentTimeMillis( ) - time;
        float temp = ( elapsedTime % (TIME_CONST * 2) );
        if (temp < TIME_CONST / 2)
            temp = -temp; 
        else if (temp < TIME_CONST)
            temp = temp - TIME_CONST;
        else if (temp < TIME_CONST * 1.5f)
            temp = temp - TIME_CONST;
        else
            temp = TIME_CONST*2 - temp;
        scale = 1f + (temp / TIME_CONST) * 0.2f; 
        displacementY = (int) ((height - (height * scale)) / 2);
        displacementX = (int) ((width - (width * scale)) / 2);
    }
}
