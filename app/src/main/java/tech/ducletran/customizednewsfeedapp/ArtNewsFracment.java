package tech.ducletran.customizednewsfeedapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtNewsFracment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<New>> {
    // Art link API
    String artAPIList[] = new String[] {
                    "https://api.artsy.net/api/artworks?sample=1",
                    "https://newsapi.org/v2/top-headlines?sources=four-four-two&apiKey=a99368fd8b7a4d028fc9aa9664cec212"};

    // Class attribute
    private boolean isConnected;
    private TextView emptyView;
    private RelativeLayout loadingLayout;
    private NewsAdapter adapter;
    private TextView dailyArtworkTitleTextView;
    private ImageView dailyArtworkImageView;
    private TextView dailyArtworkAuthorTextView;
    private TextView dailyArtworkTimeTextView;

    public ArtNewsFracment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.art_list_news,container,false);

        // Setting internet connection
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Setting view
        final ListView listView = (ListView) rootView.findViewById(R.id.list);
        emptyView = (TextView) rootView.findViewById(R.id.empty_text_view);
        listView.setEmptyView(emptyView);
        loadingLayout = (RelativeLayout) rootView.findViewById(R.id.loading_layout);
        dailyArtworkTitleTextView = (TextView) rootView.findViewById(R.id.daily_art_name_text_view);
        dailyArtworkImageView = (ImageView) rootView.findViewById(R.id.daily_art_thumbnail_image_view);
        dailyArtworkAuthorTextView = (TextView) rootView.findViewById(R.id.daily_art_artist_text_view);
        dailyArtworkTimeTextView = (TextView) rootView.findViewById(R.id.daily_art_date_text_view);

        // Setting adapter
        adapter = new NewsAdapter(getActivity(),new ArrayList<New>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                New news = adapter.getItem(position);
                Uri webpage = Uri.parse(news.getArticleURL());
                Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // Setting the LoaderManager
        LoaderManager loaderManager = getActivity().getLoaderManager();
        if (isConnected) {
            loaderManager.initLoader(0,null,this).forceLoad();
        } else {
            loadingLayout.setVisibility(View.GONE);
            emptyView.setText("No Internet Connection");
        }

        return rootView;
    }

    @Override
    public Loader<ArrayList<New>> onCreateLoader(int id, Bundle args) {
        return new ArtNewsAsyncTaskLoader(getActivity(),artAPIList);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<New>> loader, ArrayList<New> data) {
        emptyView.setText("There are no news.");

        if (adapter != null) {
            adapter.clear();
        }
        New dailyArtwork = data.get(0);
        if (dailyArtwork != null) {
            dailyArtworkTitleTextView.setText(dailyArtwork.getTitle());
            if(dailyArtwork.getTimePublished() != null || !dailyArtwork.getTimePublished().equals("")) {
                dailyArtworkTimeTextView.setText("Time: "+dailyArtwork.getTimePublished());
            }
            dailyArtworkAuthorTextView.setText("Artist: "+dailyArtwork.getWriter());
            Picasso.get().load(dailyArtwork.getImageURL()).into(dailyArtworkImageView);
        }


        adapter.addAll(data.subList(1,data.size()));
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<New>> loader) {
        adapter.clear();

    }

    private static class ArtNewsAsyncTaskLoader extends AsyncTaskLoader<ArrayList<New>> {
        private String[] urls;

        public ArtNewsAsyncTaskLoader(Context context, String[] urls) {
            super(context);
            this.urls = urls;
        }

        @Nullable
        @Override
        public ArrayList<New> loadInBackground() {
            if (urls == null || urls.length <1) {
                return null;
            }
            return QueryUtils.fetchArtNewsData(urls);

        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
}
