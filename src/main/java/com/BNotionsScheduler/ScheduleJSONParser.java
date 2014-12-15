package com.BNotionsScheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScheduleJSONParser {
    JSONArray mData;
    public ScheduleJSONParser (String url){
        try {
            mData = readJsonFromUrl(url);
        }
        catch (IOException e){
            mData = null;
            System.out.println("Encountered an IO error when trying to read json from: " + url);
            e.printStackTrace();
        }
        catch (JSONException e){
            mData = null;
            System.out.println("Encountered an JSONException when trying to read json from: " + url);
            e.printStackTrace();
        }
    }

    ArrayList<ScheduleEvent> getScheduleEvents(){
        ArrayList<ScheduleEvent> scheduleEvents = new ArrayList<ScheduleEvent>();
        if (mData != null){
            for (int i = 0; i < mData.length(); i++){
                try {
                    JSONObject cur = mData.getJSONObject(i);
                    int votes = cur.getInt("votes");
                    int minutes = cur.getInt("minutes");
                    String name = cur.getString("name");
                    ScheduleEvent s = new ScheduleEvent(votes, minutes, name);
                    scheduleEvents.add(s);
                }
                catch (JSONException e)
                {
                    System.out.println("Encountered an JSONException when trying to read json at index " + i);
                    e.printStackTrace();
                }
            }
        }
        return scheduleEvents;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray json = new JSONArray(jsonText);
            return json;
        } finally { // after exceptions handled, go here
            is.close();
        }
    }
}