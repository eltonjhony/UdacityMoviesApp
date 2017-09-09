package com.helabs.eltonjhony.udacitymovies.data;

import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Review;

import rx.Observable;

/**
 * Created by eltonjhony on 31/08/17.
 */

public interface ReviewsDataSource {

    Observable<DataResultWrapper<Review>> findUserReviews(Integer movieId, String language);
}
