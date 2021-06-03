package com.android.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.vo.Resource

interface MovieCatalogueDataSource {

    fun getMovies(): LiveData<Resource<PagedList<MovieTvEntity>>>

    fun getTvShows(): LiveData<Resource<PagedList<MovieTvEntity>>>

    fun getDetailMovie(id: Int): LiveData<DetailMovieResponse>

    fun getDetailTvShow(id: Int): LiveData<DetailTvShowResponse>

    fun getFavoriteMovie(): LiveData<PagedList<MovieTvEntity>>

    fun setFavorite(data: MovieTvEntity, state: Boolean)
}