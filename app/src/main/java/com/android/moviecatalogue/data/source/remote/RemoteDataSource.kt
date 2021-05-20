package com.android.moviecatalogue.data.source.remote

import android.os.Handler
import android.os.Looper
import com.android.moviecatalogue.api.ApiService
import com.android.moviecatalogue.data.source.remote.response.*
import com.android.moviecatalogue.utils.DataDummy
import com.android.moviecatalogue.utils.EspressoIdlingResource
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource private constructor(private val apiService: ApiService){
    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null
        @InternalCoroutinesApi
        fun getInstance(helper: ApiService): RemoteDataSource =
            instance ?: kotlinx.coroutines.internal.synchronized(this) {
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getMovies(apiKey: String, callback: LoadMoviesCallback){
        EspressoIdlingResource.increment()

        apiService.getMovies(apiKey).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.resultMovies
                    if (movies != null) {
                        callback.onAllMoviesReceive(movies)
                    }
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                val dummyMovies = DataDummy.generateDataDummyMovies()
                val resultMovies = arrayListOf<ResultMovie>()
                dummyMovies.forEach{ dummy ->
                    val entity = ResultMovie(dummy.id, dummy.imagePath, dummy.dateRelease, dummy.title, dummy.score)
                    resultMovies.add(entity)
                }
                callback.onAllMoviesReceive(resultMovies.toList())
                EspressoIdlingResource.decrement()
            }

        })
    }

    fun getTvShows(apiKey: String, callback: LoadTvsCallback){
        EspressoIdlingResource.increment()

        apiService.getTvShows(apiKey).enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>
            ) {
                if (response.isSuccessful){
                    val tvShow = response.body()?.resultTvShow
                    if (tvShow != null) {
                        callback.onAllTvsReceive(tvShow)
                    }
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                val dummyTv = DataDummy.generateDataDummyTvShows()
                val resultTv = arrayListOf<ResultTvShow>()
                dummyTv.forEach{ dummy ->
                    val entity = ResultTvShow(dummy.id, dummy.imagePath, dummy.dateRelease, dummy.title, dummy.score)
                    resultTv.add(entity)
                }
                callback.onAllTvsReceive(resultTv.toList())
                EspressoIdlingResource.decrement()
            }

        })
    }

    fun getDetailMovie(id: Int, apiKey: String, callback: LoadDetailMovieCallback) {
//        EspressoIdlingResource.increment()
        apiService.getDetailMovie(id = id, key = apiKey).enqueue(object : Callback<DetailMovieResponse>{
            override fun onResponse(call: Call<DetailMovieResponse>,
                                    response: Response<DetailMovieResponse>) {
                if (response.isSuccessful){
                    Handler(Looper.getMainLooper()).postDelayed({
                        val movies = response.body()
                        callback.onDetailMovieReceive(movies!!)

                    }, 2000)
//                    EspressoIdlingResource.decrement()

                }
            }

            override fun onFailure(call: Call<DetailMovieResponse>, t: Throwable) {
                val dummyMovies = DataDummy.generateDataDummyMovies()
                val resultMovie = dummyMovies.find { it.id == id }

                val genres = resultMovie?.genre?.map { it ->
                    Genre(it.name)
                }
                val movies = DetailMovieResponse(
                    genres,
                    resultMovie?.id,
                    resultMovie?.overview,
                    resultMovie?.imagePath,
                    resultMovie?.dateRelease,
                    resultMovie?.status,
                    resultMovie?.title,
                    resultMovie?.score)
                callback.onDetailMovieReceive(movies)
//                EspressoIdlingResource.decrement()
            }

        })
    }

    fun getDetailTvShow(id: Int, apiKey: String, callback: LoadDetailTvCallback) {
        apiService.getDetailTvShow(id = id, key = apiKey).enqueue(object : Callback<DetailTvShowResponse>{
            override fun onResponse(call: Call<DetailTvShowResponse>,
                                    response: Response<DetailTvShowResponse>) {
                if (response.isSuccessful){
                    val tvShows = response.body()
                    callback.onDetailTvReceive(tvShows!!)
//                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<DetailTvShowResponse>, t: Throwable) {
                val dummyTvShows = DataDummy.generateDataDummyTvShows()
                val resultTvShow = dummyTvShows.find { it.id == id }

                val genres = resultTvShow?.genre?.map { it ->
                    Genre(it.name)
                }
                val tvShows = DetailTvShowResponse(
                    resultTvShow?.dateRelease,
                    genres,
                    resultTvShow?.id,
                    resultTvShow?.title,
                    resultTvShow?.overview,
                    resultTvShow?.imagePath,
                    resultTvShow?.status,
                    resultTvShow?.score)
                callback.onDetailTvReceive(tvShows)
//                EspressoIdlingResource.decrement()
            }

        })
    }

    interface LoadMoviesCallback {
        fun onAllMoviesReceive(moviesResponse: List<ResultMovie>)
    }

    interface LoadTvsCallback {
        fun onAllTvsReceive(tvsResponse: List<ResultTvShow>)
    }

    interface LoadDetailMovieCallback {
        fun onDetailMovieReceive(detailMovieResponse: DetailMovieResponse)
    }

    interface LoadDetailTvCallback {
        fun onDetailTvReceive(detailTvResponse: DetailTvShowResponse)
    }
}