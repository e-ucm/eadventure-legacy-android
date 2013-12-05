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
package es.eucm.eadandroid.res.pathdirectory;

import android.os.Environment;

public final class Paths {

	public static final class repository {
		
		public static final String DEFAULT_PATH = "http://eadventure-android.googlecode.com/files/";
		public static final String SOURCE_XML = "gamesrepository.xml";

	}

	public static final class eaddirectory {
		
		public static final String ROOT_PATH = Paths.device.EXTERNAL_STORAGE + "EadAndroid/";
		public static final String GAMES_PATH = ROOT_PATH + "games/" ;
		public static final String REPORTS_PATH = ROOT_PATH + "reports/" ;
		public static final String SAVED_GAMES_PATH = ROOT_PATH + "saved_games/" ;
		public static final String PREFERENCES = "preferences/";

	}

	public static final class device {

		public static final String EXTERNAL_STORAGE = Environment
		.getExternalStorageDirectory().toString() + "/";
	}
	
	public static final class contact {
		
		public static final String DEFAULT_EMAIL = "android@e-ucm.es";
		public static final String DEFAULT_SUBJECT = "eAdventure Mobile contact";

	}

}
