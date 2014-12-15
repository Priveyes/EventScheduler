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

            DynamicKnapsackSolution morningKnapsack = new DynamicKnapsackSolution(scheduleEvents, true); // create solution class for morning
            ArrayList<ScheduleEvent> morningSolution = morningKnapsack.getSolution(); // get solution
            for (int i = 0; i < morningSolution.size(); i++) scheduleEvents.remove(morningSolution.get(i)); // remove events scheduled in morning
            p.PrintSolution(morningSolution); // print morning schedule
            DynamicKnapsackSolution afternoonKnapsack = new DynamicKnapsackSolution(scheduleEvents, false); // do the same for afternoon...
            ArrayList<ScheduleEvent> afternoonSolution = afternoonKnapsack.getSolution();
            p.PrintSolution(afternoonSolution);
        } else {
            System.out.println("Could not get the data required or no data to work with");
        }
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
