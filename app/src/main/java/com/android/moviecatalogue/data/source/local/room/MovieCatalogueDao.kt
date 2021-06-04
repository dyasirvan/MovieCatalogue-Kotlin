package com.android.moviecatalogue.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
@Dao
interface MovieCatalogueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesTvs(data: List<MovieTvEntity>)

    @Update
    fun updateFavoriteMoviesTvs(data: MovieTvEntity)

    @RawQuery(observedEntities = [MovieTvEntity::class])
    fun getFavorite(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieTvEntity>

    @RawQuery(observedEntities = [MovieTvEntity::class])
    fun getAllData(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieTvEntity>

    @RawQuery(observedEntities = [MovieTvEntity::class])
    fun sortByName(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieTvEntity>
}