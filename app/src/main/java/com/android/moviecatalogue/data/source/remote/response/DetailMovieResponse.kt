package com.android.moviecatalogue.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class DetailMovieResponse(
    @SerializedName("genres")
    val genres: List<Genre>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)