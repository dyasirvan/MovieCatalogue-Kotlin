package com.android.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.utils.DataDummy.dummyDetailMovies
import com.android.moviecatalogue.utils.DataDummy.dummyDetailTvShow
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
        movie.value = dummyDetailMovies

        Mockito.`when`(repository.getDetailMovie(1)).thenReturn(movie)
        viewModel.setSelectedMovie(1)
        val detailMovie = viewModel.getMovie().value
        verify(repository).getDetailMovie(1)
        assertNotNull(detailMovie)
        assertEquals(dummyDetailMovies.title, detailMovie?.title)
        assertEquals(dummyDetailMovies.overview, detailMovie?.overview)
        assertEquals(dummyDetailMovies.posterPath, detailMovie?.posterPath)
        assertEquals(dummyDetailMovies.releaseDate, detailMovie?.releaseDate)
        assertEquals(dummyDetailMovies.status, detailMovie?.status)
        assertEquals(dummyDetailMovies.voteAverage, detailMovie?.voteAverage)
        assertEquals(dummyDetailMovies.genres, detailMovie?.genres)

        viewModel.getMovie().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyDetailMovies)
    }

    @Test
    fun getDetailTvShow() {
        val tvShow = MutableLiveData<DetailTvShowResponse>()
        tvShow.value = dummyDetailTvShow

        Mockito.`when`(repository.getDetailTvShow(1)).thenReturn(tvShow)
        viewModel.setSelectedTvShow(1)
        val detailTvShow = viewModel.getTvShow().value
        verify(repository).getDetailTvShow(1)
        assertNotNull(detailTvShow)
        assertEquals(dummyDetailTvShow.name, detailTvShow?.name)
        assertEquals(dummyDetailTvShow.overview, detailTvShow?.overview)
        assertEquals(dummyDetailTvShow.posterPath, detailTvShow?.posterPath)
        assertEquals(dummyDetailTvShow.firstAirDate, detailTvShow?.firstAirDate)
        assertEquals(dummyDetailTvShow.status, detailTvShow?.status)
        assertEquals(dummyDetailTvShow.voteAverage, detailTvShow?.voteAverage)
        assertEquals(dummyDetailTvShow.genres, detailTvShow?.genres)

        viewModel.getTvShow().observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(dummyDetailTvShow)
    }
}