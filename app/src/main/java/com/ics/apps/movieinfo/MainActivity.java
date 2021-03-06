package com.ics.apps.movieinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ics.apps.movieinfo.genres.GenresActivity;
import com.ics.apps.movieinfo.libs.Constants;
import com.ics.apps.movieinfo.movies.MoviesListFragment;
import com.ics.apps.movieinfo.tvshows.TvShowsListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        switchToFragment(0);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void switchToFragment(int fragment) {

        FragmentManager manager = getSupportFragmentManager();
        Intent intent;

        switch (fragment) {
            case 0:
                manager.beginTransaction().replace(R.id.content_main, new MoviesListFragment()).commit();
                setTitle(getResources().getString(R.string.movies));
                break;
            case 1:
                intent = new Intent(MainActivity.this, GenresActivity.class);
                intent.putExtra(Constants.TYPE, Constants.MOVIE);
                startActivity(intent);
                break;
            case 2:
                manager.beginTransaction().replace(R.id.content_main, new TvShowsListFragment()).commit();
                setTitle(getResources().getString(R.string.tvshows));
                break;
            case 3:
                intent = new Intent(MainActivity.this, GenresActivity.class);
                intent.putExtra(Constants.TYPE, Constants.TV_SHOW);
                startActivity(intent);
                break;
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.movies) {
            switchToFragment(0);
        } else if (id == R.id.moviesGenres) {
            switchToFragment(1);
        } else if (id == R.id.tvshows) {
            switchToFragment(2);
        } else if (id == R.id.tvshowsGenres) {
            switchToFragment(3);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
