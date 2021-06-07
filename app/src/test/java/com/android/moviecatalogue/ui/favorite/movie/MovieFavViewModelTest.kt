package com.android.moviecatalogue.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.utils.Constant
import com.android.moviecatalogue.utils.DataDummy
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class MovieFavViewModelTest{
    private lateinit var viewModel: MovieFavViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieTvEntity>>

    @Before
    fun setUp(){
        viewModel = MovieFavViewModel(repository)
    }

    @Test
    fun `getMovieFavorite should be success`(){
        val expected = MutableLiveData<PagedList<MovieTvEntity>>()
        expected.value = PagedTestDataSources.snapshot(DataDummy.generateDummyMovies())

        `when`(repository.getFavorite(Constant.MOVIE)).thenReturn(expected)

        viewModel.getMovieFavorites(Constant.MOVIE).observeForever(observer)
        verify(observer).onChanged(expected.value)
        verify(repository).getFavorite(Constant.MOVIE)

        val expectedValue = expected.value
        val actualValue = viewModel.getMovieFavorites(Constant.MOVIE).value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)

    }

    @Test
    fun `getMovieFavorite should be success but data is empty`() {
        val expected = MutableLiveData<PagedList<MovieTvEntity>>()
        expected.value = PagedTestDataSources.snapshot()

        `when`(repository.getFavorite(Constant.MOVIE)).thenReturn(expected)

        viewModel.getMovieFavorites(Constant.MOVIE).observeForever(observer)
        verify(observer).onChanged(expected.value)
        verify(repository).getFavorite(Constant.MOVIE)

        val actualValueDataSize = viewModel.getMovieFavorites(Constant.MOVIE).value?.size
        Assert.assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

    class PagedTestDataSources private constructor(private val items: List<MovieTvEntity>) : PositionalDataSource<MovieTvEntity>() {
        companion object {
            fun snapshot(items: List<MovieTvEntity> = listOf()): PagedList<MovieTvEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<MovieTvEntity>) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MovieTvEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}