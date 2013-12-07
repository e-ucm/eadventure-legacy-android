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

import android.content.Context;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.control.ContextServices;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;


public class GameThread extends Thread {

	private String advPath;
	private String advName;
	Handler handler;
	boolean loadActivityGames=false;
	
	private Context context;

	private static GameThread instance = null;
	
	public static final String TAG ="GameThread";
	
	private GameThread(SurfaceHolder holder,Context context, Handler handler,String loadingGame, Display d) {
		
		this.handler =handler;
		this.context = context;
		Game.create(loadingGame);

		DisplayMetrics dm = new DisplayMetrics();
		
		int width = d.getWidth();
		int height = d.getHeight();
		
		if (width < height){
			height = d.getWidth();
			width = d.getHeight();
		}
		
		d.getMetrics(dm);
		
		float scaleDensity = dm.density;
		System.out.println("[****SCALE DENSITY****] "+scaleDensity);
				
		GUI.create(holder, context);
		ContextServices.create(context);
		GUI.getInstance().init(height, width, scaleDensity);						
	}



	public static void create(SurfaceHolder holder,Context context, Handler handler,String loadingGame, Display d) {
	          
		instance = new GameThread(holder,context,handler,loadingGame,d);	
	}
	
	@Override
	public void run() {
		
		Game.getInstance().setAdventurePath(advPath);
		Game.getInstance().setAdventureName(advName);
		Game.getInstance().setPrefs(PreferenceManager.getDefaultSharedPreferences(context));
		ResourceHandler.getInstance().setGamePath(Game.getInstance().getAdventurePath());

		Game.getInstance().start();
		Game.delete();
		ResourceHandler.getInstance().closeZipFile();
		ResourceHandler.delete();
		
		finishThread();
		
	}
	
//last function it will be done from GameThread not activitythread
	private void finishThread() {
        
        Message msg = handler.obtainMessage();        
        
        Bundle b = new Bundle();

        if (this.loadActivityGames)
        	msg.what = ActivityHandlerMessages.LOAD_GAMES;	
        else msg.what = ActivityHandlerMessages.GAME_OVER;
		msg.setData(b);
		
		GameThread.instance = null;
		
		msg.sendToTarget();
		
	}

	public boolean processTouchEvent(MotionEvent e) {
		return Game.getInstance().processTouchEvent(e);
	}
	
	public boolean processTrackballEvent(MotionEvent e) {
		return Game.getInstance().processTrackballEvent(e);
	}
	
	public boolean processKeyEvent(KeyEvent e) {
		return Game.getInstance().processKeyEvent(e);
	}
		
	public boolean processSensorEvent(SensorEvent e) {
		return Game.getInstance().processSensorEvent(e);
	}


	public void setAdventurePath(String advPath) {
		this.advPath = advPath;
	}
	
	public String getAdventurePath() {
		return advPath;
	}
	
	public void setAdventureName(String advName){
		this.advName = advName;
	}

	public void pause() {
		if (Game.getInstance()!=null) {
		Game.getInstance().pause();
		}
	}
	
	public static GameThread getInstance() {
		return instance;
	}

	public void unpause(SurfaceHolder canvasHolder) {
		
		if (Game.getInstance()!=null) {
		Game.getInstance().unpause(canvasHolder);
		}
	}
	
	public void finish(boolean loadactivitygames) {
		
		this.loadActivityGames=loadactivitygames;
		
		if(Game.getInstance()!=null)
			Game.getInstance().finish();				
	}

	public void setSurfacevideo(SurfaceHolder videoholder) {
		GUI.getInstance().setCanvasSurfaceHolder(videoholder);		
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public Handler getHandler() {
		return handler;
	}

	public void resize(boolean onescaled) {
		// TODO Auto-generated method stub
		Game.getInstance().pause();
		GUI.getInstance().resize(onescaled);
		Game.getInstance().unpause();
		
	}	

}
