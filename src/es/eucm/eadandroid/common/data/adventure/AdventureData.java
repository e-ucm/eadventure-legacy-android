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
package es.eucm.eadandroid.common.data.adventure;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadandroid.common.data.chapter.Chapter;

/**
 * Represents the whole data of an adventure game. This includes: Descriptor
 * Information (adventure title, description, path of each chapter, adaptation &
 * assessment configuration, gui configuration) List of Chapters List of
 * AssessmentProfiles List of AdaptationProfiles
 * 
 * @author Javier
 * 
 */
public class AdventureData extends DescriptorData {

    /**
     * List of chapters. Contains the main data of the adventures.
     */
    protected List<Chapter> chapters;

    /**
     * Default constructor
     */
    public AdventureData( ) {

        super( );
        this.chapters = new ArrayList<Chapter>( );
        contents = null;
    }

    /**
     * Adds a new chapter.
     * 
     * @param chapter
     */
    public void addChapter( Chapter chapter ) {

        //contents.add(chapter);
        chapters.add( chapter );
    }

    /**
     * @return the chapters
     */
    public List<Chapter> getChapters( ) {

        /*List<Chapter> chapters = new ArrayList<Chapter>();
        for (ChapterSummary summary: contents){
        	chapters.add((Chapter)summary);
        }*/
        return chapters;
    }

    /**
     * @param chapters
     *            the chapters to set
     */
    public void setChapters( List<Chapter> chapters ) {

        /*this.contents = new ArrayList<ChapterSummary>();
        for (Chapter chapter: chapters){
        	contents.add(chapter);
        }*/
        this.chapters = chapters;
    }

    /*
     * Redefine ChapterSummaries handling so no data is duplicated
     *  
     */
    @Override
    public void addChapterSummary( ChapterSummary chapter ) {

        if( chapter instanceof Chapter ) {
            chapters.add( (Chapter) chapter );
        }
    }
    
    /**
     * Returns if the chapter.xml has adaptation and/or assessment data
     * 
     * @return
     */
    public boolean hasAdapOrAssesData(){
	
	for (int i=0;i<chapters.size();i++){
	    String[] apn = chapters.get(i).getAdaptationProfilesNames();
	   if (apn.length>0)
	       return true;
	   String[] aspn = chapters.get(i).getAssessmentProfilesNames();
	   if (aspn.length>0)
	       return true;
	}
	
	return false;
    }

    /**
     * Returns the list of chapters of the game
     * 
     * @return List of chapters of the game
     */
    @Override
    public List<ChapterSummary> getChapterSummaries( ) {

        List<ChapterSummary> summary = new ArrayList<ChapterSummary>( );
        for( Chapter chapter : chapters ) {
            summary.add( chapter );
        }
        return summary;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AdventureData ad = (AdventureData) super.clone( );
        ad.buttons = new ArrayList<CustomButton>( );
        for( CustomButton cb : buttons )
            ad.buttons.add( (CustomButton) cb.clone( ) );
        for( CustomArrow ca : arrows )
            ad.arrows.add( (CustomArrow) ca.clone( ) );
        //ad.chapters = new ArrayList<Chapter>();
        //for (Chapter c : chapters)
        //	ad.chapters.add((Chapter) c.clone());
        ad.commentaries = commentaries;
        ad.contents = new ArrayList<ChapterSummary>( );
        for( ChapterSummary cs : contents )
            ad.contents.add( (ChapterSummary) cs.clone( ) );
        ad.cursors = new ArrayList<CustomCursor>( );
        for( CustomCursor cc : cursors )
            ad.cursors.add( (CustomCursor) cc.clone( ) );
        ad.description = new String( description );
        ad.guiCustomized = guiCustomized;
        ad.guiType = guiType;
        ad.playerMode = playerMode;
        ad.playerName = ( playerName != null ? new String( playerName ) : null );
        ad.title = ( title != null ? new String( title ) : null );
        return ad;
    }

}
