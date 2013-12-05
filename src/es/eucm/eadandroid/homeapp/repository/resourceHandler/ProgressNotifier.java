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
package es.eucm.eadandroid.homeapp.repository.resourceHandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Shows the progress of several actions through the application, such as downloading games,
 * to the user    
 * 
 * @author Roberto Tornero
 */
public class ProgressNotifier {

	/**
	 * Current progress percentage of the action
	 */
	private int progress;

	/**
	 * Messages for the notifier state
	 * 
	 * @author Roberto Tornero
	 */
	public class ProgressMessage {

		public static final int PROGRESS_ERROR = -1;
		public static final int PROGRESS_PERCENTAGE = 0;
		public static final int PROGRESS_UPDATE_FINISHED = 1;
		public static final int INDETERMINATE = 2;
		public static final int GAME_INSTALLED = 3;

	}

	/**
	 * A handler queue to send progress messages to
	 */
	protected Handler handler;

	/**
	 * Constructor
	 */
	public ProgressNotifier(Handler handler) {
		super();
		this.handler = handler;
		this.progress = 0;
	}

	/**
	 * Notifies the current progress
	 */
	public void notifyProgress(int nprogress, String currentOpMsg) {

		if (nprogress - this.progress >= 1) {

			this.progress = nprogress;
			removeHandlerMessages();
			Message msg = handler.obtainMessage();
			msg.what = ProgressMessage.PROGRESS_PERCENTAGE;
			Bundle b = new Bundle();
			b.putString("msg", currentOpMsg);
			b.putInt("ptg", nprogress);
			msg.setData(b);

			handler.sendMessage(msg);
		}

	}

	/**
	 * Notifies the finishing of the update action
	 */
	public void notifyUpdateFinished(String progressFinishedMsg) {

		removeHandlerMessages();
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.PROGRESS_UPDATE_FINISHED;
		Bundle b = new Bundle();
		b.putString("msg", progressFinishedMsg);

		msg.setData(b);

		handler.sendMessage(msg);

	}

	/**
	 * Notifies that an error has been produced
	 */
	public void notifyError(String progressErrorMsg) {

		removeHandlerMessages();
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.PROGRESS_ERROR;
		Bundle b = new Bundle();
		b.putString("msg", progressErrorMsg);

		msg.setData(b);

		handler.sendMessage(msg);

	}

	/**
	 * Notifies the indeterminate state of the progress
	 */
	public void notifyIndeterminate(String string) {

		removeHandlerMessages();
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.INDETERMINATE;
		Bundle b = new Bundle();
		b.putString("msg", string);

		msg.setData(b);

		handler.sendMessage(msg);

	}

	/**
	 * Notifies the installation of a game
	 */
	public void notifityGameInstalled(){

		removeHandlerMessages();
		Message msg = handler.obtainMessage();
		msg.what = ProgressMessage.GAME_INSTALLED;
		Bundle b = new Bundle();

		msg.setData(b);

		handler.sendMessage(msg);

	}

	/**
	 * Removes all the messages in the handler`s queue
	 */
	private void removeHandlerMessages() {


		handler.removeMessages(ProgressMessage.PROGRESS_PERCENTAGE);
		handler.removeMessages(ProgressMessage.PROGRESS_ERROR);
		handler.removeMessages(ProgressMessage.PROGRESS_UPDATE_FINISHED);
		handler.removeMessages(ProgressMessage.GAME_INSTALLED);
		handler.removeMessages(ProgressMessage.INDETERMINATE);

	}

}
