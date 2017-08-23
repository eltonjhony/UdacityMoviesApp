package com.helabs.eltonjhony.udacitymovies.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.common.BaseFragment;
import com.helabs.eltonjhony.udacitymovies.infrastructure.MyLog;
import com.helabs.eltonjhony.udacitymovies.infrastructure.preferences.SearcherPreferences;
import com.helabs.eltonjhony.udacitymovies.movies.ListMoviesActivity;
import com.helabs.eltonjhony.udacitymovies.movies.MoviesFragment;

import javax.inject.Inject;

import static com.helabs.eltonjhony.udacitymovies.infrastructure.Constants.PreferenceKeys.SEARCHER_KEY;

/**
 * Created by eltonjhony on 19/05/17.
 */

public abstract class SearchFragment extends BaseFragment {

    @Inject
    SearcherPreferences mSearcherPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search:
                SearchView searchView = new SearchView(((ListMoviesActivity) getContext()).getSupportActionBar().getThemedContext());
                MenuItemCompat.setActionView(item, searchView);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mSearcherPref.putString(SEARCHER_KEY, query);
                        searchMovies(query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        MyLog.info(MoviesFragment.class.getSimpleName(), newText);
                        return true;
                    }
                });
                MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.search:
                                mSearcherPref.clear();
                                searchMovies(null);
                        }
                        return true;
                    }
                });
        }
        return super.onOptionsItemSelected(item);
    }

    protected String getCurrentQuery() {
        return mSearcherPref.getString(SEARCHER_KEY);
    }

    protected abstract void searchMovies(String query);
}
