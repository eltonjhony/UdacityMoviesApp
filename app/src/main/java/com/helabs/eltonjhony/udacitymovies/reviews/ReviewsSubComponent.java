package com.helabs.eltonjhony.udacitymovies.reviews;

import com.helabs.eltonjhony.udacitymovies.di.FragmentScoped;

import dagger.Subcomponent;

/**
 * Created by eltonjhony on 04/08/17.
 */
@FragmentScoped
@Subcomponent(modules = {
        ReviewsModule.class
})
public interface ReviewsSubComponent {

    void inject(ReviewsFragment reviewsFragment);
}
