package com.ics.apps.movieinfo.genres;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ics.apps.movieinfo.R;

import java.util.ArrayList;

/**
 * Created by cabel on 21/1/2018.
 */

public class GenreAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Genre> items;

    public GenreAdapter(Activity activity, ArrayList<Genre> items) {
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

    public void addAll(ArrayList<Genre> category) {
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
            v = inf.inflate(R.layout.list_item_genre, null);
        }

        //Crea elemento con datos
        Genre tvShow = items.get(position);

        //Inicializa componentes
        TextView tvID = v.findViewById(R.id.tvID);
        TextView tvName = v.findViewById(R.id.tvName);


        //Asigna datos a los componentes
        tvID.setText(tvShow.getId());
        tvName.setText(tvShow.getName());

        return v;
    }
}
