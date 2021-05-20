package com.android.moviecatalogue.data.source.local.entity

import com.android.moviecatalogue.data.source.remote.response.Genre

data class DataEntity(
    var id: Int,
    var title: String,
    var genre: List<Genre>,
    var overview: String,
    var dateRelease: String,
    var score: Double? = null,
    var status: String,
    var imagePath: String
)