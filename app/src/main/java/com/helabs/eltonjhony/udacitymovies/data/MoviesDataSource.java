package com.helabs.eltonjhony.udacitymovies.data;

import com.helabs.eltonjhony.udacitymovies.data.exceptions.NoInternetException;
import com.helabs.eltonjhony.udacitymovies.data.model.ContentType;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;

import rx.Observable;

/**
 * Created by eltonjhony on 05/06/17.
 */

public interface MoviesDataSource {

    Observable<DataResultWrapper<Movie>> loadMovies(@ContentType int contentType, int page) throws NoInternetException;

    Observable<DataResultWrapper<Movie>> searchMovies(String language, String query, int page) throws NoInternetException;

    Observable<MovieDetail> getMovieById(String movieId, String language, @ContentType int contentType) throws NoInternetException;

}
