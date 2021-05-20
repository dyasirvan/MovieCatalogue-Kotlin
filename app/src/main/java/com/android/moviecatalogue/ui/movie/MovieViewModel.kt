package com.android.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.moviecatalogue.data.MovieCatalogueRepository
import com.android.moviecatalogue.data.source.remote.response.ResultMovie

class MovieViewModel(private val movieCatalogueRepository: MovieCatalogueRepository): ViewModel() {
    private lateinit var movies: LiveData<List<ResultMovie>>

    fun setMovies(apiKey: String){
        movies = movieCatalogueRepository.getMovies(apiKey)
    }

    fun getMovies(): LiveData<List<ResultMovie>> = movies
}