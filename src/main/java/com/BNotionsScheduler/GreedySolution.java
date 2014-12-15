package com.BNotionsScheduler;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Deppi on 12/15/2014.
 */
public class GreedySolution {

    ArrayList<ScheduleEvent> scheduleEvents;
    ArrayList<ScheduleEvent> mSolution;

    public GreedySolution(ArrayList<ScheduleEvent> scheduleEvents){
        this.scheduleEvents = scheduleEvents;
        this.mSolution = new ArrayList<ScheduleEvent>();
        ExecuteGreedySolution();
    }

    // step 2...
    private void ExecuteGreedySolution(){
        int slot1 = 180;
        int slot2 = 480;
        ArrayList<ScheduleEvent> solution = new ArrayList<ScheduleEvent>();

        Collections.sort(scheduleEvents); // take the event with smallest time first, and so on

        for (int i = 0; i < scheduleEvents.size(); i++){
            ScheduleEvent cur = scheduleEvents.get(i);
            if (cur.mMinutes < slot1) {
                cur.mInMorning = true;
                slot1 -= cur.mMinutes;
                mSolution.add(cur);
            }
            else if (cur.mMinutes < slot2){
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
