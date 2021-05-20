package com.android.moviecatalogue.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.di.Injection
import com.android.moviecatalogue.ui.detail.DetailViewModel
import com.android.moviecatalogue.ui.movie.MovieViewModel
import com.android.moviecatalogue.ui.tvshow.TvShowViewModel
import kotlinx.coroutines.InternalCoroutinesApi

class ViewModelFactory private constructor(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModelProvider.NewInstanceFactory(){
    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        @InternalCoroutinesApi
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieCatalogueRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(movieCatalogueRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(movieCatalogueRepository) as T
            }
            else -> throw Throwable(("Unknown ViewModel class: " + modelClass.name))
        }
    }

}