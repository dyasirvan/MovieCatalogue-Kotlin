package com.android.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.remote.response.ResultTvShow

class TvShowViewModel(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {
    private lateinit var tvShow: LiveData<List<ResultTvShow>>

    fun setTvShows(apiKey: String){
        tvShow = movieCatalogueRepository.getTvShows(apiKey)
    }

    fun getTvShows(): LiveData<List<ResultTvShow>> = tvShow
}