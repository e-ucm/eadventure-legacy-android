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

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

import es.eucm.eadandroid.common.data.Documented;
import es.eucm.eadandroid.common.data.Polygon;
import es.eucm.eadandroid.common.data.Positioned;
import es.eucm.eadandroid.common.data.chapter.ExitLook;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;

/**
 * This class holds the data of an exit in eAdventure
 */
public class Exit implements Cloneable, Documented, Rectangle, Positioned {

    public static final int NO_TRANSITION = 0;

    public static final int TOP_TO_BOTTOM = 1;

    public static final int BOTTOM_TO_TOP = 2;

    public static final int LEFT_TO_RIGHT = 3;

    public static final int RIGHT_TO_LEFT = 4;

    public static final int FADE_IN = 5;

    /**
     * X position of the upper left corner of the exit
     */
    private int x;

    /**
     * Y position of the upper left corner of the exit
     */
    private int y;

    /**
     * Width of the exit
     */
    private int width;

    /**
     * Height of the exit
     */
    private int height;

    /**
     * Documentation of the exit.
     */
    private String documentation;

    /**
     * List of nextscenes of the exit
     */
    private List<NextScene> nextScenes;

    /**
     * Default exit look (it can exists or not)
     */
    private ExitLook defaultExitLook;

    private boolean rectangular;

    private List<Point> points;

    private InfluenceArea influenceArea;

    /**
     * Id of the target scene
     */
    private String nextSceneId;

    /**
     * X position in which the player should appear in the new scene
     */
    private int destinyX;

    /**
     * Y position in which the player should appear in the new scene
     */
    private int destinyY;

    /**
     * Conditions of the next scene
     */
    private Conditions conditions;

    /**
     * Effects triggered before exiting the current scene
     */
    private Effects effects;

    /**
     * Post effects triggered after exiting the current scene
     */
    private Effects postEffects;

    private Effects notEffects;

    private boolean hasNotEffects;

    private Integer transitionType;

    private Integer transitionTime;

    /**
     * Creates a new Exit
     * 
     * @param x
     *            The horizontal coordinate of the upper left corner of the exit
     * @param y
     *            The vertical coordinate of the upper left corner of the exit
     * @param width
     *            The width of the exit
     * @param height
     *            The height of the exit
     */
    public Exit( boolean rectangular, int x, int y, int width, int height ) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        documentation = null;
        points = new ArrayList<Point>( );
        nextScenes = new ArrayList<NextScene>( );
        this.rectangular = rectangular;
        influenceArea = new InfluenceArea( );

