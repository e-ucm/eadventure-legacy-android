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
package es.eucm.eadandroid.common.loader.subparsers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadandroid.common.data.animation.Animation;
import es.eucm.eadandroid.common.data.animation.Transition;

public class TransitionSubParser extends DefaultHandler {

    private Animation animation;

    private Transition transition;

    public TransitionSubParser( Animation animation ) {

        this.animation = animation;
        transition = new Transition( );
    }

    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        if( sName.equals( "transition" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getLocalName( i ).equals( "type" ) ) {
                    if( attrs.getValue( i ).equals( "none" ) )
                        transition.setType( Transition.TYPE_NONE );
                    else if( attrs.getValue( i ).equals( "fadein" ) )
                        transition.setType( Transition.TYPE_FADEIN );
                    else if( attrs.getValue( i ).equals( "vertical" ) )
                        transition.setType( Transition.TYPE_VERTICAL );
                    else if( attrs.getValue( i ).equals( "horizontal" ) )
                        transition.setType( Transition.TYPE_HORIZONTAL );
                }
                else if( attrs.getLocalName( i ).equals( "time" ) ) {
                    transition.setTime( Long.parseLong( attrs.getValue( i ) ) );
                }
            }
        }
    }

    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        if( sName.equals( "transition" ) ) {
            animation.getTransitions( ).add( transition );
        }
    }
}
