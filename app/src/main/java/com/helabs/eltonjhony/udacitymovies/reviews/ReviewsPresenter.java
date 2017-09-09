package com.helabs.eltonjhony.udacitymovies.reviews;

import com.helabs.eltonjhony.udacitymovies.common.BasePresenter;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Review;
import com.helabs.eltonjhony.udacitymovies.data.repository.ReviewsRepository;

import java.lang.ref.WeakReference;

import rx.Observer;
import rx.Subscription;

/**
 * Created by eltonjhony on 09/09/17.
 */

public class ReviewsPresenter extends BasePresenter<ReviewsContract.View>
        implements ReviewsContract.Presenter {

    private final ReviewsRepository reviewsRepository;

    private Subscription mSubscription;

    public ReviewsPresenter(WeakReference<ReviewsContract.View> view, ReviewsRepository reviewsRepository) {
        super(view);
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public void loadReviews(String movieId) {
        this.mSubscription = reviewsRepository.findUserReviews(movieId)
                .subscribe(new Observer<DataResultWrapper<Review>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(e.getMessage());
                    }

                    @Override
                    public void onNext(DataResultWrapper<Review> data) {
                        getView().showReviews(data.getData());
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        super.detachView();
    }
}
