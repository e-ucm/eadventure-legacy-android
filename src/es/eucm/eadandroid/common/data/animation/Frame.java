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
package es.eucm.eadandroid.common.data.animation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import es.eucm.eadandroid.common.auxiliar.CreateImage;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.debug.ReportDialog;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * This class holds the information for an animation frame
 * 
 * @author Eugenio Marchiori
 * 
 */
public class Frame implements Cloneable, Timed {

    /**
     * The xml tag for the sound of the frame
     */
    public static final String RESOURCE_TYPE_SOUND = "sound";

    /**
     * The frame is a image
     */
    public static final int TYPE_IMAGE = 0;

    /**
     * The frame is a video
     */
    public static final int TYPE_VIDEO = 1;

    /**
     * The default time of a frame
     */
    public static final int DEFAULT_TIME = 100;

    /**
     * The url/resource path
     */
    private String uri;

    /**
     * Time to display the frame
     */
    private long time;

    /**
     * Type of the frame: {@link #TYPE_IMAGE} or {@link #TYPE_VIDEO}
     */
    private int type;

    /**
     * The image of the frame, buffered when possible
     */
    private Bitmap image;

    /**
     * Set of resources for the frame
     */
    private List<Resources> resources;

    private boolean waitforclick;

    private String soundUri;

    private int maxSoundTime;

    private String animationPath;
    
    private ImageLoaderFactory factory;

    /**
     * Creates a new empty frame
     */
    public Frame( ImageLoaderFactory factory ) {
        this.factory = factory;
        uri = "";
        type = TYPE_IMAGE;
        time = DEFAULT_TIME;
        image = null;
        waitforclick = false;
        resources = new ArrayList<Resources>( );
        soundUri = "";
        maxSoundTime = 1000;
    }

    /**
     * Creates a new frame with a image uri
     * 
     * @param uri
     *            the uri for the image
     */
    public Frame( ImageLoaderFactory factory, String uri ) {
        this.uri = uri;
        this.factory = factory;
        type = TYPE_IMAGE;
        time = DEFAULT_TIME;
        image = null;
        waitforclick = false;
        resources = new ArrayList<Resources>( );
        soundUri = "";
        maxSoundTime = 1000;
    }

    /**
     * Creates a new frame with a image uri and a duration time
     * 
     * @param uri
     *            The uri for the image
     * @param time
     *            The time (duration) of the frame
     */
    public Frame( ImageLoaderFactory factory, String uri, long time ) {
        this.uri = uri;
        this.factory = factory;
        type = TYPE_IMAGE;
        this.time = time;
        image = null;
        waitforclick = false;
        resources = new ArrayList<Resources>( );
        soundUri = "";
        maxSoundTime = 1000;
    }

    /**
     * Returns the uri/path of the frame resource
     * 
     * @return the uri/path of the frame resource
     */
    public String getUri( ) {

        return uri;
    }

    /**
     * Set the uri/path of the frame resource
     * 
     * @param uri
     *            the uri/path of the frame resource
     */
    public void setUri( String uri ) {

        this.uri = uri;
        image = null;
    }

    /**
     * Returns the time (duration) of the frame in milliseconds
     * 
     * @return the time (duration) of the frame in milliseconds
     */
    public long getTime( ) {

        return time;
    }

    /**
     * Set the time (duration) of the frame in milliseconds
     * 
     * @param time
     *            the time (duration) of the frame in milliseconds
     */
    public void setTime( long time ) {

        this.time = time;
    }

    /**
     * Returns the type of the frame
     * 
     * @return the type of the frame
     */
    public int getType( ) {

        return type;
    }

    /**
     * Sets the type of the frame
     * 
     * @param type
     *            the type of the frame
     */
    public void setType( int type ) {

        this.type = type;
    }

    public String getSoundUri( ) {

        return soundUri;
    }

    public void setSoundUri( String soundUri ) {

        this.soundUri = soundUri;
    }

    public int getMaxSoundTime( ) {

        return maxSoundTime;
    }

    public void setMaxSoundTime( int maxSoundTime ) {

        this.maxSoundTime = maxSoundTime;
    }

    /**
     * Adds some resources to the list of resources
     * 
     * @param resources
     *            the resources to add
     */
    public void addResources( Resources resources ) {

        this.resources.add( resources );
    }

    /**
     * Returns the list of resources of the frame
     * 
     * @return The list of resources of the frame
     */
    public List<Resources> getResources( ) {

        return resources;
    }

