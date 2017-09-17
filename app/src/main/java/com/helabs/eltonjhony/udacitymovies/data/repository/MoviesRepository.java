package com.helabs.eltonjhony.udacitymovies.data.repository;

import com.helabs.eltonjhony.udacitymovies.data.MoviesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.local.LocalFavoritesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.local.table.FavoritesTable;
import com.helabs.eltonjhony.udacitymovies.data.model.ContentType;
import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Favorites;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.data.model.Video;
import com.helabs.eltonjhony.udacitymovies.data.remote.RemoteMoviesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.remote.RemoteTrailersDataSource;
import com.helabs.eltonjhony.udacitymovies.utils.NetworkUtil;
import com.helabs.eltonjhony.udacitymovies.utils.ParseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by eltonjhony on 20/06/17.
 */

public class MoviesRepository implements MoviesDataSource {

    private static final int FIRST_PAGE = 1;

    private RemoteMoviesDataSource mRemoteDataSource;
    private RemoteTrailersDataSource mRemoteTrailersDataSource;
    private LocalFavoritesDataSource mLocalFavoritesDataSource;
    private NetworkUtil mNetworkUtil;

    @Inject
    public MoviesRepository(RemoteMoviesDataSource remoteDataSource,
                            RemoteTrailersDataSource remoteTrailersDataSource,
                            LocalFavoritesDataSource mLocalFavoritesDataSource,
                            NetworkUtil networkUtil) {
        this.mRemoteDataSource = remoteDataSource;
        this.mRemoteTrailersDataSource = remoteTrailersDataSource;
        this.mLocalFavoritesDataSource = mLocalFavoritesDataSource;
        this.mNetworkUtil = networkUtil;
    }

    @Override
    public Observable<DataResultWrapper<Movie>> loadMovies(@ContentType int contentType, int page) {

        // Get favorites from LocalDataSource
        if (contentType == ContentType.FAVORITE) {
            final List<Favorites> favorites = new ArrayList<>();
            mLocalFavoritesDataSource.loadAllFavorites(null, FavoritesTable.ORDER_BY, favorites::addAll);
            return Observable.just(new DataResultWrapper<>(ParseUtils.parseFrom(favorites)));
        }

        if (page == FIRST_PAGE) {
            return this.mRemoteDataSource
                    .loadMovies(contentType, page)
                    .flatMap(movieDataResultWrapper -> {
                        Movie movie = getRandomItem(movieDataResultWrapper.getData());
                        return mRemoteTrailersDataSource.getVideosById(movie.getId());
            }, (movieDataResultWrapper, videoWrapper) -> {
                if (videoWrapper != null && !videoWrapper.getResults().isEmpty()) {
                    Video video = getRandomItem(videoWrapper.getResults());
                    movieDataResultWrapper.setVideoKey(video.getKey());
                }
                return movieDataResultWrapper;
            });
        }

        return this.mRemoteDataSource
                .loadMovies(contentType, page);
    }

    @Override
    public Observable<DataResultWrapper<Movie>> searchMovies(String language, String query, int page) {
        return this.mRemoteDataSource.searchMovies(language, query, page);
    }

    @Override
    public Observable<MovieDetail> getMovieById(String movieId, String language, @ContentType int contentType) {

        if (!mNetworkUtil.isNetworkAvailable() && contentType == ContentType.FAVORITE) {
            return mLocalFavoritesDataSource.getFavoritesById(movieId, null);
        }

        return this.mRemoteDataSource.getMovieById(movieId, language, contentType);
    }

    private <T> T getRandomItem(List<T> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int i = random.nextInt(data.size());
        if (i >= data.size()) {
            return data.get(0);
        }
        return data.get(i);
    }
}
