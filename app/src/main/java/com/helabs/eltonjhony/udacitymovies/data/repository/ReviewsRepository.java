package com.helabs.eltonjhony.udacitymovies.data.repository;

import com.helabs.eltonjhony.udacitymovies.data.ReviewsDataSource;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Review;
import com.helabs.eltonjhony.udacitymovies.data.remote.RemoteReviewsDataSource;

import rx.Observable;

/**
 * Created by eltonjhony on 09/09/17.
 */
public class ReviewsRepository implements ReviewsDataSource {

    private RemoteReviewsDataSource dataSource;

    public ReviewsRepository(RemoteReviewsDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<DataResultWrapper<Review>> findUserReviews(String movieId) {
        return dataSource.findUserReviews(movieId);
    }
}
