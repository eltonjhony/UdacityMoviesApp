package com.helabs.eltonjhony.udacitymovies.data.model;

import android.database.Cursor;

import com.helabs.eltonjhony.udacitymovies.data.local.table.FavoritesTable;

/**
 * Created by eltonjhony on 09/09/17.
 */
public class Favorites {

    private String movieId;
    private String posterUrl;
    private String backdropPath;
    private String title;
    private String released;
    private String popularity;
    private String overview;
    private String voteAverage;
    private String voteCount;

    public Favorites(String movieId, String posterUrl, String backdropPath,
                     String title, String released, String popularity,
                     String overview, String voteAverage, String voteCount) {
        this.movieId = movieId;
        this.posterUrl = posterUrl;
        this.backdropPath = backdropPath;
        this.title = title;
        this.released = released;
        this.popularity = popularity;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public Favorites(Cursor cursor) {
        this.movieId = cursor.getString(cursor.getColumnIndex(FavoritesTable.MOVIE_ID));
        this.posterUrl = cursor.getString(cursor.getColumnIndex(FavoritesTable.POSTER_URL));;
        this.backdropPath = cursor.getString(cursor.getColumnIndex(FavoritesTable.BACKDROP_PATH));;
        this.title = cursor.getString(cursor.getColumnIndex(FavoritesTable.TITLE));;
        this.released = cursor.getString(cursor.getColumnIndex(FavoritesTable.RELEASE));;
        this.popularity = cursor.getString(cursor.getColumnIndex(FavoritesTable.POPULARITY));;
        this.overview = cursor.getString(cursor.getColumnIndex(FavoritesTable.OVERVIEW));;
        this.voteAverage = cursor.getString(cursor.getColumnIndex(FavoritesTable.VOTE_AVERAGE));;
        this.voteCount = cursor.getString(cursor.getColumnIndex(FavoritesTable.VOTE_COUNT));;
    }

    public static Favorites set(MovieDetail movieDetail) {
        return new Favorites(movieDetail.getId(), movieDetail.getSimplePosterUrl(),
                movieDetail.getSimpleBackdropPath(), movieDetail.getTitle(),
                movieDetail.getReleased(), movieDetail.getPopularity(),
                movieDetail.getOverview(), movieDetail.getVoteAverage(),
                movieDetail.getVoteCount());
    }

    public String getMovieId() {
        return movieId;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleased() {
        return released;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

}
