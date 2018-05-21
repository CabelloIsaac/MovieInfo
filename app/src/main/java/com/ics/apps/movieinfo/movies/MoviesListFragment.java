package com.ics.apps.movieinfo.movies;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ics.apps.movieinfo.DetailActivity;
import com.ics.apps.movieinfo.MainActivity;
import com.ics.apps.movieinfo.R;
import com.ics.apps.movieinfo.libs.Constants;
import com.ics.apps.movieinfo.libs.WebServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.GridLayout.HORIZONTAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment {

    private MoviesAdapter moviesAdapter;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private GridView gridview;
    private ProgressDialog dialog;

    public MoviesListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        gridview = view.findViewById(R.id.gridview);

        moviesAdapter = new MoviesAdapter(getActivity(), movieArrayList);
        gridview.setAdapter(moviesAdapter);

        gridview.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String ID = ((TextView) v.findViewById(R.id.tvID)).getText().toString();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constants.TYPE, Constants.MOVIE);
                intent.putExtra(Constants.ID, ID);
                startActivity(intent);
            }
        });

        getPopularMovies();

        return view;
    }


    private void getPopularMovies() {

        getActivity().setTitle(getString(R.string.movies) + " - " + getString(R.string.popular));
        movieArrayList.clear();
        StringRequest stringRequest = new StringRequest(WebServices.POPULAR_MOVIES, new Response.Listener<String>() {

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

    private void getTopRatedMovies() {

        getActivity().setTitle(getString(R.string.movies) + " - " + getString(R.string.top_rated));
        movieArrayList.clear();
        StringRequest stringRequest = new StringRequest(WebServices.TOP_RATED_MOVIES, new Response.Listener<String>() {

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

    private void getUpcomingMovies() {

        getActivity().setTitle(getString(R.string.movies) + " - " + getString(R.string.upcoming));
        movieArrayList.clear();
        StringRequest stringRequest = new StringRequest(WebServices.UPCOMING_MOVIES, new Response.Listener<String>() {

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
                getTopRatedMovies();
                return false;
            }
        });
        popular.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getPopularMovies();
                return false;
            }
        });
        upcoming.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getUpcomingMovies();
                return false;
            }
        });
    }

}
