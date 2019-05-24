package com.example.cinemapopupjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbApi {

    @GET("trending/movie/week")
    Call<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    Call<MoviesResponse> getTopRatedMovies(String tmdbApiKey, String language, int page);

    Call<MoviesResponse> getUpcomingMovies(String tmdbApiKey, String language, int page);
}