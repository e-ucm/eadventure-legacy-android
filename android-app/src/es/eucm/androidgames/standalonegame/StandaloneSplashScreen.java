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
package es.eucm.androidgames.standalonegame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import es.eucm.eadandroid.R;
import es.eucm.eadandroid.homeapp.SplashScreenActivity;

/**
 * Opening activity for the application. It shows the eAdventure Mobile logo for two seconds.
 * 
 * @author Roberto Tornero
 */
public class StandaloneSplashScreen extends SplashScreenActivity{

	/**
	 * Method that finishes {@link SplashScreenActivity} and starts {@link HomeActivity}
	 */
	public void startEngineHomeActivity() {
		String gameToLaunch=this.getGameToLaunch();
		if (!gameToLaunch.toLowerCase().equals("none")){
			
			Intent i = new Intent(this, SCoreActivity.class);
			i.putExtra("AdventureName", gameToLaunch);
			this.startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.hold);
			this.finish();
			
			
			
			/*Intent i = new Intent(this, SCoreControl.class);
			i.putExtra("AdventureName", gameToLaunch);
			startActivity(i);
			overridePendingTransition(R.anim.fade_in, R.anim.hold);
			this.finish();*/
		} else{
			super.startEngineHomeActivity();
		}
	}
	
	protected String getDialogTitle(){
		if (!getGameToLaunch().equals("none"))
			return getString(R.string.standalone_splash_title)+" "+getGameToLaunch();
		else
			return super.getDialogTitle();
	}
	
	protected String getGameToLaunch(){
		String gameToLaunch="none";
		try {
			ActivityInfo appInfo=this.getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
			System.out.println("PROPERTY="+appInfo.metaData.get("es.eucm.eadandroid.launchgame"));
			gameToLaunch = appInfo.metaData.getString("es.eucm.eadandroid.launchgame");
		} catch (Exception e) {
			//Do nothing
		}
		return gameToLaunch;
	}
}
