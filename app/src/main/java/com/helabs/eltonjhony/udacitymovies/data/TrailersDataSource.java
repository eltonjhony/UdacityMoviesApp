package com.helabs.eltonjhony.udacitymovies.data;

import com.helabs.eltonjhony.udacitymovies.data.model.VideoWrapper;

import rx.Observable;

/**
 * Created by eltonjhony on 05/06/17.
 */

public interface TrailersDataSource {

    Observable<VideoWrapper> getVideosById(String movieId);
}
