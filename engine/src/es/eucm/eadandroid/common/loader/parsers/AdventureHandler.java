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
package es.eucm.eadandroid.common.loader.parsers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadandroid.common.data.adaptation.AdaptationProfile;
import es.eucm.eadandroid.common.data.adventure.AdventureData;
import es.eucm.eadandroid.common.data.adventure.DescriptorData;
import es.eucm.eadandroid.common.data.assessment.AssessmentProfile;
import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.common.loader.InputStreamCreator;
import es.eucm.eadandroid.common.loader.Loader;
import es.eucm.eadandroid.common.loader.incidences.Incidence;

/**
 * This class is the handler to parse the e-Adventure descriptor file.
 * 
 * @author Bruno Torijano Bueno
 */
public class AdventureHandler extends DefaultHandler {

    
    
    	/**
    	 * Constant with the assessment folder path
    	 */
    	private static final String assessmentFolderPath = "assessment";
    
    	/**
    	 * Constant with the adaptation folder path
    	 */
    	private static final String adaptationFolderPath = "adaptation";
    
    	
	/**
	 * Constant for reading nothing.
	 */
	private static final int READING_NONE = 0;

	/**
	 * Constant for reading a chapter.
	 */
	private static final int READING_CHAPTER = 1;

	/**
	 * Stores the current element being read.
	 */
	private int reading = READING_NONE;

	/**
	 * Adventure data being read.
	 */
	private AdventureData adventureData;
	
	/**
	 * List of incidences
	 */
	private List<Incidence> incidences;

	/**
	 * List of chapters of the adventure.
	 */
	private List<Chapter> chapters;
	
	/**
	 * Assessment controller: to be filled with the assessment data
	 */ 
	//private List<AssessmentProfile> assessmentController;
	
	/**
	 * Adaptation controller: to be filled with the adaptation data
	 */
	//private List<AdaptationProfile> adaptationController;

	/**
	 * Chapter being currently read.
	 */
	private Chapter currentChapter;

	/**
	 * String to store the current string in the XML file
	 */
	protected StringBuffer currentString;
	
	private InputStreamCreator isCreator;
	
	/**
	 * The paths of assessments files
	 */
	private List<String> assessmentPaths;
	
	/**
	 * The paths of adaptation files
	 */
	private List<String> adaptationPaths;
	
	/**
	 * To validate or not the XML with DTD
	 * 
	 */
	private boolean validate;
	
	private static void getXMLFilePaths (InputStreamCreator isCreator,List<String> assessmentPaths, List<String> adaptationPaths){

		// Assessment
			for ( String child: isCreator.listNames(assessmentFolderPath)){
				if (child.toLowerCase().endsWith(".xml")){
					assessmentPaths.add( assessmentFolderPath+"/"+child );					
				}
			}
		
		// Adaptation
			
			for ( String child: isCreator.listNames(adaptationFolderPath)){
				if (child.toLowerCase().endsWith(".xml")){
					adaptationPaths.add( adaptationFolderPath+"/"+child );					
				}
			}
	}

	/**
	 * Constructor.
	 * 
	 * @param zipFile
	 *            Path to the zip file which helds the chapter files
	 */
	public AdventureHandler(  InputStreamCreator isCreator, List<Incidence> incidences, boolean validate ) {
		this.isCreator = isCreator;
		assessmentPaths = new ArrayList<String>();
		adaptationPaths = new ArrayList<String>();
		getXMLFilePaths(isCreator,assessmentPaths, adaptationPaths );
		
		adventureData = new AdventureData( );
		this.incidences = incidences;
		chapters = new ArrayList<Chapter>( );
		this.validate = validate;
		//this.assessmentController = adventureData.getAssessmentProfiles();
		//this.adaptationController = adventureData.getAdaptationProfiles();
		
		
	}
	
