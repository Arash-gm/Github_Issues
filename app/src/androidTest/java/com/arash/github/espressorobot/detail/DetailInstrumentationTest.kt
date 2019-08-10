package com.arash.github.espressorobot.detail

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.intent.Intents
import android.support.test.rule.ActivityTestRule
import com.arash.github.detail.DetailActivity
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Arash Golmohammadi on 8/9/2019.
 */

class DetailInstrumentationTest {

    @get:Rule
    val activityTestRule: ActivityTestRule<DetailActivity> = ActivityTestRule(DetailActivity::class.java)
    lateinit var okHttpClient: OkHttpClient
    private lateinit var resource: OkHttp3IdlingResource

    @Before
    fun setup(){
        okHttpClient = activityTestRule.activity.getHttpClient()
        resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
        Intents.init()
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(resource)
        Intents.release()
    }

    @Test
    fun checkScreen(){
        Detail {
            toolbarVisible()
            titleVisible()
            bodyVisible()
            authorVisible()
            createdAtVisible()
            stateVisible()
            pbLoadingVisible()
        }
    }
}