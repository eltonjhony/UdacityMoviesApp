package com.helabs.eltonjhony.udacitymovies.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helabs.eltonjhony.udacitymovies.R;
import com.helabs.eltonjhony.udacitymovies.bus.FavoritesChangedEvent;
import com.helabs.eltonjhony.udacitymovies.common.EndlessRecyclerViewScrollListener;
import com.helabs.eltonjhony.udacitymovies.ui.AnimatingFrameLayout;
import com.helabs.eltonjhony.udacitymovies.data.model.ContentType;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.databinding.FragmentMoviesBinding;
import com.helabs.eltonjhony.udacitymovies.details.DetailsActivity;
import com.helabs.eltonjhony.udacitymovies.infrastructure.preferences.CollapseFeaturedVideoPreferences;
import com.helabs.eltonjhony.udacitymovies.search.SearchFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.parceler.Parcels;

import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;

import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.FAVORITE;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.NOW_PLAYING;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.POPULAR;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.SEARCH;
import static com.helabs.eltonjhony.udacitymovies.data.model.ContentType.TOP_RATED;
import static com.helabs.eltonjhony.udacitymovies.infrastructure.Constants.PreferenceKeys.SHOW_HIDE_KEY;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesFragment extends SearchFragment implements MoviesContract.View {

    private static final String INDEX = "index";
    private static final String TOP_POSITION = "top";

    @Inject protected MoviesPresenter mPresenter;
    @Inject protected CollapseFeaturedVideoPreferences mCollapseFeaturedVideoPref;

    @ContentType protected int mContentType;

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
        EventBus.getDefault().register(this);
        setRetainInstance(true);
        initialize();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);
        setRetainInstance(true);

        setupAdapter();
        setupBottomNavigation();
        setupFeaturedVideo(true);
        setListeners();

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            retainScrollPosition(savedInstanceState);
        } else {
            mPresenter.loadMovies(getContentType(), INITIAL_OFF_SET);
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
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        this.mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void searchMovies(String query) {
        this.mPresenter.fetchOrSearchMovies(query, getContentType(), INITIAL_OFF_SET);
    }

    @Override
    public void setLoading(final boolean isActive) {
        if (getView() == null) {
            return;
        }
        getLayout().refreshLayout.post(() -> getLayout().refreshLayout.setRefreshing(isActive));
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
        if (getLayout().moviesLayout.getContext() != null) {
            Snackbar.make(getLayout().moviesLayout, message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void appendMoreMovies(List<Movie> data) {
        mScrollListener.setLoading(false);
        this.mAdapter.appendData(data);
    }

    @Override
    public void setupFeaturedVideo(String videoUrl) {
        replaceFragment(FeaturedVideoFragment.newInstance(videoUrl),
                R.id.youtube_player_fragment,
                getChildFragmentManager());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FavoritesChangedEvent event) {
        mPresenter.fetchOrSearchMovies(null, getContentType(), INITIAL_OFF_SET);
    }

    protected void initialize() {
        mContentType = POPULAR;
        mAdapter = new MoviesRecyclerAdapter((position, id) -> mPresenter.openDetails(id, getContentType()));
    }

    protected void setupFeaturedVideo(boolean visible) {
        if (visible) {
            boolean showFeaturedVideo = this.mCollapseFeaturedVideoPref.getBoolean(SHOW_HIDE_KEY);
            getLayout().youtubePlayerFragment.active(showFeaturedVideo);
            getLayout().hideShowBtn.setVisibility(View.VISIBLE);
        } else {
            getLayout().youtubePlayerFragment.active(false);
            getLayout().hideShowBtn.setVisibility(View.GONE);
        }
    }

    protected FragmentMoviesBinding getLayout() {
        return mBinding;
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
                setLoading(true);
                mPresenter.fetchOrSearchMovies(getCurrentQuery(), getContentType(), page);
            }
        };

        getLayout().movieList.addOnScrollListener(mScrollListener);

        getLayout().refreshLayout.setRefreshing(false);
        getLayout().refreshLayout.setEnabled(false);
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
        getLayout().moviesBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_popular:
                    mContentType = POPULAR;
                    collapseSearchItem();
                    break;

                case R.id.action_now_playing:
                    mContentType = NOW_PLAYING;
                    collapseSearchItem();
                    break;

                case R.id.action_top_rated:
                    mContentType = TOP_RATED;
                    collapseSearchItem();
                    break;
            }

            setupFeaturedVideo(true);
            changeMoviesDescLabel();
            mPresenter.loadMovies(mContentType, INITIAL_OFF_SET);
            return true;
        });
    }

    private void retainScrollPosition(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
        boolean hasIndex = savedInstanceState.containsKey(INDEX);
        boolean hasPosition = savedInstanceState.containsKey(TOP_POSITION);
        if (hasIndex && hasPosition) {
            int index = savedInstanceState.getInt(INDEX);
            int top = savedInstanceState.getInt(TOP_POSITION);
            mLayoutManager.scrollToPositionWithOffset(index, top);
        }
    }

    private @ContentType int getContentType() {
        final String currentQuery = getCurrentQuery();
        if (!TextUtils.isEmpty(currentQuery)) {
            mContentType = SEARCH;
        }

        changeMoviesDescLabel();
        return mContentType;
    }

    private void changeMoviesDescLabel() {
        switch (mContentType) {
            case POPULAR:
                getLayout().movieDescText.setText(R.string.popular_label);
                break;

            case NOW_PLAYING:
                getLayout().movieDescText.setText(R.string.now_playing_label);
                break;

            case TOP_RATED:
                getLayout().movieDescText.setText(R.string.top_rated_label);
                break;

            case SEARCH:
                getLayout().movieDescText.setText(R.string.search_label);
                break;

            case FAVORITE:
                getLayout().movieDescText.setText(R.string.favorites_label);
                break;
        }
    }
}
