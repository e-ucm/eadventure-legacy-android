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
package es.eucm.eadandroid.ecore.control.animations;

import android.graphics.Bitmap;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

/**
 * This class implements a set of images, that can be used as a set of slides.
 */
public class ImageSet implements Animation {

    /**
     * Set of images.
     */
    protected Bitmap[] imageSet;
    
    /**
     * Set of images.
     */
    protected String[] imagePathSet;

    /**
     * Index of the current frame.
     */
    protected int currentFrameIndex;
    
    /**
     * Bitmap of the current frame.
     */
    protected Bitmap currentFrame;

    /**
     * Constructor.
     */
    public ImageSet( ) {

        imageSet = null;
        currentFrameIndex = 0;
        currentFrame = null;
    }

    /**
     * Adds an image to the animation with the specified duration (time to
     * display the image).
     */
    public void setImages( Bitmap[] imageSet ) {

        this.imageSet = imageSet;
        if( imageSet.length == 0 )
            this.imageSet = getNoAnimationAvailableImageSet( );
    }
    
    /**
     * Adds an image to the animation with the specified duration (time to
     * display the image).
     */
    public void setImagesPath( String[] imagePathSet ) {

        this.imagePathSet = imagePathSet;
        if( imagePathSet.length == 0 )
            this.imageSet = getNoAnimationAvailableImageSet( );
    }

    /**
     * Starts this animation over from the beginning.
     */
    public synchronized void start( ) {

        currentFrameIndex = 0;
    }

    public boolean nextImage( ) {

        boolean noMoreFrames = false;

        currentFrameIndex++;
        
        if( imageSet == null ){
        	if( currentFrameIndex >= imagePathSet.length ) {
         	   currentFrameIndex %= imagePathSet.length;
        	    noMoreFrames = true;
     	   }else currentFrame = null;
		}else if( currentFrameIndex >= imageSet.length ) {
         	   currentFrameIndex %= imageSet.length;
        	    noMoreFrames = true;
     	}
			
		
        return noMoreFrames;
    }

    /**
     * Returns the current image from the set.
     */
    public Bitmap getImage( ) {
    	if( imageSet == null ){
    		if (currentFrame == null){
    			Bitmap temp = MultimediaManager.getInstance().loadImage(imagePathSet[currentFrameIndex], MultimediaManager.IMAGE_SCENE);
    			currentFrame = MultimediaManager.getInstance().getFullscreenImage(temp);
    			temp = null;
    		}
    	}
    	else currentFrame = imageSet[currentFrameIndex];
    	return currentFrame;
    }

    public boolean isPlayingForFirstTime( ) {

        return true;
    }

    public void update( long elapsedTime ) {

    }

    /**
     * Dirty fix: Needed for avoiding null pointer exception when no available
     * resources block
     * 
     * @return
     */
    protected Bitmap[] getNoAnimationAvailableImageSet( ) {

        Bitmap[] array = new Bitmap[ 1 ];
        array[0] = MultimediaManager.getInstance( ).loadImage( ResourceHandler.DEFAULT_SLIDES, MultimediaManager.IMAGE_SCENE );
        return array;
    }
}
