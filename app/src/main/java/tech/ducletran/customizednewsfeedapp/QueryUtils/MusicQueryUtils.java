package tech.ducletran.customizednewsfeedapp.QueryUtils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tech.ducletran.customizednewsfeedapp.OtherResource.MusicTrack;

public class MusicQueryUtils {

    public static ArrayList<MusicTrack>[] fetchMusicNewsData(String[] urls) {

        ArrayList<MusicTrack>[] musicList = new ArrayList[2];

        for (int i=0;i<2;i++) {
                String jsonResponse = QueryUtils.getNewsData(urls[i]);
                ArrayList<MusicTrack> musicSubList = extractMusicList(jsonResponse);
                musicList[i] = musicSubList;

        }


        return musicList;
    }

    private static ArrayList<MusicTrack> extractMusicList(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding news to
        ArrayList<MusicTrack> musicList = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonObject = new JSONObject(newsJSON);
            JSONArray jsonArray = jsonObject.getJSONObject("tracks").getJSONArray("track");
            if (jsonArray == null || jsonArray.length() <1) {
                return null;
            }
            for (int i=0;i<jsonArray.length();i++) {
                // Getting information from URL Link, parsing through JSON Object
                JSONObject newDataJSONData = jsonArray.getJSONObject(i);
                String songName = newDataJSONData.getString("name");
                String artist = newDataJSONData.getJSONObject("artist").getString("name");
                String webURL = newDataJSONData.getString("url");
                String thumbnail = newDataJSONData.getJSONArray("image").getJSONObject(2).getString("#text");

                MusicTrack newMusic = new MusicTrack(songName,artist,webURL,thumbnail);
                musicList.add(newMusic);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("MusicQueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        // Return the list of earthquakes
        return musicList;
    }
}
