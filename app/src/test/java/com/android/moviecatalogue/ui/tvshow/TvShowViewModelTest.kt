package com.android.moviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.moviecatalogue.api.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.remote.response.ResultTvShow
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
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var  observer: Observer<List<ResultTvShow>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(movieCatalogueRepository)
    }

    @Test
    fun getTvShows() {
        val dataDummyTvShow = DataDummy.generateDataDummyTvShows()
        val resultTvShow = arrayListOf<ResultTvShow>()
        dataDummyTvShow.forEach {
            val entity =  ResultTvShow(
                it.id,
                it.imagePath,
                it.dateRelease,
                it.title,
                it.score)
            resultTvShow.add(entity)
        }
        val tvShows = MutableLiveData<List<ResultTvShow>>()
        tvShows.value = resultTvShow

        Mockito.`when`(movieCatalogueRepository.getTvShows(API_KEY)).thenReturn(tvShows)
        viewModel.setTvShows(API_KEY)
        val tvList = viewModel.getTvShows().value
        Mockito.verify(movieCatalogueRepository).getTvShows(API_KEY)
        assertNotNull(tvList)
        assertEquals( 10 , tvList?.size)

        viewModel.getTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(resultTvShow)
    }
}