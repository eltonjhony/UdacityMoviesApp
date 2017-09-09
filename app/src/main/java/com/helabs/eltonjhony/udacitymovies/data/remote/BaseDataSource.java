package com.helabs.eltonjhony.udacitymovies.data.remote;

import com.helabs.eltonjhony.udacitymovies.data.exceptions.ApiException;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by eltonjhony on 08/09/17.
 */

public abstract class BaseDataSource {

    protected <T> Observable<T> handleServerResponse(Response<T> response) {
        ErrorHandler.Error error = handleError(response);
        if (error != null) {
            return Observable.error(new ApiException(error.getErrorMessage()));
        }
        return Observable.just(response.body());
    }

    protected  <T> ErrorHandler.Error handleError(Response<T> response) {
        int code = response.code();
        if (code != 200) {
            return new ErrorHandler(response.errorBody()).extract();
        }
        return null;
    }
}
