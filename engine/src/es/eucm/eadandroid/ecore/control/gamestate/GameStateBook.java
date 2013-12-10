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
package es.eucm.eadandroid.ecore.control.gamestate;




import android.graphics.Canvas;
import android.view.MotionEvent;
import es.eucm.eadandroid.common.data.chapter.book.Book;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalBook;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalStyledBook;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalTextBook;
import es.eucm.eadandroid.ecore.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadandroid.ecore.gui.GUI;

/**
 * A game main loop when a "bookscene" is being displayed
 */
public class GameStateBook extends GameState {

    /**
     * Functional book to be displayed
     */
    private FunctionalBook book;

    /**
     * Creates a new GameStateBook
     */
    public GameStateBook( ) {

        super( );
        if( game.getBook( ).getType( ) == Book.TYPE_PARAGRAPHS ) {
            //System.out.println( "[LOG] GameStateBook - Constructor - Paragraphs Book" );
            book = new FunctionalTextBook( game.getBook( ) );
        	//Log.d("GameStateBook","Paragraph books not supported, yet");
        }
        else if( game.getBook( ).getType( ) == Book.TYPE_PAGES ) {
            //System.out.println( "[LOG] GameStateBook - Constructor - Pages Book" );
            book = new FunctionalStyledBook( game.getBook( ) );
        }
        
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    @Override
    public void mainLoop( long elapsedTime, int fps ) {

    	if( book != null && book.getBook( ).getType( ) == Book.TYPE_PARAGRAPHS ) {
    		
    		Canvas c = GUI.getInstance( ).getGraphics( );
            //c.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
            

            ( (FunctionalTextBook) book ).draw( c );

            //c.drawColor( Color.WHITE );
            //g.drawString(Integer.toString( fps ), 780, 14);

            GUI.getInstance( ).endDraw( );


    	}
    	
    	else if( book != null && book.getBook( ).getType( ) == Book.TYPE_PAGES ) {
            
    		Canvas c = GUI.getInstance( ).getGraphics( );
            //c.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
            

            ( (FunctionalStyledBook) book ).draw( c );

            //c.drawColor( Color.WHITE );
            //g.drawString(Integer.toString( fps ), 780, 14);

            GUI.getInstance( ).endDraw( );

           // c.dispose( );
    	}

    	else {
    		book.clearBookBitmap();
            FunctionalEffects.storeAllEffects( new Effects( ) );
            game.setState( Game.STATE_RUN_EFFECTS ); 
    	}
    }
    
    
    /**
	 * Called to process touch screen events.
	 * 
	 * @param event
	 * @return
	 */
    @Override
	public boolean processTouchEvent(MotionEvent event) {
    	
    	if((event.getAction()==MotionEvent.ACTION_UP)) {
		
    	if( book.isInLastPage( ) ) {
           // GUI.getInstance( ).restoreFrame( );
            // this method also change the state to run effects
            FunctionalEffects.storeAllEffects( new Effects( ) );
            game.setState( Game.STATE_RUN_EFFECTS );
        }
        else
            book.nextPage( );
			
    	}
			
			/*
			if( book.isInPreviousPage( e.getX( ), e.getY( ) ) )
                book.previousPage( );

            else if( book.isInNextPage( e.getX( ), e.getY( ) ) ) {

                if( book.isInLastPage( ) ) {
                    GUI.getInstance( ).restoreFrame( );
                    // this method also change the state to run effects
                    FunctionalEffects.storeAllEffects( new Effects( ) );
                    //game.setState( Game.STATE_RUN_EFFECTS );
                }
                else
                    book.nextPage( );
            }*/
			
			
			

		return true;
	}
 
    
}
