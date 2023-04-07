package com.samikb.assignment

import com.samikb.assignment.model.MovieApiConfiguration
import com.samikb.assignment.model.MovieCompleteDetails
import com.samikb.assignment.model.MoviesResponse
import com.samikb.assignment.network.MovieInfoService
import com.google.gson.Gson

class TestMovieInfoService: MovieInfoService {
    override suspend fun getConfiguration(apiKey: String): MovieApiConfiguration {
        val fileReader = FileReaderHelper("configuration_response.json")
        return Gson().fromJson(fileReader.content, MovieApiConfiguration::class.java)
    }

    override suspend fun getNowPlaying(
        language: String,
        page: String,
        apiKey: String
    ): MoviesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun gePopularMovies(
        apiKey: String,
        language: String,
        page: Int
    ): MoviesResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getCompleteMovieDetails(
        movieId: Int,
        apiKey: String,
        language: String
    ): MovieCompleteDetails {
        TODO("Not yet implemented")
    }
}