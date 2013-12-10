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
package es.eucm.eadandroid.ecore.control.functionaldata;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import es.eucm.eadandroid.common.data.chapter.Action;
import es.eucm.eadandroid.common.data.chapter.CustomAction;
import es.eucm.eadandroid.common.data.chapter.ElementReference;
import es.eucm.eadandroid.common.data.chapter.InfluenceArea;
import es.eucm.eadandroid.common.data.chapter.elements.Atrezzo;
import es.eucm.eadandroid.common.data.chapter.elements.Element;
import es.eucm.eadandroid.common.data.chapter.elements.Item;
import es.eucm.eadandroid.common.data.chapter.resources.Asset;
import es.eucm.eadandroid.common.data.chapter.resources.Resources;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.multimedia.MultimediaManager;
import es.eucm.eadandroid.res.resourcehandler.ResourceHandler;
/**
 * An atrezzo object in the game
 */
public class FunctionalAtrezzo extends FunctionalElement {

    /**
     * Resources being used in the atrezzo item
     */
    protected Resources resources;

    /**
     * Image of the atrezo item, to display in the scene
     */
    protected Bitmap image;

    /**
     * Image of the atrezzo item, to display on the inventory
     */
  
    private Bitmap oldImage = null;
    
    private int x1, y1, x2, y2;
    
    private int width, height;

    private float oldScale = -1;

    private Bitmap oldOriginalImage = null;

    /**
     * Atrezzo item containing the data
     */
    protected Atrezzo atrezzo;

    private ElementReference reference;

    public FunctionalAtrezzo( Atrezzo atrezzo, ElementReference reference ) {

        this( atrezzo, reference.getX( ), reference.getY( ) );
        this.reference = reference;
        this.layer = reference.getLayer( );
        this.scale = reference.getScale( );
    }

