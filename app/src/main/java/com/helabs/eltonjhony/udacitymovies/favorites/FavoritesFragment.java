package com.helabs.eltonjhony.udacitymovies.favorites;

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
    protected void setupFeaturedVideo() {
        getLayout().youtubePlayerFragment.active(false);
        getLayout().hideShowBtn.setVisibility(View.GONE);
    }
}
