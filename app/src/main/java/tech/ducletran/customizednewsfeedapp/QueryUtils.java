package tech.ducletran.customizednewsfeedapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class QueryUtils {

    public QueryUtils() {

    }

    public static ArrayList<New> fetchNewsData(String[] urls) {

        ArrayList<New> newsList = new ArrayList<New>() {};

        String jsonResponse = null;
        for (String url:urls) {
            jsonResponse = getNewsData(url);
            ArrayList<New> newsExtracted = extractNews(jsonResponse);
            if (newsExtracted != null) {
                newsList.addAll(newsExtracted);
            }
        }


//        newsList = extractNews(HARDCODED_JSON_STRING);
        return newsList;
    }

    private static String readDataFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        } else {
            return null;
        }

        return output.toString();
    }

    private static ArrayList<New> extractNews(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding news to
        ArrayList<New> newsList = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonObject = new JSONObject(newsJSON);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            if (jsonArray == null || jsonArray.length() <1) {
                return null;
            }
            for (int i=0;i<jsonArray.length();i++) {
                // Getting information from URL Link, parsing through JSON Object
                JSONObject newDataJSONData = jsonArray.getJSONObject(i);
                String author = newDataJSONData.getString("author");
                String title = newDataJSONData.getString("title");
                String description = newDataJSONData.getString("description");
                String articleURL = newDataJSONData.getString("url");
                String imageURL = newDataJSONData.getString("urlToImage");
                String timePublished = newDataJSONData.getString("publishedAt");
                String content = newDataJSONData.getString("content");
                String source = newDataJSONData.getJSONObject("source").getString("name");

                // Creating new article and add to newsList
                New newArticle = new New(imageURL,title,articleURL,timePublished,description,
                        author,content,source);
                newsList.add(newArticle);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return newsList;
    }

    private static String getNewsData(String urlLink) {
        URL urlNewsquakeObject = null;
        String newsStream = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlNewsquakeObject = new URL(urlLink);
            if (urlNewsquakeObject == null) {
                return newsStream;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return newsStream;
        }

        try {
            urlConnection = (HttpURLConnection) urlNewsquakeObject.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                newsStream = readFromStream(inputStream);
            }


        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return newsStream;
    }

    // Reading information from a stream
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
