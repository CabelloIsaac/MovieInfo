package com.ics.apps.movieinfo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ics.apps.movieinfo.R;
import com.ics.apps.movieinfo.libs.Constants;
import com.ics.apps.movieinfo.libs.WebServices;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivCover;
    private TextView tvOverview;
    private ProgressDialog dialog;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private String id, title, overview, poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        ivCover = findViewById(R.id.ivCover);
        tvOverview = findViewById(R.id.tvOverview);

        int type = getIntent().getExtras().getInt(Constants.TYPE);
        id = getIntent().getExtras().getString(Constants.ID);

        if (type == 0) {
            getMovieInfo();
        } else {
            getTvShowInfo();
        }

    }

    private void getMovieInfo() {

        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();

        StringRequest stringRequest = new StringRequest(WebServices.MOVIE_DETAIL_PART_A + id + WebServices.MOVIE_DETAIL_PART_B, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    // JSONObject results = jsonObject.getJSONObject(Constants.RESULTS);

                    title = jsonObject.getString(Constants.ORIGINAL_TITLE);
                    // results.getString(Constants.ORIGINAL_TITLE);
                    overview = jsonObject.getString(Constants.OVERVIEW);
                    poster = jsonObject.getString(Constants.BACKDROP_PATH);

                    tvOverview.setText(overview);
                    collapsingToolbarLayout.setTitle(title);
                    Picasso.get()
                            .load(WebServices.IMAGES_BASE_URL + poster)
                            .placeholder(R.drawable.banner)
                            .error(R.drawable.banner)
                            //.resize(500, 500)
                            .into(ivCover);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Error de respuesta de servidor
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(DetailActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getTvShowInfo() {

        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();

        StringRequest stringRequest = new StringRequest(WebServices.TV_SHOW_DETAIL_PART_A + id + WebServices.TV_SHOW_DETAIL_PART_B, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    // JSONObject results = jsonObject.getJSONObject(Constants.RESULTS);

                    title = jsonObject.getString(Constants.NAME);
                    // results.getString(Constants.ORIGINAL_TITLE);
                    overview = jsonObject.getString(Constants.OVERVIEW);
                    poster = jsonObject.getString(Constants.BACKDROP_PATH);

                    tvOverview.setText(overview);
                    collapsingToolbarLayout.setTitle(title);
                    Picasso.get()
                            .load(WebServices.IMAGES_BASE_URL + poster)
                            .placeholder(R.drawable.banner)
                            .error(R.drawable.banner)
                            //.resize(500, 500)
                            .into(ivCover);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Error de respuesta de servidor
                Toast.makeText(DetailActivity.this, getResources().getString(R.string.no_response), Toast.LENGTH_SHORT).show();
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(DetailActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
