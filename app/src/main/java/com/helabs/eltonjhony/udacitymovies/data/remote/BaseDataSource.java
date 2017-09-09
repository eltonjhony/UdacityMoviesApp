package com.helabs.eltonjhony.udacitymovies.data.remote;

import com.helabs.eltonjhony.udacitymovies.data.exceptions.ApiException;
import com.helabs.eltonjhony.udacitymovies.data.exceptions.FeaturedVideoException;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.data.model.VideoWrapper;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by eltonjhony on 08/09/17.
 */

public abstract class BaseDataSource {

    public Observable<DataResultWrapper<Movie>> handleMovieResponse(Response<DataResultWrapper<Movie>> response) {
        ErrorHandler.Error error = handleError(response);
        if (error != null) {
            return Observable.error(new ApiException(error.getErrorMessage()));
        }
        return Observable.just(response.body());
    }

    public Observable<MovieDetail> handleMovieDetailResponse(Response<MovieDetail> response) {
        ErrorHandler.Error error = handleError(response);
        if (error != null) {
            return Observable.error(new ApiException(error.getErrorMessage()));
        }
        return Observable.just(response.body());
    }

    public Observable<VideoWrapper> handleVideosResponse(Response<VideoWrapper> response) {
        ErrorHandler.Error error = handleError(response);
        if (error != null) {
            return Observable.error(new FeaturedVideoException(error.getErrorMessage()));
        }
        return Observable.just(response.body());
    }

    private <T> ErrorHandler.Error handleError(Response<T> response) {
        int code = response.code();
        if (code != 200) {
            return new ErrorHandler(response.errorBody()).extract();
        }
        return null;
    }
}
