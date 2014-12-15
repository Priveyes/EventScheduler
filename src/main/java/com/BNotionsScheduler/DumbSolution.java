package com.BNotionsScheduler;

import java.util.ArrayList;

/**
 * Created by Deppi on 12/15/2014.
 */
public class DumbSolution {

    ArrayList<ScheduleEvent> scheduleEvents;
    private ArrayList<ScheduleEvent> mSolution;

    public DumbSolution(ArrayList<ScheduleEvent> scheduleEvents) {
        this.scheduleEvents = scheduleEvents;
        this.mSolution = new ArrayList<ScheduleEvent>();
        ExecuteDumbSolution();
    }

    // step 1...
    private void ExecuteDumbSolution(){ // first solution just to get this to work
        int slot1 = 180;
        int slot2 = 480;

        for (int i = 0; i < scheduleEvents.size(); i++){
            ScheduleEvent cur = scheduleEvents.get(i);
            if (cur.mMinutes <= slot1) {
                cur.mInMorning = true;
                slot1 -= cur.mMinutes;
                mSolution.add(cur);
            }
            else if (cur.mMinutes <= slot2){
                cur.mInMorning = false;
                slot2 -= cur.mMinutes;
                mSolution.add(cur);
            }
        }
    }

    public ArrayList<ScheduleEvent> getSolution(){
        return  mSolution;
    }
}
