package com.ics.apps.movieinfo.tvshows;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ics.apps.movieinfo.R;
import com.ics.apps.movieinfo.libs.WebServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cabel on 21/1/2018.
 */

public class TvShowsAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<TvShow> items;

    public TvShowsAdapter(Activity activity, ArrayList<TvShow> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<TvShow> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_item_movie, null);
        }

        //Crea elemento con datos
        TvShow tvShow = items.get(position);

        //Inicializa componentes
        TextView tvID = v.findViewById(R.id.tvID);
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        ImageView ivCover = v.findViewById(R.id.ivPoster);


        //Asigna datos a los componentes
        tvID.setText(tvShow.getId());
        tvTitle.setText(tvShow.getTitle());
        Picasso.get()
                .load(WebServices.IMAGES_BASE_URL + tvShow.getPosterPath())
                //.placeholder(R.drawable.non_artist)
                //.error(R.drawable.non_artist)
                .resize(270, 410)
                .into(ivCover);

        return v;
    }
}
