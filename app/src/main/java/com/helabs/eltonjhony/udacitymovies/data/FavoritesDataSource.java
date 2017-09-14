package com.helabs.eltonjhony.udacitymovies.data;

import com.helabs.eltonjhony.udacitymovies.data.model.Favorites;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;

import java.util.List;

import rx.Observable;

/**
 * Created by eltonjhony on 10/09/17.
 */

public interface FavoritesDataSource {

    interface OnLoadFavoritesCallback {

        void onLoaded(List<Favorites> favorites);
    }

    interface OnGetFavoriteByIdCallback {

        void onLoaded(Favorites favorites);
    }

    void loadAllFavorites(OnLoadFavoritesCallback callback);

    void getFavoritesById(String movieId, OnGetFavoriteByIdCallback callback);

    void insert(Favorites favorites);

    void delete(Favorites favorites);

    Observable<MovieDetail> getFavoritesById(String movieId);
}
