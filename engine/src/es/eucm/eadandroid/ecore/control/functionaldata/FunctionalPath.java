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
package es.eucm.eadandroid.ecore.control.functionaldata;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadandroid.common.data.chapter.Trajectory.Side;

public class FunctionalPath implements Comparable<FunctionalPath> {

    private float length;

    private List<FunctionalSide> sides;

    private float destX;

    private float destY;

    private boolean getsTo;

    private float distance;

    public FunctionalPath( float length, float distance, List<FunctionalSide> sides ) {

        this.length = length;
        this.sides = new ArrayList<FunctionalSide>( sides );
        this.distance = distance;
        getsTo = false;
    }

    public void setLength( float length ) {

        this.length = length;
    }

    public void setGetsTo( boolean b ) {

        getsTo = b;
    }

    public boolean isGetsTo( ) {

        return getsTo;
    }

    public void updateUpTo( float dist, float posX, float posY ) {

        if( dist < distance ) {
            destX = posX;
            destY = posY;
            distance = dist;
        }
    }

    public List<FunctionalSide> getSides( ) {

        return sides;
    }

    public List<Side> getNormalSides( ) {

        List<Side> temp = new ArrayList<Side>( );
        for( FunctionalSide side : sides )
            temp.add( side.getSide( ) );
        return temp;
    }

    public float getLength( ) {

        return length;
    }

    public float getDistance( ) {

        return distance;
    }

    public void addSide( float lenght, float distance, FunctionalSide side ) {

        sides.add( side );
        this.length += lenght;
        this.distance = distance;
    }

    public FunctionalPath newFunctionalPath( float length, float distance, FunctionalSide side ) {

        if( sides.contains( side ) )
            return null;
        for( FunctionalSide tempSide : sides )
            if( tempSide.getSide( ) == side.getSide( ) )
                return null;

        FunctionalPath temp = new FunctionalPath( this.length, this.distance, this.sides );
        temp.addSide( length, distance, side );
        return temp;
    }

    public int compareTo( FunctionalPath arg0 ) {

        if( this.getsTo && !arg0.getsTo ) {
            return 1;
        }
        else if( !this.getsTo && arg0.getsTo ) {
            return -1;
        }
        int distDif = (int) ( arg0.distance - distance );
        if( Math.abs( distDif ) < 200 ) {
            return (int) ( arg0.length - length );
        }
        else {
            return distDif;
        }
    }

    public float getDestX( ) {

        return destX;
    }

    public float getDestY( ) {

        return destY;
    }

    public void print( ) {

        for( FunctionalSide side : sides ) {
            System.out.print( side.getStartNode( ).getID( ) + "->" );
        }
        System.out.println( sides.get( sides.size( ) - 1 ).getEndNode( ).getID( ) );
    }
}
