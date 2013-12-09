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
package es.eucm.eadandroid.ecore.control;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Canvas;
import es.eucm.eadandroid.common.data.chapter.Timer;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalConditions;
import es.eucm.eadandroid.ecore.data.SaveTimer;

public class TimerManager {

    private static int ID = 0;

    private static TimerManager singleton = new TimerManager( );

    public static TimerManager getInstance( ) {

        return singleton;
    }

    private HashMap<Integer, FunctionalTimer> timers;

    public TimerManager( ) {

        timers = new HashMap<Integer, FunctionalTimer>( );
        ID = 0;
    }

    public void reset( ) {

        if( timers != null )
            timers.clear( );
        timers = new HashMap<Integer, FunctionalTimer>( );
        ID = 0;
    }

    public int addTimer( Conditions initConditions, Conditions endConditions, boolean usesEndConditions, TimerEventListener listener ) {

        FunctionalTimer newTimer = new FunctionalTimer( initConditions, endConditions, usesEndConditions, listener );
        timers.put( new Integer( ID ), newTimer );
        ID++;
        return ID - 1;
    }

    public int addTimer( Timer timer, TimerEventListener listener, long timeUpdate ) {

        FunctionalTimer newTimer = new FunctionalTimer( timer.getInitCond( ), timer.getEndCond( ), listener, timeUpdate, false );
        newTimer.setUsesEndCondition( timer.isUsesEndCondition( ) );
        newTimer.setRunsInLoop( timer.isRunsInLoop( ) );
        newTimer.setMultipleStarts( timer.isMultipleStarts( ) );
        newTimer.setShowTime( timer.isShowTime( ) );
        newTimer.setCountDown( timer.isCountDown( ) );
        newTimer.setName( timer.getDisplayName( ) );
        newTimer.setShowWhenStopped( timer.isShowWhenStopped( ) );
        timers.put( new Integer( ID ), newTimer );
        ID++;
        return ID - 1;
    }

    public void deleteTimer( int id ) {

        if( timers.containsKey( new Integer( id ) ) ) {
            timers.remove( new Integer( id ) );
        }
    }

    public boolean isTimerRunning( int id ) {

        if( id >= 0 && id < timers.size( ) ) {
            FunctionalTimer timer = timers.get( id );
            return timer.getState( ) == FunctionalTimer.STATE_RUNNING;
        }
        return false;
    }

    public boolean isTimerDone( int id ) {

        if( id >= 0 && id < timers.size( ) ) {
            FunctionalTimer timer = timers.get( id );
            return timer.getState( ) == FunctionalTimer.STATE_DONE;
        }
        return true;
    }

    public boolean isTimerNotStarted( int id ) {

        if( id >= 0 && id < timers.size( ) ) {
            FunctionalTimer timer = timers.get( id );
            return timer.getState( ) == FunctionalTimer.STATE_NO_INIT;
        }
        return false;
    }

    /**
     * Get the suitable attributes to store in save data
     * 
     * @return the array list with suitable attributes to save
     */
    public ArrayList<SaveTimer> getTimers( ) {

        ArrayList<SaveTimer> list = new ArrayList<SaveTimer>( );

        FunctionalTimer ft;
        for( int i = 0; i < ID; i++ ) {
            SaveTimer st = new SaveTimer( );
            ft = timers.get( new Integer( i ) );
            st.setLastUpdate( ft.getLastUpdate( ) );
            st.setState( ft.getState( ) );
            st.setTimeUpdate( ft.getTimeUpdate( ) );
            st.setAssessmentRule( ft.isAssessmentTimer( ) );
            list.add( st );
        }
        return list;
    }

    /*
    public FunctionalTimer getTimer(int i){
    	//TODO ver que pasa en el caso chungo
    	if (i < ID){
    		return timers.get(new Integer(i));
    	} else {
    		return new FunctionalTimer();
    	}
    }
    */

    public boolean isRunningState( int state ) {

        return ( state == FunctionalTimer.STATE_RUNNING );
    }

    /**
     * Change the state, the timeUpdate and lastUpdate of indexed timer when a
     * game is loaded
     * 
     * @return -1 if the parameter "i" does´t fit with "ID", "i" in other case
     */
    public int changeValueOfTimer( int i, SaveTimer st ) {

        if( i > ID ) {
            return -1;
        }
        else {
            FunctionalTimer currentTimer = timers.get( new Integer( i ) );
            if( currentTimer != null ) {
                currentTimer.setLastUpdate( st.getLastUpdate( ) );
                currentTimer.setState( st.getState( ) );
                currentTimer.setTimeUpdate( st.getTimeUpdate( ) );
                return i;
            }
            return -1;
        }
    }

    /**
     * 
     * @param notifyCycles
     */

