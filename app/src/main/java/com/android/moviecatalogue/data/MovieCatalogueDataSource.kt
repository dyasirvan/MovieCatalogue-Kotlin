package com.android.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.vo.Resource

interface MovieCatalogueDataSource {

    fun getMovies(type: String): LiveData<Resource<PagedList<MovieTvEntity>>>

    fun getTvShows(type: String): LiveData<Resource<PagedList<MovieTvEntity>>>

    fun getDetailMovie(id: Int): LiveData<DetailMovieResponse>

    fun getDetailTvShow(id: Int): LiveData<DetailTvShowResponse>

    fun getFavorite(type: String): LiveData<PagedList<MovieTvEntity>>

    fun setFavorite(data: MovieTvEntity, state: Boolean)

    fun sortByName(type: String, sort: String): LiveData<PagedList<MovieTvEntity>>
}