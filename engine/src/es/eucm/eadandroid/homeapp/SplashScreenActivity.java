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
package es.eucm.eadandroid.homeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.apkinstalling.EngineResInstaller;
import es.eucm.eadandroid.homeapp.repository.resourceHandler.RepoResourceHandler;
import es.eucm.eadandroid.res.pathdirectory.Paths;

/**
 * Opening activity for the application. It shows the eAdventure Mobile logo for two seconds.
 * 
 * @author Roberto Tornero
 */
public class SplashScreenActivity extends Activity{

	/**
	 * A runnable to execute after two seconds of showing the main logo.
	 */
	private Runnable endSplash;
	/**
	 * This value allows us not to initialize {@link endSplash} if it is installing.
	 */
	private boolean installing = false;
	/**
	 * The data included with the Intent that started this application.
	 */
	private Uri data;

	/**
	 * Class that defines the information messages for the {@link ActivityHandler}
	 * 
	 * @author Roberto Tornero
	 */
	public class ActivityHandlerInstalling {

		public static final int FINISHINSTALLING = 0;

	}

	/**
	 * A dialog to show the installation progress to the user.
	 */
	private ProgressDialog dialog;
	/**
	 * A Handler for controlling when the application has ended installing
	 */
	public Handler ActivityHandler = new Handler() {
		@Override
		/**    
		 * Called when a message is sent to Engines Handler Queue 
		 */
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case ActivityHandlerInstalling.FINISHINSTALLING:
				dialog.setIndeterminate(false);
				dialog.dismiss();
				break;
			}

		}

	};

	/**
	 * Shows the eAdventure logo screen and checks if an installation is needed 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//DEBUG
		Log.e("Inicio aplicacion",String.valueOf(Debug.getNativeHeapAllocatedSize()));

		setContentView(R.layout.splash_screen);

		EngineResInstaller is = new EngineResInstaller(this, ActivityHandler);
		if (is.isInstallOrUpdateNeeded()) {
			installing = true;
			is.start();

			dialog = new ProgressDialog(this);
			dialog.setTitle(getDialogTitle());
			dialog.setIcon(R.drawable.dialog_icon);
			dialog.setMessage(getString(R.string.splash_message));
			dialog.setIndeterminate(true);
			dialog.show();

		}

		if (this.getIntent().getData() != null){
			data = this.getIntent().getData();
		}

		if (!installing){
			endSplash = new Runnable() {
				public void run() {
					startEngineHomeActivity();
				}
			};

			ActivityHandler.postDelayed(endSplash, 2500);
		}
	}		

	/**
	 * Register the touching events on the screen to end the showing of the logo screen  
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		startEngineHomeActivity();
		ActivityHandler.removeCallbacks(endSplash);
		return true;
	}

	/**
	 * Method that finishes {@link SplashScreenActivity} and starts {@link HomeActivity}
	 */
	public void startEngineHomeActivity() {

		Intent i = new Intent(this, HomeActivity.class);
		if (data != null)
			i.setData(data);			
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.hold);
		this.finish();

	}

	protected String getDialogTitle(){
		return getString(R.string.splash_title);
	}
}
