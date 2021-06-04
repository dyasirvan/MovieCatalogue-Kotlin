package com.android.moviecatalogue.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import com.android.moviecatalogue.utils.Constant.Companion.MOVIE
import com.android.moviecatalogue.utils.Constant.Companion.TV_SHOW
import java.lang.StringBuilder

object MovieTvUtils {

    fun getAllDataQuery(type: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movie ")
        when(type){
            MOVIE -> {
                simpleQuery.append("WHERE type = \"$MOVIE\"")
            }
            TV_SHOW -> {
                simpleQuery.append("WHERE type = \"$TV_SHOW\"")
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}