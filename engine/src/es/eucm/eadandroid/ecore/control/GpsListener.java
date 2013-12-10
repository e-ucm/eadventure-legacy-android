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
package es.eucm.eadandroid.ecore.control;

import es.eucm.eadandroid.ecore.GameThread;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GpsListener implements LocationListener {

	GpsManager manager;
	
	


	public GpsListener(GpsManager manager) {
		super();
		this.manager=manager;
		Log.d("GpsListener", " XXXXXXXXXXXXXXXXXXXXXXXXX");
	}

	public void onLocationChanged(Location location) {
		
		manager.updategps(location);
		Log.d("onLocationChanged", " XXXXXXXXXXXXXXXXXXXXXXXXX");
	}

	public void onProviderDisabled(String provider) {
		Log.d("onProviderDisabled", " XXXXXXXXXXXXXXXXXXXXXXXXX");
		
	}

	public void onProviderEnabled(String provider) {
		
		Log.d("onProviderEnabled", " XXXXXXXXXXXXXXXXXXXXXXXXX");
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		
		Log.d("onStatusChanged", " XXXXXXXXXXXXXXXXXXXXXXXXX  "+status);
		
		switch (status)
		{
		case LocationProvider.AVAILABLE:
			
			if (Game.getInstance()==null)
			{
				manager.setActiveGps(true);
			}else{
				//TODO tengo que cambiar a que continue
				
				Handler handler = GameThread.getInstance().getHandler();
				Message msg = handler.obtainMessage();

				Bundle b = new Bundle();
				b.putInt("dialog", 0);
				msg.what = ActivityHandlerMessages.FINISH_DIALOG;
				msg.setData(b);
				msg.sendToTarget();
				
				if(Game.getInstance().ispause())
				{
					Game.getInstance().unpause();
				}
			}
			
			break;
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			
			
			
			
			break;	
		case LocationProvider.OUT_OF_SERVICE:
			
			if (Game.getInstance()==null)
			{
				Game.getInstance().pause();
				Handler handler = GameThread.getInstance().getHandler();
				String text=new String("gps service is currently out of service");

				Message msg = handler.obtainMessage();

				Bundle b = new Bundle();
				b.putString("content", text);
				msg.what = ActivityHandlerMessages.SHOW_DIALOG;
				msg.setData(b);
				msg.sendToTarget();
			}
			break;
		}
		
		
		
	}
	
	public void finish(){
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	


	
}
