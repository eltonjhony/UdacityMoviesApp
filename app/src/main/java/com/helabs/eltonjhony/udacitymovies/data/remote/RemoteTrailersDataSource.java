package com.helabs.eltonjhony.udacitymovies.data.remote;

import com.helabs.eltonjhony.udacitymovies.data.TrailersDataSource;
import com.helabs.eltonjhony.udacitymovies.data.model.VideoWrapper;
import com.helabs.eltonjhony.udacitymovies.infrastructure.MyLog;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationConfiguration.getApiKey;

/**
 * Created by eltonjhony on 05/06/17.
 */

public class RemoteTrailersDataSource extends BaseDataSource implements TrailersDataSource {

    private Api mApi;

    @Inject
    public RemoteTrailersDataSource(Api api) {
        this.mApi = api;
    }

    @Override
    public Observable<VideoWrapper> getVideosById(String movieId) {
        return this.mApi.getVideosById(movieId, getApiKey())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(this::handleServerResponse);
    }

    @Override
    protected <T> Observable<T> handleServerResponse(Response<T> response) {
        ErrorHandler.Error error = handleError(response);
        if (error != null) {
            MyLog.error(error.getErrorMessage());
            return Observable.just(null);
        }
        return Observable.just(response.body());
    }
}
