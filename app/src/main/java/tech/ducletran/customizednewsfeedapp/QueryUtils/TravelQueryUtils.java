package tech.ducletran.customizednewsfeedapp.QueryUtils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import tech.ducletran.customizednewsfeedapp.OtherResource.New;

public class TravelQueryUtils {

    public static ArrayList<New> fetchTravelNewsData(String[] urls) {

        ArrayList<New> randomCityList = new ArrayList<New>() {};

        String jsonResponse = null;
        for (String url:urls) {
            jsonResponse = QueryUtils.getNewsData(url);
            New placeExtracted = extractTravelNews(jsonResponse);
            if (placeExtracted != null) {
                randomCityList.add(placeExtracted);
            }
        }

        return randomCityList;
    }

    private static New extractTravelNews(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding news to
        New cityNew = null;

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject == null) {
                return null;
            }
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("content");
            String imageURL = jsonObject.getString("cover_image_url");
            String articleURL = jsonObject.getString("url");
            String country = jsonObject.getJSONObject("country").getString("name");

            cityNew = new New(imageURL,name,articleURL,null,description,country,null,null);
        } catch (JSONException e) {
            Log.e("TravelQueryUtils","extractTravelNews: Can't get JSONObject city");
        }


        // Return the list of earthquakes
        return cityNew;
    }


}
