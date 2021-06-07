package com.android.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.android.moviecatalogue.data.source.local.LocalDataSource
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.remote.RemoteDataSource
import com.android.moviecatalogue.utils.*
import com.android.moviecatalogue.utils.DataDummy.dummyDetailMovies
import com.android.moviecatalogue.utils.DataDummy.dummyDetailTvShow
import com.android.moviecatalogue.vo.Resource
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieCatalogueRepositoryTest {
    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val fakeRepository = FakeRepository(remote, local, appExecutors)

    private val dummyMovie = DataDummy.generateDataDummyMovies()
    private val dummyTvShow = DataDummy.generateDataDummyTvShows()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieTvEntity>
        `when`(local.getAllData(Constant.MOVIE)).thenReturn(dataSourceFactory)
        fakeRepository.getMovies(Constant.MOVIE)

        val movieEntities = Resource.success(PagedListUtils.mockPagedList(DataDummy.generateDataDummyMovies()))

        verify(local).getAllData(Constant.MOVIE)
        assertNotNull(movieEntities.data)
        assertEquals(dummyMovie.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieTvEntity>
        `when`(local.getAllData(Constant.TV_SHOW)).thenReturn(dataSourceFactory)
        fakeRepository.getMovies(Constant.TV_SHOW)

        val tvEntities = Resource.success(PagedListUtils.mockPagedList(DataDummy.generateDataDummyTvShows()))

        verify(local).getAllData(Constant.TV_SHOW)
        assertNotNull(tvEntities.data)
        assertEquals(dummyTvShow.size.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun getDetailMovie() {

        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailMovieCallback)
                .onDetailMovieReceive(dummyDetailMovies)
            null
        }.`when`(remote).getDetailMovie(eq(1),any())

        val detailMovieLiveData = LiveDataTestUtil.getValue(fakeRepository.getDetailMovie(1))
        verify(remote).getDetailMovie(id=eq(1), any())
        assertNotNull(detailMovieLiveData)
        assertEquals(dummyDetailMovies, detailMovieLiveData)
    }

    @Test
    fun getDetailTvShow() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailTvCallback)
                .onDetailTvReceive(dummyDetailTvShow)
            null
        }.`when`(remote).getDetailTvShow(eq(1), any())

        val detailTvShowLiveData = LiveDataTestUtil.getValue(fakeRepository.getDetailTvShow(1))
        verify(remote).getDetailTvShow(id=eq(1), any())
        assertNotNull(detailTvShowLiveData)
        assertEquals(dummyDetailTvShow, detailTvShowLiveData)
    }

}

