package com.helabs.eltonjhony.udacitymovies.movies;

import android.support.annotation.NonNull;

import com.helabs.eltonjhony.udacitymovies.data.model.ContentType;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;

import java.util.List;

/**
 * Created by eltonjhony on 3/31/17.
 */

public interface MoviesContract {

    interface View {

        int INITIAL_OFF_SET = 1;

        void setLoading(boolean isActive);

        void showMovies(List<Movie> movies);

        void showMovieDetails(MovieDetail movieDetail);

        void showError(String message);

        void appendMoreMovies(List<Movie> data);

        void setupFeaturedVideo(String videoUrl);
    }

    interface Actions {

        void fetchOrSearchMovies(String query, @ContentType int contentType, int offSet);

        void loadMovies(@ContentType int contentType, int offSet);

        void searchMovies(String query, int offSet);

        void openDetails(@NonNull String id);

        void onDestroy();
    }
}
