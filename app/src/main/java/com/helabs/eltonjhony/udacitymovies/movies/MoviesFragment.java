package com.helabs.eltonjhony.udacitymovies.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.common.EndlessRecyclerViewScrollListener;
import com.helabs.eltonjhony.udacitymovies.ui.AnimatingFrameLayout;
import com.helabs.eltonjhony.udacitymovies.data.model.ContentType;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.databinding.FragmentMoviesBinding;
import com.helabs.eltonjhony.udacitymovies.details.DetailsActivity;
import com.helabs.eltonjhony.udacitymovies.infrastructure.preferences.CollapseFeaturedVideoPreferences;
import com.helabs.eltonjhony.udacitymovies.search.SearchFragment;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.NOW_PLAYING;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.POPULAR;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.TOP_RATED;
import static com.helabs.eltonjhony.udacitymovies.infrastructure.Constants.PreferenceKeys.SHOW_HIDE_KEY;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesFragment extends SearchFragment implements MoviesContract.View {

    private static final String INDEX = "index";
    private static final String TOP_POSITION = "top";

    @Inject
    MoviesPresenter mPresenter;

    @Inject
    CollapseFeaturedVideoPreferences mCollapseFeaturedVideoPref;

    @ContentType
    private int mContentType;

    private FragmentMoviesBinding mBinding;
    private MoviesRecyclerAdapter mAdapter;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private GridLayoutManager mLayoutManager;

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().plus(new MoviesModule(this)).inject(this);
        setRetainInstance(true);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);

        setupAdapter();
        setupBottomNavigation();
        setListeners();

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean showFeaturedVideo = this.mCollapseFeaturedVideoPref.getBoolean(SHOW_HIDE_KEY);
        getLayout().youtubePlayerFragment.active(showFeaturedVideo);

        if (savedInstanceState != null) {
            retainScrollPosition(savedInstanceState);
        } else {
            mPresenter.loadMovies(mContentType, INITIAL_OFF_SET);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        View startView = mLayoutManager.getChildAt(0);
        outState.putInt(INDEX, mLayoutManager.findFirstVisibleItemPosition());
        outState.putInt(TOP_POSITION, (startView == null) ? 0 :
                (startView.getTop() - mLayoutManager.getPaddingTop()));
    }

    @Override
    public void onDestroy() {
        this.mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void searchMovies(String query) {
        this.mPresenter.fetchOrSearchMovies(query, mContentType, INITIAL_OFF_SET);
    }

    @Override
    public void setLoading(final boolean isActive) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = mBinding.refreshLayout;
        srl.post(() -> srl.setRefreshing(isActive));
    }

    @Override
    public void showMovies(List<Movie> movies) {
        this.mScrollListener.resetCurrentPage();
        this.mAdapter.replaceData(movies);
    }

    @Override
    public void showMovieDetails(MovieDetail detail) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.MOVIE_EXTRA, Parcels.wrap(detail));
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(getLayout().moviesLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void appendMoreMovies(List<Movie> data) {
        this.mAdapter.appendData(data);
    }

    @Override
    public void setupFeaturedVideo(String videoUrl) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_player_fragment, FeaturedVideoFragment.newInstance(videoUrl)).commit();
    }

    private FragmentMoviesBinding getLayout() {
        return mBinding;
    }

    private void initialize() {
        mContentType = POPULAR;
        mAdapter = new MoviesRecyclerAdapter((position, id) -> mPresenter.openDetails(id));
    }

    private void setListeners() {

        getLayout().hideShowBtn.setOnClickListener(v -> {
            final AnimatingFrameLayout youtubeFrameLayout = getLayout().youtubePlayerFragment;
            if (youtubeFrameLayout.isVisible()) {
                youtubeFrameLayout.active(false);
                this.mCollapseFeaturedVideoPref.putBoolean(SHOW_HIDE_KEY, false);
            } else {
                youtubeFrameLayout.active(true);
                this.mCollapseFeaturedVideoPref.putBoolean(SHOW_HIDE_KEY, true);
            }
        });

        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemCount, RecyclerView recyclerView) {
                mPresenter.fetchOrSearchMovies(getCurrentQuery(), mContentType, page);
            }
        };

        getLayout().movieList.addOnScrollListener(mScrollListener);

        getLayout().refreshLayout.setOnRefreshListener(() -> {
            mPresenter.fetchOrSearchMovies(getCurrentQuery(), mContentType, INITIAL_OFF_SET);
        });
    }

    private void setupAdapter() {
        final int numColumns = 3;

        getLayout().movieList.setAdapter(mAdapter);
        getLayout().movieList.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getContext(), numColumns);

        getLayout().movieList.setLayoutManager(mLayoutManager);
        getLayout().movieList.setEmptyView(mBinding.emptyLayout.getRoot());
    }

    private void setupBottomNavigation() {
        BottomNavigationView mBottomNavigation = getLayout().moviesBottomNavigation;
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_popular:
                    mContentType = POPULAR;
                    getLayout().movieDescText.setText(R.string.most_popular_label);
                    break;

                case R.id.action_now_playing:
                    mContentType = NOW_PLAYING;
                    getLayout().movieDescText.setText(R.string.now_playing_label);
                    break;

                case R.id.action_top_rated:
                    mContentType = TOP_RATED;
                    getLayout().movieDescText.setText(R.string.top_rated_label);
                    break;
            }
            mPresenter.loadMovies(mContentType, INITIAL_OFF_SET);
            return true;
        });
    }

    private void retainScrollPosition(@Nullable Bundle savedInstanceState) {
        boolean hasIndex = savedInstanceState.containsKey(INDEX);
        boolean hasPosition = savedInstanceState.containsKey(TOP_POSITION);
        if (hasIndex && hasPosition) {
            int index = savedInstanceState.getInt(INDEX);
            int top = savedInstanceState.getInt(TOP_POSITION);
            mLayoutManager.scrollToPositionWithOffset(index, top);
        }
    }

}
