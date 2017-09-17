package com.helabs.eltonjhony.udacitymovies.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.helabs.eltonjhony.udacitymovies.data.FavoritesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.local.provider.FavoritesContentProvider;
import com.helabs.eltonjhony.udacitymovies.data.local.table.FavoritesTable;
import com.helabs.eltonjhony.udacitymovies.data.model.Favorites;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.utils.ParseUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by eltonjhony on 09/09/17.
 */

public class LocalFavoritesDataSource implements FavoritesDataSource {

    private Context context;

    @Inject
    public LocalFavoritesDataSource(Context context) {
        this.context = context;
    }

    @Override
    public void loadAllFavorites(String[] projection, final String sortOrder, OnLoadFavoritesCallback callback) {

        if (projection == null) {
            projection = (String[]) FavoritesTable.ALL_COLUMNS.toArray();
        }

        Cursor cursor = context.getContentResolver().query(FavoritesContentProvider.FAVORITES_CONTENT_URI,
                projection, null, null, sortOrder);

        List<Favorites> favoriteList = getFavoriteList(cursor);
        callback.onLoaded(favoriteList);
    }

    @Override
    public void getFavoritesById(String movieId, String[] projection, OnGetFavoriteByIdCallback callback) {

        if (projection == null) {
            projection = (String[]) FavoritesTable.ALL_COLUMNS.toArray();
        }

        Cursor cursor = getById(movieId, projection);

        callback.onLoaded(getFavorite(cursor));
    }

    @Override
    public Observable<MovieDetail> getFavoritesById(String movieId, String[] projection) {
        Cursor cursor = getById(movieId, projection);
        Favorites favorite = getFavorite(cursor);
        return Observable.just(ParseUtils.parseFrom(favorite));
    }

    @Override
    public void insert(Favorites favorites) {

        ContentValues values = new ContentValues();
        values.put(FavoritesTable.BACKDROP_PATH, favorites.getBackdropPath());
        values.put(FavoritesTable.MOVIE_ID, favorites.getMovieId());
        values.put(FavoritesTable.OVERVIEW, favorites.getOverview());
        values.put(FavoritesTable.POPULARITY, favorites.getPopularity());
        values.put(FavoritesTable.POSTER_URL, favorites.getPosterUrl());
        values.put(FavoritesTable.RELEASE, favorites.getReleased());
        values.put(FavoritesTable.TITLE, favorites.getTitle());
        values.put(FavoritesTable.VOTE_AVERAGE, favorites.getVoteAverage());
        values.put(FavoritesTable.VOTE_COUNT, favorites.getVoteCount());

        context.getContentResolver().insert(FavoritesContentProvider.FAVORITES_CONTENT_URI, values);
    }

    @Override
    public void delete(Favorites favorites) {
        context.getContentResolver().delete(Uri.parse(FavoritesContentProvider.FAVORITES_CONTENT_URI + "/" +
                favorites.getMovieId()), null, null);
    }

    private Cursor getById(String movieId, String[] projection) {
        return context.getContentResolver().query(FavoritesContentProvider.FAVORITES_CONTENT_URI, projection,
                FavoritesTable.WHERE_ID_EQUALS, new String[]{movieId}, null);
    }

    private List<Favorites> getFavoriteList(final Cursor cursor) {

        List<Favorites> favoritesList = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Favorites favorites = new Favorites(cursor);
                favoritesList.add(favorites);
            }
            cursor.close();
        }

        return favoritesList;
    }

    private Favorites getFavorite(final Cursor cursor) {

        Favorites favorites = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                favorites = new Favorites(cursor);
            }
            cursor.close();
        }

        return favorites;
    }
}
