package com.samikb.assignment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.samikb.assignment.adapter.MoviesPagingSource
import com.samikb.assignment.model.MovieDetails
import com.samikb.assignment.network.ServiceHelper
import kotlinx.coroutines.flow.Flow

const val NETWORK_PAGE_SIZE = 25

open class MovieRepository(private val serviceHelper: ServiceHelper) {
    open suspend fun getConfiguration() = serviceHelper.getConfiguration()
    open suspend fun getNowPlaying(language: String = "en-US", page: String = "undefined") = serviceHelper.getNowPlaying(language, page)
    open fun getPopularMovies(): Flow<PagingData<MovieDetails>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service = serviceHelper)
            }
        ).flow
    }
}