package com.helabs.eltonjhony.udacitymovies.ui.movies;

import com.helabs.eltonjhony.udacitymovies.di.FragmentScoped;

import dagger.Subcomponent;

/**
 * Created by eltonjhony on 04/08/17.
 */
@FragmentScoped
@Subcomponent(modules = {
        MoviesModule.class
})
public interface MoviesSubComponent {

    void inject(MoviesFragment moviesFragment);
}
