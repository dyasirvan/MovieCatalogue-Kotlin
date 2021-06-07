package com.android.moviecatalogue.ui.favorite.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvFavViewModel @Inject constructor(private val repository: MovieCatalogueRepository): ViewModel() {
    fun getTvFavorites(type: String): LiveData<PagedList<MovieTvEntity>> =
        repository.getFavorite(type)

    fun sortByName(type: String, sort: String): LiveData<PagedList<MovieTvEntity>> =
        repository.sortByName(type, sort)
}