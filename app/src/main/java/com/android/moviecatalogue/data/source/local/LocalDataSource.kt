package com.android.moviecatalogue.data.source.local

import androidx.paging.DataSource
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.local.room.MovieCatalogueDao
import com.android.moviecatalogue.utils.FavoriteUtils
import com.android.moviecatalogue.utils.MovieTvUtils
import com.android.moviecatalogue.utils.SortUtils
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mCatalogueDao: MovieCatalogueDao) {

    fun insertMovies(movieTvs: List<MovieTvEntity>) = mCatalogueDao.insertMoviesTvs(movieTvs)

    fun updateMovies(movieTvEntity: MovieTvEntity, newState: Boolean){
        movieTvEntity.isFavorite = newState
        mCatalogueDao.updateFavoriteMoviesTvs(movieTvEntity)
    }

    fun getFavoriteMovies(type: String): DataSource.Factory<Int, MovieTvEntity> {
        val query = FavoriteUtils.getFavoriteQuery(type)
        return mCatalogueDao.getFavorite(query)
    }

    fun getAllData(type: String): DataSource.Factory<Int, MovieTvEntity>{
        val query = MovieTvUtils.getAllDataQuery(type)
        return mCatalogueDao.getAllData(query)
    }

    fun sortByName(type: String, sort: String): DataSource.Factory<Int, MovieTvEntity>{
        val query = SortUtils.getSortedQuery(type, sort)
        return mCatalogueDao.sortByName(query)
    }
}