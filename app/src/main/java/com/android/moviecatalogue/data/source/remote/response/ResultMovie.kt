package com.android.moviecatalogue.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResultMovie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)