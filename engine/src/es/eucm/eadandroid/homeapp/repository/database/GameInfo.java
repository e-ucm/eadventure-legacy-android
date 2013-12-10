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
package es.eucm.eadandroid.homeapp.repository.database;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * Represents the information that each game has on the repository
 * 
 * @author Roberto Tornero
 */
public class GameInfo implements Comparable<GameInfo> , Serializable {

	/**
	 * Tag for the class GameInfo
	 */
	public static final String TAG = "GameInfoCache";
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stored information for each game on the repository
	 */
	private String gameTitle = "";
	private String gameDescription = "";
	private String eadUrl;
	private Bitmap imageIcon;
	private String gameFileName;

	/**
	 * Constructor
	 */
	public GameInfo(String ti, String desc, String eadURL, Bitmap imageIcon) {

		gameDescription = desc;
		gameTitle = ti;
		eadUrl = eadURL;
		this.imageIcon = imageIcon;

		int last = eadURL.lastIndexOf("/");
		gameFileName = eadURL.substring(last + 1);

	}

	/**
	 * Allows comparing between two instances of GameInfo
	 */
	public int compareTo(GameInfo other) {

		if (this.gameDescription != null)
			return this.gameDescription.compareTo(other.gameDescription);
		else
			throw new IllegalArgumentException();
	}

	/**
	 * Returns the title of the game
	 */
	public String getGameTitle() {
		return gameTitle;
	}

	/**
	 * Returns the description of the game
	 */
	public String getGameDescription() {
		return gameDescription;
	}

	/**
	 * Returns the url of the game
	 */
	public String getEadUrl() {
		return eadUrl;
	}

	/**
	 * Returns the name of the game file
	 */
	public String getFileName() {
		return gameFileName;
	}

	/**
	 * Returns the icon image of the game
	 */
	public Bitmap getImageIcon() {
		return imageIcon;
	}


}
