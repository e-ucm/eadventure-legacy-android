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
package es.eucm.eadandroid.ecore.control.gamestate.scene;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.TouchListener;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.FlingEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.LongPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.OnDownEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.PressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.TapEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UnPressedEvent;


public class SceneTouchListener implements TouchListener , TouchListener.CallBack{


    private static final int TOUCH_SLOP_SQUARE = ViewConfiguration.getTouchSlop()
            * ViewConfiguration.getTouchSlop();

    // constants for Message.what used by GestureHandler below
    private static final int LONG_PRESS = 2;

    private final Handler mHandler;


    private boolean mPressedOrAndMoved;
    private boolean mAlwaysInTapRegion;

    private MotionEvent mCurrentDownEvent;
    private MotionEvent mCurrentUpEvent;

    private float mLastMotionY;
    private float mLastMotionX;

    protected Queue<UIEvent> vEvents;    


    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;

    private class GestureHandler extends Handler {
        GestureHandler() {
            super();
        }

        GestureHandler(Handler handler) {
            super(handler.getLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                
            case LONG_PRESS:
                dispatchLongPress();
                break;

            default:
                throw new RuntimeException("Unknown message " + msg); //never                
            }
        }
    }


    public SceneTouchListener(Handler handler) {
        mHandler = new GestureHandler(handler);
        vEvents = new ConcurrentLinkedQueue<UIEvent>();    
    }


    public SceneTouchListener() {
        mHandler = new GestureHandler();
        vEvents = new ConcurrentLinkedQueue<UIEvent>();
    }


    public boolean processTouchEvent(MotionEvent ev) {
   
    	final long tapTime = ViewConfiguration.getTapTimeout();   	
        final long longpressTime = ViewConfiguration.getLongPressTimeout();       
        
        final int action = ev.getAction();
        final float y = ev.getY();
        final float x = ev.getX();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        boolean handled = false;

        switch (action) {
        case MotionEvent.ACTION_DOWN:
        	
        	onDown(ev);
        	
            mLastMotionX = x;
            mLastMotionY = y;
            mCurrentDownEvent = MotionEvent.obtain(ev);
            mAlwaysInTapRegion = true;
            mPressedOrAndMoved = false;
            
            mHandler.removeMessages(LONG_PRESS);
            mHandler.sendEmptyMessageAtTime(LONG_PRESS, mCurrentDownEvent.getDownTime()
                        + tapTime + longpressTime);
        
           // handled = mListener.onDown(ev);
            
            handled = true;
            
            break;

        case MotionEvent.ACTION_MOVE:
        	
        	
            final float scrollX = mLastMotionX - x;
            final float scrollY = mLastMotionY - y;
            if (mAlwaysInTapRegion) {
                final int deltaX = (int) (x - mCurrentDownEvent.getX());
                final int deltaY = (int) (y - mCurrentDownEvent.getY());
                int distance = (deltaX * deltaX) + (deltaY * deltaY);
                if (distance > TOUCH_SLOP_SQUARE) {
                    handled = this.onScrollPressed(mCurrentDownEvent, ev, scrollX, scrollY);
                    mLastMotionX = x;
                    mLastMotionY = y;
                    mAlwaysInTapRegion = false;
                    
                    mPressedOrAndMoved=true;
                    
                    mHandler.removeMessages(LONG_PRESS);
                }
            } else if ((Math.abs(scrollX) >= 1) || (Math.abs(scrollY) >= 1)) {
                handled = this.onScrollPressed(mCurrentDownEvent, ev, scrollX, scrollY);
                mLastMotionX = x;
                mLastMotionY = y;
                mPressedOrAndMoved=true; // A–adido para que tire el listener , partiendo de eventos on move , y no teniendo en cuenta que el primer evento sea DOWN.
            }
            break;

        case MotionEvent.ACTION_UP:
            mCurrentUpEvent = MotionEvent.obtain(ev);
            
            
            if(mPressedOrAndMoved) {
            	
                // A fling must travel the minimum tap distance
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                final float velocityY = velocityTracker.getYVelocity();
                final float velocityX = velocityTracker.getXVelocity();

                if ((Math.abs(velocityY) > ViewConfiguration.getMinimumFlingVelocity())
                        || (Math.abs(velocityX) > ViewConfiguration.getMinimumFlingVelocity())){
                    handled = this.onFling(mCurrentDownEvent, mCurrentUpEvent, velocityX, velocityY);
                }
                
            	handled = this.onUnPressed(ev);
            	
            }
            
            else if (mAlwaysInTapRegion) {
                 	handled = this.onTap(ev);
                   }


            mVelocityTracker.recycle();
            mVelocityTracker = null;
            mHandler.removeMessages(LONG_PRESS);
            mPressedOrAndMoved = false;
            break;
        case MotionEvent.ACTION_CANCEL:
            mHandler.removeMessages(LONG_PRESS);
            mVelocityTracker.recycle();
            mVelocityTracker = null;
            mPressedOrAndMoved=false;
        }
        return handled;
    }

    private void dispatchLongPress() {
    	
    	mPressedOrAndMoved=true;
        this.onLongPressed(mCurrentDownEvent);
        
    }
    
    /** INTERFACE IMPLEMENTATION TOUCH_LISTENER.CALLBACK **/
    
	public boolean onTap(MotionEvent e) {
		
		vEvents.add(new TapEvent(e));
		return true;
	}


	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
					
		vEvents.add(new FlingEvent(e1,e2,velocityX,velocityY));			
		return true;
	}


	public boolean onPressed(MotionEvent e) {

		vEvents.add(new PressedEvent(e));
		return true;
	}


	public boolean onScrollPressed(MotionEvent e1, MotionEvent e2,
			float distanceX, float distanceY) {

		vEvents.add(new ScrollPressedEvent(e1,e2,distanceX,distanceY));			
		return true;
	}


	public boolean onUnPressed(MotionEvent e) {

		vEvents.add(new UnPressedEvent(e));		
		return true;
	}
	
	
	public Queue<UIEvent> getEventQueue() {
		return vEvents;
	}


	public boolean onDown(MotionEvent e) {
		return vEvents.add(new OnDownEvent(e));
	}


	public boolean onLongPressed(MotionEvent e) {
		vEvents.add(new LongPressedEvent(e));
		return true;
	}

}
