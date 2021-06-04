package com.android.moviecatalogue.utils

import androidx.sqlite.db.SimpleSQLiteQuery
import com.android.moviecatalogue.utils.Constant.Companion.MOVIE
import com.android.moviecatalogue.utils.Constant.Companion.TV_SHOW
import java.lang.StringBuilder

object SortUtils {
    const val ASCENDING = "ASC"
    const val DESCENDING = "DESC"

    fun getSortedQuery(type: String, sort: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM movie ")
        when(type){
            MOVIE -> {
                if(sort == ASCENDING) {
                    simpleQuery.append("WHERE type = \"$MOVIE\" AND isFavorite = 1 ORDER BY title $ASCENDING")
                }else{
                    simpleQuery.append("WHERE type = \"$MOVIE\" AND isFavorite = 1 ORDER BY title $DESCENDING")
                }
            }
            TV_SHOW -> {
                if(sort == ASCENDING) {
                    simpleQuery.append("WHERE type = \"$TV_SHOW\" AND isFavorite = 1 ORDER BY title $ASCENDING")
                }else{
                    simpleQuery.append("WHERE type = \"$TV_SHOW\" AND isFavorite = 1 ORDER BY title $DESCENDING")
                }
            }
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}