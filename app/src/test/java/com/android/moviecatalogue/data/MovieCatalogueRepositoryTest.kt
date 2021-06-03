package com.android.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.moviecatalogue.data.source.remote.network.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.data.source.remote.RemoteDataSource
import com.android.moviecatalogue.data.source.remote.response.*
import com.android.moviecatalogue.utils.DataDummy
import com.android.moviecatalogue.utils.DataDummy.dummyDetailMovies
import com.android.moviecatalogue.utils.DataDummy.dummyDetailTvShow
import com.android.moviecatalogue.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock

class MovieCatalogueRepositoryTest {
    private val remote = mock(RemoteDataSource::class.java)
    private val fakeRepository = FakeRepository(remote)

    private val dummyMovie = DataDummy.generateDataDummyMovies()
    private val dummyTvShow = DataDummy.generateDataDummyTvShows()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getMovies() {
        val resultMovies = arrayListOf<ResultMovie>()
        dummyMovie.forEach{ dummy ->
            val entity = ResultMovie(dummy.id, dummy.imagePath, dummy.dateRelease, dummy.title, dummy.score)
            resultMovies.add(entity)
        }

        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMoviesCallback)
                .onAllMoviesReceive(resultMovies.toList())
            null
        }.`when`(remote).getMovies(eq(API_KEY), any())

        val movieList = LiveDataTestUtil.getValue(fakeRepository.getMovies(API_KEY))
        verify(remote).getMovies(eq(API_KEY), any())
        assertNotNull(movieList)
        assertEquals(dummyMovie.size.toLong(), movieList.size.toLong())
    }

    @Test
    fun getTvShows() {
        val resultTvShow = arrayListOf<ResultTvShow>()
        dummyTvShow.forEach{ dummy ->
            val entity = ResultTvShow(dummy.id, dummy.imagePath, dummy.dateRelease, dummy.title, dummy.score)
            resultTvShow.add(entity)
        }

        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadTvsCallback)
                .onAllTvsReceive(resultTvShow.toList())
            null
        }.`when`(remote).getTvShows(eq(API_KEY), any())

        val tvShowList = LiveDataTestUtil.getValue(fakeRepository.getTvShows(API_KEY))
        verify(remote).getTvShows(eq(API_KEY), any())
        assertNotNull(tvShowList)
        assertEquals(dummyTvShow.size.toLong(), tvShowList.size.toLong())
    }

    @Test
    fun getDetailMovie() {
        doAnswer { invocation ->
            (invocation.arguments[2] as RemoteDataSource.LoadDetailMovieCallback)
                .onDetailMovieReceive(dummyDetailMovies)
            null
        }.`when`(remote).getDetailMovie(eq(1), eq(API_KEY),any())

        val detailMovieLiveData = LiveDataTestUtil.getValue(fakeRepository.getDetailMovie(1, apiKey = API_KEY))
        verify(remote).getDetailMovie(id=eq(1), apiKey = eq(API_KEY), any())
        assertNotNull(detailMovieLiveData)
        assertEquals(dummyDetailMovies, detailMovieLiveData)
    }

    @Test
    fun getDetailTvShow() {
        doAnswer { invocation ->
            (invocation.arguments[2] as RemoteDataSource.LoadDetailTvCallback)
                .onDetailTvReceive(dummyDetailTvShow)
            null
        }.`when`(remote).getDetailTvShow(eq(1), eq(API_KEY),any())

        val detailTvShowLiveData = LiveDataTestUtil.getValue(fakeRepository.getDetailTvShow(1, apiKey = API_KEY))
        verify(remote).getDetailTvShow(id=eq(1), apiKey = eq(API_KEY), any())
        assertNotNull(detailTvShowLiveData)
        assertEquals(dummyDetailTvShow, detailTvShowLiveData)
    }
}

