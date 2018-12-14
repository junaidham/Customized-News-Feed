package tech.ducletran.customizednewsfeedapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<New>> {
    // List of Tech API Website
    private String techAPIList[] = new String[] {
            "https://newsapi.org/v2/top-headlines?sources=wired&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5",
            "https://newsapi.org/v2/top-headlines?sources=hacker-news&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5",
            "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5",
            "https://newsapi.org/v2/top-headlines?sources=techradar&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5",
            "https://newsapi.org/v2/top-headlines?sources=the-verge&apiKey=a99368fd8b7a4d028fc9aa9664cec212&pageSize=5"
    };

    // Private attribute of the class
    private String LOG_TAG = "NewsActivity";
    private NewsAdapter adapter;
    private ArrayList<New> articleList;
    private TextView emptyView;
    private boolean isConnected;
    private RelativeLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Getting basic layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Getting connection
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Getting items from view
        ListView listView = (ListView) findViewById(R.id.list);
        emptyView = (TextView) findViewById(R.id.empty_text_view);
        listView.setEmptyView(emptyView);
        loadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);

        // Setting the adapter
        adapter = new NewsAdapter(this,new ArrayList<New>());
        listView.setAdapter(adapter);

        // Callback method when click on items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                New news = adapter.getItem(position);
                Uri webpage = Uri.parse(news.getArticleURL());
                Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        // Setting the LoaderManager
        LoaderManager loaderManager = getLoaderManager();
        if (isConnected) {
            loaderManager.initLoader(1,null,this).forceLoad();
        } else {
            loadingLayout.setVisibility(View.GONE);
            emptyView.setText("No Internet Connection");
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<New>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new NewsAsyncTaskLoader(this,techAPIList);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<New>> loader, ArrayList<New> news) {
        emptyView.setText("There are no news.");

        if (adapter != null) {
            adapter.clear();
        }
        adapter.addAll(news);
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<New>> loader) {
        adapter.clear();
    }

    private static class NewsAsyncTaskLoader extends AsyncTaskLoader<ArrayList<New>> {
        private String[] urls;

        public NewsAsyncTaskLoader(Context context, String[] urls) {
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
