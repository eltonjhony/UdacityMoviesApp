package com.helabs.eltonjhony.udacitymovies.data.repository;

import com.helabs.eltonjhony.udacitymovies.data.MoviesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.exceptions.NoInternetException;
import com.helabs.eltonjhony.udacitymovies.data.model.ContentType;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.data.model.Video;
import com.helabs.eltonjhony.udacitymovies.data.remote.RemoteMoviesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.remote.RemoteTrailersDataSource;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by eltonjhony on 20/06/17.
 */

public class MoviesRepository implements MoviesDataSource {

    private static final int FIRST_ITEM = 0;
    private static final int FIRST_PAGE = 1;

    private RemoteMoviesDataSource mRemoteDataSource;
    private RemoteTrailersDataSource remoteTrailersDataSource;

    @Inject
    public MoviesRepository(RemoteMoviesDataSource remoteDataSource, RemoteTrailersDataSource remoteTrailersDataSource) {
        this.mRemoteDataSource = remoteDataSource;
        this.remoteTrailersDataSource = remoteTrailersDataSource;
    }

    @Override
    public Observable<DataResultWrapper<Movie>> loadMovies(@ContentType int contentType, int page) throws NoInternetException {

        if (page == FIRST_PAGE) {
            return this.mRemoteDataSource.loadMovies(contentType, page).flatMap(movieDataResultWrapper -> {
                Movie movie = movieDataResultWrapper.getData().get(FIRST_ITEM);
                return remoteTrailersDataSource.getVideosById(movie.getId());
            }, (movieDataResultWrapper, videoWrapper) -> {
                if (videoWrapper != null && !videoWrapper.getResults().isEmpty()) {
                    Video video = videoWrapper.getResults().get(FIRST_ITEM);
                    movieDataResultWrapper.setVideoKey(video.getKey());
                }
                return movieDataResultWrapper;
            });
        }

        return this.mRemoteDataSource.loadMovies(contentType, page);
    }

    @Override
    public Observable<DataResultWrapper<Movie>> searchMovies(String language, String query, int page) throws NoInternetException {
        return this.mRemoteDataSource.searchMovies(language, query, page);
    }

    @Override
    public Observable<MovieDetail> getMovieById(String movieId, String language) throws NoInternetException {
        return this.mRemoteDataSource.getMovieById(movieId, language);
    }
}
