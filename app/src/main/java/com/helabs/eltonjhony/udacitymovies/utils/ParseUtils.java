package com.helabs.eltonjhony.udacitymovies.utils;

import com.helabs.eltonjhony.udacitymovies.data.model.Favorites;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eltonjhony on 09/09/17.
 */
public class ParseUtils {

    private ParseUtils() {
    }

    public static List<Movie> parseFrom(List<Favorites> favorites) {
        List<Movie> movies = new ArrayList<>();
        if (favorites == null) {
            return movies;
        }
        for (Favorites fav : favorites) {
            Movie movie = new Movie(fav.getMovieId(), fav.getPosterUrl(),
                    fav.getBackdropPath(), fav.getTitle());
            movies.add(movie);
        }
        return movies;
    }

    public static MovieDetail parseFrom(Favorites favorites) {
        MovieDetail movieDetail = new MovieDetail(
                favorites.getMovieId(),
                favorites.getPosterUrl(),
                favorites.getPopularity(),
                favorites.getOverview(),
                favorites.getBackdropPath(),
                favorites.getVoteAverage(),
                favorites.getVoteCount(),
                favorites.getTitle(),
                favorites.getReleased()
        );
        return movieDetail;
    }
}
