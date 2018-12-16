package tech.ducletran.customizednewsfeedapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

        return newsList;
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
        URL urlNewsObject;
        String newsStream = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlNewsObject = new URL(urlLink);
            if (urlNewsObject == null) {
                return newsStream;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return newsStream;
        }

        try {
            urlConnection = (HttpURLConnection) urlNewsObject.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                newsStream = readFromStream(inputStream);
            } else {
                Log.e("QueryUtils - GetNewsD..","Response code is: "+urlConnection.getResponseCode());

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


    /*
     * Getting artwork SECTION
     */
    // Fetching data
    public static New fetchArtNewsData(String url) {
        New dailyArtNew = getDailyArtwork(url);

        return dailyArtNew;
    }

    // Get the Art News data
    private static String getArtNewsData(String urlLink) {
        URL urlNewsObject = null;
        String newsStream = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlNewsObject = new URL(urlLink);
            if (urlNewsObject == null) {
                return newsStream;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return newsStream;
        }

        try {
            String token = getTokenArtwork();
            urlConnection = (HttpURLConnection) urlNewsObject.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("X-Xapp-Token",token);
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                newsStream = readFromStream(inputStream);
            } else {
                Log.e("QueryUtils - getArt...","Response code is: "+urlConnection.getResponseCode());

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

        Log.d("QueryUtils - getArt...",newsStream);
        return newsStream;
    }

    // Get the artwork New object from url link
    private static New getDailyArtwork(String url) {
        String jsonResponse = getArtNewsData(url);

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;

        }
        New dailyArtwork;

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject urls = jsonObject.getJSONObject("_links");
            String date = jsonObject.getString("date");
            String title = jsonObject.getString("title");
            String image_version = jsonObject.getJSONArray("image_versions").getString(0);
            String urlImage = urls.getJSONObject("image").getString("href").replace("{image_version}",image_version);
            String urlThumbnail = urls.getJSONObject("thumbnail").getString("href");

            String jsonArtistResponse = getArtNewsData(urls.getJSONObject("artists").getString("href"));

            if (TextUtils.isEmpty(jsonArtistResponse)) {
                return null;
            }
            JSONObject jsonArtistObject = new JSONObject(jsonArtistResponse);
            String artist = jsonArtistObject.getJSONObject("_embedded").getJSONArray("artists").getJSONObject(0).getString("name");

            dailyArtwork = new New(urlThumbnail,title,urlImage,date,image_version,artist,null,null);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return dailyArtwork;
    }


    // Getting token
    private static String getTokenArtwork() {
        StringBuilder tokenReader = new StringBuilder();
        URL urlLink = null;
        try {
            urlLink = new URL("https://api.artsy.net/api/tokens/xapp_token");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        // Setting up JSONObject for toen
        JSONObject tokenBody = new JSONObject();
        try {
            tokenBody.put("client_id","2290fd27c2737d8b22f9");
            tokenBody.put("client_secret","d2cb44ef5d0e85d8829b4f9f10ed30e7");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        // Setting connection for POST request
        try {
            HttpURLConnection con = (HttpURLConnection) urlLink.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json");

            // Post the request
            DataOutputStream writer = new DataOutputStream(con.getOutputStream());
            writer.writeBytes(tokenBody.toString());
            writer.flush();
            writer.close();

            if (con.getResponseCode() == 201) {
                Log.d("TOKEN","Token: HEY");
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(),Charset.forName("UTF-8")));
                String line = reader.readLine();
                while (line != null) {
                    tokenReader.append(line);
                    line = reader.readLine();
                }
            } else {
                Log.e("ARTWORK TOKEN","Response Code: " + con.getResponseCode());
            }

            // Get the response token
        } catch (IOException e) {
            e.printStackTrace();
        }

        String token = null;
        try {
            token = new JSONObject(tokenReader.toString()).getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

}
