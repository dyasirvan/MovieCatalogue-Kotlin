package com.android.moviecatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.moviecatalogue.data.source.remote.network.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.remote.response.ResultMovie
import com.android.moviecatalogue.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var movieViewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var  observer: Observer<List<ResultMovie>>

    @Before
    fun setUp() {
        movieViewModel = MovieViewModel(movieCatalogueRepository)
    }

    @Test
    fun getMovies() {
        val dataDummyMovies = DataDummy.generateDataDummyMovies()
        val resultMovie = arrayListOf<ResultMovie>()
        dataDummyMovies.forEach {
            val entity =  ResultMovie(
                it.id,
                it.imagePath,
                it.dateRelease,
                it.title,
                it.score)
            resultMovie.add(entity)
        }
        val movies = MutableLiveData<List<ResultMovie>>()
        movies.value = resultMovie

        Mockito.`when`(movieCatalogueRepository.getMovies(API_KEY)).thenReturn(movies)
        movieViewModel.setMovies(API_KEY)
        val movieList = movieViewModel.getMovies().value
        Mockito.verify(movieCatalogueRepository).getMovies(API_KEY)
        assertNotNull(movieList)
        assertEquals( 10 , movieList?.size)

        movieViewModel.getMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(resultMovie)
    }
}