    /**
     * Returns the image for the frame. The image can be vertically inverted or
     * scaled to fullscreen
     * 
     * @param mirror
     *            If true, the image is vertically inverted
     * @param fullscreen
     *            If true, the image is scaled to fullscreen
     * @return The image for the frame, with the necessary modifications made
     */
    public Bitmap getImage( boolean mirror, boolean fullscreen, int where ) {

        if( image != null )
            return image;
        if( uri != null && uri.length( ) > 0 ) {
            if( where == Animation.ENGINE || where == Animation.EDITOR)
                image = factory.getImageFromPath(  uri );
            else if( where == Animation.PREVIEW )
                image = getImageFromAnimationPath( );
        }
        if( image != null && mirror )
            image = getScaledImage( image, -1, 1 );
        if( image != null && fullscreen )
            image = getFullscreenImage( image );
        if( image == null ) {
         
        	image = ResourceHandler.getInstance().getResourceAsImage("img/icons/noImageFrame.png");
        	
        	if (image==null)
        		image = CreateImage.createImage(200, 200, "No image Frame");
        }

        return image;
    }

    private Bitmap getImageFromAnimationPath( ) {

        Bitmap image = null;

        try {
            InputStream inputStream = null;

            String regexp = java.io.File.separator;
            if( regexp.equals( "\\" ) )
                regexp = "\\\\";
            String temp[] = this.animationPath.split( regexp );
            String filename = "";
            for( int i = 0; i < temp.length - 1; i++ ) {
                filename += temp[i] + java.io.File.separator;
            }

            temp = this.uri.split( "/" );
            filename += temp[temp.length - 1];
            if( new File( filename ).exists( ) )
                inputStream = new FileInputStream( filename );

            if( inputStream != null ) {
            	
            	image = ResourceHandler.getInstance().getInputStreamAsImage(inputStream);

                if( image == null || image.getHeight() == -1 || image.getWidth() == -1 ) {
                    factory.showErrorDialog( TC.get( "Error.Title") , TC.get( "Error.ImageTypeNotSupported" ) );
                }
                inputStream.close( );
            }
        }
        catch( IOException e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
        }

        return image;
    }

    /**
     * Returns a new imaged scaled accordingly.
     * 
     * @param image
     *            The original image
     * @param x
     *            The scale along the x-axis
     * @param y
     *            The scale alogn the y-axis
     * @return A new, scaled image
     */
    private Bitmap getScaledImage( Bitmap image, float x, float y ) {

        Bitmap scaledImage = null;
         
  		  if( image != null ) {	  
  			 scaledImage = Bitmap.createScaledBitmap(image, (int) (image.getWidth() * x),(int) (image.getHeight() * y), false);
  		  }
  		          
		return scaledImage;
    }

    /**
     * Returns a scaled image that fits in the game screen.
     * 
     * @param image
     *            the image to be scaled.
     * @return a scaled image that fits in the game screen.
     */
    private Bitmap getFullscreenImage( Bitmap image ) {

    	Bitmap newImage = Bitmap.createScaledBitmap(image, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, true);

        return newImage;
    }

    /**
     * Returns the value of waitforclick
     * 
     * @return the value of waitforclick
     */
    public boolean isWaitforclick( ) {

        return waitforclick;
    }

    /**
     * Set the value of waitforclick
     * 
     * @param waitforclick
     */
    public void setWaitforclick( boolean waitforclick ) {

        this.waitforclick = waitforclick;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Frame f = (Frame) super.clone( );
        f.image = image;
        if( resources != null ) {
            f.resources = new ArrayList<Resources>( );
            for( Resources r : resources )
                f.resources.add( (Resources) r.clone( ) );
        }
        f.time = time;
        f.type = type;
        f.uri = ( uri != null ? new String( uri ) : null );
        f.waitforclick = waitforclick;
        return f;
    }

    public void setAbsolutePath( String animationPath ) {

        this.animationPath = animationPath;
    }

    public String getImageAbsolutePath( ) {

        String regexp = java.io.File.separator;
        if( regexp.equals( "\\" ) )
            regexp = "\\\\";
        String temp[] = this.animationPath.split( regexp );
        String filename = "";
        for( int i = 0; i < temp.length - 1; i++ ) {
            filename += temp[i] + java.io.File.separator;
        }

        temp = this.uri.split( "/" );
        filename += temp[temp.length - 1];

        return filename;
    }

    public String getSoundAbsolutePath( ) {

        if( soundUri == null || soundUri.equals( "" ) )
            return null;

        String regexp = java.io.File.separator;
        if( regexp.equals( "\\" ) )
            regexp = "\\\\";
        String temp[] = this.animationPath.split( regexp );
        String filename = "";
        for( int i = 0; i < temp.length - 1; i++ ) {
            filename += temp[i] + java.io.File.separator;
        }

        temp = this.soundUri.split( "/" );
        filename += temp[temp.length - 1];

        return filename;
    }


}
