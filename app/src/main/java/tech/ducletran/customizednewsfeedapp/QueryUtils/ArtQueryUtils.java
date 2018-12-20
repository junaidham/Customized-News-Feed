package tech.ducletran.customizednewsfeedapp.QueryUtils;

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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import tech.ducletran.customizednewsfeedapp.OtherResource.New;

public class ArtQueryUtils {

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
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                newsStream = QueryUtils.readFromStream(inputStream);
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
            String urlThumbnail;
            if (urls.getJSONObject("thumbnail") != null) {
                urlThumbnail = urls.getJSONObject("thumbnail").getString("href");
            } else {
                urlThumbnail = urlImage;
            }

            String jsonArtistResponse = getArtNewsData(urls.getJSONObject("artists").getString("href"));

            if (TextUtils.isEmpty(jsonArtistResponse)) {
                return null;
            }
            JSONObject jsonArtistObject = new JSONObject(jsonArtistResponse);
            JSONArray artistListArray = jsonArtistObject.getJSONObject("_embedded").getJSONArray("artists");
            String artist = "Unknown";
            if (!(artistListArray.length() == 0)) {
                artist = jsonArtistObject.getJSONObject("_embedded").getJSONArray("artists").getJSONObject(0).getString("name");
            }

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
