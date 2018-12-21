package tech.ducletran.customizednewsfeedapp.Fragments;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tech.ducletran.customizednewsfeedapp.OtherResource.MusicAdapter;
import tech.ducletran.customizednewsfeedapp.OtherResource.MusicTrack;
import tech.ducletran.customizednewsfeedapp.QueryUtils.MusicQueryUtils;
import tech.ducletran.customizednewsfeedapp.R;

public class MusicNewsFracment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MusicTrack>[]> {

    // Class attribute
    private boolean isConnected;
    private final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";
    private final String API_KEY = "c4acda16e368a3806000f96b3b9a8eb5";
    private final String TOP_CHART_METHOD = "chart.gettoptracks";
    private final String TOP_CHART_TAG_METHOD = "tag.gettoptracks";

    private String urls[];
    private MusicAdapter topSongAdapter;
    private MusicAdapter topSongTagAdapter;
    private TextView topSongEmptyView;
    private TextView topSongTagEmptyView;

    public MusicNewsFracment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_music_news,container,false);

        // Setting internet connection
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        urls = generateURL("");

        // Setting view
        final ListView topSongListView = rootView.findViewById(R.id.top_song_list_view);
        topSongEmptyView = rootView.findViewById(R.id.empty_top_song_text_view);
        final ListView topSongTagListView = rootView.findViewById(R.id.top_song_tag_list_view);
        topSongTagEmptyView = rootView.findViewById(R.id.empty_top_song_tag_text_view);
        final EditText getTagView = rootView.findViewById(R.id.tag_edit_text);
        Button getTagChartButton = rootView.findViewById(R.id.get_tag_chart_button);

        // Set empty view
        topSongListView.setEmptyView(topSongEmptyView);
        topSongTagListView.setEmptyView(topSongTagEmptyView);


        // Setting adapter
        topSongAdapter = new MusicAdapter(getActivity(),new ArrayList<MusicTrack>());
        topSongTagAdapter = new MusicAdapter(getActivity(),new ArrayList<MusicTrack>());
        topSongListView.setAdapter(topSongAdapter);
        topSongTagListView.setAdapter(topSongTagAdapter);

        // Setting the LoaderManager
        final LoaderManager loaderManager = getActivity().getLoaderManager();
        if (isConnected) {
            loaderManager.initLoader(2,null,this).forceLoad();
        } else {
            topSongEmptyView.setText("No Internet Connection");
            topSongTagEmptyView.setText("No Internet Connection");
        }

        // Set click on tag chart
        getTagChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = getTagView.getText().toString();
                urls = generateURL(tag);

                getTagView.setText("");
                loaderManager.restartLoader(2,null,MusicNewsFracment.this).forceLoad();
            }
        });

        return rootView;
    }

    @Override
    public Loader<ArrayList<MusicTrack>[]> onCreateLoader(int id, Bundle args) {
        return new MusicNewsAsyncTaskLoader(getContext(),urls);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MusicTrack>[]> loader, ArrayList<MusicTrack>[] data) {
        topSongEmptyView.setText("There is no songs loaded.");
//        topSongTagEmptyView.setText("There are no song loaded");

        if(topSongAdapter != null) {
            topSongAdapter.clear();
        }
        if (topSongTagAdapter != null) {
            topSongTagAdapter.clear();
        }
        topSongAdapter.addAll(data[0]);
        if (data[1] != null) {
            topSongTagAdapter.addAll(data[1]);
        }

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MusicTrack>[]> loader) {
        topSongTagAdapter.clear();
        topSongAdapter.clear();
    }


    private static class MusicNewsAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MusicTrack>[]> {
        private String[] urls;

        public MusicNewsAsyncTaskLoader(Context context, String[] urls) {
            super(context);
            this.urls = urls;
        }

        @Nullable
        @Override
        public ArrayList<MusicTrack>[] loadInBackground() {

            return MusicQueryUtils.fetchMusicNewsData(this.urls);

        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }

    private String[] generateURL(String tag) {
        String[] urls = new String[2];
        String topChartURL = BASE_URL + "?method=" + TOP_CHART_METHOD + "&api_key=" + API_KEY + "&format=json";
        if (tag != "") {
            String topChartTagURL =  BASE_URL + "?method=" + TOP_CHART_TAG_METHOD + "&tag=" + tag +
                    "&api_key=" + API_KEY + "&format=json";
            urls[1] = topChartTagURL;
        }

        urls[0] = topChartURL;

        return urls;
    }
}
