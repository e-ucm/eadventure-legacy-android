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
package es.eucm.eadandroid.multimedia;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import android.graphics.Bitmap;
import es.eucm.eadandroid.common.loader.Loader;
import es.eucm.eadandroid.ecore.control.animations.Animation;
import es.eucm.eadandroid.ecore.control.animations.FrameAnimation;
import es.eucm.eadandroid.ecore.control.animations.ImageAnimation;
import es.eucm.eadandroid.ecore.control.animations.ImageSet;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.res.resourcehandler.EngineImageLoader;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;


/**
 * This class manages various aspects related to multimedia in eAdventure.
 */
public class MultimediaManager {


	/**
	 * Cached scene image category
	 */
	public static final int IMAGE_SCENE = 0;

	/**
	 * Cached menu image category
	 */
	public static final int IMAGE_MENU = 1;

	/**
	 * Cached player image category
	 */
	public static final int IMAGE_PLAYER = 2;

	private HashMap<String, WeakReference<Bitmap>>[] imageCache;

	/**
	 * Mirrored images cache
	 */
	private HashMap<String, WeakReference<Bitmap>>[] mirrorImageCache;

	/**
	 * Sounds cache
	 */
	private HashMap<Long, Sound> soundCache;

	/**
	 * Background music's id
	 */
	private long musicSoundId;

	/**
	 * Instance for the singleton
	 */
	private static MultimediaManager instance = new MultimediaManager();

	private HashMap<String, Animation> animationCache;

	/**
	 * Returns the MultimediaManager instance. Notice MultimediaManager is a
	 * singleton class.
	 * 
	 * @return the MultimediaManager singleton instance
	 */
	public static MultimediaManager getInstance() {

		return instance;
	}

	/**
	 * Empty constructor
	 */
	@SuppressWarnings("unchecked")
	private MultimediaManager() {

		imageCache = new HashMap[3];
		for (int i = 0; i < 3; i++)
			imageCache[i] = new HashMap<String, WeakReference<Bitmap>>();
		mirrorImageCache = new HashMap[3];
		for (int i = 0; i < 3; i++)
			mirrorImageCache[i] = new HashMap<String, WeakReference<Bitmap>>();

		soundCache = new HashMap<Long, Sound>();

		 animationCache = new HashMap<String, Animation>( );

		musicSoundId = -1;
	}

	/**
	 * Returns an Image for imagePath. If the image file does not exists, a
	 * FileNotFoundException is thrown.
	 * 
	 * @param imagePath
	 * @param category
	 *            image category for caching
	 * @return an Image for imagePath.
	 */
	public Bitmap loadImage(String bitmapPath, int category) {
		
		WeakReference<Bitmap> wrImg = imageCache[category].get(bitmapPath);		
		
		Bitmap image= (wrImg!=null ?  (Bitmap) wrImg.get() : null);
		
		if (image == null) {
			 image = ResourceHandler.getInstance().getResourceAsImage( bitmapPath );
			 if (image != null) {
				imageCache[category].put(bitmapPath, new WeakReference<Bitmap>(image));
			}
		}
		
		wrImg = null;
		
		return image;
	}


	/**
	 * Returns an Image for imagePath after mirroring it. If the image file does
	 * not exists, a FileNotFoundException is thrown.
	 * 
	 * @param imagePath
	 *            Image path
	 * @param category
	 *            Category for the image
	 * @return an Image for imagePath.
	 */
	public Bitmap loadMirroredImage(String bitmapPath, int category) {

		WeakReference<Bitmap> wrImg = mirrorImageCache[category].get(bitmapPath);
		
		Bitmap image = (wrImg!=null ?  (Bitmap) wrImg.get() : null);
		// If the image is in cache, don't load it
		if (image == null) {
			// Load the image and store it in cache

			image = getScaledImage( loadImage( bitmapPath, category ),-1, 1 );
			if (image != null) {
				mirrorImageCache[category].put(bitmapPath, new WeakReference<Bitmap>(image));
			}
		}
		
		wrImg = null;
		
		return image;
	}

	/**
	 * Returns a scaled image of the given image. The new width is x times the
	 * old width. The new height is y times the old height.
	 * 
	 * @param image
	 *            the image to be scaled
	 * @param width
	 *            width scale factor
	 * @param height
	 *            height scale factor
	 * @return a scaled image of the given image.
	 */

