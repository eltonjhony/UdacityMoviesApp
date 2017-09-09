package com.helabs.eltonjhony.udacitymovies.di;

import android.content.Context;

import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationMessages;
import com.helabs.eltonjhony.udacitymovies.details.DetailsSubComponent;
import com.helabs.eltonjhony.udacitymovies.details.DetailsModule;
import com.helabs.eltonjhony.udacitymovies.movies.MoviesModule;
import com.helabs.eltonjhony.udacitymovies.reviews.ReviewsModule;
import com.helabs.eltonjhony.udacitymovies.reviews.ReviewsSubComponent;
import com.helabs.eltonjhony.udacitymovies.trailers.TrailersModule;
import com.helabs.eltonjhony.udacitymovies.movies.MoviesSubComponent;
import com.helabs.eltonjhony.udacitymovies.trailers.TrailersSubComponent;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by eltonjhony on 11/08/17.
 */
@Singleton
@Component(modules = {ApplicationModule.class,
        PreferencesModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    MoviesSubComponent plus(MoviesModule module);
    DetailsSubComponent plus(DetailsModule module);
    TrailersSubComponent plus(TrailersModule module);
    ReviewsSubComponent plus(ReviewsModule module);

    Context getContext();

    ApplicationMessages getApplicationMessages();
}
