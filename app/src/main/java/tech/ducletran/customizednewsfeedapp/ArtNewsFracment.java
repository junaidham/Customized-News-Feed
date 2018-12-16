package tech.ducletran.customizednewsfeedapp;

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
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


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
    private static int loadingImage = 0;


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
        return new ArtNewsAsyncTaskLoader(getActivity(),artAPIList);
    }

    @Override
    public void onLoadFinished(Loader<New> loader, New data) {
        Log.d("dd","BIG ERROR: "+ data );
        if (loadingImage == 0 & data != null) {
            final New dailyArtwork = data;
            if (dailyArtwork != null) {
                dailyArtworkTitleTextView.setText(dailyArtwork.getTitle());
                if(dailyArtwork.getTimePublished() != null || !dailyArtwork.getTimePublished().equals("")) {
                    dailyArtworkTimeTextView.setText("Time: "+dailyArtwork.getTimePublished());
                }
                dailyArtworkAuthorTextView.setText("Artist: "+dailyArtwork.getWriter());
                Picasso.get().load(dailyArtwork.getArticleURL()).resize(360,640).into(dailyArtworkImageView);

                dailyArtworkImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),DisplayImageActivity.class);
                        intent.putExtra(EXTRA_MESSAGE,dailyArtwork.getArticleURL());
                        startActivity(intent);
                    }
                });

            }
            loadingImage++;
        }

        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<New> loader) {
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
            return QueryUtils.fetchArtNewsData(url);

        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }


    }
}