    /**
     * Creates a new FunctionalItem
     * 
     * @param atrezzo
     *            the atrezzo's data
     * @param x
     *            the atrezzo's horizontal position
     * @param y
     *            the atrezzo's vertical position
     */
    public FunctionalAtrezzo( Atrezzo atrezzo, int x, int y ) {

        super( x, y );
        this.atrezzo = atrezzo;
        this.scale = 1;
        Bitmap tempimage = null;
        //icon = null;

        resources = createResourcesBlock( );

        // Load the resources
        MultimediaManager multimediaManager = MultimediaManager.getInstance( );
        if( resources.existAsset( Item.RESOURCE_TYPE_IMAGE ) ) {
            tempimage = multimediaManager.loadImage( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGE ), MultimediaManager.IMAGE_SCENE );
            removeTransparentParts(tempimage);
        }
    }
    
    private void removeTransparentParts(Bitmap tempimage) {
        x1 = tempimage.getWidth(); y1 = tempimage.getHeight(); x2 = 0; y2 = 0;
        width = x1;
        height = y1;
        for (int i = 0; i < tempimage.getWidth(); i++) {
            boolean x_clear = true;
            for (int j = 0; j < tempimage.getHeight(); j++) {
                boolean y_clear = true;
                Bitmap bufferedImage = tempimage;
                int alpha = Color.alpha(bufferedImage.getPixel(i,j)); 
                if (alpha > 128) {
                    if (x_clear)
                        x1 = Math.min( x1, i );
                    if (y_clear)
                        y1 = Math.min( y1, j );
                    x_clear = false;
                    y_clear = false;
                    x2 = Math.max( x2, i );
                    y2 = Math.max( y2, j );
                }
            }
        }
        
        // create a transparent (not translucent) image
        image = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( x2 - x1, y2 - y1, true );

        // draw the transformed image
        
        Canvas c = new Canvas(image);       
        c.drawBitmap(tempimage,new Rect(x1,y1,x2,y2),new Rect(0,0,x2-x1,y2-y1), null);
        
        
    }

    /**
     * Updates the resources of the icon (if the current resources and the new
     * one are different)
     */
    public void updateResources( ) {

        // Get the new resources
        Resources newResources = createResourcesBlock( );

        // If the resources have changed, load the new one
        if( resources != newResources ) {
            resources = newResources;

            // Load the resources
            MultimediaManager multimediaManager = MultimediaManager.getInstance( );
            Bitmap tempimage = null;
            if( resources.existAsset( Item.RESOURCE_TYPE_IMAGE ) ) {
                tempimage = multimediaManager.loadImage( resources.getAssetPath( Item.RESOURCE_TYPE_IMAGE ), MultimediaManager.IMAGE_SCENE );
                removeTransparentParts(tempimage);
            }
            // if( resources.existAsset( Item.RESOURCE_TYPE_ICON ) )
            //   icon = multimediaManager.loadImageFromZip( resources.getAssetPath( Item.RESOURCE_TYPE_ICON ), MultimediaManager.IMAGE_SCENE );
        }
    }

    /**
     * Returns this atrezzo's data
     * 
     * @return this atrezzo's data
     */
    public Atrezzo getAtrezzo( ) {

        return atrezzo;
    }

    /**
     * Returns this atrezzo's icon image
     * 
     * @return this atrezzo's icon image
     */
    // public Image getIconImage( ) {
    //   return icon;
    //}
    @Override
    public Element getElement( ) {

        return atrezzo;
    }

    @Override
    public int getWidth( ) {

        return width;//image.getWidth( null );
    }

    @Override
    public int getHeight( ) {

        return height;//image.getHeight( null );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#update(long)
     */
    public void update( long elapsedTime ) {

        // Do nothing
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    public void draw( ) {

        int x_image = Math.round( x - ( getWidth( ) * scale / 2 ) ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
        int y_image = Math.round( y - getHeight( ) * scale );
        
        x_image+=x1;
        y_image+=y1;
        
        if( scale != 1 ) {
        	Bitmap temp = null;
            if( image == oldOriginalImage && scale == oldScale ) {
                temp = oldImage;
            }
            else {

            	temp = Bitmap.createScaledBitmap(image, Math.round( image.getWidth( ) * scale ),  Math.round( image.getHeight(  ) * scale ), true);
            	/*temp = GUI.getInstance( ).getGraphicsConfiguration( ).createCompatibleImage( Math.round( image.getWidth( ) * scale ),  Math.round( image.getHeight(  ) * scale ), true );
                Canvas c = new Canvas(temp);
                
                Matrix m = new Matrix();
                m.setScale(scale,scale);
                c.drawBitmap(image, m, null);*/
                
                oldImage = temp;
                oldOriginalImage = image;
                oldScale = scale;
                
            }
            if( layer == -1 )
                GUI.getInstance( ).addElementToDraw( temp, x_image, y_image, Math.round( y ), Math.round( y ), null, null );
            else
                GUI.getInstance( ).addElementToDraw( temp, x_image, y_image, layer, Math.round( y ), null, null  );
            
        }
        else if( layer == -1 )
            GUI.getInstance( ).addElementToDraw( image, x_image, y_image, Math.round( y ), Math.round( y ), null, null  );
        else
            GUI.getInstance( ).addElementToDraw( image, x_image, y_image, layer, Math.round( y ), null, null  );
    }

    @Override
    public boolean isPointInside( float x, float y ) {

        boolean isInside = false;

        int mousex = (int) ( x - ( this.x - getWidth( ) * scale / 2 ) );
        int mousey = (int) ( y - ( this.y - getHeight( ) * scale ) );

        if (mousex < x1 || mousey < y1 || mousex >= x2 || mousey >= y2)
            return false;
        mousex = mousex - x1;
        mousey = mousey - y1;
        if( ( mousex >= 0 ) && ( mousex < getWidth( ) * scale ) && ( mousey >= 0 ) && ( mousey < getHeight( ) * scale ) ) {
            int alpha = Color.alpha(image.getPixel((int) ( mousex / scale ),  (int) ( mousey / scale ))) ;
            //GRAPHICS
            //bufferedImage.getRGB( (int) ( mousex / scale ), (int) ( mousey / scale ) ) >>> 24;
            isInside = alpha > 128;
        }

        return isInside;
    }

    //TODO creo k hay que quitarlo
    @Override
    public boolean isInInventory( ) {

        return false;//Game.getInstance( ).getItemSummary( ).isItemGrabbed( atrezzo.getId( ) );
    }

    @Override
    public boolean examine( ) {

        return false;
    }

    @Override
    public boolean canBeUsedAlone( ) {

        return false;
    }

    /* Own methods */

    /**
     * Creates the current resource block to be used
     */
    public Resources createResourcesBlock( ) {

        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < atrezzo.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( atrezzo.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = atrezzo.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if( newResources == null ) {
            newResources = new Resources( );
            //  newResources.addAsset( new Asset( Item.RESOURCE_TYPE_ICON, ResourceHandler.DEFAULT_ICON ) );
            newResources.addAsset( new Asset( Item.RESOURCE_TYPE_IMAGE, ResourceHandler.DEFAULT_IMAGE ) );
        }
        return newResources;
    }

    @Override
    public boolean canPerform( int action ) {

        return false;
    }

    @Override
    public Action getFirstValidAction( int actionType ) {

        return null;
    }

    @Override
    public CustomAction getFirstValidCustomAction( String actionName ) {

        return null;
    }

    @Override
    public CustomAction getFirstValidCustomInteraction( String actionName ) {

        return null;
    }

    @Override
    public InfluenceArea getInfluenceArea( ) {

        return null;
    }

    public ElementReference getReference( ) {

        return reference;
    }

}
