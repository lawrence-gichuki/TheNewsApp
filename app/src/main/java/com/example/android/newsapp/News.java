package com.example.android.newsapp;

/**
 * An {@link News} object contains information related to a single news feed.
 */
public class News {
    private String mTitle;
    private String mDate;
    private String mUrl;
    private String mAuthor;
    private String mSection;

    /**
     * Constructs a new {@link News} object.
     *
     * @param title   is the title of the news
     * @param date    is the date of the publication
     * @param url     is the website URL to find more details about the news
     * @param author  is the author of the news
     * @param section is the section of the news
     */

    public News(String title, String date, String url, String author, String section) {
        mTitle = title;
        mDate = date;
        mUrl = url;
        mAuthor = author;
        mSection = section;
    }

    /**
     * Returns the title of the news.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the date of the news.
     */
    public String getDate() {
        return mDate;
    }

    /**
     * Returns the Url of the news.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Returns the author of the news.
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Returns the section of the news.
     */
    public String getSection() {
        return mSection;
    }
}
