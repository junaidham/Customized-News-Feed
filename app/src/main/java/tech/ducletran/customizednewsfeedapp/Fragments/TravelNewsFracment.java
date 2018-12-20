package tech.ducletran.customizednewsfeedapp.Fragments;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tech.ducletran.customizednewsfeedapp.OtherResource.New;
import tech.ducletran.customizednewsfeedapp.OtherResource.TravelAdapter;
import tech.ducletran.customizednewsfeedapp.QueryUtils.TravelQueryUtils;
import tech.ducletran.customizednewsfeedapp.R;

public class TravelNewsFracment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<New>> {
    // Travel Link API
    private final String travelLink = "https://developers.musement.com/api/v3/cities/";


    // Class attribute
    private boolean isConnected;
    private TextView emptyView;
    private RelativeLayout loadingLayout;
    private TravelAdapter adapter;

    public TravelNewsFracment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_news,container,false);

        // Setting internet connection
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
//
        // Setting view
        final ListView listView = (ListView) rootView.findViewById(R.id.list);
        emptyView = (TextView) rootView.findViewById(R.id.empty_text_view);
        listView.setEmptyView(emptyView);
        loadingLayout = (RelativeLayout) rootView.findViewById(R.id.loading_layout);

        // Setting adapter
        adapter = new TravelAdapter(getActivity(),new ArrayList<New>());
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
        android.app.LoaderManager loaderManager = getActivity().getLoaderManager();
        if (isConnected) {
            loaderManager.initLoader(3,null,this).forceLoad();
        } else {
            loadingLayout.setVisibility(View.GONE);
            emptyView.setText("No Internet Connection");
        }

        return rootView;

    }

    @Override
    public Loader<ArrayList<New>> onCreateLoader(int id, Bundle args) {
        return new TravelNewsAsyncTaskLoader(getActivity(),getTravelAPILink(travelLink,12));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<New>> loader, ArrayList<New> data) {
        emptyView.setText("There is no city loaded");

        if(adapter != null) {
            adapter.clear();
        }
        adapter.addAll(data);
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<New>> loader) {

    }


    private static class TravelNewsAsyncTaskLoader extends AsyncTaskLoader<ArrayList<New>> {
        private String[] urls;

        public TravelNewsAsyncTaskLoader(Context context, String[] urls) {
            super(context);
            this.urls = urls;
        }

        @Nullable
        @Override
        public ArrayList<New> loadInBackground() {
            return TravelQueryUtils.fetchTravelNewsData(urls);

        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }

    // Method to generate random id for city
    private String[] getTravelAPILink(String baseURL, int numberOfCity) {
        String[] cityListURL = new String[numberOfCity];

        while (cityListURL[numberOfCity-1] == null) {
            int i =0;
            while (i<numberOfCity) {
                int cityId = ((int)(Math.random()*617)) + 1;
                boolean isPlaced = false;
                for (int j=0;j<=i;j++) {
                    if (cityListURL[j] == baseURL+cityId) {
                        isPlaced = true;
                    }
                }
                if (!isPlaced) {
                    cityListURL[i] = baseURL + cityId;
                    i++;
                }
            }
        }
        for (int i = 0;i<numberOfCity;i++) {
            Log.d("HELLO","HELLO WORLD: "+cityListURL[i]);
        }
        return cityListURL;
    }
}