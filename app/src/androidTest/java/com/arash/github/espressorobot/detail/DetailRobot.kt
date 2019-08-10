package com.arash.github.espressorobot.detail

import com.arash.github.R
import com.arash.github.espressorobot.base.BaseRobot

/**
 * Created by Arash Golmohammadi on 8/9/2019.
 */

fun Detail(func: DetailRobot.() -> Unit) = DetailRobot().apply{func()}

class DetailRobot: BaseRobot() {

    private var tvTitle = R.id.tvDetailTitle
    private var tvState = R.id.tvDetailState
    private var tvAuthor = R.id.tvDetailAuthor
    private var tvBody = R.id.tvDetailBody
    private var tvCreatedAt = R.id.tvDetailCreatedAt
    private var toolbar = R.id.toolbar
    private var pbCommentLoading = R.id.pbCommentLoading
    private var rvComments = R.id.rvComments

    fun titleVisible() = checkViewVisibile(tvTitle)
    fun toolbarVisible() = checkViewVisibile(toolbar)
    fun stateVisible() = checkViewVisibile(tvState)
    fun authorVisible() = checkViewVisibile(tvAuthor)
    fun bodyVisible() = checkViewVisibile(tvBody)
    fun createdAtVisible() = checkViewVisibile(tvCreatedAt)
    fun pbLoadingVisible() = checkViewVisibile(pbCommentLoading)
}