	/**
	 * Load the assessment and adaptation profiles from xml.
	 * 
	 */
	//This method must be called after all chapter data is parse, because is a past functionality, and must be preserved in order
	// to bring the possibility to load game of past versions. Now the adaptation and assessment profiles are into chapter.xml, and not 
	// in separate files.
	public void loadProfiles(){
	
	    //check if in chapter.xml there was any assessment or adaptation data
	    if (!adventureData.hasAdapOrAssesData()) {
	    
	    // Load all the assessment files in each chapter
		for (String assessmentPath : assessmentPaths){
		    boolean added = false;
		    AssessmentProfile assessProfile = Loader.loadAssessmentProfile ( isCreator, assessmentPath, incidences) ;
		    if (assessProfile!=null){	
		    for (Chapter chapter : adventureData.getChapters()){
			if (chapter.getAssessmentName().equals(assessProfile.getName())){
			    chapter.addAssessmentProfile(assessProfile);
			    added=true;
			}
			}
		    if (!added){
			for (Chapter chapter : adventureData.getChapters()){
				    chapter.addAssessmentProfile(assessProfile);
				}
		    }
		    
		    
		    }
		}
		
		// Load all the adaptation files in each chapter
		for (String adaptationPath: adaptationPaths){
		    boolean added=false;
		    AdaptationProfile adaptProfile= Loader.loadAdaptationProfile( isCreator, adaptationPath, incidences) ;
		    if (adaptProfile!=null){
			for (Chapter chapter : adventureData.getChapters()){
				if (chapter.getAdaptationName().equals(adaptProfile.getName())){
				    chapter.addAdaptationProfile(adaptProfile);
				    added=true;
				}
				}
			    if (!added){
				for (Chapter chapter : adventureData.getChapters()){
					    chapter.addAdaptationProfile(adaptProfile);
					}
			    }
		}
		}
	    }
		    
	}

	/**
	 * Returns the adventure data read
	 * 
	 * @return The adventure data from the XML descriptor
	 */
	public AdventureData getAdventureData( ) {
		return adventureData;
	}

	@Override
	public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

		if (sName.equals( "game-descriptor" )){
		    for( int i = 0; i < attrs.getLength( ); i++ )
			if( attrs.getLocalName( i ).equals( "versionNumber" ) ){
			    adventureData.setVersionNumber(attrs.getValue(i));
			}
		}
		
		if( qName.equals( "configuration" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {    
            	
            	if( attrs.getLocalName( i ).equals( "keepShowing" ) )
            		adventureData.setKeepShowing( attrs.getValue( i ).equals( "yes" ) );         

            }
        }
		    
	    
	    	// If reading a title, empty the current string
		if( sName.equals( "title" ) || sName.equals( "description" ) ) {
			currentString = new StringBuffer( );
		}

		if (sName.endsWith("automatic-commentaries")) {
			adventureData.setCommentaries(true);
		}
		
		// If reading the GUI tag, store the settings
		if( sName.equals( "gui" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ ) {
				if( attrs.getLocalName( i ).equals( "type" ) ) {
					if( attrs.getValue( i ).equals( "traditional" ) )
						adventureData.setGUIType( DescriptorData.GUI_TRADITIONAL );
					else if( attrs.getValue( "type" ).equals( "contextual" ) )
						adventureData.setGUIType( DescriptorData.GUI_CONTEXTUAL );
				}
				if (attrs.getLocalName( i ).equals( "customized" )) {
					if (attrs.getValue(i).equals("yes"))
						adventureData.setGUI(adventureData.getGUIType(), true);
					else
						adventureData.setGUI(adventureData.getGUIType(), false);
				}
				if (attrs.getLocalName( i ).equals( "inventoryPosition" )) {
					if (attrs.getValue(i).equals("none"))
						adventureData.setInventoryPosition(DescriptorData.INVENTORY_NONE);
					else if (attrs.getValue(i).equals("top_bottom"))
						adventureData.setInventoryPosition(DescriptorData.INVENTORY_TOP_BOTTOM);
					else if (attrs.getValue(i).equals("top"))
						adventureData.setInventoryPosition(DescriptorData.INVENTORY_TOP);
					else if (attrs.getValue(i).equals("bottom"))
						adventureData.setInventoryPosition(DescriptorData.INVENTORY_BOTTOM);
				}
			}
		}
		
	       //Cursor
        if (sName.equals( "cursor" )){
            String type="";String uri="";
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if (attrs.getLocalName( i ).equals( "type" )){
                    type=attrs.getValue( i );
                }else if (attrs.getLocalName( i ).equals( "uri" )){
                    uri=attrs.getValue( i );
                }
            }
            adventureData.addCursor( type, uri );
        }		

