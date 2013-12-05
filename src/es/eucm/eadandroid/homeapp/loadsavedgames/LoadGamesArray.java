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
package es.eucm.eadandroid.homeapp.loadsavedgames;

import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * A class to store the information of the saved games
 * 
 * @author Roberto Tornero 
 */
public class LoadGamesArray {	

	/**
	 * The list of information of the saved games 
	 */
	private ArrayList<InfoLoadGames> savedGames;

	/**
	 * Constructor
	 */
	public LoadGamesArray(){

		savedGames = new ArrayList<InfoLoadGames>();
	}

	/**
	 * Return the list of saved games information
	 */
	public ArrayList<InfoLoadGames> getSavedGames() {
		return savedGames;
	}

	/**
	 * Set the list of saved games
	 */
	public void setSavedGames(ArrayList<InfoLoadGames> savedGames) {
		this.savedGames = savedGames;
	}

	/**
	 * Add a new saved game to the list from its information
	 */
	public void addGame(String game, String saved, Bitmap bmp){

		InfoLoadGames info = new InfoLoadGames();
		info.setGame(game);
		info.setSaved(saved);
		info.setScreenShot(bmp);
		savedGames.add(info);
	}

	/**
	 * Each saved game information consists of the name of the save file, the name of the game, and
	 * a screen shot of the moment it was saved. 
	 * 
	 * @author Roberto Tornero
	 */
	public class InfoLoadGames {

		/**
		 * The name of the ead game
		 */
		private String game;
		/**
		 * The name of the save file
		 */
		private String saved;
		/**
		 * The screen capture when the game was saved
		 */
		private Bitmap screenShot;

		/**
		 * Returns the name of the game
		 */
		public String getGame() {
			return game;
		}

		/**
		 * Sets the name of the game
		 */
		public void setGame(String game) {
			this.game = game;
		}

		/**
		 * Returns the name of the save file
		 */
		public String getSaved() {
			return saved;
		}

		/**
		 * Sets the name of the save file
		 */
		public void setSaved(String saved) {
			this.saved = saved;
		}

		/**
		 * Returns the screen capture
		 */
		public Bitmap getScreenShot() {
			return screenShot;
		}

		/**
		 * Sets the screen capture
		 */
		public void setScreenShot(Bitmap screenShot) {
			this.screenShot = screenShot;
		}


	}
}