	private Bitmap getScaledImage(Bitmap image, int width, int height) {

		Bitmap scaledImage = null;
		
		  if( image != null && (width!=1 || height!=1)) {
			     scaledImage = Bitmap.createScaledBitmap(image, image.getWidth() * width, image.getHeight() * height, false);
		  }
		
		return scaledImage;
	}
	
	public Bitmap loadScaledImage(String bitmapPath, int wfactor, int hfactor,int category) {
		
		Bitmap image = loadImage(bitmapPath, category);

		Bitmap scaledImage = null;
		
		if( image != null ) {
			
			 scaledImage = Bitmap.createScaledBitmap(image, image.getWidth() * wfactor, image.getHeight() * hfactor, false);
		  }
		  
		image = null;
		  
		return scaledImage;
	}

	/**
	 * Returns a scaled image that fits in the game screen.
	 * 
	 * @param image
	 *            the image to be scaled.
	 * @return a scaled image that fits in the game screen.
	 */

	public Bitmap getFullscreenImage(Bitmap image) {

		return Bitmap.createScaledBitmap(image, GUI.WINDOW_WIDTH,
				GUI.WINDOW_HEIGHT, false);
	}

	/**
	 * Returns an Id of the sound of type soundType for the file in soundPath,
	 * and sets whether it has to be played or not in a loop.
	 * 
	 * @param soundType
	 *            the type of the sound to be created
	 * @param soundPath
	 *            the path to the sound to be created
	 * @param loop
	 *            whether or not the sound must be played in a loop
	 * @return an Id that represents the sound with the given configuration.
	 */
	public long loadSound(int soundType, String soundPath, boolean loop) {
		// to start with we will only use mp3 sound
		Sound sound = null;
		long soundId = 1;

		sound = new SoundAndroidMp3(soundPath, loop);
		if (sound != null) {
			soundCache.put(new Long(sound.getId()), sound);
			soundId = sound.getId();
		}
		
		return soundId;
	}

	/**
	 * Returns an Id of the sound for the file in soundPath, and sets whether it
	 * has to be played or not in a loop. The sound's type is guessed from its
	 * soundPath's extension.
	 * 
	 * @param soundPath
	 *            the path to the sound to be created
	 * @param loop
	 *            whether or not the sound must be played in a loop
	 * @return an Id that represents the sound with the given configuration.
	 */

	public long loadSound(String soundPath, boolean loop) {
		/*
		 * 
		 * String type = soundPath.substring( soundPath.lastIndexOf( "." ) + 1
		 * ).toLowerCase( ); int soundType = -1;
		 * 
		 * if( type.equals( "mp3" ) ) soundType = MP3; else if( type.equals(
		 * "midi" ) ) soundType = MIDI; else if( type.equals( "mid" ) )
		 * soundType = MIDI; return loadSound( soundType, soundPath, loop );
		 */
		return loadSound(1, soundPath, loop);
	}

	/**
	 * Returns an Id of the music of type musicType for the file in musicPath,
	 * and sets whether it has to be played or not in a loop.
	 * 
	 * @param soundType
	 *            the type of the music to be created
	 * @param soundPath
	 *            the path to the music to be created
	 * @param loop
	 *            whether or not the music must be played in a loop
	 * @return an Id that represents the music with the given configuration.
	 */
	public long loadMusic(int musicType, String musicPath, boolean loop) {

		musicSoundId = loadSound(musicType, musicPath, loop);
		return musicSoundId;
	}

	/**
	 * Returns an Id of the music for the file in musicPath, and sets whether it
	 * has to be played or not in a loop. The sound's type is guessed from its
	 * musicPath's extension.
	 * 
	 * @param soundPath
	 *            the path to the music to be created
	 * @param loop
	 *            whether or not the music must be played in a loop
	 * @return an Id that represents the music with the given configuration.
	 */
	public long loadMusic(String musicPath, boolean loop) {

		musicSoundId = loadSound(musicPath, loop);
		return musicSoundId;
	}

	/**
	 * Plays the sound from the cache that have soundId as id
	 * 
	 * @param soundId
	 *            Id of the sound to be played
	 */
	public void startPlaying(long soundId) {

		if (soundCache.containsKey(soundId)) {
			Sound sound = soundCache.get(soundId);
			if (sound != null)
				sound.startPlaying();
		}
	}

