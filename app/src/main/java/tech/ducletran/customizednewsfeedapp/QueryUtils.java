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
    private static final String HARDCODED_JSON_STRING = "{\"status\":\"ok\",\"totalResults\":411,\"articles\":[{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Louise Matsakis\",\"title\":\"At a New York Privacy Pop-Up, Facebook Sells Itself\",\"description\":\"The one-day pop-up kiosk is meant to show that Facebook takes users’ privacy concerns seriously. It also was an opportunity to gather more data.\",\"url\":\"https://www.wired.com/story/facebook-nyc-privacy-pop-up/\",\"urlToImage\":\"https://media.wired.com/photos/5c12d0dee1431d5ffc3f79fa/191:100/pass/facebook_consumer_privacy-02.jpg\",\"publishedAt\":\"2018-12-14T00:38:25Z\",\"content\":\"If you havent heard, 2018 was extremely bad for Facebook. The company was rocked by so many scandals that its become hard to list them all in one place. I wont try here, but I will say its equally difficult to determine how much those missteps really matter t… [+4830 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Emily Dreyfuss\",\"title\":\"Nationwide Bomb Threats Look Like New Spin on an Old Bitcoin Scam\",\"description\":\"Apparent bitcoin scammers caused chaos across the US Thursday, radically escalating longstanding tactics.\",\"url\":\"https://www.wired.com/story/bomb-threats-bitcoin-scam/\",\"urlToImage\":\"https://media.wired.com/photos/5c12f3dc7688383b5505a2f3/191:100/pass/BombThreat-503032224.jpg\",\"publishedAt\":\"2018-12-14T00:12:39Z\",\"content\":\"In offices and universities all across the country Thursday, the same threat appeared in email inboxes: Pay $20,000 worth of bitcoin, or a bomb will detonate in your building. Police departments sent out alerts. Workers from Los Angeles to Raleigh, North Caro… [+6217 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Jack Stewart\",\"title\":\"Virgin Galactic Takes Off, and Space Tourism Draws Nearer\",\"description\":\"Richard Branson's extra-planetary effort takes a giant leap toward realizing its dream of making space tourism a reality.\",\"url\":\"https://www.wired.com/story/virgin-galactic-space-vss-unity-flight/\",\"urlToImage\":\"https://media.wired.com/photos/5c12e820d396be0cb1d2b1e2/191:100/pass/VSSUnity_120316_VG01_A2A_OO021.jpg\",\"publishedAt\":\"2018-12-13T23:29:31Z\",\"content\":\"For the first time since the US retired the Space Shuttle in 2011, humans have taken off from American soil and gone into space. This morning, Richard Branson's Virgin Galactic rocketed two test pilots beyond Earth's boundaries and brought them back safely, i… [+3645 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Lily Hay Newman\",\"title\":\"Facebook Bug Bounty Makes Biggest Payout Yet\",\"description\":\"Despite Cambridge Analytica and a damaging hack, Facebook's bug bounty program offers a bright spot.\",\"url\":\"https://www.wired.com/story/facebook-bug-bounty-biggest-payout/\",\"urlToImage\":\"https://media.wired.com/photos/5c11a2704eefba6112fac0e5/191:100/pass/facebook_bugs1-01.jpg\",\"publishedAt\":\"2018-12-13T21:00:00Z\",\"content\":\"This has not been Facebook's proudest year for privacy and security. The company faced the massive Cambridge Analytica data misuse and abuse scandal in April and beyond. It disclosed its first data breach in October, which compromised information from 30 mill… [+4872 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Brian Raftery\",\"title\":\"Radiohead Will Enter the Rock and Roll Hall of Fame in 2019\",\"description\":\"Plus: Miley Cyrus will appear on the next season of *Black Mirror*.\",\"url\":\"https://www.wired.com/story/radiohead-rock-and-roll-hall-of-fame/\",\"urlToImage\":\"https://media.wired.com/photos/5c12a71422337f072b621f71/191:100/pass/Radiohead-534645430.jpg\",\"publishedAt\":\"2018-12-13T19:49:42Z\",\"content\":\"It's time once again to turn on The Monitor, WIRED's roundup of the latest in the world of culture, from music news to casting announcements. In today's installment: Radiohead gets the \\\"OK\\\" from the Rock and Roll Hall of Fame; Miley Cyrus looks into Black Mir… [+2133 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"WIRED Autocomplete Interviews: Season 1\",\"title\":\"WIRED Autocomplete Interviews - Lin-Manuel Miranda Answers the Web's Most Searched Questions\",\"description\":\"\\\"Mary Poppins Returns\\\" star Lin-Manuel Miranda takes the WIRED Autocomplete Interview and answers the internet's most searched questions about himself. Which Hamilton song is Lin-Manuel Miranda's favorite? Is Lin-Manuel knighted? When did he cut his hair?\\r\\n\\r\\n…\",\"url\":\"http://video.wired.com/watch/lin-manuel-miranda-answers-the-web-s-most-searched-questions\",\"urlToImage\":\"http://dwgyu36up6iuz.cloudfront.net/heru80fdn/image/upload/c_fill,d_placeholder_thescene.jpg,fl_progressive,g_face,h_450,q_80,w_800/v1544651031/wired_lin-manuel-miranda-answers-the-web-s-most-searched-questions.jpg\",\"publishedAt\":\"2018-12-13T17:00:00Z\",\"content\":\"1 World Trade Center, New York, NY 10007 Tel (212) 286-2860 © 2018 Condé Nast. All rights reserved. Use of and/or registration on any portion of this site constitutes acceptance of our User Agreement (updated 5/25/18) and Privacy Policy (updated 5/25/18). You… [+237 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Angela Watercutter\",\"title\":\"'Spider-Man: Into the Spider-Verse' Reveals the Hero's Future\",\"description\":\"After umpteen versions of Peter Parker, the new animated feature gives fans the multidimensional hero they deserve.\",\"url\":\"https://www.wired.com/story/spider-man-into-the-spider-verse-review/\",\"urlToImage\":\"https://media.wired.com/photos/5c1168d41d856b064de84729/191:100/pass/SpiderVerse_pef735.1187_FA.jpg\",\"publishedAt\":\"2018-12-13T17:00:00Z\",\"content\":\"Nothing has followed Spider-Man's long and varied character history quite like one single phrase: \\\"With great power comes great responsibility.\\\" Generally attributed to Peter Parker's Uncle Ben, it long ago left the world of Marvel Comics to become a general-… [+4898 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Paris Martineau\",\"title\":\"Startup Founders Think Real Progress on Diversity Is Years Away\",\"description\":\"Most startup founders think it’ll take more than a decade for the tech workforce to look like America; more than one-third think it will take more than two decades.\",\"url\":\"https://www.wired.com/story/startup-founders-think-real-progress-diversity-years-away/\",\"urlToImage\":\"https://media.wired.com/photos/5c11bc7c01961b246f1937d8/191:100/pass/Tech-Diversity-563937187.jpg\",\"publishedAt\":\"2018-12-13T15:00:00Z\",\"content\":\"Tech has a diversity problem. This isnt new. Women and minorities have long been woefully underrepresented in startup land, a problem that founders have insisted they are trying their best to fix. However, a new survey conducted by venture firm First Round Ca… [+2758 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Arielle Pardes\",\"title\":\"Postmates' Quest to Build the Delivery Robot of the Future\",\"description\":\"The company has spent the past two years stealthily learning how to design and build its own delivery robots, coming soon to a city near you.\",\"url\":\"https://www.wired.com/story/postmates-delivery-robot-serve/\",\"urlToImage\":\"https://media.wired.com/photos/5c11be33924a0771108790f4/191:100/pass/postmates.jpg\",\"publishedAt\":\"2018-12-13T14:00:00Z\",\"content\":\"Hanging on the wall of Postmates' stealth R&amp;D laboratory, there's a framed photo of an iconic scene from Star Wars, Luke Skywalker bent down beside R2D2. Except someone has used Photoshop to replace Luke's face with Ali Kashani, Postmates' VP of Robotics.… [+11376 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Megan Molteni\",\"title\":\"A Designer Seed Company Is Building a Farming Panopticon\",\"description\":\"Indigo Ag, known for its microbe-coated seeds, is acquiring geospatial data startup TellusLabs to use satellites to learn every last thing about its farmers’ fields.\",\"url\":\"https://www.wired.com/story/a-designer-seed-company-is-building-a-farming-panopticon/\",\"urlToImage\":\"https://media.wired.com/photos/5c11647863fd53044a49f9e2/191:100/pass/irrigated_ag_colorado.jpg\",\"publishedAt\":\"2018-12-13T14:00:00Z\",\"content\":\"When Geoffrey von Maltzahn was first pitching farmers to try out his startups special seeds, he sometimes told them, half-acknowledging his own hyperbole, that if were right, you shouldnt just see results in the field, you should be able to see them from oute… [+6177 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Jack Stewart\",\"title\":\"Aston Martin's $3M Valkyrie Hypercar Gets a V12 Engine\",\"description\":\"The forthcoming hypercar will draw more than 1,000 horsepower from the naturally aspirated engine, made with help from Formula 1 engineers.\",\"url\":\"https://www.wired.com/story/aston-martin-valkyrie-engine-v12-f1-cosworth/\",\"urlToImage\":\"https://media.wired.com/photos/5c11856ea63a3e590e5308df/191:100/pass/1-FA.jpg\",\"publishedAt\":\"2018-12-13T14:00:00Z\",\"content\":\"Less than a decade ago, an automobile having an engine that pumped out 300 horsepower was all it took to earn the moniker of muscle car, along with a certain level of respect on the road. Anything above 500 hp was bonkers, reserved for the likes of McLaren's … [+3287 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Michael Calore\",\"title\":\"Gift Ideas for the Neat Freak: Coasters, Desk Organizers, Slippers\",\"description\":\"From coasters and slippers to elegant lifestyle accoutrements, this collection of beautiful objects will keep their home looking perfect.\",\"url\":\"https://www.wired.com/gallery/gift-ideas-for-neat-freaks/\",\"urlToImage\":\"https://media.wired.com/photos/5a6a75d43766960ab49fc4df/191:100/pass/12-dustpan-SOURCE-Oxo-Good-Grips.jpg\",\"publishedAt\":\"2018-12-13T13:04:23Z\",\"content\":null},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Ellen Airhart\",\"title\":\"How Zoos Protect Animals When Natural Disasters Strike\",\"description\":\"With hurricanes and wildfires becoming more frequent and intense, zoos are facing the staggering task of protecting animals while the world runs amok.\",\"url\":\"https://www.wired.com/story/even-zoos-are-learning-the-art-of-doomsday-prepping/\",\"urlToImage\":\"https://media.wired.com/photos/5c1197b9e4cb650e808493a0/191:100/pass/Science-Zoo-Wildfires-1059590024-w.jpg\",\"publishedAt\":\"2018-12-13T13:00:00Z\",\"content\":\"When smoke from Californias wildfires was smothering the Bay Area last month, the Oakland Zoo closed to the public. The staff worked in shifts, many of them wearing N95 face masks, monitoring how animals dealt with the smoke from the fires more than a hundred… [+4510 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Emma Grey Ellis\",\"title\":\"Social Media Is Terrible For Mental Health. But It Could Also Help\",\"description\":\"The internet holds potential solutions to pernicious mental health issues. Harnessing that potential is proving complicated.\",\"url\":\"https://www.wired.com/story/social-media-mental-health-terrible-salvation/\",\"urlToImage\":\"https://media.wired.com/photos/5c100d9ec133b122740c45bb/191:100/pass/mental_health_apps-02.jpg\",\"publishedAt\":\"2018-12-13T12:02:29Z\",\"content\":\"The internet, you have undoubtedly heard, is bad for your brain. It can be especially damaging for those struggling with mental illness. Trolls are everywhere. Searching for information about your struggles may lead you into dark places. Websites that promote… [+6249 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Klint Finley\",\"title\":\"How Facebook Made a Universal Open Source Language for the Web\",\"description\":\"GraphQL is a widely used tool allowing applications written in different programming languages to talk to one another.\",\"url\":\"https://www.wired.com/story/how-facebook-made-universal-open-source-language-web/\",\"urlToImage\":\"https://media.wired.com/photos/5c11bfb0abea2a03bbe69810/191:100/pass/LaTigre-headers2400-A6.jpg\",\"publishedAt\":\"2018-12-13T12:00:00Z\",\"content\":\"The code that runs the web is a melting pot of programming languages and technologies. JavaScript, the most popular language on the web, is the standard for writing code that runs in your browser. But the server side is much more diverse. Java (no relationshi… [+6164 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Charles Duhigg\",\"title\":\"Dr. Elon & Mr. Musk: Life Inside Tesla's Production Hell\",\"description\":\"Unfettered genius. Unpredictable rages. Here's what it was like to work at Tesla as Model 3 manufacturing ramped up and the company's leader melted down.\",\"url\":\"https://www.wired.com/story/elon-musk-tesla-life-inside-gigafactory/\",\"urlToImage\":\"https://media.wired.com/photos/5c10101a58e5b62d2b29ef0a/191:100/pass/Web%20Social%20Art%20-%20Cars%20Elon%20Musk.jpg\",\"publishedAt\":\"2018-12-13T11:00:00Z\",\"content\":\"The young Tesla engineer was excited. Ecstatic, in fact. It was a Saturday in October 2017, and he was working at the Gigafactory, Teslas enormous battery manufacturing plant in Nevada. Over the previous year, he had been living out of a suitcase, putting in … [+54981 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Adam Rogers\",\"title\":\"Marvel Comics Genius Stan Lee Turned Outcasts Into Heroes\",\"description\":\"Stan Lee, who died in November at the age of 95, turned comic books into art through his ability to empower others.\",\"url\":\"https://www.wired.com/story/marvel-comics-genius-stan-lee-outcasts-heroes/\",\"urlToImage\":\"https://media.wired.com/photos/5c0ef870b88dcf34238efa1d/191:100/pass/Stan-Lee_Feature-Art_Jeff-Minton.jpg\",\"publishedAt\":\"2018-12-13T11:00:00Z\",\"content\":\"If you havent spent your life reading comic books, they can seem weird. Like any medium, like movies or books or podcasts, comics have their own informational syntax. The pairing of static images with cartoon indicators of motion, typographically distinct ono… [+19795 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Klint Finley\",\"title\":\"5G: The Complete WIRED Guide\",\"description\":\"Here's everything you'll ever want to know about the spectrum, millimeter wave technology, and on why 5G could give China an edge in the AI race.\",\"url\":\"https://www.wired.com/story/wired-guide-5g/\",\"urlToImage\":\"https://media.wired.com/photos/5c116434ef4db4155a5e8611/191:100/pass/2018_12_07_Wired_guides_PhonesToCars.png\",\"publishedAt\":\"2018-12-13T11:00:00Z\",\"content\":\"The future depends on connectivity. From artificial intelligence and self-driving cars to telemedicine and mixed reality to as yet undreamt technologies, all the things we hope will make our lives easier, safer, and healthier will require high-speed, always-o… [+14652 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Garrett M. Graff\",\"title\":\"9 Trumpworld Figures That Should Fear Mueller the Most\",\"description\":\"After Michael Cohen's sentencing, plenty more people and entities in Trump's orbit potentially sit in the special counsel's crosshairs.\",\"url\":\"https://www.wired.com/story/mueller-investigation-targets-cohen-sentencing/\",\"urlToImage\":\"https://media.wired.com/photos/5c112df95021782da245e02a/191:100/pass/Security-Cohen_sentencing-1072150526-w.jpg\",\"publishedAt\":\"2018-12-12T21:20:55Z\",\"content\":\"Wednesdays sentencing of one-time Trump fixer Michael Cohen to 36 months of prison continues a frenetic pace of revelations around the Russia probe. Nearly every single day since Thanksgiving has brought fresh developments in special counsel Robert Muellers p… [+11762 chars]\"},{\"source\":{\"id\":\"wired\",\"name\":\"Wired\"},\"author\":\"Eric Niiler\",\"title\":\"In the Sierras and Rockies, the Snowpack Is Shrinking Fast\",\"description\":\"An analysis of 36 years of snowpack shows that the US's peaks are shrinking, and that means more wildfires, less drinking water ... and less skiing.\",\"url\":\"https://www.wired.com/story/as-snow-disappears-the-sierras-and-rockies-are-shrinking/\",\"urlToImage\":\"https://media.wired.com/photos/5c115ebca5f1337c8d9e176f/191:100/pass/sierranevada-640990522.jpg\",\"publishedAt\":\"2018-12-12T19:31:42Z\",\"content\":\"The mountains of the High Sierra and the Rockies are, in effect, shrinking, according to a new analysis of the nations snowpack over the past 36 years. These places are experiencing a shorter winter with less snow, just like regions closer to sea level. Thats… [+3444 chars]\"}]}";

    public QueryUtils() {

    }

    public static ArrayList<New> fetchNewsData(String url) {

        ArrayList<New> newsList = null;

        String jsonResponse = null;
        jsonResponse = getNewsData(url);

        newsList = extractNews(jsonResponse);

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
            JSONObject jsonObject = new JSONObject(HARDCODED_JSON_STRING);
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

                // Creating new article and add to newsList
                New newArticle = new New(imageURL,title,articleURL,timePublished,description,
                        author,content);
                newsList.add(newArticle);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        Log.d("NewsActivity",""+newsList.size() +" ABCDEEE");
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
