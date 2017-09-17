package com.helabs.eltonjhony.udacitymovies.data.local.table;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by eltonjhony on 17/09/17.
 */

public class FavoritesTable {

    public static final String TABLE_NAME = "favorites";
    public static final String MOVIE_ID = "movieId";
    public static final String POSTER_URL = "posterUrl";
    public static final String BACKDROP_PATH = "backdropPath";
    public static final String TITLE = "title";
    public static final String RELEASE = "released";
    public static final String POPULARITY = "popularity";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "voteAverage";
    public static final String VOTE_COUNT = "voteCount";

    public static final String WHERE_ID_EQUALS = MOVIE_ID + "=?";
    public static final String ORDER_BY = MOVIE_ID + " ASC";

    public static List<String> ALL_COLUMNS = Collections.unmodifiableList(Arrays.asList(
            MOVIE_ID, POSTER_URL, BACKDROP_PATH, TITLE,
            RELEASE, POPULARITY, OVERVIEW, VOTE_AVERAGE,
            VOTE_COUNT));

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "("
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
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
