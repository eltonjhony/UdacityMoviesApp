package com.helabs.eltonjhony.udacitymovies.ui.movies;

import com.helabs.eltonjhony.udacitymovies.data.repository.MoviesRepository;
import com.helabs.eltonjhony.udacitymovies.di.FragmentScoped;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eltonjhony on 04/08/17.
 */
@Module
public class MoviesModule {

    private final WeakReference<MoviesContract.View> mView;

    public MoviesModule(MoviesContract.View view) {
        mView = new WeakReference<>(view);
    }

    @Provides
    @FragmentScoped
    WeakReference<MoviesContract.View> provideView() {
        return mView;
    }

    @Provides
    @FragmentScoped
    MoviesContract.Actions provideMoviesPresenter(MoviesRepository moviesRepository) {
        return new MoviesPresenter(mView, moviesRepository);
    }
}
