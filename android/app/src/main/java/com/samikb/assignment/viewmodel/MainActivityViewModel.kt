package com.samikb.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.samikb.assignment.network.Response
import com.samikb.assignment.network.ServiceHelper
import com.samikb.assignment.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class MainActivityViewModel(private val movieRepository: MovieRepository): ViewModel() {
    var hasError = MutableLiveData<Boolean>().apply { value = false }
    fun getConfiguration() = liveData(Dispatchers.IO) {
        hasError.postValue(false)
        emit(Response.loading(data = null))
        try {
            emit(Response.success(data = movieRepository.getConfiguration()))
        }catch (e: Exception){
            hasError.postValue(true)
            emit(Response.error(data = null, message = e.message?:"Error occurred!"))
        }
    }

    class MainActivityViewModelFactory(private val serviceHelper: ServiceHelper): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                return MainActivityViewModel(MovieRepository(serviceHelper)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }
    }
}
