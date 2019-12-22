package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link NewsAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link News} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    List<News> mNewsList;

    public NewsAdapter(@NonNull Context context, List<News> newsList) {
        super(context, 0, newsList);
        mNewsList = newsList;
    }

    /**
     * Returns a list item view that displays information about a News feed at the given position
     * in the list of News.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Find the News Feed at the given position in the list of news
        News currentNews = mNewsList.get(position);

        //Trim the date to 10 characters in length
        String trimedDate = currentNews.getDate().substring(0, 10);

        // Find the TextView with view ID title_text_view
        TextView textViewTitle = (TextView) listItemView.findViewById(R.id.title_text_view);
        // Display the title of the current news in that TextView
        textViewTitle.setText(currentNews.getTitle());

        // Find the TextView with view ID date_text_view
        TextView textViewDate = (TextView) listItemView.findViewById(R.id.date_text_view);
        // Display the date of the current news in that TextView
        textViewDate.setText(trimedDate);

        // Find the TextView with view ID type_text_view
        TextView textViewType = (TextView) listItemView.findViewById(R.id.type_text_view);
        // Display the Section of the current news feed in that TextView
        textViewType.setText(currentNews.getSection());


        // Find the TextView with view ID author_text_view
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        // Display the author of the current news feed in that TextView
        authorTextView.setText(currentNews.getAuthor());


        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}
