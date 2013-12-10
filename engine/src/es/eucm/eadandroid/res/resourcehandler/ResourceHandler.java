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
package es.eucm.eadandroid.res.resourcehandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import es.eucm.eadandroid.common.loader.InputStreamCreator;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.res.pathdirectory.Paths;
import es.eucm.eadandroid.res.resourcehandler.zipurl.ZipURL;

public class ResourceHandler implements InputStreamCreator {

	/**
	 * Path of the default background image
	 */
	public static final String DEFAULT_BACKGROUND = Paths.eaddirectory.ROOT_PATH + "gui/defaultassets/NRB_background.jpg";

	/**
	 * Path of the default slides fileset
	 */
	public static final String DEFAULT_SLIDES = Paths.eaddirectory.ROOT_PATH + "gui/defaultassets/NRB_slides_01.jpg";

	/**
	 * Path of the default animation fileset
	 */
	public static final String DEFAULT_ANIMATION = Paths.eaddirectory.ROOT_PATH + "gui/defaultassets/NRB_animation_01.png";

	/**
	 * Path of the default image
	 */
	public static final String DEFAULT_IMAGE = Paths.eaddirectory.ROOT_PATH + "gui/defaultassets/NRB_image.png";

	/**
	 * Path of the default icon image
	 */
	public static final String DEFAULT_ICON = Paths.eaddirectory.ROOT_PATH + "gui/defaultassets/NRB_icon.png";

	/**
	 * Path of the default foreground image
	 */
	public static final String DEFAULT_FOREGROUND = Paths.eaddirectory.ROOT_PATH + "gui/defaultassets/NRB_foreground.png";

	/**
	 * Path of the default hardmap image
	 */
	public static final String DEFAULT_HARDMAP = Paths.eaddirectory.ROOT_PATH + "gui/defaultassets/NRB_hardmap.png";

	/**
	 * Stores the zip file containing the needed files for the game
	 */
//	protected static ZipFile zipFile = null;

	/**
	 * Stores the zip file containing the needed files for the game
	 */
	public static String gamePath = null;

	private static ResourceHandler INSTANCE = null;

	private ResourceHandler() {
	}

	private synchronized static void createInstance() {

		if (INSTANCE == null) {
			INSTANCE = new ResourceHandler();
		}

	}
	

