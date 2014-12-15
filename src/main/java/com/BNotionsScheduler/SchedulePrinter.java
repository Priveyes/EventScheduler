package com.BNotionsScheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Deppi on 12/14/2014.
 */
public class SchedulePrinter {
    // expects the scheduleEvent mInMorning field to be set properly
    // prints out a schedule
    public void PrintSolution(ArrayList<ScheduleEvent> solution){
        //region function setup
        final DateFormat dateFormat = new SimpleDateFormat("HH:mm a");
        Calendar morning = Calendar.getInstance();
        morning.set(Calendar.MILLISECOND, 0);
        morning.set(Calendar.SECOND, 0);
        morning.set(Calendar.MINUTE, 0);
        morning.set(Calendar.HOUR_OF_DAY, 9);

        Calendar afternoon = Calendar.getInstance();
        afternoon.set(Calendar.MILLISECOND, 0);
        afternoon.set(Calendar.SECOND, 0);
        afternoon.set(Calendar.MINUTE, 0);
        afternoon.set(Calendar.HOUR_OF_DAY, 13);
        //endregion

        //region print the solution
        for (int i = 0; i < solution.size(); i++){
            ScheduleEvent cur = solution.get(i);
            if (cur.mInMorning){
                System.out.print(dateFormat.format(morning.getTime()));
                morning.add(Calendar.MINUTE, cur.mMinutes);
                System.out.print(" - " + dateFormat.format(morning.getTime()) + ": " + cur.mName + "\n");
            } else { // it's in the afternoon
                System.out.print(dateFormat.format(afternoon.getTime()));
                afternoon.add(Calendar.MINUTE, cur.mMinutes);
                System.out.print(" - " + dateFormat.format(afternoon.getTime()) + ": " + cur.mName + "\n");
            }
        }
        //endregion
    }
}
