package com.helabs.eltonjhony.udacitymovies.movies;

import android.os.Bundle;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.common.BaseActivity;

/**
 * Created by eltonjhony on 19/05/17.
 */
public class ListMoviesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            addFragment(R.id.main_content, MoviesFragment.newInstance());
        }
    }
}
