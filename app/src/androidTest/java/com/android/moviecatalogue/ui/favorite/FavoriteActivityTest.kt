package com.android.moviecatalogue.ui.favorite

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Before
import org.junit.Test

class FavoriteActivityTest{
    @Before
    fun setUp() {
        ActivityScenario.launch(FavoriteActivity::class.java)
    }

    @Test
    fun loadMovieFavorite() {
        onView(withText("Movie")).perform(click())
    }

    @Test
    fun loadTvShowFavorite() {
        onView(withText("Tv Show")).perform(click())
    }
}