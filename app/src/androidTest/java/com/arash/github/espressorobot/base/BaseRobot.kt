package com.arash.github.espressorobot.base

import android.support.test.espresso.Espresso
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.RecyclerView
import com.arash.github.util.Globals
import org.hamcrest.CoreMatchers

/**
 * Created by Arash Golmohammadi on 8/9/2019.
 */

open class BaseRobot {

    fun checkViewVisibile(resId: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(resId)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    fun checkViewNotVisible(resId: Int): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(resId)).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))

    fun checkListItemVisibile(resId: Int): ViewInteraction =
        Espresso.onView(CoreMatchers.allOf(ViewMatchers.withId(resId), ViewMatchers.isDisplayed()))

    fun checkViewText(resId: Int, text: String): ViewInteraction =
        Espresso.onView(ViewMatchers.withId(resId)).check(
            ViewAssertions.matches(ViewMatchers.withText(CoreMatchers.containsString(text))))

    fun checkActivityLaunched(activityName: String) {
        Intents.intended(IntentMatchers.hasComponent(activityName))
    }

    fun performListItemClick(resId: Int) {
        Espresso.onView(ViewMatchers.withId(resId)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
    }

    fun scrollListToBottom(resId: Int) {
        Espresso.onView(ViewMatchers.withId(resId))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(Globals.PAGE_SIZE - 1))
    }
}