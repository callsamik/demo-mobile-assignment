package com.samikb.assignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.samikb.assignment.MainCoroutineRule
import com.samikb.assignment.TestMovieInfoService
import com.samikb.assignment.getOrAwaitValue
import com.samikb.assignment.model.MovieApiConfiguration
import com.samikb.assignment.network.ServiceHelper
import com.samikb.assignment.network.Status
import com.samikb.assignment.observeForTesting
import com.samikb.assignment.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private lateinit var viewModel: MainActivityViewModel

    @Before
    fun setUp() {
        viewModel = MainActivityViewModel(MovieRepository(ServiceHelper(TestMovieInfoService())))
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getConfiguration() {
        mainCoroutineRule.runBlockingTest {
            val liveData = viewModel.getConfiguration()
            liveData.observeForTesting{
                val firstResult = liveData.getOrAwaitValue()
                assert(firstResult.status == Status.LOADING)
                Thread.sleep(2000)
                val actualResult = liveData.getOrAwaitValue()
                assert(actualResult.status == Status.SUCCESS)
                assert(actualResult.data is MovieApiConfiguration)
            }
        }
    }
}