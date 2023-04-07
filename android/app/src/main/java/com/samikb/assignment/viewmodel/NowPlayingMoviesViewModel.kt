package com.samikb.assignment.viewmodel

import androidx.lifecycle.*
import com.samikb.assignment.model.MoviesResponse
import com.samikb.assignment.network.ServiceHelper
import com.samikb.assignment.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NowPlayingMoviesViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val _IsLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _IsLoading
    private val _IsError = MutableLiveData<Boolean>().apply { value = false }
    val isError: LiveData<Boolean> = _IsError
    private val _NowPlayingMovies = MutableLiveData<MoviesResponse>()
    val nowPlayingMovies: LiveData<MoviesResponse> = _NowPlayingMovies
    fun getNowPlayingMovieList(){
        viewModelScope.launch {
            _IsError.postValue(false)
            _IsLoading.postValue(true)
            withContext(Dispatchers.IO){
                val data = runCatching {
                    movieRepository.getNowPlaying()
                }.getOrNull()
                _IsLoading.postValue(false)
                data?.let { _NowPlayingMovies.postValue(it) } ?: run{ _IsError.postValue(true)}
            }
        }

    }

    class NowPlayingMovieListViewModelFactory(private val serviceHelper: ServiceHelper): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NowPlayingMoviesViewModel::class.java)) {
                return NowPlayingMoviesViewModel(MovieRepository(serviceHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }

}