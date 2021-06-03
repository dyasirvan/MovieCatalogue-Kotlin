package com.android.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {
    private lateinit var detailMovieResponse: LiveData<DetailMovieResponse>
    private lateinit var detailTvShowResponse: LiveData<DetailTvShowResponse>

    fun setSelectedMovie(movieId: Int) {
        detailMovieResponse = movieCatalogueRepository.getDetailMovie(movieId)
    }

    fun setSelectedTvShow(tvShowId: Int) {
        detailTvShowResponse = movieCatalogueRepository.getDetailTvShow(tvShowId)
    }

    fun getMovie(): LiveData<DetailMovieResponse> = detailMovieResponse

    fun getTvShow(): LiveData<DetailTvShowResponse> = detailTvShowResponse

    fun setFavorite(data: MovieTvEntity, newState: Boolean) =
        movieCatalogueRepository.setFavorite(data, newState)
}