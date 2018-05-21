package com.ics.apps.movieinfo.movies;

/**
 * Created by cabel on 21/1/2018.
 */

public class Movie {

    String id, title, posterPath;

    public Movie(String id, String title, String posterPath) {

        this.id = id;
        this.title = title;
        this.posterPath = posterPath;

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }
}