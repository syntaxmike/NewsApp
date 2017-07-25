package com.example.android.newsapp.data;

/**
 * Created by Syntax Mike on 6/22/2017.
 */

public class NewsItem {

    private String title;
    private String description;
    private String url;
    private String time;
    private String imageURL;
    private String author;

    public NewsItem(String title, String description, String url, String time, String imageURL, String author) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.url = url;
        this.imageURL = imageURL;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
