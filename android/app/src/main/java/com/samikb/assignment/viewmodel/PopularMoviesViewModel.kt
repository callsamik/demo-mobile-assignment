package com.samikb.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.samikb.assignment.network.ServiceHelper
import com.samikb.assignment.repository.MovieRepository

class PopularMoviesViewModel(private val moviesRepository: MovieRepository): ViewModel() {
    fun getPopularMovies() = moviesRepository.getPopularMovies().cachedIn(viewModelScope)


    class PopularMoviesViewModelFactory(private val serviceHelper: ServiceHelper): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PopularMoviesViewModel::class.java)) {
                return PopularMoviesViewModel(MovieRepository(serviceHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
}