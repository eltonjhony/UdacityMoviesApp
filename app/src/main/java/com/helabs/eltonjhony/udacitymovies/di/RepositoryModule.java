package com.helabs.eltonjhony.udacitymovies.di;

import com.helabs.eltonjhony.udacitymovies.data.remote.RemoteReviewsDataSource;
import com.helabs.eltonjhony.udacitymovies.data.repository.ReviewsRepository;
import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationMessages;
import com.helabs.eltonjhony.udacitymovies.data.remote.RetrofitClient;
import com.helabs.eltonjhony.udacitymovies.data.repository.MoviesRepository;
import com.helabs.eltonjhony.udacitymovies.data.remote.Api;
import com.helabs.eltonjhony.udacitymovies.data.remote.RemoteMoviesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.remote.RemoteTrailersDataSource;
import com.helabs.eltonjhony.udacitymovies.data.repository.TrailersRepository;
import com.helabs.eltonjhony.udacitymovies.utils.NetworkUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eltonjhony on 05/06/17.
 */
@Module
public class RepositoryModule {

    @Singleton
    @Provides
    Api provideApi() {
        return RetrofitClient.getClient().create(Api.class);
    }

    @Singleton
    @Provides
    RemoteMoviesDataSource provideRemoteMoviesDataSource(Api api, ApplicationMessages applicationMessages, NetworkUtil networkUtil) {
        return new RemoteMoviesDataSource(api, applicationMessages, networkUtil);
    }

    @Singleton
    @Provides
    MoviesRepository provideMoviesRepository(RemoteMoviesDataSource remoteMoviesDataSource, RemoteTrailersDataSource remoteTrailersDataSource) {
        return new MoviesRepository(remoteMoviesDataSource, remoteTrailersDataSource);
    }

    @Singleton
    @Provides
    RemoteTrailersDataSource provideRemoteTrailersDataSource(Api api) {
        return new RemoteTrailersDataSource(api);
    }

    @Singleton
    @Provides
    TrailersRepository provideTrailersRepository(RemoteTrailersDataSource remoteTrailersDataSource) {
        return new TrailersRepository(remoteTrailersDataSource);
    }

    @Singleton
    @Provides
    RemoteReviewsDataSource provideReviewsDataSource(Api api, NetworkUtil networkUtil, ApplicationMessages applicationMessages) {
        return new RemoteReviewsDataSource(api, networkUtil, applicationMessages);
    }

    @Singleton
    @Provides
    ReviewsRepository provideReviewsRepository(RemoteReviewsDataSource dataSource) {
        return new ReviewsRepository(dataSource);
    }
}
