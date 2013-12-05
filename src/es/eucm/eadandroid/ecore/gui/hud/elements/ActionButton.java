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
package es.eucm.eadandroid.ecore.gui.hud.elements;

import android.graphics.Bitmap;
import es.eucm.eadandroid.common.data.adventure.DescriptorData;
import es.eucm.eadandroid.common.data.chapter.CustomAction;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.common.gui.TC;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.pathdirectory.Paths;

public class ActionButton {

    /**
     * Width of an action button
     */
    public static final int ACTIONBUTTON_WIDTH = (int) ( 80 * GUI.DISPLAY_DENSITY_SCALE );

    /**
     * Height of an action button
     */
    public static final int ACTIONBUTTON_HEIGHT = (int) ( 48 * GUI.DISPLAY_DENSITY_SCALE );

    /**
     * Constant that represent the hand button
     */
    public static final int GRAB_BUTTON = 0;

    /**
     * Constant that represents the eye button
     */
    public static final int EXAMINE_BUTTON = 1;

    /**
     * Constant that represents the mouth button
     */
    public static final int TALK_BUTTON = 2;
    
    /**
     * Constant that represents the use button
     */
    public static final int USE_BUTTON = 3;
    
    /**
     * Constant that represents the drag button
     */
    public static final int DRAG_BUTTON = 4;
    
    /**
     * Constant that represents the give to button
     */
    public static final int GIVE_TO_BUTTON = 5;
      
    /**
     * Constant that represents the use with button
     */
    public static final int USE_WITH_BUTTON = 6;
    
    
    /**
     * Constant that represents the custom button
     */
    public static final int CUSTOM_BUTTON = 7;
    

    /**
     * Image of the button in it's normal state
     */
    private Bitmap buttonNormal;

    /**
     * Image of the button when it's pressed
     */
    private Bitmap buttonPressed;

  
    /**
     * Name of the action represented by the button
     */
    private String actionName;
    
    /**
     * Custom action of the button
     */
    private CustomAction customAction;

    /**
     * The type of the button
     */
    private int type;

    /**
     * Construct a button form it's type
     * 
     * @param type
     *            the type of the button
     */
    public ActionButton( int type ) {

        switch( type ) {
            case GRAB_BUTTON:
                loadButtonImages( DescriptorData.USE_GRAB_BUTTON, "Grab" );
                actionName = TC.get( "ActionButton.GrabGiveUse" );
                break;
            case EXAMINE_BUTTON:
                loadButtonImages( DescriptorData.EXAMINE_BUTTON, "Examine" );
                actionName = TC.get( "ActionButton.Examine" );
                break;
            case TALK_BUTTON:
                loadButtonImages( DescriptorData.TALK_BUTTON, "Talk" );
                actionName = TC.get( "ActionButton.Talk" );
                break;
            case USE_BUTTON:
                loadButtonImages( DescriptorData.USE_GRAB_BUTTON, "Use" );
                actionName = TC.get( "ActionButton.Use" );
                break;
            case DRAG_BUTTON:
                loadButtonImages( DescriptorData.USE_GRAB_BUTTON, "Drag" );
                actionName = TC.get( "ActionButton.Drag" );
                break;
            case USE_WITH_BUTTON:
                loadButtonImages( DescriptorData.USE_GRAB_BUTTON, "UseWith" );
                actionName = TC.get( "ActionButton.UseWith" );
                break;
            case GIVE_TO_BUTTON:
                loadButtonImages( DescriptorData.USE_GRAB_BUTTON, "GiveTo" );
                actionName = TC.get( "ActionButton.GiveTo" );
                break;
        }
        this.type = type;
    }
    
    /**
     * Construct a button from a custom action
     * 
     * @param action
     *            the custom action
     */
    public ActionButton( CustomAction action ) {

        actionName = action.getName( );
        customAction = action;

        Resources resources = null;
        for( int i = 0; i < action.getResources( ).size( ) && resources == null; i++ )
            if( new FunctionalConditions( action.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                resources = action.getResources( ).get( i );

        buttonNormal = loadImage( resources.getAssetPath( "buttonNormal" ), Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/btnError.png" );
        buttonPressed = loadImage( resources.getAssetPath( "buttonPressed" ), Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/btnError.png" );

//        buttonNormal = scaleButton( buttonNormal );
//        buttonPressed = scaleButton( buttonPressed );

        this.type = CUSTOM_BUTTON;
    }

    private void loadButtonImages( String type, String name ) {

        DescriptorData descriptor = Game.getInstance( ).getGameDescriptor( );
        String customNormalPath = null;
        String customPressedPath = null;

        customNormalPath = descriptor.getButtonPath( type, DescriptorData.NORMAL_BUTTON );
        customPressedPath = descriptor.getButtonPath( type, DescriptorData.PRESSED_BUTTON );

        buttonNormal = loadImage( customNormalPath, Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/"  + name + "-Normal.png" );
        buttonPressed = loadImage( customPressedPath, Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/"  + name + "-Pressed.png" );
        
        
    }

    private Bitmap loadImage( String customPath, String defaultPath ) {

        Bitmap temp = null;
        if( customPath == null )
            temp = MultimediaManager.getInstance( ).loadImage( defaultPath,MultimediaManager.IMAGE_MENU );
        else {
            try {
                temp = MultimediaManager.getInstance( ).loadImage( customPath, MultimediaManager.IMAGE_MENU );
            }
            catch( Exception e ) {
                temp = MultimediaManager.getInstance( ).loadImage( Paths.eaddirectory.ROOT_PATH + "gui/hud/contextual/btnError.png", MultimediaManager.IMAGE_MENU );
            }
        }
        return temp;
    }

   

//    /**
//     * Scale an image of a button
//     * 
//     * @param button
//     *            the image to scale
//     * @return the scaled image
//     */
//    private Bitmap scaleButton( Bitmap button ) {
//
//        if( button.getWidth() > ActionButtons.MAX_BUTTON_WIDTH )
//            button = Bitmap.createScaledBitmap(button, ActionButtons.MAX_BUTTON_WIDTH, button.getHeight(),false);
//        if( button.getWidth() < ActionButtons.MIN_BUTTON_WIDTH )
//            button = Bitmap.createScaledBitmap(button, ActionButtons.MIN_BUTTON_WIDTH, button.getHeight(),false);
//        if( button.getHeight() > ActionButtons.MAX_BUTTON_HEIGHT )
//            button = Bitmap.createScaledBitmap(button,button.getWidth(), ActionButtons.MAX_BUTTON_HEIGHT, false);
//        if( button.getHeight() < ActionButtons.MIN_BUTTON_HEIGHT )
//            button = Bitmap.createScaledBitmap(button,button.getWidth(), ActionButtons.MIN_BUTTON_HEIGHT, false);
//        return button;
//    }

    /**
     * @return the buttonNormal
     */
    public Bitmap getButtonNormal( ) {

        return buttonNormal;
    }

    /**
     * @return the buttonPressed
     */
    public Bitmap getButtonPressed( ) {

        return buttonPressed;
    }


    public String getName( ) {

        return actionName;
    }

    public int getType( ) {

        return type;
    }
    
    public void setName( String string ) {

        actionName = string;
    }
    
    public CustomAction getCustomAction( ) {

        return customAction;
    }

}
