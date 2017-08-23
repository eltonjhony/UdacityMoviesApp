package com.helabs.eltonjhony.udacitymovies.data.remote;

import com.helabs.eltonjhony.udacitymovies.data.model.DataResultWrapper;
import com.helabs.eltonjhony.udacitymovies.data.model.Movie;
import com.helabs.eltonjhony.udacitymovies.data.model.MovieDetail;
import com.helabs.eltonjhony.udacitymovies.data.model.VideoWrapper;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by eltonjhony on 3/31/17.
 */
public interface Api {

    @GET("./movie/popular")
    Observable<DataResultWrapper<Movie>> fetchPopularMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("./movie/top_rated")
    Observable<DataResultWrapper<Movie>> fetchTopRatedMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("./movie/now_playing")
    Observable<DataResultWrapper<Movie>> fetchNowPlayingMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

    @GET("./search/movie")
    Observable<DataResultWrapper<Movie>> searchMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query("query") String query, @Query("page") int page);

    @GET("./movie/{movieId}/videos")
    Observable<VideoWrapper> getVideosById(@Path("movieId") String movieId, @Query("api_key") String apiKey);

    @GET("./movie/{movieId}")
    Observable<MovieDetail> getMovieById(@Path("movieId") String movieId, @Query("language") String language, @Query("api_key") String apiKey);

}
