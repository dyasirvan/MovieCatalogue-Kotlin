package com.android.moviecatalogue.data.source.remote.network

import com.android.moviecatalogue.data.source.remote.network.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.data.source.remote.response.MovieResponse
import com.android.moviecatalogue.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("movie/top_rated")
    fun getMovies(
        @Query("api_key") key: String = API_KEY
    ): Call<MovieResponse>

    @GET("tv/top_rated")
    fun getTvShows(
        @Query("api_key") key: String = API_KEY
    ): Call<TvShowResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String = API_KEY
    ): Call<DetailMovieResponse>

    @GET("tv/{tv_id}")
    fun getDetailTvShow(
        @Path("tv_id") id: Int,
        @Query("api_key") key: String = API_KEY
    ): Call<DetailTvShowResponse>
}