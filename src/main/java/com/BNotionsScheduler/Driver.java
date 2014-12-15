package com.BNotionsScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Deppi on 12/9/2014.
 */

public class Driver {

    public static void main(String [] args)
    {
        ScheduleJSONParser jp;
        jp =  new ScheduleJSONParser("http://play.bnotions.com:9090/test");

        ArrayList<ScheduleEvent> scheduleEvents = jp.getScheduleEvents();

        if (scheduleEvents.size() != 0){
            SchedulePrinter p = new SchedulePrinter();

            //PrintSolution(DumbSolution(scheduleEvents));
            DynamicKnapsackSolution morningKnapsack = new DynamicKnapsackSolution(scheduleEvents, true);
            ArrayList<ScheduleEvent> morningSolution = morningKnapsack.getSolution();
            for (int i = 0; i < morningSolution.size(); i++) scheduleEvents.remove(morningSolution.get(i));
            p.PrintSolution(morningSolution);
            DynamicKnapsackSolution afternoonKnapsack = new DynamicKnapsackSolution(scheduleEvents, false);
            ArrayList<ScheduleEvent> afternoonSolution = afternoonKnapsack.getSolution();
            p.PrintSolution(afternoonSolution);
        } else {
            System.out.println("Could not get the data required or no data to work with");
        }
    }

    // precondition: scheduleEvents is sorted in increasing minute value
    private static Integer DynamicKnapsack(ArrayList<ScheduleEvent> scheduleEvents, boolean isMorning){
        //region declarations
        Vector<Vector<Integer>> m = new Vector<Vector<Integer>>(); // can't be primitive type in the container vector
        int W;
        int n = scheduleEvents.size();
        //endregion
        //region initialization and set up
        Collections.sort(scheduleEvents); // O(NlogN) where N = size(scheduleEvents)
        if (isMorning) W = 180 + 1; // + 1 because we count 0 minute time block as well
        else W = 480 + 1;
        for (int i = 0; i < scheduleEvents.size(); i++){
            m.add(new Vector<Integer>());
            for (int j = 0; j < W; j++){
                m.get(i).add(0);
            }
        }
        scheduleEvents.add(0, new ScheduleEvent(0, 0, "none")); // sentinel value
        //endregion
        //region solve for max vote value
        for (int i = 1; i < n; i++){ // need to start with 1 event at the beginning
            for (int j = 0; j < W; j++){
                if (scheduleEvents.get(i).mMinutes <= j){ // is this current time less than the allowed time slot so far?
                    // look for the max between not adding the new event or going back scheduleEvents[i].mins and adding the new vote value
                    int previousOptimal = m.get(i - 1).get(j);
                    int potentialNewOptimal = m.get(i - 1)
                            .get(j - scheduleEvents.get(i).mMinutes) // go back current event's min value, no index out of bound due to precondition.
                            + scheduleEvents.get(i).mVotes;
                    int optimalAtJMins = Math.max(previousOptimal, potentialNewOptimal);
                    m.get(i).set(j, optimalAtJMins);
                } else { // this current time value does not fit in j minutes
                    m.get(i).set(j, m.get(i - 1).get(j)); // optimal solution at j mins must be previous value
                }
            }
        }

        for (int i = 0; i < m.size(); i ++){
            for (int j = 0; j < m.get(i).size(); j++){
                System.out.print(m.get(i).get(j) + " ");
            }
            System.out.println("");
        }
        scheduleEvents.remove(0); // remove the sentinel value
        //endregion
        return m.get(n - 1).get(W - 1);
    }

    // step 1...
    private static ArrayList<ScheduleEvent> DumbSolution(ArrayList<ScheduleEvent> scheduleEvents){ // first solution just to get this to work
        int slot1 = 180;
        int slot2 = 480;
        ArrayList<ScheduleEvent> solution = new ArrayList<ScheduleEvent>();

        for (int i = 0; i < scheduleEvents.size(); i++){
            ScheduleEvent cur = scheduleEvents.get(i);
            if (cur.mMinutes <= slot1) {
                cur.mInMorning = true;
                slot1 -= cur.mMinutes;
                solution.add(cur);
            }
            else if (cur.mMinutes <= slot2){
                cur.mInMorning = false;
                slot2 -= cur.mMinutes;
                solution.add(cur);
            }
        }

        return  solution;
    }

    // step 2...
    private static ArrayList<ScheduleEvent> GreedySolution(ArrayList<ScheduleEvent> scheduleEvents){ // first solution just to get this to work
        int slot1 = 180;
        int slot2 = 480;
        ArrayList<ScheduleEvent> solution = new ArrayList<ScheduleEvent>();

        Collections.sort(scheduleEvents);

        for (int i = 0; i < scheduleEvents.size(); i++){
            ScheduleEvent cur = scheduleEvents.get(i);
            if (cur.mMinutes < slot1) {
                cur.mInMorning = true;
                slot1 -= cur.mMinutes;
                solution.add(cur);
            }
            else if (cur.mMinutes < slot2){
                cur.mInMorning = false;
                slot2 -= cur.mMinutes;
                solution.add(cur);
            }
        }

        return  solution;
    }
}
