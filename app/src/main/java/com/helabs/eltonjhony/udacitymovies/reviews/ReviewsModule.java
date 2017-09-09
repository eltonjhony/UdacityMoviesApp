package com.helabs.eltonjhony.udacitymovies.reviews;

import com.helabs.eltonjhony.udacitymovies.data.repository.ReviewsRepository;
import com.helabs.eltonjhony.udacitymovies.di.FragmentScoped;

import java.lang.ref.WeakReference;

import dagger.Module;
import dagger.Provides;

/**
 * Created by eltonjhony on 04/08/17.
 */
@Module
public class ReviewsModule {

    private final WeakReference<ReviewsContract.View> mView;

    public ReviewsModule(ReviewsContract.View view) {
        mView = new WeakReference<>(view);
    }

    @Provides
    @FragmentScoped
    WeakReference<ReviewsContract.View> provideView() {
        return mView;
    }

    @Provides
    @FragmentScoped
    ReviewsContract.Presenter provideReviewsPresenter(ReviewsRepository reviewsRepository) {
        return new ReviewsPresenter(mView, reviewsRepository);
    }
}
