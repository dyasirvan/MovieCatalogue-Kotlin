package com.android.moviecatalogue.data.source.local

import androidx.paging.DataSource
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.local.room.MovieCatalogueDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mCatalogueDao: MovieCatalogueDao) {

    fun getAllMovies(): DataSource.Factory<Int, MovieTvEntity> = mCatalogueDao.getMovies()

    fun getAllTvShows(): DataSource.Factory<Int, MovieTvEntity> = mCatalogueDao.getTvShows()

    fun insertMovies(movieTvs: List<MovieTvEntity>) = mCatalogueDao.insertMoviesTvs(movieTvs)

    fun updateMovies(movieTvEntity: MovieTvEntity, newState: Boolean){
        movieTvEntity.isFavorite = newState
        mCatalogueDao.updateFavoriteMoviesTvs(movieTvEntity)
    }

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieTvEntity> = mCatalogueDao.getFavoriteMovies()
}