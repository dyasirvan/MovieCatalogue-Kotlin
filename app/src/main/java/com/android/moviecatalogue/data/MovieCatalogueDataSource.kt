package com.android.moviecatalogue.data

import androidx.lifecycle.LiveData
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.data.source.remote.response.ResultMovie
import com.android.moviecatalogue.data.source.remote.response.ResultTvShow

interface MovieCatalogueDataSource {

    fun getMovies(apiKey: String): LiveData<List<ResultMovie>>

    fun getTvShows(apiKey: String): LiveData<List<ResultTvShow>>

    fun getDetailMovie(id: Int, apiKey: String): LiveData<DetailMovieResponse>

    fun getDetailTvShow(id: Int, apiKey: String): LiveData<DetailTvShowResponse>
}