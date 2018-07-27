package com.ics.apps.movieinfo.tvshows;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowsListFragment extends Fragment {

    private TvShowsAdapter tvShowsAdapter;
    private ArrayList<TvShow> tvShowArrayList = new ArrayList<>();
    private GridView gridview;
    private ProgressDialog dialog;

    public TvShowsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_shows_list, container, false);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        gridview = view.findViewById(R.id.gridview);

        tvShowsAdapter = new TvShowsAdapter(getActivity(), tvShowArrayList);
        gridview.setAdapter(tvShowsAdapter);

        gridview.setAdapter(tvShowsAdapter);
        tvShowsAdapter.notifyDataSetChanged();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String ID = ((TextView) v.findViewById(R.id.tvID)).getText().toString();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constants.TYPE, Constants.TV_SHOW);
                intent.putExtra(Constants.ID, ID);
                startActivity(intent);
            }
        });

        getPopularTvShows();

        return view;
    }

    private void getPopularTvShows() {

        final RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        getActivity().setTitle(getString(R.string.tvshows) + " - " + getString(R.string.popular));
        tvShowArrayList.clear();

        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();

        StringRequest stringRequest = new StringRequest(WebServices.POPULAR_TV_SHOWS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray resultsArray = jsonObject.getJSONArray(Constants.RESULTS);

                    for (int i = 0; i < resultsArray.length(); i++) {

                        JSONObject jsonObjectResults = resultsArray.getJSONObject(i);

                        String id = jsonObjectResults.getString(Constants.ID);
                        String title = jsonObjectResults.getString(Constants.NAME);
                        String posterPath = jsonObjectResults.getString(Constants.POSTER_PATH);

                        tvShowArrayList.add(new TvShow(id, title, posterPath));

                    }

                    gridview.setAdapter(tvShowsAdapter);
                    tvShowsAdapter.notifyDataSetChanged();

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

                Toast.makeText(getContext(), getResources().getString(R.string.no_response), Toast.LENGTH_SHORT).show();
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

    private void getTopRatedTvShows() {

        final RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        getActivity().setTitle(getString(R.string.tvshows) + " - " + getString(R.string.top_rated));
        tvShowArrayList.clear();

        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();

        StringRequest stringRequest = new StringRequest(WebServices.TOP_RATED_TV_SHOWS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray resultsArray = jsonObject.getJSONArray(Constants.RESULTS);

                    for (int i = 0; i < resultsArray.length(); i++) {

                        JSONObject jsonObjectResults = resultsArray.getJSONObject(i);

                        String id = jsonObjectResults.getString(Constants.ID);
                        String title = jsonObjectResults.getString(Constants.NAME);
                        String posterPath = jsonObjectResults.getString(Constants.POSTER_PATH);

                        tvShowArrayList.add(new TvShow(id, title, posterPath));

                    }

                    gridview.setAdapter(tvShowsAdapter);
                    tvShowsAdapter.notifyDataSetChanged();

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

                Toast.makeText(getContext(), getResources().getString(R.string.no_response), Toast.LENGTH_SHORT).show();
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

    private void search(String query) {

        final RequestQueue mRequestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

        getActivity().setTitle(getString(R.string.tvshows) + " - " + query);
        tvShowArrayList.clear();

        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();

        StringRequest stringRequest = new StringRequest(WebServices.SEARCH_TV_A + query + WebServices.SEARCH_TV_B, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray resultsArray = jsonObject.getJSONArray(Constants.RESULTS);

                    for (int i = 0; i < resultsArray.length(); i++) {

                        JSONObject jsonObjectResults = resultsArray.getJSONObject(i);

                        String id = jsonObjectResults.getString(Constants.ID);
                        String title = jsonObjectResults.getString(Constants.NAME);
                        String posterPath = jsonObjectResults.getString(Constants.POSTER_PATH);

                        tvShowArrayList.add(new TvShow(id, title, posterPath));

                    }

                    gridview.setAdapter(tvShowsAdapter);
                    tvShowsAdapter.notifyDataSetChanged();

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

                Toast.makeText(getContext(), getResources().getString(R.string.no_response), Toast.LENGTH_SHORT).show();
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
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem topRated = menu.findItem(R.id.top_rated);
        MenuItem popular = menu.findItem(R.id.popular);
        MenuItem upcoming = menu.findItem(R.id.upcoming);
        topRated.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getTopRatedTvShows();
                return false;
            }
        });
        popular.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getPopularTvShows();
                return false;
            }
        });
        MenuItem mSearchMenuItem = menu.findItem(R.id.search_canciones);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                //if (newText.equals(""))
                //search(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;

            }
        });
        upcoming.setVisible(false);

    }

}
