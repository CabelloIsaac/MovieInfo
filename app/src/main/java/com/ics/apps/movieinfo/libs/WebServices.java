package com.ics.apps.movieinfo.libs;

/**
 * Esta clase contiene las direcciones y nombres de archivos para acceder al WebServices
 *
 * @author Isaac Cabello
 */

public class WebServices {

    //public static String IP = "http://rpcinternacional.com/server/";
    public static String IP = "https://api.themoviedb.org/3/";

    public static String IMAGES_BASE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";

    public static String POPULAR_MOVIES = IP + "movie/popular?api_key=8fa9d8050f6971b432b9bdcfcd26621c&language=en-US&page=1";
    public static String TOP_RATED_MOVIES = IP + "movie/top_rated?api_key=8fa9d8050f6971b432b9bdcfcd26621c&language=en-US&page=1";
    public static String UPCOMING_MOVIES = IP + "movie/upcoming?api_key=8fa9d8050f6971b432b9bdcfcd26621c&language=en-US&page=1";

    public static String POPULAR_TV_SHOWS = IP + "tv/popular?api_key=8fa9d8050f6971b432b9bdcfcd26621c&language=en-US&page=1";
    public static String TOP_RATED_TV_SHOWS = IP + "tv/top_rated?api_key=8fa9d8050f6971b432b9bdcfcd26621c&language=en-US&page=1";

    public static String MOVIE_DETAIL_PART_A = "https://api.themoviedb.org/3/movie/";
    public static String MOVIE_DETAIL_PART_B = "?api_key=8fa9d8050f6971b432b9bdcfcd26621c&language=en-US";

    public static String TV_SHOW_DETAIL_PART_A ="https://api.themoviedb.org/3/tv/";
    public static String TV_SHOW_DETAIL_PART_B = "?api_key=8fa9d8050f6971b432b9bdcfcd26621c&language=en-US";

}
