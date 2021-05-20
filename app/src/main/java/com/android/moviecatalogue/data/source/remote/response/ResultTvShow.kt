package com.android.moviecatalogue.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class ResultTvShow(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)