        //Button
        if (sName.equals( "button" )){
            String type="";String uri=""; String action ="";
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if (attrs.getLocalName( i ).equals( "type" )){
                    type=attrs.getValue( i );
                }else if (attrs.getLocalName( i ).equals( "uri" )){
                    uri=attrs.getValue( i );
                }else if (attrs.getLocalName( i ).equals( "action" )){
                    action=attrs.getValue( i );
                }
            }
            adventureData.addButton( action, type, uri );
        }
        
        if (sName.equals( "arrow" )) {
        	String type="";String uri="";
        	for (int i = 0; i < attrs.getLength(); i++) {
               if (attrs.getLocalName( i ).equals( "type" )){
                  type=attrs.getValue( i );
               }else if (attrs.getLocalName( i ).equals( "uri" )){
                  uri=attrs.getValue( i );
               }
        	}
        	adventureData.addArrow( type, uri);
        }
        
		// If reading the mode tag:
		if( sName.equals( "mode" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getLocalName( i ).equals( "playerTransparent" ) )
					if( attrs.getValue( i ).equals( "yes" ) )
						adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_1STPERSON );
					else if( attrs.getValue( i ).equals( "no" ) )
						adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_3RDPERSON );
		}
		
        if (sName.equals("graphics")){
        	for( int i = 0; i < attrs.getLength(); i++) {
        		if (attrs.getLocalName( i ).equals("mode")) {
        			if (attrs.getValue( i ).equals( "windowed")) {
        				adventureData.setGraphicConfig(DescriptorData.GRAPHICS_WINDOWED);
        			}
        			else if (attrs.getValue( i ).equals( "fullscreen" )) {
        				adventureData.setGraphicConfig(DescriptorData.GRAPHICS_FULLSCREEN);
        			}
        			else if (attrs.getValue( i ).equals( "blackbkg") ) {
        				adventureData.setGraphicConfig(DescriptorData.GRAPHICS_BLACKBKG);
        			}
        		}
        	}
        }


		// If reading the contents tag, switch to the chapters mode
		else if( sName.equals( "contents" ) ) {
			reading = READING_CHAPTER;
		}

		// If reading the contents of a chapter, create a new one to store the data
		else if( sName.equals( "chapter" ) ) {
			// Create the chapter
			currentChapter = new Chapter( );

			// Search and store the path of the file
			String chapterPath = null;
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getLocalName( i ).equals( "path" ) )
					chapterPath = attrs.getValue( i );
			
			if (chapterPath!=null){
				currentChapter.setChapterPath( chapterPath );
			} else 
				currentChapter.setChapterPath( "" );

			// Open the file and load the data
			try {
				// Set the chapter handler
				ChapterHandler chapterParser = new ChapterHandler( isCreator, currentChapter);

				// Create a new factory
				SAXParserFactory factory = SAXParserFactory.newInstance( );
				//factory.setValidating( validate );
				factory.setValidating( false );
				SAXParser saxParser = factory.newSAXParser( );

				// Set the input stream with the file
				InputStream chapterIS = isCreator.buildInputStream( chapterPath );

				// Parse the data and close the data
				saxParser.parse( chapterIS, chapterParser );
				chapterIS.close( );

			} catch( ParserConfigurationException e ) {
				incidences.add( Incidence.createChapterIncidence( TC.get( "Error.LoadData.SAX" ), chapterPath , e) );
			} catch( SAXException e ) {
			    	incidences.add( Incidence.createChapterIncidence( TC.get( "Error.LoadData.SAX" ), chapterPath , e) );
			} catch( IOException e ) {
				incidences.add( Incidence.createChapterIncidence( TC.get( "Error.LoadData.IO" ), chapterPath, e) );
			}

		}
		// If reading the adaptation configuration, store it
		// With last profile modifications, only old games includes that information in its descriptor file.
        	// For that reason, the next "path" info is the name of the profile, and it is necessary to eliminate the path's characteristic
        	// such as / and .xml
		else if( sName.equals( "adaptation-configuration" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getLocalName( i ).equals( "path" ) ){
					String adaptationName = attrs.getValue( i );
					// delete the path's characteristics
					adaptationName = adaptationName.substring(adaptationName.indexOf("/")+1);
					adaptationName = adaptationName.substring(0,adaptationName.indexOf("."));
					currentChapter.setAdaptationName( adaptationName );
					// Search in incidences. If an adaptation incidence was related to this profile, the error is more relevant
					for (int j=0; j<incidences.size( ); j++){
						Incidence current = incidences.get( j );
						if (current.getAffectedArea( ) == Incidence.ADAPTATION_INCIDENCE && current.getAffectedResource( ).equals( adaptationName )){
							String message = current.getMessage( );
							incidences.remove( j );
							incidences.add( j, Incidence.createAdaptationIncidence( true, message+TC.get( "Error.LoadAdaptation.Referenced" ), adaptationName , null) );
						}
					}
				}
		}
		// If reading the assessment configuration, store it
        	// With last profile modifications, only old games includes that information in its descriptor file.
		// For that reason, the next "path" info is the name of the profile, and it is necessary to eliminate the path's characteristic
		// such as / and .xml
		else if( sName.equals( "assessment-configuration" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getLocalName( i ).equals( "path" ) ){
					String assessmentName = attrs.getValue( i );
					// delete the path's characteristics
					assessmentName = assessmentName.substring(assessmentName.indexOf("/")+1);
					assessmentName = assessmentName.substring(0,assessmentName.indexOf("."));
					currentChapter.setAssessmentName( assessmentName );
					// Search in incidences. If an adaptation incidence was related to this profile, the error is more relevant
					for (int j=0; j<incidences.size( ); j++){
						Incidence current = incidences.get( j );
						if (current.getAffectedArea( ) == Incidence.ASSESSMENT_INCIDENCE && current.getAffectedResource( ).equals( assessmentName )){
							String message = current.getMessage( );
							incidences.remove( j );
							incidences.add( j, Incidence.createAssessmentIncidence( true, message+TC.get( "Error.LoadAssessment.Referenced" ), assessmentName ,null) );
						}
					}

				}
		}
	}

	@Override
	public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {

		// If the title is complete, store it
		if( sName.equals( "title" ) ) {
			// Store it in the adventure data
			if( reading == READING_NONE )
				adventureData.setTitle( currentString.toString( ).trim( ) );

			// Or in the chapter
			else if( reading == READING_CHAPTER )
				currentChapter.setTitle( currentString.toString( ).trim( ) );
		}

		// If the description is complete, store it
		else if( sName.equals( "description" ) ) {
			// Store it in the adventure data
			if( reading == READING_NONE )
				adventureData.setDescription( currentString.toString( ).trim( ) );

			// Or in the chapter
			else if( reading == READING_CHAPTER )
				currentChapter.setDescription( currentString.toString( ).trim( ) );
		}

		// If the list of chapters is closing, store it
		else if( sName.equals( "contents" ) ) {
			adventureData.setChapters( chapters );
		}

		// If a chapter is closing, store it in the list
		else if( sName.equals( "chapter" ) ) {
			chapters.add( currentChapter );
		}
	}

	@Override
	public void characters( char[] buf, int offset, int len ) throws SAXException {
		// Append the new characters
		currentString.append( new String( buf, offset, len ) );
	}

	@Override
	public void error( SAXParseException exception ) throws SAXParseException {
		throw exception;
	}

