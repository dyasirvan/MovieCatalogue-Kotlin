package com.android.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.RemoteDataSource
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.data.source.remote.response.ResultMovie
import com.android.moviecatalogue.data.source.remote.response.ResultTvShow

class FakeRepository (private val remoteDataSource: RemoteDataSource): MovieCatalogueDataSource {

    override fun getMovies(apiKey: String): LiveData<List<ResultMovie>> {
        val movies = MutableLiveData<List<ResultMovie>>()
        remoteDataSource.getMovies(apiKey, object : RemoteDataSource.LoadMoviesCallback{
            override fun onAllMoviesReceive(moviesResponse: List<ResultMovie>) {
                movies.postValue(moviesResponse)
            }

        })

        return movies
    }

    override fun getTvShows(apiKey: String): LiveData<List<ResultTvShow>> {
        val tvShow = MutableLiveData<List<ResultTvShow>>()
        remoteDataSource.getTvShows(apiKey, object : RemoteDataSource.LoadTvsCallback{
            override fun onAllTvsReceive(tvsResponse: List<ResultTvShow>) {
                tvShow.postValue(tvsResponse)
            }
        })

        return tvShow
    }

    override fun getDetailMovie(id: Int, apiKey: String): LiveData<DetailMovieResponse> {
        val detailMovie = MutableLiveData<DetailMovieResponse>()
        remoteDataSource.getDetailMovie(id, apiKey, object : RemoteDataSource.LoadDetailMovieCallback{
            override fun onDetailMovieReceive(detailMovieResponse: DetailMovieResponse) {
                detailMovie.postValue(detailMovieResponse)
            }
        })

        return detailMovie
    }

    override fun getDetailTvShow(id: Int, apiKey: String): LiveData<DetailTvShowResponse> {
        val detailTvShow = MutableLiveData<DetailTvShowResponse>()
        remoteDataSource.getDetailTvShow(id, apiKey, object : RemoteDataSource.LoadDetailTvCallback{
            override fun onDetailTvReceive(detailTvResponse: DetailTvShowResponse) {
                detailTvShow.postValue(detailTvResponse)
            }
        })

        return detailTvShow
    }

}