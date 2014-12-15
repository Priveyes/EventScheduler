package com.BNotionsScheduler;

import java.util.ArrayList;

/**
 * Created by Deppi on 12/9/2014.
 */

public class Driver {

    public static void main(String [] args)
    {
        ScheduleJSONParser jp;
        jp =  new ScheduleJSONParser("http://play.bnotions.com:9090/test"); // Change this to whatever input

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

}