    public void update( boolean notifyCycles ) {

        // Current time in seconds
        long currentTime = System.currentTimeMillis( ) / 1000;

        // Iterate through timers
        for( int i = 0; i < ID; i++ ) {
            if( timers.containsKey( new Integer( i ) ) ) {
                FunctionalTimer currentTimer = timers.get( new Integer( i ) );

                // First case: the timer has not been initialized yet. In this
                // case check if current conditions are satisfied.
                if( currentTimer.getState( ) == FunctionalTimer.STATE_NO_INIT || ( currentTimer.isMultipleStarts( ) && currentTimer.getState( ) == FunctionalTimer.STATE_DONE ) ) {

                    if( new FunctionalConditions( currentTimer.getInitConditions( ) ).allConditionsOk( ) && ( !currentTimer.isUsesEndCondition( ) || !( new FunctionalConditions( currentTimer.getEndConditions( ) ).allConditionsOk( ) ) ) ) {

                  //      DebugLog.general( "Timer started " + i );

                        currentTimer.setLastUpdate( currentTime );
                        currentTimer.setState( FunctionalTimer.STATE_RUNNING );

                        TimerEventListener listener = currentTimer.getListener( );
                        if( listener != null )
                            listener.timerStarted( i, currentTime );
                    }
                }

                // Second case: the timer is running. Two checks are required:
                // 1) Check if end conditions are OK. In such situation, stop
                // the timer
                // 2) Check if a timer cycle has been accomplished to notify the
                // listener
                else if( currentTimer.getState( ) == FunctionalTimer.STATE_RUNNING ) {
                    if( ( currentTimer.isUsesEndCondition( ) && new FunctionalConditions( currentTimer.getEndConditions( ) ).allConditionsOk( ) ) || ( !currentTimer.isUsesEndCondition( ) && !( new FunctionalConditions( currentTimer.getInitConditions( ) ).allConditionsOk( ) ) ) ) {

//                        DebugLog.general( "Timer stoped " + i );

                        currentTimer.setLastUpdate( currentTime );
                        currentTimer.setState( FunctionalTimer.STATE_DONE );

                        TimerEventListener listener = currentTimer.getListener( );
                        if( listener != null )
                            listener.timerStopped( i, currentTime );

                    }
                    else if( notifyCycles ) {

                        // Check the time gap:
                        long gap = currentTime - currentTimer.getLastUpdate( );
                        if( gap >= currentTimer.getTimeUpdate( ) && currentTimer.isNotifyUpdates( ) ) {

                            if( !currentTimer.isRunsInLoop( ) ) {
           //                     DebugLog.general( "Timer ended first cycle and stoped " + i );
                                currentTimer.setState( FunctionalTimer.STATE_DONE );
                            }
                            else {
             //                   DebugLog.general( "Timer ended cycle and continues " + i );
                            }

                            TimerEventListener listener = currentTimer.getListener( );
                            if( listener != null ) {
                                currentTimer.setLastUpdate( currentTime );
                                listener.cycleCompleted( i, gap );
                                if( !currentTimer.isRunsInLoop( ) )
                                    listener.timerStopped( i, currentTime );
                            }
                        }

                    }
                }
            }
        }
    }

    private static class FunctionalTimer {

        public static final long NO_UPDATE = Long.MIN_VALUE;

        public static final int STATE_NO_INIT = 0;

        public static final int STATE_RUNNING = 1;

        public static final int STATE_DONE = 2;

        /**
         * Last time the timer was updated
         */
        private long lastUpdate;

        /**
         * True when timer manager must notify the listener
         */
        private boolean notifyUpdates;

        /**
         * The time gap of a timer cycle
         */
        private long timeUpdate;

        private Conditions initConditions;

        private Conditions endConditions;

        private TimerEventListener listener;

        /**
         * True if it is an assessment functional timer, false if it isn´t
         */
        private boolean isAssessmentTimer;

        private boolean usesEndCondition;

        private boolean runsInLoop;

        private boolean multipleStarts;

        private int state;

        private String name;

        private boolean showTime;

        private boolean countDown;

        private boolean showWhenStopped;

        public FunctionalTimer( Conditions initConditions, Conditions endConditions, boolean usesEndConditions, TimerEventListener listener ) {

            this( initConditions, endConditions, listener, NO_UPDATE, true );
            this.usesEndCondition = usesEndConditions;
        }

        public boolean isUsesEndCondition( ) {

            return usesEndCondition;
        }

        public void setUsesEndCondition( boolean usesEndCondition ) {

            this.usesEndCondition = usesEndCondition;
        }

        public boolean isRunsInLoop( ) {

            return runsInLoop;
        }

        public void setRunsInLoop( boolean runsInLoop ) {

            this.runsInLoop = runsInLoop;
        }

        public boolean isMultipleStarts( ) {

            return multipleStarts;
        }

        public void setMultipleStarts( boolean multipleStarts ) {

            this.multipleStarts = multipleStarts;
        }

        /*
        // Empty constructor
        public FunctionalTimer(){
        	
        }*/

