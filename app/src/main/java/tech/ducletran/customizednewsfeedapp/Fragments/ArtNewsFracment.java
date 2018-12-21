package tech.ducletran.customizednewsfeedapp.Fragments;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tech.ducletran.customizednewsfeedapp.Activity.DisplayImageActivity;
import tech.ducletran.customizednewsfeedapp.OtherResource.New;
import tech.ducletran.customizednewsfeedapp.QueryUtils.ArtQueryUtils;
import tech.ducletran.customizednewsfeedapp.QueryUtils.QueryUtils;
import tech.ducletran.customizednewsfeedapp.R;


public class ArtNewsFracment extends Fragment implements LoaderManager.LoaderCallbacks<New>{
    // Art link API
    String artAPIList = "https://api.artsy.net/api/artworks?sample=1";

    // Class attribute
    private boolean isConnected;
    private RelativeLayout loadingLayout;
    private TextView dailyArtworkTitleTextView;
    private ImageView dailyArtworkImageView;
    private TextView dailyArtworkAuthorTextView;
    private TextView dailyArtworkTimeTextView;
    public static final String EXTRA_MESSAGE = "tech.ducletran.customizednewsfeedapp.MESSAGE";
    private LoaderManager loaderManager;
    private static boolean isArtLoaded = false;
    private String artTitle = "";
    private String artist = "";
    private String artTime = "";
    private String artURL = null;
    private int loadingView = View.VISIBLE;

    public ArtNewsFracment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.art_list_news,container,false);

        // Setting internet connection
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Setting view
        loadingLayout =  rootView.findViewById(R.id.loading_layout);
        dailyArtworkTitleTextView =  rootView.findViewById(R.id.daily_art_name_text_view);
        dailyArtworkImageView =  rootView.findViewById(R.id.daily_art_thumbnail_image_view);
        dailyArtworkAuthorTextView = rootView.findViewById(R.id.daily_art_artist_text_view);
        dailyArtworkTimeTextView = rootView.findViewById(R.id.daily_art_date_text_view);

        // Setting the LoaderManager
        loaderManager = getActivity().getLoaderManager();
        if (isConnected) {
            loaderManager.initLoader(0,null,this).forceLoad();

        } else {
            loadingLayout.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public Loader<New> onCreateLoader(int id, Bundle args) {
        Log.d("DD","CHECK LOADER MANAGER LOADING");
        return new ArtNewsAsyncTaskLoader(getActivity(),artAPIList);
    }

    @Override
    public void onLoadFinished(Loader<New> loader, final New dailyArtwork) {

        if (!isArtLoaded && dailyArtwork != null) {
            artTitle = dailyArtwork.getTitle();
            if(TextUtils.isEmpty(dailyArtwork.getTimePublished())) {
                artTitle = "Time: "+dailyArtwork.getTimePublished();
            } else {
                artTime = "Time: Unknown";
            }
            artist = dailyArtwork.getWriter();
            artURL = dailyArtwork.getArticleURL();

            isArtLoaded = true;
            loadingView = View.GONE;
        }

        // Set visual for view
        dailyArtworkTitleTextView.setText(artTitle);
        dailyArtworkTimeTextView.setText(artTime);
        dailyArtworkAuthorTextView.setText(artist);
        if (artURL != null) {
            Picasso.get().load(artURL).resize(360,640).into(dailyArtworkImageView);
            dailyArtworkImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),DisplayImageActivity.class);
                    intent.putExtra(EXTRA_MESSAGE,artURL);
                    startActivity(intent);
                }
            });
        }
        loadingLayout.setVisibility(loadingView);

    }

    @Override
    public void onLoaderReset(Loader<New> loader) {
        loader.abandon();
    }


    private static class ArtNewsAsyncTaskLoader extends AsyncTaskLoader<New> {
        private String url;

        public ArtNewsAsyncTaskLoader(Context context, String url) {
            super(context);
            this.url = url;
        }

        @Nullable
        @Override
        public New loadInBackground() {
            if (url == null ) {
                return null;
            }
            return ArtQueryUtils.fetchArtNewsData(url);

        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }


    }
}
