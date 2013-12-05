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

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import es.eucm.eadandroid.common.data.chapter.QrcodeRule;
import es.eucm.eadandroid.ecore.ECoreActivity.ActivityHandlerMessages;
import es.eucm.eadandroid.ecore.GameThread;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.ecore.control.gamestate.GameStatePlaying;

public class QrcodeManager {

	
	private static QrcodeManager singleton = null;
	//private Vector<QrcodeRule> qrcodeActive;
	private Vector<QrcodeRule> allqrrules;


	public static QrcodeManager getInstance() {

		return singleton;
	}

	private QrcodeManager()  {
		//qrcodeActive = new Vector<QrcodeRule>();
		allqrrules = new Vector<QrcodeRule>();
	
	}

	public static void create() {
		singleton = new QrcodeManager();
	}
	
	public void addAllQRRules(List<QrcodeRule> list)
	{
		this.allqrrules.removeAllElements();
		
		for(int i=0;i<list.size();i++)
		{
			this.allqrrules.add(list.get(i));
		}
		
		
		//loads global qrrules
		/*for(int i=0;i<list.size();i++)
		{
			if (list.get(i).getSceneName().equals(""))
				this.qrcodeActive.add(list.get(i));
		}*/
		

	}

	public void addqrrules(QrcodeRule rule) {
		allqrrules.add(rule);
	}
	
/*	public void changeOfScene(String scene)
	{
	//first removes from gpsActive all qr related to other scenes
		for (int i=this.qrcodeActive.size()-1;i>=0;i=i-1)
		{
			if(!this.qrcodeActive.elementAt(i).getSceneName().equals(""))
				qrcodeActive.remove(i);
		}
//then adds gpsrules from new scene		
		for (int i=0;i<this.allqrrules.size();i++)
		{
			if (this.allqrrules.elementAt(i).getSceneName().equals(scene))
				qrcodeActive.add(allqrrules.elementAt(i));
		}
		
	}*/
	


	public void updateQRcode(String password) {
		for (int i = 0; i < allqrrules.size(); i++) {

			if (new FunctionalConditions(allqrrules.elementAt(i).getInitCond()).allConditionsOk())
			{
				if (allqrrules.elementAt(i).getCode().equals(password)) {
					
					Handler handler = GameThread.getInstance().getHandler();
					String text=new String("QRCode found ");
					Message msg = handler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("toast", text);
					msg.what = ActivityHandlerMessages.SHOW_TOAST;
					msg.setData(b);

					msg.sendToTarget();
					
					FunctionalEffects.storeAllEffects(allqrrules.elementAt(i)
							.getEffects());
					this.allqrrules.remove(i);

				}
			}

		}

	}
	
	public boolean isGameStatePlaying()
	{
		boolean isGamStatePlaying=false;
		if (Game.getInstance().getCurrentState() instanceof GameStatePlaying)
		{
			isGamStatePlaying=true;
		}
		
		return isGamStatePlaying;
	}

}
