package com.helabs.eltonjhony.udacitymovies.data.remote;

import com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationMessages;
import com.helabs.eltonjhony.udacitymovies.data.exceptions.NoInternetException;
import com.helabs.eltonjhony.udacitymovies.data.model.ContentType;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.MoviesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.utils.NetworkUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationConfiguration.getApiKey;
import static com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationConfiguration.getLanguage;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.NOW_PLAYING;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.POPULAR;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.TOP_RATED;

/**
 * Created by eltonjhony on 05/06/17.
 */
public class RemoteMoviesDataSource implements MoviesDataSource {

    private Api mApi;
    private ApplicationMessages applicationMessages;
    private NetworkUtil networkUtil;

    @Inject
    public RemoteMoviesDataSource(Api api, ApplicationMessages applicationMessages, NetworkUtil networkUtil) {
        this.mApi = api;
        this.applicationMessages = applicationMessages;
        this.networkUtil = networkUtil;
    }

    @Override
    public Observable<DataResultWrapper<Movie>> loadMovies(@ContentType int contentType, int page)
            throws NoInternetException {

        if (!networkUtil.isNetworkAvailable()) {
            throw new NoInternetException(applicationMessages.getNoNetworkAvailableMessage());
        }

        return this.getMovieEndpointByType(contentType, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<DataResultWrapper<Movie>> searchMovies(String language, String query, int page)
            throws NoInternetException {

        if (!networkUtil.isNetworkAvailable()) {
            throw new NoInternetException(applicationMessages.getNoNetworkAvailableMessage());
        }

        return this.mApi.searchMovies(getApiKey(), language, query, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieDetail> getMovieById(String movieId, String language) throws NoInternetException {

        if (!networkUtil.isNetworkAvailable()) {
            throw new NoInternetException(applicationMessages.getNoNetworkAvailableMessage());
        }

        return this.mApi.getMovieById(movieId, language, getApiKey())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<DataResultWrapper<Movie>> getMovieEndpointByType(@ContentType int contentType, int offSet) {
        Observable<DataResultWrapper<Movie>> observable;
        switch (contentType) {
            case POPULAR:
                observable = mApi.fetchPopularMovies(getApiKey(), getLanguage(), offSet);
                break;
            case NOW_PLAYING:
                observable = mApi.fetchNowPlayingMovies(getApiKey(), getLanguage(), offSet);
                break;
            case TOP_RATED:
                observable = mApi.fetchTopRatedMovies(getApiKey(), getLanguage(), offSet);
                break;
            default:
                throw new RuntimeException("Invalid option " + contentType);
        }
        return observable;
    }

}
