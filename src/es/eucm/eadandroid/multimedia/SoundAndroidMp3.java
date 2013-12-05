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

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;

public class SoundAndroidMp3 extends Sound {
	/*
	 * There are lot of options in android you can pause the reproduction and
	 * continue it afterwards, this options are not implemented but can be
	 * easily added
	 */

	private String path;
	private MediaPlayer mMediaPlayer;

	public SoundAndroidMp3(String filename, boolean loop) {
		super(loop);
		path = filename;
		mMediaPlayer = null;

	}

	@Override
	public void playOnce() {
		// TODO if the path is not correct I should disable the sound and send
		// an error
		
		if (path == null || path.equals("") || !path.endsWith(".mp3")) return;
		
		try {

			if (mMediaPlayer == null) {
				mMediaPlayer = new MediaPlayer();
				Log.d("EL PATH ES XXXXXXXXX        ", path);
				String media = ResourceHandler.getInstance().getMediaPath(path);
				mMediaPlayer.setDataSource(media);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
			} else mMediaPlayer.start();

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void stopPlaying() {
		 stop = true;
	}

	@Override
	public synchronized void finalize() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

	}

}
