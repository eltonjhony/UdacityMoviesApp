package com.helabs.eltonjhony.udacitymovies.data.remote;

import com.helabs.eltonjhony.udacitymovies.data.ReviewsDataSource;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Review;

import rx.Observable;

/**
 * Created by eltonjhony on 31/08/17.
 */

public class RemoteReviewsDataSource implements ReviewsDataSource {

    @Override
    public Observable<DataResultWrapper<Review>> findUserReviews(Integer movieId, String language) {
        return null;
    }
}
