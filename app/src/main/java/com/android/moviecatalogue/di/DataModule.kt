package com.android.moviecatalogue.di

import android.app.Application
import androidx.room.Room
import com.android.moviecatalogue.data.source.remote.network.ApiConfig
import com.android.moviecatalogue.data.source.remote.network.ApiService
import com.android.moviecatalogue.data.MovieCatalogueDataSource
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.local.LocalDataSource
import com.android.moviecatalogue.data.source.local.room.MovieCatalogueDao
import com.android.moviecatalogue.data.source.local.room.MovieCatalogueDatabase
import com.android.moviecatalogue.data.source.remote.RemoteDataSource
import com.android.moviecatalogue.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApiService() : ApiService {
        return ApiConfig.getApiService()
    }


    private const val  DATABASE_NAME = "MovieCatalogue.db"

    @Provides
    @Singleton
    fun providedeAppDatabase(app : Application) : MovieCatalogueDatabase {
        return Room.databaseBuilder(
            app,
            MovieCatalogueDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserFavoriteDao(appDatabase: MovieCatalogueDatabase) : MovieCatalogueDao {
        return appDatabase.movieCatalogueDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        movieCatalogueDao: MovieCatalogueDao
    ) : LocalDataSource {
        return LocalDataSource(movieCatalogueDao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService: ApiService
    ) : RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideExecutorService() : AppExecutors = AppExecutors()

    @Provides
    @Singleton
    fun provideHerbsRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        appExecutors: AppExecutors
    ) : MovieCatalogueDataSource {
        return MovieCatalogueRepository(remoteDataSource, localDataSource, appExecutors)
    }

}