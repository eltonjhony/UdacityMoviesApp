package com.helabs.eltonjhony.udacitymovies.movies;

import android.support.annotation.NonNull;

import com.helabs.eltonjhony.udacitymovies.common.BasePresenter;
import com.helabs.eltonjhony.udacitymovies.data.repository.MoviesRepository;
import com.helabs.eltonjhony.udacitymovies.data.model.ContentType;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

import static com.helabs.eltonjhony.udacitymovies.infrastructure.ApplicationConfiguration.*;

/**
 * Created by eltonjhony on 3/31/17.
 */
public class MoviesPresenter extends BasePresenter<MoviesContract.View> implements MoviesContract.Actions {

    private static final int FIRST_PAGE = 1;

    private MoviesRepository mMoviesRepository;

    private Subscription mSubscription;

    @Inject
    public MoviesPresenter(WeakReference<MoviesContract.View> view, MoviesRepository moviesRepository) {
        super(view);
        this.mMoviesRepository = moviesRepository;
    }

    @Override
    public void fetchOrSearchMovies(String query, @ContentType int contentType, int offSet) {
        if (query == null || query.isEmpty()) {
            loadMovies(contentType, offSet);
        } else {
            searchMovies(query, offSet);
        }
    }

    @Override
    public void loadMovies(@ContentType int contentType, int offSet) {
        getView().setLoading(true);
        mSubscription = this.mMoviesRepository.loadMovies(contentType, offSet)
                .subscribe(new Observer<DataResultWrapper<Movie>>() {
                    @Override
                    public void onCompleted() {
                        getViewOrThrow().setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewOrThrow().showError(e.getMessage());
                        getViewOrThrow().setLoading(false);
                    }

                    @Override
                    public void onNext(DataResultWrapper<Movie> dataResultWrapper) {
                        if (dataResultWrapper.getPage() == FIRST_PAGE) {
                            getViewOrThrow().showMovies(dataResultWrapper.getData());
                            getViewOrThrow().setupFeaturedVideo(dataResultWrapper.getVideoKey());
                        } else {
                            getViewOrThrow().appendMoreMovies(dataResultWrapper.getData());
                        }
                    }
                });

    }

    @Override
    public void searchMovies(String query, int offSet) {
        getView().setLoading(true);
        mSubscription = this.mMoviesRepository.searchMovies(getLanguage(), query, offSet)
                .subscribe(new Observer<DataResultWrapper<Movie>>() {
                    @Override
                    public void onCompleted() {
                        getViewOrThrow().setLoading(false);
                        mSubscription.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewOrThrow().showError(e.getMessage());
                        getViewOrThrow().setLoading(false);
                    }

                    @Override
                    public void onNext(DataResultWrapper<Movie> dataResultWrapper) {
                        if (dataResultWrapper.getPage() == 1) {
                            getViewOrThrow().showMovies(dataResultWrapper.getData());
                        } else {
                            getViewOrThrow().appendMoreMovies(dataResultWrapper.getData());
                        }
                    }
                });
    }

    @Override
    public void openDetails(@NonNull String id) {
        getView().setLoading(true);
        mSubscription = this.mMoviesRepository.getMovieById(id, getLanguage())
                .subscribe(new Observer<MovieDetail>() {
                    @Override
                    public void onCompleted() {
                        getViewOrThrow().setLoading(false);
                        mSubscription.unsubscribe();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewOrThrow().showError(e.getMessage());
                        getViewOrThrow().setLoading(false);
                    }

                    @Override
                    public void onNext(MovieDetail movieDetail) {
                        getViewOrThrow().showMovieDetails(movieDetail);
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
