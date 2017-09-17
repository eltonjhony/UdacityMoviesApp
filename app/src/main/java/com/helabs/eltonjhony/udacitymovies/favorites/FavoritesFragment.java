package com.helabs.eltonjhony.udacitymovies.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.helabs.eltonjhony.udacitymovies.movies.MoviesFragment;

import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.FAVORITE;

/**
 * Created by eltonjhony on 13/09/17.
 */
public class FavoritesFragment extends MoviesFragment {

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    protected void initialize() {
        super.initialize();
        mContentType = FAVORITE;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFeaturedVideo(false);
    }
}
