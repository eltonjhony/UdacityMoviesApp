package com.helabs.eltonjhony.udacitymovies.ui.trailers;

import com.helabs.eltonjhony.udacitymovies.di.FragmentScoped;

import dagger.Subcomponent;

/**
 * Created by eltonjhony on 04/08/17.
 */
@FragmentScoped
@Subcomponent(modules = {
        TrailersModule.class
})
public interface TrailersSubComponent {

    void inject(TrailersFragment trailerFragment);
}
