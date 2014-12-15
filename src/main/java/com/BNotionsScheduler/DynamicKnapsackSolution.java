package com.BNotionsScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Deppi on 12/14/2014.
 */
public class DynamicKnapsackSolution {

    private ArrayList<ScheduleEvent> scheduleEvents; // no m in front of this because it is passed in by ref
    private boolean mIsMorning;
    private ArrayList<ScheduleEvent> mSolution;
    private int W;
    private int n;

    private class intBoolPair{ // in this context, num represents the optimal value at the current index in the dynamic matrix
        int num;            // and the bool represents whether we took the item at [i,j] or not. to be used for backtracking
        boolean bool;

        public intBoolPair(int num, boolean bool){
            this.num = num;
            this.bool = bool;
        }

        @Override
        public String toString(){
            if (!bool){ return ""; }
            return "num: " + num + " bool: " + bool;
        }
    }

    public  DynamicKnapsackSolution (ArrayList<ScheduleEvent> scheduleEvents /*this is in by reference*/, boolean isMorning){
        //region initializations
        scheduleEvents.add(0, new ScheduleEvent(0, 0, "none")); // sentinel value
        this.scheduleEvents = scheduleEvents;
        this.mIsMorning = isMorning;
        this.mSolution = new ArrayList<ScheduleEvent>(); // initialize the solution
        n = scheduleEvents.size();
        if (mIsMorning) W = 180 + 1; // + 1 because we count 0 minute time block as well
        else W = 480 + 1;
        //endregion

        // solve the problem
        DynamicKnapsackBacktrack(DynamicKnapsack()); // these functions must be called together because there is a sentinel value
                                                    // could be a potential improvement in the solution...
    }

    // precondition: scheduleEvents is sorted in increasing minute value
    private Vector<Vector<intBoolPair>> DynamicKnapsack(){
        //region declarations
        Vector<Vector<intBoolPair>> m = new Vector<Vector<intBoolPair>>(); // can't be primitive type in the container vector
        //endregion
        //region initialization and set up
        Collections.sort(scheduleEvents); // O(NlogN) where N = size(scheduleEvents)
        for (int i = 0; i < scheduleEvents.size(); i++){
            m.add(new Vector<intBoolPair>());
            for (int j = 0; j < W; j++){
                m.get(i).add(new intBoolPair(0, false));
            }
        }
        //endregion
        //region solve for max vote value
        for (int i = 1; i < n; i++){ // need to start with 1 event at the beginning
            for (int j = 0; j < W; j++){ // O(N*W) where N is size of scheduleEvents and W is length of time
                if (scheduleEvents.get(i).mMinutes <= j){ // is this current time less than the allowed time slot so far?
                    // look for the max between not adding the new event or going back scheduleEvents[i].mins and adding the new vote value
                    int previousOptimal = m.get(i - 1).get(j).num;
                    int potentialNewOptimal = m.get(i - 1)
                            .get(j - scheduleEvents.get(i).mMinutes).num // go back current event's min value, no index out of bound due to precondition.
                            + scheduleEvents.get(i).mVotes;
                    int optimalAtJMins = Math.max(previousOptimal, potentialNewOptimal);
                    boolean take = true;
                    if (optimalAtJMins > potentialNewOptimal) take = false;
                    m.get(i).set(j, new intBoolPair(optimalAtJMins, take));
                } else { // this current time value does not fit in j minutes
                    m.get(i).set(j, m.get(i - 1).get(j)); // optimal solution at j mins must be previous value
                }
            }
        }
        //endregion
        return m;
    }

    private void DynamicKnapsackBacktrack (Vector<Vector<intBoolPair>> m){
        int w = W - 1;
        for (int i = n - 1; i > 0; i--) { // determine which items to take
            if (m.get(i).get(w).bool) { // we take this value
                scheduleEvents.get(i).mInMorning = mIsMorning;
                mSolution.add(scheduleEvents.get(i)); // add to solution
                w = w - scheduleEvents.get(i).mMinutes; // step back weight amount taken
            }
        }
        scheduleEvents.remove(0); // remove the sentinel value
    }

    public ArrayList<ScheduleEvent> getSolution(){
        return  mSolution;
    }

}
