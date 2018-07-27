package com.ics.apps.movieinfo.genres;

/**
 * Created by cabel on 21/1/2018.
 */

public class Genre {

    String id, name;

    public Genre(String id, String name) {

        this.id = id;
        this.name = name;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}