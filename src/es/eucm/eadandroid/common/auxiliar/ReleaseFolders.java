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
package es.eucm.eadandroid.common.auxiliar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

import es.eucm.eadandroid.res.pathdirectory.Paths;

/**
 * The only purpose of this class is to keep the path of the folders and files
 * which will be in the release in a common place for both engine and editor
 * 
 * @author Javier
 * 
 */
public class ReleaseFolders {

    private static String PROJECTS_FOLDER = "../Projects";

    private static String EXPORTS_FOLDER = "../Exports";

    private static String REPORTS_FOLDER = Paths.eaddirectory.REPORTS_PATH;

    private static final String WEB_FOLDER = Paths.eaddirectory.ROOT_PATH + "web";

    private static final String WEB_TEMP_FOLDER = Paths.eaddirectory.ROOT_PATH + "web/temp";

    private static final String CONFIG_FILE_PATH_EDITOR = Paths.eaddirectory.ROOT_PATH + "config_editor.xml";

    private static final String CONFIG_FILE_PATH_ENGINE = Paths.eaddirectory.ROOT_PATH + "config_engine.xml";

    public static final String LANGUAGE_DIR_EDITOR = Paths.eaddirectory.ROOT_PATH + "i18n/editor";

    public static final String LANGUAGE_DIR_ENGINE = Paths.eaddirectory.ROOT_PATH + "i18n/engine";

    private static final String ENGLISH_LOADING_IMAGE = Paths.eaddirectory.ROOT_PATH + "img/Editor2D-Loading-Eng.png";

    private static final String SPANISH_LOADING_IMAGE = Paths.eaddirectory.ROOT_PATH + "img/Editor2D-Loading-Esp.png";
    
    private static HashMap<String, String> languageNames = new HashMap<String, String>();

    /**
     * Language constant for Unknown language
     */
    public static final String LANGUAGE_UNKNOWN = "es_ES";

    /**
     * Language constant for Spanish language
     */
    public static final String LANGUAGE_SPANISH = "es_ES";

    /**
     * Language constant for English language
     */
    public static final String LANGUAGE_ENGLISH = "en_EN";

    /**
     * Language constant for Default language
     */
    public static final String LANGUAGE_DEFAULT = LANGUAGE_ENGLISH;

    public static final es.eucm.eadandroid.common.auxiliar.File projectsFolder( ) {

        return new es.eucm.eadandroid.common.auxiliar.File( PROJECTS_FOLDER );
    }

    public static final es.eucm.eadandroid.common.auxiliar.File exportsFolder( ) {

        return new es.eucm.eadandroid.common.auxiliar.File( EXPORTS_FOLDER );
    }

    public static final es.eucm.eadandroid.common.auxiliar.File reportsFolder( ) {

        return new es.eucm.eadandroid.common.auxiliar.File( REPORTS_FOLDER );
    }

    public static final File webFolder( ) {

        return new File( WEB_FOLDER );
    }

    public static final es.eucm.eadandroid.common.auxiliar.File webTempFolder( ) {

        return new es.eucm.eadandroid.common.auxiliar.File( WEB_TEMP_FOLDER );
    }

    public static final File[] forbiddenFolders( ) {

        return new File[] { webFolder( ), webTempFolder( ) };
    }

    public static final String configFileEditorRelativePath( ) {

        return CONFIG_FILE_PATH_EDITOR;
    }

    public static final String configFileEngineRelativePath( ) {

        return CONFIG_FILE_PATH_ENGINE;
    }

    /**
     * Returns the relative path of a language file for both editor and engine
     * NOTE: To be used only from editor
     */
    public static String getLanguageFilePath4Editor( boolean editor, String language ) {
        String path = LANGUAGE_DIR_EDITOR + "/";
        if( editor )
            path += language + ".xml";
        else {
            path = LANGUAGE_DIR_ENGINE + File.separator;
            path += language + ".xml";
        }
        return path;
    }

    /**
     * Returns the relative path of a language file NOTE: To be used only from
     * engine
     */
    public static String getLanguageFilePath4Engine( String language ) {

        String path = LANGUAGE_DIR_ENGINE + "/";
        path += language + ".xml";
        return path;
    }

    /**
     * Returns the language ({@link #LANGUAGE_ENGLISH} or
     * {@value #LANGUAGE_SPANISH}) associated to the relative path passed as
     * argument. If no language is recognized, or if path is null, the method
     * returns {@value #LANGUAGE_DEFAULT}
     * 
     * @param path
     * @return
     */
    public static String getLanguageFromPath( String path ) {
        if( path != null && path.endsWith( ".xml" ) ) {
            return path.substring( path.length( ) - 9, path.length() - 4 );
        }
        else
            return LANGUAGE_DEFAULT;

    }

    public static final String getAboutFilePath( String string ) {
        return "about-" + string + ".html";
    }

    public static final String getLoadingImagePath( String language ) {

        if( language.equals(LANGUAGE_ENGLISH) ) {
            return ENGLISH_LOADING_IMAGE;
        }
        else if( language.equals(LANGUAGE_SPANISH) ) {
            return SPANISH_LOADING_IMAGE;
        }
        else {
            return getLoadingImagePath( LANGUAGE_DEFAULT );
        }
    }

    public static final String getLanguageFilePath( String language ) {
        return language + ".xml";
    }

    /**
     * @param projects_folder
     *            the pROJECTS_FOLDER to set
     */
    public static void setProjectsPath( String projects_folder ) {

        PROJECTS_FOLDER = projects_folder;
    }

    /**
     * @param exports_folder
     *            the eXPORTS_FOLDER to set
     */
    public static void setExportsPath( String exports_folder ) {

        EXPORTS_FOLDER = exports_folder;
    }

    /**
     * @param reports_folder
     *            the rEPORTS_FOLDER to set
     */
    public static void setReportsPath( String reports_folder ) {

        REPORTS_FOLDER = reports_folder;
    }

    public static List<String> getLanguages(String where ) {
        File directory = new File("i18n" + File.separator + where); 
        List<String> languages = new ArrayList<String>();
        for (File file : directory.listFiles()) {
            if (file.getName().endsWith("xml")) {
                String identifier = file.getName().substring(0, file.getName().length() - 4);
                languages.add( identifier );
                Properties prop = new Properties();
                try {
                    prop.loadFromXML(new FileInputStream(file));
                    languageNames.put( identifier,(String )prop.get( "Language.Name" ));
                } catch (InvalidPropertiesFormatException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return languages;
    }
    
    public static String getLanguageName(String language) {
        return languageNames.get( language );
    }
}
