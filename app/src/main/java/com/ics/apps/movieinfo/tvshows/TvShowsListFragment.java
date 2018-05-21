package com.ics.apps.movieinfo.tvshows;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ics.apps.movieinfo.DetailActivity;
import com.ics.apps.movieinfo.R;
import com.ics.apps.movieinfo.libs.Constants;
import com.ics.apps.movieinfo.libs.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        getActivity().setTitle(getString(R.string.tvshows) + " - " + getString(R.string.popular));
        tvShowArrayList.clear();
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


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void getTopRatedTvShows() {

        getActivity().setTitle(getString(R.string.tvshows) + " - " + getString(R.string.top_rated));
        tvShowArrayList.clear();
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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), getString(R.string.no_response), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

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

        upcoming.setVisible(false);

    }

}
