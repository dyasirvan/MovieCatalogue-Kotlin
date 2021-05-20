package com.android.moviecatalogue.di

import android.content.Context
import com.android.moviecatalogue.api.ApiConfig
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.remote.RemoteDataSource
import kotlinx.coroutines.InternalCoroutinesApi

object Injection {
    @InternalCoroutinesApi
    fun provideRepository(): MovieCatalogueRepository {

        val retrofit = ApiConfig.getApiService()
        val remoteDataSource = RemoteDataSource.getInstance(retrofit)

        return MovieCatalogueRepository.getInstance(remoteDataSource)

    }
}