	/**
	 * Returns the instance of the resource handler
	 * 
	 * @return Instance of the resource handler
	 */
	public synchronized static ResourceHandler getInstance() {

		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	/**
	 * Deletes the resource handler.
	 */
	public static void delete() {

		if (INSTANCE != null && INSTANCE.tempFiles != null) {
			for (TempFile file : INSTANCE.tempFiles) {
				file.delete();
			}
		}
		INSTANCE = null;
	}

	/**
	 * Returns the extension of the given asset.
	 * 
	 * @param assetPath
	 *            Path to the asset
	 * @return Extension of the file
	 */
	public static String getExtension(String assetPath) {

		return assetPath.substring(assetPath.lastIndexOf('.') + 1, assetPath
				.length());
	}

	/**
	 * Sets the new zip file to load the game files
	 * 
	 * @param zipFilename
	 *            Filename of the zip
	 */
	public void setGamePath(String gamePath) {
		
			ResourceHandler.gamePath = gamePath;
	}
	
	public String getMediaPath(String a)
	{
		return ResourceHandler.gamePath+a;
	}
	

	/**
	 * Closes the open zip file in use.
	 */
	public void closeZipFile() {
/*
		try {
			if (zipFile != null)
				zipFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	*/
	}

	/**
	 * Returns an output stream specified
	 * 
	 * @param path
	 *            Name of the file
	 * @return The output stream if it could be loaded, null otherwise
	 */
	public OutputStream getOutputStream(String path) {

		OutputStream os = null;

		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		try {
			os = new FileOutputStream(path);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			os = null;
		}

		return os;

	}

	/**
	 * Loads a file as an input stream
	 * 
	 * @param path
	 *            Path of the file
	 * @return The file as an input stream
	 */
	public InputStream getResourceAsStream(String path) {

		InputStream is = null;

		try {
			is = new FileInputStream( path);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			is = null;
		}
		return is;

	}

	/**
	 * Loads a file as a Bitmap Image
	 * 
	 * @param path
	 *            Path of the file
	 * @return The file as a Bitmap Image
	 */

	public Bitmap getResourceAsImage(String path) {

		Bitmap image = null;

		if (!path.startsWith(Environment.getExternalStorageDirectory().toString()))
			path=gamePath+path;

		File f = new File(path);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inDither = false;
		options.inInputShareable = true;
		options.inTempStorage = new byte [32 * 1024];
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		
		try {
			image = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfMemoryError err){
			System.gc();
			
			try {
				image = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return image;
	}
	
	public Bitmap getInputStreamAsImage(InputStream is){
		
		Bitmap image = null;
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inDither = false;
		options.inInputShareable = true;
		options.inTempStorage = new byte [32 * 1024];
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		
		try {
			image = BitmapFactory.decodeStream(is, null, options);
		} catch (OutOfMemoryError err){
			System.gc();
			image = BitmapFactory.decodeStream(is, null, options);			
		}
		
		return image;
		
	}
	
	public URL buildURL(String path) {

		return getResourceAsURLFromZip(path);
	}

	public InputStream buildInputStream(String filePath) {
		return getResourceAsStream(gamePath + filePath);
	}

	public String[] listNames(String filePath) {
		File dir = new File(gamePath, filePath);
		return dir.list();
	}

	
	//////// NEW //////////////////////
	
	private static Random random = new Random();

	private static int MAX_RANDOM = 100000;

	protected ArrayList<TempFile> tempFiles = new ArrayList<TempFile>();

	private static final String TEMP_FILE_NAME = "$temp_ead_";
	
	
	public URL getResourceAsURLFromZip(String path) {

		try {
			return ZipURL.createAssetURL(gamePath, path);
		} catch (MalformedURLException e) {
			return null;
		}

	}

	public URL getResourceAsURL(String assetPath) {

		URL toReturn = null;
		try {
			InputStream is = this.getResourceAsStream(assetPath);
			String filePath = generateTempFileAbsolutePath(getExtension(assetPath));
			// File sourceFile = new File( zipPath, assetPath );
			File destinyFile = new File(filePath);
			if (writeFile(is, destinyFile)) {
				toReturn = destinyFile.toURI().toURL();
				TempFile tempFile = new TempFile(destinyFile.getAbsolutePath());
				tempFile.setOriginalAssetPath(assetPath);
				tempFiles.add(tempFile);
			} else
				toReturn = null;
		} catch (Exception e) {
			toReturn = null;
		}

		return toReturn;

	}

	public boolean writeFile(InputStream is, File dest) {
		try {
			// FileWriter out = new FileWriter(dest);
			FileOutputStream os = new FileOutputStream(dest);
			int c;
			byte[] buffer = new byte[512];
			while ((c = is.read(buffer)) != -1)
				os.write(buffer, 0, c);
			os.close();
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	public boolean copyFile(File source, File dest) {
		try {
			FileReader in = new FileReader(source);
			FileWriter out = new FileWriter(dest);
			int c;

			while ((c = in.read()) != -1)
				out.write(c);

			in.close();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	protected String generateTempFileAbsolutePath(String extension) {

		String tempDirectory = null;
		if (System.getenv("TEMP") != null && !System.getenv("TEMP").equals("")) {
			tempDirectory = System.getenv("TEMP");
		} else if (System.getenv("HOME") != null
				&& !System.getenv("HOME").equals("")) {
			tempDirectory = System.getenv("HOME");
		} else if (System.getenv("ROOT") != null
				&& !System.getenv("ROOT").equals("")) {
			tempDirectory = System.getenv("ROOT");
		} else {
			tempDirectory = "";
		}

		String fileName = TEMP_FILE_NAME + random.nextInt(MAX_RANDOM) + "."
				+ extension;
		File file = new File(tempDirectory + java.io.File.separatorChar
				+ fileName);
		while (file.exists()) {
			fileName = TEMP_FILE_NAME + random.nextInt(MAX_RANDOM) + "."
					+ extension;
			file = new File(tempDirectory + java.io.File.separatorChar
					+ fileName);
		}
		return tempDirectory + java.io.File.separatorChar + fileName;

	}

	public class TempFile extends java.io.File {

		private String originalAssetPath;

		/**
		 * @return the originalAssetPath
		 */
		public String getOriginalAssetPath() {

			return originalAssetPath;
		}

		/**
		 * @param originalAssetPath
		 *            the originalAssetPath to set
		 */
		public void setOriginalAssetPath(String originalAssetPath) {

			this.originalAssetPath = originalAssetPath;
		}

		public TempFile(String pathname) {

			super(pathname);
		}

		/**
         * 
         */
		private static final long serialVersionUID = 896282044492374745L;

	}

	


}
