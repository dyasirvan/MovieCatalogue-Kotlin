package com.android.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.moviecatalogue.api.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.data.source.remote.RemoteDataSource
import com.android.moviecatalogue.data.source.remote.response.*
import com.android.moviecatalogue.utils.DataDummy
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
    private val dummyDetailMovie = DataDummy.generateDataDummyMovies()[0]
    private val dummyDetailTvShow = DataDummy.generateDataDummyTvShows()[0]

    private val detailMovie = DetailMovieResponse(
        listOf(Genre("Action, Fantasy, Adventure")),
        1,
        "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
        "2021-04-07",
        "Released",
        "Mortal Kombat",
        7.6
    )

    private val detailTvShow = DetailTvShowResponse(
        "2021-05-04",
        listOf(Genre("Sci-Fi & Fantasy, Action & Adventure, Animation")),
        1,
        "The Bad Batch",
        "Follow the elite and experimental Clones of the Bad Batch as they find their way in a rapidly changing galaxy in the aftermath of the Clone Wars.",
        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/WjQmEWFrOf98nT5aEfUfVYz9N2.jpg",
        "Returning Series",
        9.0
    )

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
        val genre = dummyDetailMovie.genre.map {
            Genre(it.name)
        }
        val detailMovieResponse = DetailMovieResponse(
            genre,
            dummyDetailMovie.id,
            dummyDetailMovie.overview,
            dummyDetailMovie.imagePath,
            dummyDetailMovie.dateRelease,
            dummyDetailMovie.status,
            dummyDetailMovie.title,
            dummyDetailMovie.score)
        val movie = MutableLiveData<DetailMovieResponse>()
        movie.value = detailMovieResponse

        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailMovieCallback)
                .onDetailMovieReceive(detailMovie)
            null
        }.`when`(remote).getDetailMovie(id = eq(1), apiKey = eq(API_KEY), any())

        val detailMovieLiveData = LiveDataTestUtil.getValue(fakeRepository.getDetailMovie(1, apiKey = API_KEY))
        verify(remote).getDetailMovie(id=eq(1), apiKey = eq(API_KEY), any())
        assertNotNull(detailMovieLiveData)
        assertEquals(detailMovieResponse, detailMovieLiveData)
    }

    @Test
    fun getDetailTvShow() {
    }
}