/*	@Override
	public InputSource resolveEntity( String publicId, String systemId ) throws FileNotFoundException {
		// Take the name of the file SAX is looking for
		int startFilename = systemId.lastIndexOf( "/" ) + 1;
		String filename = systemId.substring( startFilename, systemId.length( ) );

		// Create the input source to return
		InputSource inputSource = null;

		try {
			// If the file is descriptor.dtd, use the one in the editor's folder
			if( filename.toLowerCase( ).equals( "descriptor.dtd" ) )
				inputSource = new InputSource( new FileInputStream( filename ) );

			// If it is any other file, use the super's method
			else
				inputSource = super.resolveEntity( publicId, systemId );
		} catch( IOException e ) {
			e.printStackTrace( );
		} catch( SAXException e ) {
			e.printStackTrace( );
		}

		return inputSource;
	}*/

	/**
	 * @return the incidences
	 */
	public List<Incidence> getIncidences( ) {
		return incidences;
	}
	
    /*
     *  (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    @Override
	public InputSource resolveEntity( String publicId, String systemId ) {
    	
    	int startFilename = systemId.lastIndexOf( "/" ) + 1;
        String filename = systemId.substring( startFilename, systemId.length( ) );
        InputStream inputStream = isCreator.buildInputStream( filename );
        return new InputSource( inputStream );
    }

}