        destinyX = Integer.MIN_VALUE;
        destinyY = Integer.MIN_VALUE;
        conditions = new Conditions( );
        effects = new Effects( );
        postEffects = new Effects( );
        notEffects = new Effects( );
        hasNotEffects = false;
        transitionType = NO_TRANSITION;
        transitionTime = 0;
        defaultExitLook = new ExitLook( );
    }

    public Exit( String targetId ) {

        this( false, 0, 0, 20, 20 );
        this.nextSceneId = targetId;
    }

    /**
     * @return the nextSceneId
     */
    public String getNextSceneId( ) {

        return nextSceneId;
    }

    /**
     * @param nextSceneId
     *            the nextSceneId to set
     */
    public void setNextSceneId( String nextSceneId ) {

        this.nextSceneId = nextSceneId;
    }

    /**
     * @return the destinyX
     */
    public int getDestinyX( ) {

        return destinyX;
    }

    /**
     * @param destinyX
     *            the destinyX to set
     */
    public void setDestinyX( int destinyX ) {

        this.destinyX = destinyX;
    }

    /**
     * @return the destinyY
     */
    public int getDestinyY( ) {

        return destinyY;
    }

    /**
     * @param destinyY
     *            the destinyY to set
     */
    public void setDestinyY( int destinyY ) {

        this.destinyY = destinyY;
    }

    /**
     * @return the conditions
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    /**
     * @param conditions
     *            the conditions to set
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    /**
     * @return the effects
     */
    public Effects getEffects( ) {

        return effects;
    }

    /**
     * @param effects
     *            the effects to set
     */
    public void setEffects( Effects effects ) {

        this.effects = effects;
    }

    /**
     * @return the postEffects
     */
    public Effects getPostEffects( ) {

        return postEffects;
    }

    /**
     * @param postEffects
     *            the postEffects to set
     */
    public void setPostEffects( Effects postEffects ) {

        this.postEffects = postEffects;
    }

    /**
     * @return the notEffects
     */
    public Effects getNotEffects( ) {

        return notEffects;
    }

    /**
     * @param notEffects
     *            the notEffects to set
     */
    public void setNotEffects( Effects notEffects ) {

        this.notEffects = notEffects;
    }

    /**
     * @return the hasNotEffects
     */
    public Boolean isHasNotEffects( ) {

        return hasNotEffects;
    }

    /**
     * @param hasNotEffects
     *            the hasNotEffects to set
     */
    public void setHasNotEffects( Boolean hasNotEffects ) {

        this.hasNotEffects = hasNotEffects;
    }

    /**
     * @return the transitionType
     */
    public Integer getTransitionType( ) {

        return transitionType;
    }

    /**
     * @param transitionType
     *            the transitionType to set
     */
    public void setTransitionType( Integer transitionType ) {

        this.transitionType = transitionType;
    }

    /**
     * @return the transitionTime
     */
    public Integer getTransitionTime( ) {

        return transitionTime;
    }

    /**
     * @param transitionTime
     *            the transitionTime to set
     */
    public void setTransitionTime( Integer transitionTime ) {

        this.transitionTime = transitionTime;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight( int height ) {

        this.height = height;
    }

    /**
     * @param nextScenes
     *            the nextScenes to set
     */
    public void setNextScenes( List<NextScene> nextScenes ) {

        this.nextScenes = nextScenes;
    }

    /**
     * Returns the horizontal coordinate of the upper left corner of the exit
     * 
     * @return the horizontal coordinate of the upper left corner of the exit
     */
    public int getX( ) {

        if( rectangular )
            return x;
        else {
            int minX = Integer.MAX_VALUE;
            for( Point point : points ) {
                if( point.x < minX )
                    minX = point.x;
            }
            return minX;
        }
    }

    /**
     * Returns the horizontal coordinate of the bottom right of the exit
     * 
     * @return the horizontal coordinate of the bottom right of the exit
     */
    public int getY( ) {

        if( rectangular )
            return y;
        else {
            int minY = Integer.MAX_VALUE;
            for( Point point : points ) {
                if( point.y < minY )
                    minY = point.y;
            }
            return minY;
        }
    }

    /**
     * Returns the width of the exit
     * 
     * @return Width of the exit
     */
    public int getWidth( ) {

        if( rectangular )
            return width;
        else {
            int maxX = Integer.MIN_VALUE;
            int minX = Integer.MAX_VALUE;
            for( Point point : points ) {
                if( point.x > maxX )
                    maxX = point.x;
                if( point.x < minX )
                    minX = point.x;
            }
            return maxX - minX;

        }
    }

    /**
     * Returns the height of the exit
     * 
     * @return Height of the exit
     */
    public int getHeight( ) {

        if( rectangular )
            return height;
        else {
            int maxY = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            for( Point point : points ) {
                if( point.y > maxY )
                    maxY = point.y;
                if( point.y < minY )
                    minY = point.y;
            }
            return maxY - minY;
        }
    }

    /**
     * Set the values of the exit.
     * 
     * @param x
     *            X coordinate of the upper left point
     * @param y
     *            Y coordinate of the upper left point
     * @param width
     *            Width of the exit area
     * @param height
     *            Height of the exit area
     */
    public void setValues( int x, int y, int width, int height ) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the documentation of the exit.
     * 
     * @return the documentation of the exit
     */
    public String getDocumentation( ) {

        return documentation;
    }

    /**
     * Returns the list of the next scenes from this scene
     * 
     * @return the list of the next scenes from this scene
     */
    public List<NextScene> getNextScenes( ) {

        return nextScenes;
    }

    /**
     * Changes the documentation of this exit.
     * 
     * @param documentation
     *            The new documentation
     */
    public void setDocumentation( String documentation ) {

        this.documentation = documentation;
    }

    /**
     * Adds a next scene to the list of next scenes
     * 
     * @param nextScene
     *            the next scene to add
     */
    public void addNextScene( NextScene nextScene ) {

        nextScenes.add( nextScene );
    }

    /**
     * @return the defaultExitLook
     */
    public ExitLook getDefaultExitLook( ) {

        return defaultExitLook;
    }

    /**
     * @param defaultExitLook
     *            the defaultExitLook to set
     */
    public void setDefaultExitLook( ExitLook defaultExitLook ) {

        this.defaultExitLook = defaultExitLook;
    }

    /**
     * Returns whether a point is inside the exit
     * 
     * @param x
     *            the horizontal positon
     * @param y
     *            the vertical position
     * @return true if the point (x, y) is inside the exit, false otherwise
     */
    public boolean isPointInside( int x, int y ) {

        if( rectangular )
            return x > getX0( ) && x < getX1( ) && y > getY0( ) && y < getY1( );
        else {
            Polygon polygon = new Polygon(getPoints().size());
            for( Point point : getPoints( ) ) {
                polygon.addPoint( point.x, point.y );
            }
            return polygon.contains( x, y );
        }
    }

    /**
     * Returns the horizontal coordinate of the upper left corner of the exit
     * 
     * @return the horizontal coordinate of the upper left corner of the exit
     */
    public int getX0( ) {

        return getX( );
    }

    /**
     * Returns the vertical coordinate of the upper left corner of the exit
     * 
     * @return the vertical coordinate of the upper left corner of the exit
     */
    public int getX1( ) {

        return getX( ) + getWidth( );
    }

    /**
     * Returns the horizontal coordinate of the bottom right of the exit
     * 
     * @return the horizontal coordinate of the bottom right of the exit
     */
    public int getY0( ) {

        return getY( );
    }

    /**
     * Returns the vertical coordinate of the bottom right of the exit
     * 
     * @return the vertical coordinate of the bottom right of the exit
     */
    public int getY1( ) {

        return getY( ) + getHeight( );
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Exit e = (Exit) super.clone( );
        e.defaultExitLook = ( defaultExitLook != null ? (ExitLook) defaultExitLook.clone( ) : null );
        e.documentation = ( documentation != null ? new String( documentation ) : null );
        e.height = height;
        if( nextScenes != null ) {
            e.nextScenes = new ArrayList<NextScene>( );
            for( NextScene ns : nextScenes )
                e.nextScenes.add( (NextScene) ns.clone( ) );
        }
        e.influenceArea = ( influenceArea != null ? (InfluenceArea) influenceArea.clone( ) : null );
        e.width = width;
        e.x = x;
        e.y = y;
        e.rectangular = rectangular;
        if( points != null ) {
            e.points = new ArrayList<Point>( );
            for( Point p : points ) {
           // el apa–o!! 	
            	Point paux = new Point(p);
                e.points.add( paux );
                
            }
        }
        e.conditions = ( conditions != null ? (Conditions) conditions.clone( ) : null );
        e.effects = ( effects != null ? (Effects) effects.clone( ) : null );
        e.postEffects = ( postEffects != null ? (Effects) postEffects.clone( ) : null );
        e.notEffects = ( notEffects != null ? (Effects) notEffects.clone( ) : null );
        e.destinyX = destinyX;
        e.destinyY = destinyY;
        e.hasNotEffects = hasNotEffects;
        e.nextSceneId = ( nextSceneId != null ? new String( nextSceneId ) : null );
        e.transitionTime = new Integer( transitionTime );
        e.transitionType = new Integer( transitionType );
        return e;
    }

    public boolean isRectangular( ) {

        return rectangular;
    }

    public void setRectangular( boolean rectangular ) {

        this.rectangular = rectangular;
    }

    public List<Point> getPoints( ) {

        return points;
    }

    public void addPoint( Point point ) {

        points.add( point );
    }

    public InfluenceArea getInfluenceArea( ) {

        return influenceArea;
    }

    public void setInfluenceArea( InfluenceArea influeceArea ) {

        this.influenceArea = influeceArea;
    }

    public boolean hasPlayerPosition( ) {

        return ( destinyX != Integer.MIN_VALUE ) && ( destinyY != Integer.MIN_VALUE );
    }

    public int getPositionX( ) {

        return destinyX;
    }

    public int getPositionY( ) {

        return destinyY;
    }

    public void setPositionX( int newX ) {

        this.destinyX = newX;
    }

    public void setPositionY( int newY ) {

        this.destinyY = newY;
    }
}
