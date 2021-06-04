package com.android.moviecatalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {

    fun getTvShows(type: String): LiveData<Resource<PagedList<MovieTvEntity>>> =
        movieCatalogueRepository.getTvShows(type)
}