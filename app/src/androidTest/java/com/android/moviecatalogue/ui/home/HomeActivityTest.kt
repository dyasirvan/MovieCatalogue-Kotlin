package com.android.moviecatalogue.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.android.moviecatalogue.R
import com.android.moviecatalogue.utils.DataDummy
import com.android.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.roundToInt

class HomeActivityTest {
    private val dummyMovie = DataDummy.generateDataDummyMovies()
    private val dummyTvShow = DataDummy.generateDataDummyTvShows()

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withText("Movie")).perform(click())
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_overview_desc)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_date)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))

        onView(withId(R.id.img_thumbnail)).check(matches(isDisplayed()))

        onView(withId(R.id.fab_favorite)).perform(click())

        onView(isRoot()).perform(pressBack())

        onView(withText("Movie")).perform(click())
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.fab_favorite)).perform(click())
    }

    @Test
    fun loadTvShows() {
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShow.size))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_overview_desc)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_date)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_score)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))

        onView(withId(R.id.img_thumbnail)).check(matches(isDisplayed()))

        onView(withId(R.id.fab_favorite)).perform(click())

        onView(isRoot()).perform(pressBack())

        onView(withText("Tv Show")).perform(click())
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.fab_favorite)).perform(click())
    }

}