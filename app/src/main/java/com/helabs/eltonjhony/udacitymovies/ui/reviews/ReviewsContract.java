package com.helabs.eltonjhony.udacitymovies.ui.reviews;

import com.helabs.eltonjhony.udacitymovies.data.model.Review;

import java.util.List;

/**
 * Created by eltonjhony on 09/09/17.
 */

public interface ReviewsContract {

    interface View {

        void showError(String message);

        void showReviews(List<Review> data);
    }

    interface Presenter {

        void loadReviews(String movieId);

        void onDestroy();
    }
}
