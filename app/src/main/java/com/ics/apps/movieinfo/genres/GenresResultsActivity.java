package com.ics.apps.movieinfo.genres;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.ics.apps.movieinfo.DetailActivity;
import com.ics.apps.movieinfo.R;
import com.ics.apps.movieinfo.libs.Constants;
import com.ics.apps.movieinfo.libs.WebServices;
import com.ics.apps.movieinfo.movies.Movie;
import com.ics.apps.movieinfo.movies.MoviesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class GenresResultsActivity extends AppCompatActivity {

    private MoviesAdapter moviesAdapter;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private GridView gridview;
    private ProgressDialog dialog;
    private static String TYPE = "";
    private String genreId, genreName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_shows_genres_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        gridview = findViewById(R.id.gridview);
        moviesAdapter = new MoviesAdapter(this, movieArrayList);
        gridview.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();

        if (getIntent().getExtras() != null) {
            TYPE = getIntent().getExtras().getString(Constants.TYPE);
            genreName = getIntent().getExtras().getString(Constants.NAME);
            genreId = getIntent().getExtras().getString(Constants.ID);
        }

        Toast.makeText(this, TYPE, Toast.LENGTH_SHORT).show();

        if (TYPE != null) {
            if (TYPE.equals(Constants.MOVIE)) {

                setTitle(getString(R.string.movies) + " - " + genreName);
                getResultsMovies();

            } else if (TYPE.equals(Constants.TV_SHOW)) {

                setTitle(getString(R.string.tvshows) + " - " + genreName);
                getResultsTvShows();

            }
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String ID = ((TextView) v.findViewById(R.id.tvID)).getText().toString();

                if (TYPE != null) {
                    if (TYPE.equals(Constants.MOVIE)) {

                        Intent intent = new Intent(GenresResultsActivity.this, DetailActivity.class);
                        intent.putExtra(Constants.TYPE, Constants.MOVIE);
                        intent.putExtra(Constants.ID, ID);
                        startActivity(intent);

                    } else if (TYPE.equals(Constants.TV_SHOW)){

                        Intent intent = new Intent(GenresResultsActivity.this, DetailActivity.class);
                        intent.putExtra(Constants.TYPE, Constants.TV_SHOW);
                        intent.putExtra(Constants.ID, ID);
                        startActivity(intent);

                    }
                }


            }
        });


    }

    private void getResultsMovies() {

        final RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        movieArrayList.clear();

        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();

        StringRequest stringRequest = new StringRequest(WebServices.MOVIES_BY_GENRE + genreId, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray resultsArray = jsonObject.getJSONArray(Constants.RESULTS);

                    for (int i = 0; i < resultsArray.length(); i++) {

                        JSONObject jsonObjectResults = resultsArray.getJSONObject(i);

                        String id = jsonObjectResults.getString(Constants.ID);
                        String title = jsonObjectResults.getString(Constants.TITLE);
                        String posterPath = jsonObjectResults.getString(Constants.POSTER_PATH);

                        movieArrayList.add(new Movie(id, title, posterPath));

                    }

                    gridview.setAdapter(moviesAdapter);
                    moviesAdapter.notifyDataSetChanged();

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(GenresResultsActivity.this, getResources().getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new String(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };

        mRequestQueue.add(stringRequest);
    }

    private void getResultsTvShows() {

        final RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        movieArrayList.clear();

        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();

        StringRequest stringRequest = new StringRequest(WebServices.TV_SHOW_BY_GENRE + genreId, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray resultsArray = jsonObject.getJSONArray(Constants.RESULTS);

                    for (int i = 0; i < resultsArray.length(); i++) {

                        JSONObject jsonObjectResults = resultsArray.getJSONObject(i);

                        String id = jsonObjectResults.getString(Constants.ID);
                        String name = jsonObjectResults.getString(Constants.NAME);
                        String posterPath = jsonObjectResults.getString(Constants.POSTER_PATH);

                        movieArrayList.add(new Movie(id, name, posterPath));

                    }

                    gridview.setAdapter(moviesAdapter);
                    moviesAdapter.notifyDataSetChanged();

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(GenresResultsActivity.this, getResources().getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 3 * 60 * 1000; // in 3 minutes cache will be hit, but also refreshed on background
                    final long cacheExpired = 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires completely
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new String(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }

            @Override
            public void deliverError(VolleyError error) {
                super.deliverError(error);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }
        };

        mRequestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
