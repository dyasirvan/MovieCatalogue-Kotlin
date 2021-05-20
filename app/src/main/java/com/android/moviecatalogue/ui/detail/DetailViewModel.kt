package com.android.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse

class DetailViewModel(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {
    private lateinit var detailMovieResponse: LiveData<DetailMovieResponse>
    private lateinit var detailTvShowResponse: LiveData<DetailTvShowResponse>

    fun setSelectedMovie(movieId: Int, apiKey: String) {
        detailMovieResponse = movieCatalogueRepository.getDetailMovie(movieId, apiKey)
    }

    fun setSelectedTvShow(tvShowId: Int, apiKey: String) {
        detailTvShowResponse = movieCatalogueRepository.getDetailTvShow(tvShowId, apiKey)
    }

    fun getMovie(): LiveData<DetailMovieResponse> = detailMovieResponse


    fun getTvShow(): LiveData<DetailTvShowResponse> = detailTvShowResponse
}