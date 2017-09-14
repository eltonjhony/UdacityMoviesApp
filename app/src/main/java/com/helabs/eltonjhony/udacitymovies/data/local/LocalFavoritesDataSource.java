package com.helabs.eltonjhony.udacitymovies.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.helabs.eltonjhony.udacitymovies.data.FavoritesDataSource;
import com.helabs.eltonjhony.udacitymovies.data.model.Favorites;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.utils.ParseUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by eltonjhony on 09/09/17.
 */

public class LocalFavoritesDataSource implements FavoritesDataSource {

    public static final String FAVORITES_TABLE = "favorites";
    public static final String MOVIE_ID = "movieId";
    public static final String POSTER_URL = "posterUrl";
    public static final String BACKDROP_PATH = "backdropPath";
    public static final String TITLE = "title";
    public static final String RELEASE = "released";
    public static final String POPULARITY = "popularity";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "voteAverage";
    public static final String VOTE_COUNT = "voteCount";

    private LocalDatabase localDatabase;

    public LocalFavoritesDataSource(LocalDatabase localDatabase) {
        this.localDatabase = localDatabase;
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS "
                + FAVORITES_TABLE + "("
                + MOVIE_ID + " TEXT PRIMARY KEY, "
                + POSTER_URL + " TEXT, "
                + BACKDROP_PATH + " TEXT, "
                + TITLE + " TEXT, "
                + RELEASE + " TEXT, "
                + POPULARITY + " TEXT, "
                + OVERVIEW + " TEXT, "
                + VOTE_AVERAGE + " TEXT, "
                + VOTE_COUNT + " TEXT" + ")";
    }

    public static String dropTable() {
        return "DROP TABLE IF EXISTS " + FAVORITES_TABLE;
    }

    @Override
    public void loadAllFavorites(OnLoadFavoritesCallback callback) {

        SQLiteDatabase db = localDatabase.getWritableDatabase();

        final List<Favorites> results = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + FAVORITES_TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                Favorites favorites = new Favorites(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8));
                results.add(favorites);
            } while (cursor.moveToNext());
        }

        callback.onLoaded(results);

        db.close();
    }

    @Override
    public void getFavoritesById(String movieId, OnGetFavoriteByIdCallback callback) {
        Favorites favorites = getById(movieId);
        callback.onLoaded(favorites);
    }

    @Override
    public Observable<MovieDetail> getFavoritesById(String movieId) {
        return Observable.just(ParseUtils.parseFrom(getById(movieId)));
    }

    @Override
    public void insert(Favorites favorites) {
        SQLiteDatabase db = localDatabase.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BACKDROP_PATH, favorites.getBackdropPath());
        values.put(MOVIE_ID, favorites.getMovieId());
        values.put(OVERVIEW, favorites.getOverview());
        values.put(POPULARITY, favorites.getPopularity());
        values.put(POSTER_URL, favorites.getPosterUrl());
        values.put(RELEASE, favorites.getReleased());
        values.put(TITLE, favorites.getTitle());
        values.put(VOTE_AVERAGE, favorites.getVoteAverage());
        values.put(VOTE_COUNT, favorites.getVoteCount());

        db.insert(FAVORITES_TABLE, null, values);
        db.close();
    }

    @Override
    public void delete(Favorites favorites) {
        SQLiteDatabase db = localDatabase.getWritableDatabase();
        db.delete(FAVORITES_TABLE, MOVIE_ID + " = ?", new String[] {
                String.valueOf(favorites.getMovieId())
        });
        db.close();
    }

    private Favorites getById(String movieId) {
        Favorites favorites = null;
        SQLiteDatabase db = localDatabase.getWritableDatabase();

        Cursor cursor = db.query(FAVORITES_TABLE, new String[]{
                        MOVIE_ID,
                        POSTER_URL,
                        BACKDROP_PATH,
                        TITLE,
                        RELEASE,
                        POPULARITY,
                        OVERVIEW,
                        VOTE_AVERAGE,
                        VOTE_COUNT
                }, MOVIE_ID + "=?", new String[]{movieId},
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                favorites = new Favorites(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8));
            }
        }

        db.close();
        return favorites;
    }
}
