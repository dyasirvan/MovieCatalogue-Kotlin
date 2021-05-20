package com.android.moviecatalogue.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class DetailTvShowResponse(
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("genres")
    val genres: List<Genre>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)