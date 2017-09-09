package com.helabs.eltonjhony.udacitymovies.data.remote;

import com.helabs.eltonjhony.udacitymovies.data.TrailersDataSource;
import com.helabs.eltonjhony.udacitymovies.data.model.VideoWrapper;

import javax.inject.Inject;

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
                .flatMap(this::handleVideosResponse);
    }
}
