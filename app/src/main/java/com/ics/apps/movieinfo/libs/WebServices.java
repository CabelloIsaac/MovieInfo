package com.ics.apps.movieinfo.libs;

import java.util.Locale;

/**
 * Esta clase contiene las direcciones y nombres de archivos para acceder al WebServices
 *
 * @author Isaac Cabello
 */

public class WebServices {

    private static String LOCALE = Locale.getDefault().toString();

    //public static String IP = "http://rpcinternacional.com/server/";
    private static String IP = "https://api.themoviedb.org/3/";
    private static String API_KEY = "8fa9d8050f6971b432b9bdcfcd26621c";

    public static String IMAGES_BASE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";

    public static String POPULAR_MOVIES = IP + "movie/popular?api_key=" + API_KEY + "&language=" + LOCALE + "&page=1";
    public static String TOP_RATED_MOVIES = IP + "movie/top_rated?api_key=" + API_KEY + "&language=" + LOCALE + "&page=1";
    public static String UPCOMING_MOVIES = IP + "movie/upcoming?api_key=" + API_KEY + "&language=" + LOCALE + "&page=1";
    public static String SEARCH_MOVIES_A = IP + "search/movie?api_key=" + API_KEY + "&language=" + LOCALE + "&query=";
    public static String SEARCH_MOVIES_B = "";

    public static String MOVIES_VIDEOS_A = IP + "movie/";
    public static String MOVIES_VIDEOS_B = "/videos?api_key=" + API_KEY + "&language=en-US";

    public static String TV_VIDEOS_A = IP + "tv/";
    public static String TV_VIDEOS_B = "/videos?api_key=" + API_KEY + "&language=en-US";

    public static String POPULAR_TV_SHOWS = IP + "tv/popular?api_key=" + API_KEY + "&language=" + LOCALE + "&page=1";
    public static String TOP_RATED_TV_SHOWS = IP + "tv/top_rated?api_key=" + API_KEY + "&language=" + LOCALE + "&page=1";
    public static String SEARCH_TV_A = IP + "search/tv?api_key=" + API_KEY + "&language=" + LOCALE + "&query=";
    public static String SEARCH_TV_B = "";

    public static String MOVIE_DETAIL_PART_A = "https://api.themoviedb.org/3/movie/";
    public static String MOVIE_DETAIL_PART_B = "?api_key=" + API_KEY + "&language=" + LOCALE + "";

    public static String TV_SHOW_DETAIL_PART_A = "https://api.themoviedb.org/3/tv/";
    public static String TV_SHOW_DETAIL_PART_B = "?api_key=" + API_KEY + "&language=" + LOCALE + "";

    public static String MOVIES_GENRES = IP + "genre/movie/list?api_key=" + API_KEY + "&language=" + LOCALE;
    public static String TV_SHOW_GENRES = IP + "genre/tv/list?api_key=" + API_KEY + "&language=" + LOCALE;

    public static String MOVIES_BY_GENRE = IP + "discover/movie?api_key=" + API_KEY + "&language=" + LOCALE + "&sort_by=popularity.desc&include_adult=true&page=1&with_genres=";
    public static String TV_SHOW_BY_GENRE = IP + "discover/tv?api_key=" + API_KEY + "&language=" + LOCALE + "&sort_by=popularity.desc&include_adult=true&page=1&with_genres=";
}
