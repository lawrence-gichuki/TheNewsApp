package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    /**
     * Constant value for the news loader ID
     */
    private static final int NEWS_LOADER_ID = 1;

    /**
     * URL for news from the GUARDIAN news set
     */
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?";

    /**
     * ListView for displaying the news
     */
    private ListView mListView;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mTextView;

    /**
     * Adapter for the list of news
     */
    private NewsAdapter mAdapter;

    /**
     * Progress bar to be displayed when load in background is running
     */
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // Find a reference to the {@link ListView} in the layout
        mListView = (ListView) findViewById(R.id.list);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        mTextView = (TextView) findViewById(R.id.empty_view);

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(R.string.no_internet_text);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(getString(R.string.news_category_name), getString(R.string.news_category_name_value));
        uriBuilder.appendQueryParameter(getString(R.string.tag), getString(R.string.tag_value));
        uriBuilder.appendQueryParameter(getString(R.string.limit), getString(R.string.limit_value));
        uriBuilder.appendQueryParameter(getString(R.string.show_references), getString(R.string.show_references_value));
        uriBuilder.appendQueryParameter(getString(R.string.from_date), getString(R.string.from_date_value));
        uriBuilder.appendQueryParameter(getString(R.string.api_key), getString(R.string.api_key_value));

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mAdapter = new NewsAdapter(this, news);
        mProgressBar.setVisibility(View.GONE);

        //Check if the Loader returned any data
        if (mAdapter.isEmpty()) {
            // Set empty state text to display "No news found."
            mTextView.setText(R.string.no_news_text);
        }

        /** If there is a valid list of {@link News}, then add them to the adapter's
         data set. This will trigger the ListView to update. */
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
            updateUi(news);
        }
    }

    private void updateUi(List<News> news) {
        /** Set the adapter on the {@link ListView}
         so the list can be populated in the user interface */
        mListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news feed
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current news feed that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the Guardian URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing news.
        mAdapter.clear();
    }
}
