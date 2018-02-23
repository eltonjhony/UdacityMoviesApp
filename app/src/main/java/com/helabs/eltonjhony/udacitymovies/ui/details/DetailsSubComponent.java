package com.helabs.eltonjhony.udacitymovies.ui.details;

import com.helabs.eltonjhony.udacitymovies.di.FragmentScoped;

import dagger.Subcomponent;

/**
 * Created by eltonjhony on 04/08/17.
 */
@FragmentScoped
@Subcomponent(modules = { DetailsModule.class })
public interface DetailsSubComponent {

    void inject(DetailsFragment detailsFragment);
}
