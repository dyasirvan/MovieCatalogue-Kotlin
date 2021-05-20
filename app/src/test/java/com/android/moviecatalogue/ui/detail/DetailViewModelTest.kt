package com.android.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.moviecatalogue.api.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.data.source.remote.response.Genre
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DetailMovieResponse(
        listOf(Genre("Action, Fantasy, Adventure, Science Fiction")),
        1,
        "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
        "2021-04-07",
        "Released",
        "Mortal Kombat",
        7.6
    )

    private val dummyTvShow = DetailTvShowResponse(
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

    @Mock
    private lateinit var repository: MovieCatalogueRepository

    @Mock
    private lateinit var observer: Observer<DetailMovieResponse>

    @Mock
    private lateinit var observerTvShow: Observer<DetailTvShowResponse>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun getDetailMovie() {
        val movie = MutableLiveData<DetailMovieResponse>()
        movie.value = dummyMovie

        Mockito.`when`(repository.getDetailMovie(1, API_KEY)).thenReturn(movie)
        viewModel.setSelectedMovie(1, API_KEY)
        val detailMovie = viewModel.getMovie().value
        verify(repository).getDetailMovie(1, API_KEY)
        assertNotNull(detailMovie)
        assertEquals(dummyMovie.title, detailMovie?.title)
        assertEquals(dummyMovie.overview, detailMovie?.overview)
        assertEquals(dummyMovie.posterPath, detailMovie?.posterPath)
        assertEquals(dummyMovie.releaseDate, detailMovie?.releaseDate)
        assertEquals(dummyMovie.status, detailMovie?.status)
        assertEquals(dummyMovie.voteAverage, detailMovie?.voteAverage)
        assertEquals(dummyMovie.genres, detailMovie?.genres)

        viewModel.getMovie().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun getDetailTvShow() {
        val tvShow = MutableLiveData<DetailTvShowResponse>()
        tvShow.value = dummyTvShow

        Mockito.`when`(repository.getDetailTvShow(1, API_KEY)).thenReturn(tvShow)
        viewModel.setSelectedTvShow(1, API_KEY)
        val detailTvShow = viewModel.getTvShow().value
        verify(repository).getDetailTvShow(1, API_KEY)
        assertNotNull(detailTvShow)
        assertEquals(dummyTvShow.name, detailTvShow?.name)
        assertEquals(dummyTvShow.overview, detailTvShow?.overview)
        assertEquals(dummyTvShow.posterPath, detailTvShow?.posterPath)
        assertEquals(dummyTvShow.firstAirDate, detailTvShow?.firstAirDate)
        assertEquals(dummyTvShow.status, detailTvShow?.status)
        assertEquals(dummyTvShow.voteAverage, detailTvShow?.voteAverage)
        assertEquals(dummyTvShow.genres, detailTvShow?.genres)

        viewModel.getTvShow().observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(dummyTvShow)
    }
}