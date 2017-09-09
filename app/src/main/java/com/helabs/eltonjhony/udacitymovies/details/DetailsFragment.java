package com.helabs.eltonjhony.udacitymovies.details;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.common.BaseFragment;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.databinding.FragmentDetailsBinding;
import com.helabs.eltonjhony.udacitymovies.reviews.ReviewsFragment;
import com.helabs.eltonjhony.udacitymovies.trailers.TrailersFragment;
import com.helabs.eltonjhony.udacitymovies.bus.UpdateTrailersEvent;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.parceler.Parcels;

import javax.inject.Inject;

/**
 * Created by eltonjhony on 20/05/17.
 */
public class DetailsFragment extends BaseFragment implements DetailsContract.View {

    public static final String MOVIE_ARGS = "com.helabs.eltonjhony.MOVIE_ARGS";

    @Inject DetailsPresenter mPresenter;

    private FragmentDetailsBinding mBinding;

    public static DetailsFragment newInstance(MovieDetail movieDetail) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_ARGS, Parcels.wrap(movieDetail));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().plus(new DetailsModule(this)).inject(this);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        initialize();

        return mBinding.getRoot();
    }

    public FragmentDetailsBinding getLayout() {
        return mBinding;
    }

    private void initialize() {

        MovieDetail movieDetail = Parcels.unwrap(getArguments().getParcelable(MOVIE_ARGS));

        // fill details view information
        Picasso.with(getActivity()).load(movieDetail.getPosterUrl()).into(getLayout().posterImageView);
        getLayout().viewReleaseDate.setText(movieDetail.getReleasedDateFormatted());
        getLayout().viewPopularity.setText(movieDetail.getPopularityConverted());
        getLayout().viewVoteCount.setText(movieDetail.getVoteCount());
        getLayout().viewVoteAverage.setRating(movieDetail.getVoteAverageAsFloat());

        mPresenter.defineOverviewMessage(movieDetail.getOverview());

        // inflating trailers fragment
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container_trailer, TrailersFragment.newInstance(movieDetail.getId()))
                .commit();

        // inflating reviews fragment
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.container_reviews, ReviewsFragment.newInstance(movieDetail.getId()))
                .commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateTrailersEvent e) {
        if (e.isTrailersLoaded()) {
            getLayout().trailerTextView.setVisibility(View.VISIBLE);
        } else {
            getLayout().trailerTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setOverview(String overview) {
        getLayout().summaryTextView.setText(overview);
    }
}
