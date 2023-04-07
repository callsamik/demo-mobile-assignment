package com.samikb.assignment.network

import com.samikb.assignment.BuildConfig
import com.samikb.assignment.model.MovieApiConfiguration
import com.samikb.assignment.model.MovieCompleteDetails
import com.samikb.assignment.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInfoService {
    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String = BuildConfig.API_KEY): MovieApiConfiguration

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "undefined",
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MoviesResponse

    @GET("movie/popular")
    suspend fun gePopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getCompleteMovieDetails(
        @Path("movie_id") movieId: Int, @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieCompleteDetails
}