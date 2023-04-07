package com.samikb.assignment.network

import com.samikb.assignment.model.MoviesResponse


class ServiceHelper(private val movieInfoService: MovieInfoService) {
    suspend fun getConfiguration() = movieInfoService.getConfiguration()
    suspend fun getNowPlaying(language: String = "en-US", page: String = "undefined") = movieInfoService.getNowPlaying(language, page)
    suspend fun getPopularMovies(pageIndex: Int): MoviesResponse = movieInfoService.gePopularMovies(page = pageIndex)
    suspend fun getCompleteMovieDetails(movieId: Int) = movieInfoService.getCompleteMovieDetails(movieId)
}