	/**
	 * Stops the sound from the cache that have soundId as id
	 * 
	 * @param soundId
	 *            Id of the sound to be stopped
	 */
	public void stopPlaying(long soundId) {

		if (soundCache.containsKey(soundId)) {
			Sound sound = soundCache.get(soundId);
			if (sound != null)
				{sound.stopPlaying();
				 soundCache.remove(soundId);			
				}
		}
	}

	public void stopPlayingMusic() {

		Collection<Sound> sounds = soundCache.values();
		for (Sound sound : sounds) {
			if (sound.getId() == musicSoundId) {
				sound.stopPlaying();
				
			}
		}
	}

	public void stopPlayingInmediately(long soundId) {

		if (soundCache.containsKey(soundId)) {
			Sound sound = soundCache.get(soundId);
			if (sound != null) {
				sound.stopPlaying();
				sound.finalize();
			}
		}
	}

	/**
	 * Given the soundId, it returns whether the sound is playing or not
	 * 
	 * @param soundId
	 *            long The soundId
	 * @return true if the sound is playing
	 */
	public boolean isPlaying(long soundId) {

		boolean playing = false;
		if (soundCache.containsKey(soundId)) {
			Sound sound = soundCache.get(soundId);
			if (sound != null)
				playing = sound.isPlaying();
		}
		return playing;
	}

	/**
	 * Update the status of all sounds cache and if they have finished, they are
	 * removed from the cache
	 * 
	 * @throws InterruptedException
	 */
	public void update() throws InterruptedException {

		Collection<Sound> sounds = soundCache.values();
		ArrayList<Sound> soundsToRemove = new ArrayList<Sound>();
		for (Sound sound : sounds) {
			if (sound.getId() != musicSoundId && !sound.isAlive()) {
				soundsToRemove.add(sound);
			}
		}
		for (int i = 0; i < soundsToRemove.size(); i++) {
			Sound sound = soundsToRemove.get(i);
			sound.join();
			sounds.remove(sound);
		}
	}

	/**
	 * Stops and deletes all sounds currently in the cache, except the music
	 */
	public void stopAllSounds() {

		Collection<Sound> sounds = soundCache.values();
		ArrayList<Sound> soundsToRemove = new ArrayList<Sound>();
		for (Sound sound : sounds) {
			if (sound.getId() != musicSoundId) {
				soundsToRemove.add(sound);
			}
		}
		for (int i = 0; i < soundsToRemove.size(); i++) {
			Sound sound = soundsToRemove.get(i);
			try {
				sound.join();
			} catch (InterruptedException e) {
			}
			sounds.remove(sound);
		}
		sounds.clear();
		soundsToRemove.clear();
	}

	/**
	 * Stops and deletes all sounds currently in the cache even the music
	 */
	public void deleteSounds() {

		Collection<Sound> sounds = soundCache.values();
		for (Sound sound : sounds) {
			if (sound.getId() != musicSoundId) {
				sound.stopPlaying();
				sound.finalize();
				try {
					sound.join();
				} catch (InterruptedException e) {
				}
			}
		}
		sounds.clear();
	}
	
	/**
     * Returns a animation from a path.
     * <p>
     * The animation can be generated from an eaa describing the animation or
     * with frames animationPath_xy.jpg, with xy from 01 to the last existing
     * file with that format (the extension can also be .png).
     * <p>
     * For example, loadAnimation( "path" ) will return an animation with frames
     * path_01.jpg, path_02.jpg, path_03.jpg, if path_04.jpg doesn't exists.
     * 
     * @param animationPath
     *            base path to the animation frames
     * @param mirror
     *            whether or not the frames must be mirrored
     * @param category
     *            Category of the animation
     * @return an Animation with frames animationPath_xy.jpg
     */
    public Animation loadAnimation( String animationPath, boolean mirror, int category ) {
        Animation temp = animationCache.get( animationPath + ( mirror ? "t" : "f" ) );
        if( temp != null )
            return temp;

        if( animationPath != null && animationPath.endsWith( ".eaa" ) ) {
            FrameAnimation animation = new FrameAnimation( Loader.loadAnimation( ResourceHandler.getInstance( ), animationPath, new EngineImageLoader() ) );
            animation.setMirror( mirror );
            temp = animation;
        }
        else {
            int i = 1;
            List<Bitmap> frames = new ArrayList<Bitmap>( );
            Bitmap currentFrame = null;
            boolean end = false;
            while( !end ) {
                if( mirror )
                    currentFrame = loadMirroredImage( animationPath + "_" + leadingZeros( i ) + ".png", category );
                else
                    currentFrame = loadImage( animationPath + "_" + leadingZeros( i ) + ".png", category );

                if( currentFrame != null ) {
                    frames.add( currentFrame );
                    i++;
                }
                
                else 
                	end = true;
              
            }
            ImageAnimation animation = new ImageAnimation( );
            animation.setImages( frames.toArray( new Bitmap[] {} ) );
            temp = animation;
        }
        animationCache.put( animationPath + ( mirror ? "t" : "f" ), temp );
        return temp;
    }