        public FunctionalTimer( Conditions initConditions, Conditions endConditions, TimerEventListener listener, long timeUpdate, boolean assessment ) {

            this.initConditions = initConditions;
            this.endConditions = endConditions;
            this.listener = listener;
            this.lastUpdate = 0;
            this.timeUpdate = timeUpdate;
            notifyUpdates = ( timeUpdate != NO_UPDATE );
            this.state = STATE_NO_INIT;
            this.isAssessmentTimer = assessment;

            usesEndCondition = true;
            runsInLoop = true;
            multipleStarts = true;
            showTime = false;
            name = "timer";
            countDown = false;
        }

        public boolean isAssessmentTimer( ) {

            return isAssessmentTimer;
        }

        /**
         * @return the lastUpdate
         */
        public long getLastUpdate( ) {

            return lastUpdate;
        }

        /**
         * @param lastUpdate
         *            the lastUpdate to set
         */
        public void setLastUpdate( long lastUpdate ) {

            this.lastUpdate = lastUpdate;
        }

        /**
         * @return the notifyUpdates
         */
        public boolean isNotifyUpdates( ) {

            return notifyUpdates;
        }

        /**
         * @param notifyUpdates
         *            the notifyUpdates to set
         */
        public void setNotifyUpdates( boolean notifyUpdates ) {

            this.notifyUpdates = notifyUpdates;
        }

        /**
         * @return the timeUpdate
         */
        public long getTimeUpdate( ) {

            return timeUpdate;
        }

        /**
         * @param timeUpdate
         *            the timeUpdate to set
         */
        public void setTimeUpdate( long timeUpdate ) {

            this.timeUpdate = timeUpdate;
        }

        /**
         * @return the initConditions
         */
        public Conditions getInitConditions( ) {

            return initConditions;
        }

        /**
         * @param initConditions
         *            the initConditions to set
         */
        public void setInitConditions( Conditions initConditions ) {

            this.initConditions = initConditions;
        }

        /**
         * @return the endConditions
         */
        public Conditions getEndConditions( ) {

            return endConditions;
        }

        /**
         * @param endConditions
         *            the endConditions to set
         */
        public void setEndConditions( Conditions endConditions ) {

            this.endConditions = endConditions;
        }

        /**
         * @return the listener
         */
        public TimerEventListener getListener( ) {

            return listener;
        }

        /**
         * @param listener
         *            the listener to set
         */
        public void setListener( TimerEventListener listener ) {

            this.listener = listener;
        }

        /**
         * @return the state
         */
        public int getState( ) {

            return state;
        }

        /**
         * @param state
         *            the state to set
         */
        public void setState( int state ) {

            this.state = state;

        }

        public String getName( ) {

            return name;
        }

        public void setName( String name ) {

            this.name = name;
        }

        public void setShowTime( boolean showTime ) {

            this.showTime = showTime;
        }

        public boolean isShowTime( ) {

            return showTime;
        }

        public void setCountDown( boolean countDown ) {

            this.countDown = countDown;
        }

        public boolean isCountDown( ) {

            return countDown;
        }

        public String getTime( ) {

            long time = 0;
            if( ( state == STATE_NO_INIT && countDown ) || ( state == STATE_DONE && !countDown ) )
                time = timeUpdate;
            else if( ( state == STATE_NO_INIT && !countDown ) || ( state == STATE_DONE && countDown ) )
                time = 0;
            else {
                time = System.currentTimeMillis( ) / 1000 - lastUpdate;
                if( countDown )
                    time = timeUpdate - time;
            }
            //System.out.println("" + state + " -> "+ timeUpdate + " : " + lastUpdate + " : " + System.currentTimeMillis());

            DecimalFormat myFormatter = new DecimalFormat( "00" );
            String temp = ( countDown ? "-" : "" ) + myFormatter.format( Math.abs( time / 3600 ) );
            temp += ":" + myFormatter.format( Math.abs( time % 3600 / 60 ) );
            temp += ":" + myFormatter.format( Math.abs( time % 60 ) );
            return temp;
        }

        public void setShowWhenStopped( boolean showWhenStopped ) {

            this.showWhenStopped = showWhenStopped;
        }

        public boolean isShowWhenStopped( ) {

            return showWhenStopped;
        }
    }

    public void draw( Canvas c ) {

        ArrayList<String> timerText = new ArrayList<String>( );
        for( Integer key : this.timers.keySet( ) ) {
            FunctionalTimer timer = this.timers.get( key );
            if( timer.isShowTime( ) ) {
                if( timer.isShowWhenStopped( ) || timer.getState( ) == FunctionalTimer.STATE_RUNNING ) {
                    String text = timer.getName( );
                    if( text.length( ) > 0 ) {
                        text += " : ";
                    }
                    text += timer.getTime( );
                    timerText.add( text );
                }
            }
        }

        String[] a = new String[ timerText.size( ) ];

        for( int i = 0; i < timerText.size( ); i++ ) {
            a[i] = timerText.get( i );
        }
   //TODO not ported    GUI.drawStringOnto( c, a, GUI.WINDOW_WIDTH, 0, Color.WHITE, Color.BLACK );
    }

}
