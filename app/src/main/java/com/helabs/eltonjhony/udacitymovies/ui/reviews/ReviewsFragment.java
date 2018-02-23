package com.helabs.eltonjhony.udacitymovies.ui.reviews;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.common.BaseFragment;
import com.helabs.eltonjhony.udacitymovies.common.TextViewBuilder;
import com.helabs.eltonjhony.udacitymovies.common.ViewBuilder;
import com.helabs.eltonjhony.udacitymovies.data.model.Review;
import com.helabs.eltonjhony.udacitymovies.databinding.FragmentReviewsBinding;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 09/09/17.
 */

public class ReviewsFragment extends BaseFragment implements ReviewsContract.View {

    public static final String MOVIE_ID_EXTRA = "movieIdExtra";

    @Inject ReviewsContract.Presenter mPresenter;

    private FragmentReviewsBinding mBinding;

    public static ReviewsFragment newInstance(String movieId) {
        Bundle args = new Bundle();
        args.putString(MOVIE_ID_EXTRA, movieId);
        ReviewsFragment fragment = new ReviewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent()
                .plus(new ReviewsModule(this))
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false);
        return getLayout().getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String movieId = getArguments().getString(MOVIE_ID_EXTRA);

        if (movieId != null) {
            mPresenter.loadReviews(movieId);
        }
    }

    @Override
    public void showError(String message) {
        Snackbar.make(getLayout().reviewsLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showReviews(List<Review> data) {
        for (Review review : data) {

            TextViewBuilder
                    .with(getContext())
                    .text(String.format("%s %s:", review.getAuthor(), getString(R.string.says_label)))
                    .textColor(ContextCompat.getColor(getContext(), R.color.yelloWhite))
                    .padding(0, 50, 0, 0)
                    .into(getLayout().reviewsLayout);

            TextViewBuilder
                    .with(getContext())
                    .text(review.getContent())
                    .textColor(ContextCompat.getColor(getContext(), R.color.grayWhite))
                    .padding(0, 10, 10, 0)
                    .into(getLayout().reviewsLayout);

            ViewBuilder
                    .with(getContext())
                    .params(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1))
                    .backgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite))
                    .into(getLayout().reviewsLayout);
        }
    }

    private FragmentReviewsBinding getLayout() {
        return mBinding;
    }
}
