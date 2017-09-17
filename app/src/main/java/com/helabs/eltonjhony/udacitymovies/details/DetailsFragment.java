package com.helabs.eltonjhony.udacitymovies.details;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.bus.FavoritesChangedEvent;
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

    private MovieDetail movieDetail;
    private Menu mMenu;
    private FragmentDetailsBinding mBinding;
    private int scrollX;
    private int scrollY;

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
        setHasOptionsMenu(true);
        setRetainInstance(true);
        movieDetail = Parcels.unwrap(getArguments().getParcelable(MOVIE_ARGS));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    public FragmentDetailsBinding getLayout() {
        return mBinding;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.mMenu = menu;
        inflater.inflate(R.menu.detail_menu, menu);
        mPresenter.checkFavorites(movieDetail.getId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.favorite_action:
                mPresenter.markAsFavorite(movieDetail);
        }

        return true;
    }

    @Override
    public void setOverview(String overview) {
        getLayout().summaryTextView.setText(overview);
    }

    @Override
    public void favoriteMarked(boolean updateFavorites) {
        mMenu.getItem(0)
                .setIcon(ContextCompat.getDrawable(getContext(),
                        R.drawable.ic_favorite_red_24dp));

        if (updateFavorites) {
            EventBus.getDefault().post(new FavoritesChangedEvent());
        }
    }

    @Override
    public void favoriteUnMarked(boolean updateFavorites) {
        mMenu.getItem(0)
                .setIcon(ContextCompat.getDrawable(getContext(),
                        R.drawable.ic_favorite_border_red_24dp));

        if (updateFavorites) {
            EventBus.getDefault().post(new FavoritesChangedEvent());
        }
    }

    private void initialize() {

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
}
