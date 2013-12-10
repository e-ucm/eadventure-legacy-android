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
package es.eucm.eadandroid.ecore;

import java.util.List;

import es.eucm.eadandroid.R;
import es.eucm.eadandroid.common.data.adventure.DescriptorData;
import es.eucm.eadandroid.common.loader.Loader;
import es.eucm.eadandroid.homeapp.WorkspaceActivity;
import es.eucm.eadandroid.res.pathdirectory.Paths;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ECoreControl extends Activity{
	
	private boolean gpsGame=false;
	private boolean qrCodeGame=false;
	protected String adventureName;
	protected boolean loadingfromsavedgame=false;
	public static final int REQUEST_QRCODE=0;
	public static final int REQUEST_GPS=1;
	
	//State=0 if checking barcode
	//State=1 if checking Gps
	LocationManager locManager;
	
	
	public class ActivityHandlerMessages {

		public static final int INSTALLED_BARCODE = 0;
		public static final int GPS_ENABLE = 1;
		
	}
	
	/**
	 * activity Handler
	 */
	public Handler ActivityHandler = new Handler() {
		@Override
		/**    * Called when a message is sent to Engines Handler Queue **/
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case ActivityHandlerMessages.INSTALLED_BARCODE: {
				if (qrCodeGame)
					chekingGps();
			else  changeActivity();
				

				break;
			}
			case ActivityHandlerMessages.GPS_ENABLE:
				changeActivity();
				break;

			}

		}

	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adventureName = (String) this.getIntent().getExtras().get(
		"AdventureName");
		String advPath = Paths.eaddirectory.GAMES_PATH + adventureName
		+ "/";
		
		loadingfromsavedgame = this.getIntent().getExtras().getBoolean(
		"savedgame");

		ResourceHandler.getInstance().setGamePath(advPath);
		DescriptorData gameDescriptor = Loader
				.loadDescriptorData(ResourceHandler.getInstance());
		
		gpsGame = gameDescriptor.isGpsMode();
		qrCodeGame = gameDescriptor.isQrCodeMode();
		
		if (!gpsGame && !qrCodeGame)
				changeActivity();
			
		else if (qrCodeGame)
			chekingBarCode();
		else chekingGps();
		
	}




	private void chekingBarCode() {
		final boolean scanAvailable = isIntentAvailable(this,
        "com.google.zxing.client.android.SCAN");
		
		if (!scanAvailable)
		{
			showQRCodeDialog();
		}else if (gpsGame)
					chekingGps();
			else  changeActivity();
	}




	private void chekingGps() {
		locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){  
			   showGPSDialog(); 
		}else changeActivity();
		
	}




	protected void changeActivity() {
		Intent i = new Intent(this, ECoreActivity.class);
		i.putExtra("AdventureName", adventureName);
		if (loadingfromsavedgame)
			i.putExtra("savedgame", true);
		this.startActivity(i);
		this.finish();
		
	}
	
	private void showQRCodeDialog() {
		new AlertDialog.Builder(this)
				.setTitle(getString(R.string.qr_game))
				.setIcon(R.drawable.dialog_icon)
				.setMessage(getString(R.string.qr_message_app))
				.setPositiveButton(getString(R.string.install_app),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
				
								Intent i = new Intent(
										Intent.ACTION_VIEW,
										Uri
												.parse("market://search?q=pname:com.google.zxing.client.android"));
														
								startActivityForResult(i, REQUEST_QRCODE);	
								
							}
						}).setNeutralButton(getString(R.string.option_quit),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								quitGame();
							}

						}).show();

	}
	
	
	private void showGPSDialog(){  
		AlertDialog.Builder builder = new AlertDialog.Builder(this);  
		builder.setMessage(getString(R.string.gps_message))  
		     .setCancelable(false) 
		     .setTitle(getString(R.string.gps_game))
		     .setIcon(R.drawable.dialog_icon)
		     .setPositiveButton(getString(R.string.gps_enabling),  
		          new DialogInterface.OnClickListener(){  
		          public void onClick(DialogInterface dialog, int id){  
		        	  Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		      		startActivityForResult(intent, REQUEST_GPS);  
		          }  
		     });  
		     builder.setNegativeButton(getString(R.string.option_quit),  
		          new DialogInterface.OnClickListener(){  
		          public void onClick(DialogInterface dialog, int id){  
		               dialog.cancel(); 
		               quitGame();
		          }  
		     });  
		AlertDialog alert = builder.create();  
		alert.show();  
		}  
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_GPS) {

			if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				showGPSDialog();
			} else {

				Message msg = ActivityHandler.obtainMessage();
				Bundle b = new Bundle();
				msg.what = ActivityHandlerMessages.GPS_ENABLE;
				msg.setData(b);
				msg.sendToTarget();
			}

		} else {

			final boolean scanAvailable = isIntentAvailable(this,
					"com.google.zxing.client.android.SCAN");

			if (!scanAvailable) {

				showQRCodeDialog();
			} else {

				Message msg = ActivityHandler.obtainMessage();
				Bundle b = new Bundle();
				msg.what = ActivityHandlerMessages.INSTALLED_BARCODE;
				msg.setData(b);
				msg.sendToTarget();
			}
		}

	}
	
	public static boolean isIntentAvailable(Context context, String action) {
	    final PackageManager packageManager = context.getPackageManager();
	    final Intent intent = new Intent(action);
	    List<ResolveInfo> list =
	            packageManager.queryIntentActivities(intent,
	                    PackageManager.MATCH_DEFAULT_ONLY);
	    return list.size() > 0;
	}
	
	public void quitGame(){
		
		Intent i = new Intent(this, WorkspaceActivity.class);
		i.putExtra("Tab", 0);
		i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.hold);
		this.finish();
	}

}
