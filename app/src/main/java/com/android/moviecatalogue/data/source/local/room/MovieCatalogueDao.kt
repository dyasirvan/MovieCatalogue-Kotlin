package com.android.moviecatalogue.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
@Dao
interface MovieCatalogueDao {
    @Query("SELECT * FROM movie WHERE type = 'Movie'")
    fun getMovies(): DataSource.Factory<Int, MovieTvEntity>

    @Query("SELECT * FROM movie WHERE type= 'TvShow'")
    fun getTvShows(): DataSource.Factory<Int, MovieTvEntity>

    @Query("SELECT * FROM movie WHERE type = 'Movie' AND isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieTvEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesTvs(data: List<MovieTvEntity>)

    @Update
    fun updateFavoriteMoviesTvs(data: MovieTvEntity)
}