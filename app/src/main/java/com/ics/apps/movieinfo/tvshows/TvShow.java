package com.ics.apps.movieinfo.tvshows;

/**
 * Created by cabel on 21/1/2018.
 */

public class TvShow {

    String id, title, posterPath;

    public TvShow(String id, String title, String posterPath) {

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