package com.android.moviecatalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.utils.Constant
import com.android.moviecatalogue.utils.DataDummy
import com.android.moviecatalogue.vo.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieCatalogueRepository: MovieCatalogueRepository

    @Mock
    private lateinit var  observer: Observer<Resource<PagedList<MovieTvEntity>>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(movieCatalogueRepository)
    }

    @Test
    fun getTvShows() {
        val tvShow = PagedTestDataSources.snapshot(DataDummy.generateDummyTvShows())
        val expected = MutableLiveData<Resource<PagedList<MovieTvEntity>>>()
        expected.value = Resource.success(tvShow)

        Mockito.`when`(movieCatalogueRepository.getTvShows(Constant.TV_SHOW)).thenReturn(expected)

        viewModel.getTvShows(Constant.TV_SHOW).observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)
        Mockito.verify(movieCatalogueRepository).getTvShows(Constant.TV_SHOW)

        val expectedValue = expected.value
        val actualValue = viewModel.getTvShows(Constant.TV_SHOW).value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.data, actualValue?.data)
        assertEquals(expectedValue?.data?.size, actualValue?.data?.size)
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