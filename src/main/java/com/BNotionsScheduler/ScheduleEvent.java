package com.BNotionsScheduler;

/**
 * Created by Deppi on 12/10/2014.
 */
public class ScheduleEvent implements Comparable<ScheduleEvent> {
    int mVotes;
    int mMinutes;
    String mName;
    boolean mInMorning;

    public ScheduleEvent(int votes, int minutes, String name){
        this.mVotes = votes;
        this.mMinutes = minutes;
        this.mName = name;
        this.mInMorning = false;
    }

    @Override
    public String toString(){
        return "votes: " + mVotes + " minutes: " + mMinutes + " name: " + mName;
    }

    @Override
    public int compareTo(ScheduleEvent SE) { // sort by ascending order
        int compareMinutes=((ScheduleEvent)SE).mMinutes;
        return (mMinutes-compareMinutes);
    }
}
