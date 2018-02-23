package com.helabs.eltonjhony.udacitymovies.di;

import android.content.Context;

import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationMessages;
import com.helabs.eltonjhony.udacitymovies.ui.details.DetailsSubComponent;
import com.helabs.eltonjhony.udacitymovies.ui.details.DetailsModule;
import com.helabs.eltonjhony.udacitymovies.ui.movies.MoviesModule;
import com.helabs.eltonjhony.udacitymovies.ui.reviews.ReviewsModule;
import com.helabs.eltonjhony.udacitymovies.ui.reviews.ReviewsSubComponent;
import com.helabs.eltonjhony.udacitymovies.ui.trailers.TrailersModule;
import com.helabs.eltonjhony.udacitymovies.ui.movies.MoviesSubComponent;
import com.helabs.eltonjhony.udacitymovies.ui.trailers.TrailersSubComponent;

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
