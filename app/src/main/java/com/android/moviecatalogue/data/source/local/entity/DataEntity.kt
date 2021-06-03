package com.android.moviecatalogue.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.moviecatalogue.data.source.remote.response.Genre

@Entity(tableName = "data_entities")
data class DataEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "genre")
    var genre: List<Genre>,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "dateRelease")
    var dateRelease: String,

    @ColumnInfo(name = "score")
    var score: Double? = null,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "imagePath")
    var imagePath: String
)