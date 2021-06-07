package com.android.moviecatalogue.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.moviecatalogue.data.source.remote.network.ApiService
import com.android.moviecatalogue.data.source.remote.response.*
import com.android.moviecatalogue.utils.DataDummy
import com.android.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val apiService: ApiService){
    private val handler = Handler(Looper.getMainLooper())

    companion object{
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 5000

    }

    fun getMovies(): LiveData<ApiResponse<List<ResultMovie>>>{
        EspressoIdlingResource.increment()

        val result = MutableLiveData<ApiResponse<List<ResultMovie>>>()
        handler.postDelayed({
            apiService.getMovies().enqueue(object : Callback<MovieResponse> {
                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val movies = response.body()?.resultMovies
                        if (movies != null) {
                            result.value = ApiResponse.success(movies)
                        }
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

                    EspressoIdlingResource.decrement()
                }

            })

        }, SERVICE_LATENCY_IN_MILLIS)

        return result
    }

    fun getTvShows(): LiveData<ApiResponse<List<ResultTvShow>>>{
        EspressoIdlingResource.increment()

        val result = MutableLiveData<ApiResponse<List<ResultTvShow>>>()
        handler.postDelayed({
            apiService.getTvShows().enqueue(object : Callback<TvShowResponse> {
                override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>
                ) {
                    if (response.isSuccessful){
                        val tvShow = response.body()?.resultTvShow
                        if (tvShow != null) {
                            result.value = ApiResponse.success(tvShow)
                        }
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {

                    EspressoIdlingResource.decrement()
                }

            })

        }, SERVICE_LATENCY_IN_MILLIS)

        return result
    }

    fun getDetailMovie(id: Int, callback: LoadDetailMovieCallback) {
        apiService.getDetailMovie(id = id).enqueue(object : Callback<DetailMovieResponse>{
            override fun onResponse(call: Call<DetailMovieResponse>,
                                    response: Response<DetailMovieResponse>) {
                if (response.isSuccessful){
                    Handler(Looper.getMainLooper()).postDelayed({
                        val movies = response.body()
                        callback.onDetailMovieReceive(movies!!)

                    }, 2000)

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
            }

        })
    }

    fun getDetailTvShow(id: Int, callback: LoadDetailTvCallback) {
        apiService.getDetailTvShow(id = id).enqueue(object : Callback<DetailTvShowResponse>{
            override fun onResponse(call: Call<DetailTvShowResponse>,
                                    response: Response<DetailTvShowResponse>) {
                if (response.isSuccessful){
                    val tvShows = response.body()
                    callback.onDetailTvReceive(tvShows!!)
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
            }

        })
    }

    interface LoadDetailMovieCallback {
        fun onDetailMovieReceive(detailMovieResponse: DetailMovieResponse)
    }

    interface LoadDetailTvCallback {
        fun onDetailTvReceive(detailTvResponse: DetailTvShowResponse)
    }
}