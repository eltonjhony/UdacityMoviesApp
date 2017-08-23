package com.helabs.eltonjhony.udacitymovies.data.repository;

import com.helabs.eltonjhony.udacitymovies.data.TrailersDataSource;
import com.helabs.eltonjhony.udacitymovies.data.model.VideoWrapper;
import com.helabs.eltonjhony.udacitymovies.data.remote.RemoteTrailersDataSource;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by eltonjhony on 20/06/17.
 */

public class TrailersRepository implements TrailersDataSource {

    private RemoteTrailersDataSource remoteTrailersDataSource;

    @Inject
    public TrailersRepository(RemoteTrailersDataSource remoteTrailersDataSource) {
        this.remoteTrailersDataSource = remoteTrailersDataSource;
    }

    @Override
    public Observable<VideoWrapper> getVideosById(String movieId) {
        return this.remoteTrailersDataSource.getVideosById(movieId);
    }
}
