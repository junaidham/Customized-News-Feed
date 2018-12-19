package tech.ducletran.customizednewsfeedapp;

import android.app.LoaderManager;
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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TechNewsFracment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<New>> {
    private String techAPIList[] = new String[] {
            "https://newsapi.org/v2/top-headlines?sources=wired&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5",
            "https://newsapi.org/v2/top-headlines?sources=hacker-news&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5",
            "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5",
            "https://newsapi.org/v2/top-headlines?sources=techradar&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5",
            "https://newsapi.org/v2/top-headlines?sources=the-verge&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5"
    };
    private boolean isConnected;
    private TextView emptyView;
    private RelativeLayout loadingLayout;
    private NewsAdapter adapter;

    public TechNewsFracment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_news,container,false);

//        // Setting internet connection
//        ConnectivityManager cm =
//                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        isConnected = activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting();
//
//        // Setting view
//        final ListView listView = (ListView) rootView.findViewById(R.id.list);
//        emptyView = (TextView) rootView.findViewById(R.id.empty_text_view);
//        listView.setEmptyView(emptyView);
//        loadingLayout = (RelativeLayout) rootView.findViewById(R.id.loading_layout);
//
//        // Setting the adapter
//        adapter = new NewsAdapter(getActivity(),new ArrayList<New>());
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                New news = adapter.getItem(position);
//                Uri webpage = Uri.parse(news.getArticleURL());
//                Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
//                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    startActivity(intent);
//                }
//            }
//        });
//
//        // Setting the LoaderManager
//        LoaderManager loaderManager = getActivity().getLoaderManager();
//        if (isConnected) {
//            loaderManager.initLoader(1,null,this).forceLoad();
//        } else {
//            loadingLayout.setVisibility(View.GONE);
//            emptyView.setText("No Internet Connection");
//        }

        return rootView;
    }

    @Override
    public Loader<ArrayList<New>> onCreateLoader(int id, Bundle args) {
        return new TechNewsAsyncTaskLoader(getActivity(),techAPIList);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<New>> loader, ArrayList<New> data) {
        emptyView.setText("There are no news.");

        if(adapter != null) {
            adapter.clear();
        }
        adapter.addAll(data);
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<New>> loader) {
        adapter.clear();
    }

    private static class TechNewsAsyncTaskLoader extends AsyncTaskLoader<ArrayList<New>> {
        private String[] urls;

        public TechNewsAsyncTaskLoader(Context context, String[] urls) {
            super(context);
            this.urls = urls;
        }

        @Nullable
        @Override
        public ArrayList<New> loadInBackground() {
            if (urls == null || urls.length <1) {
                return null;
            }
            return QueryUtils.fetchNewsData(urls);

        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }
    }
}