	/**
	 * Returns a animation with frames animationPath_xy.jpg, with xy from 01 to
	 * the last existing file with that format (also the extension can be .png).
	 * <p>
	 * For example, loadAnimation( "path" ) will return an animation with frames
	 * path_01.jpg, path_02.jpg, path_03.jpg, if path_04.jpg doesn't exists.
	 * 
	 * @param slidesPath
	 *            base path to the animation frames
	 * @param category
	 *            Category of the animation
	 * @return an Animation with frames animationPath_xy.jpg
	 */


	public Animation loadSlides(String slidesPath, int category) {

		ImageSet imageSet = null;
		if (slidesPath.endsWith(".eaa")) {
			FrameAnimation animation = new FrameAnimation(Loader.loadAnimation(
					ResourceHandler.getInstance(), slidesPath,
					new EngineImageLoader()));
			animation.setFullscreen(true);
			return animation;
		} else {
			int i = 1;
			List<Bitmap> slides = new ArrayList<Bitmap>();
			Bitmap currentSlide = null;
			boolean end = false;

			while (!end) {
				currentSlide = loadImage(slidesPath + "_"
						+ leadingZeros(i) + ".jpg", category);

				if (currentSlide != null) {
					slides.add(getFullscreenImage(currentSlide));
					currentSlide=null;
					i++;
				} else
					end = true;
			}

			imageSet = new ImageSet();			
				
			Bitmap[] arrayImages = (Bitmap[]) slides.toArray();
			slides.clear();			
			imageSet.setImages(arrayImages);
			arrayImages=null;
		}

		return imageSet;
	}
	
	/**
	 * Returns a animation with frames animationPath_xy.jpg, with xy from 01 to
	 * the last existing file with that format (also the extension can be .png).
	 * <p>
	 * For example, loadAnimation( "path" ) will return an animation with frames
	 * path_01.jpg, path_02.jpg, path_03.jpg, if path_04.jpg doesn't exists.
	 * 
	 * @param slidesPath
	 *            base path to the animation frames
	 * @param category
	 *            Category of the animation
	 * @return an Animation with frames animationPath_xy.jpg
	 */


	public Animation loadSlidesReference(String slidesPath, int category) {

		ImageSet imageSet = null;
		if (slidesPath.endsWith(".eaa")) {
			FrameAnimation animation = new FrameAnimation(Loader.loadAnimation(
					ResourceHandler.getInstance(), slidesPath,
					new EngineImageLoader()));
			animation.setFullscreen(true);
			return animation;
		} else {
			int i = 1;
			List<String> slides = new ArrayList<String>();
			String currentSlide = null;
			boolean end = false;

			while (!end) {
				currentSlide = slidesPath + "_"	+ leadingZeros(i) + ".jpg";

				if (loadImage(currentSlide, category)!=null) {
					slides.add(currentSlide);
					i++;
					currentSlide = null;
				} else
					end = true;
			}

			imageSet = new ImageSet();
			imageSet.setImagesPath(slides.toArray(new String[] {}));
			slides.clear();
		}

		return imageSet;
	}
	
	

	/**
	 * @param n
	 *            number to convert to a String
	 * @return a 2 character string with value n
	 */
	private String leadingZeros(int n) {

		String s;
		if (n < 10)
			s = "0";
		else
			s = "";
		s = s + n;
		return s;
	}
	
	/**
	 * Clear the image cache of the given category
	 * 
	 * @param category
	 *            Image category to clear
	 */
	public void flushImagePool(int category) {
		
		imageCache[category].clear();
		mirrorImageCache[category].clear();		
	}

	public void flushAnimationPool() {
		
		animationCache.clear();
	}

}
