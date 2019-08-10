package com.arash.github.espressorobot.issue

import com.arash.github.espressorobot.base.BaseRobot
import com.arash.github.R

/**
 * Created by Arash Golmohammadi on 8/9/2019.
 */

fun Issues(func: IssueRobot.() -> Unit) = IssueRobot().apply{func()}

class IssueRobot: BaseRobot() {

    private var rvIssues = R.id.rvIssues
    private var switchIcon = R.id.switchIcon
    private var pbIssueLoading = R.id.pbIssuesLoading
    private var toolbar = R.id.toolbar
    private var edtSearch = R.id.edtSearch
    private var imgSearchIcon = R.id.imgSearchIcon
    private var pbPagination = R.id.pbPaginationLoading

    fun issueListVisible() = checkViewVisibile(rvIssues)
    fun switchIconVisible() = checkViewVisibile(switchIcon)
    fun issueListNotVisible() = checkViewNotVisible(rvIssues)
    fun pbLoadingVisible() = checkViewVisibile(pbIssueLoading)
    fun searchBarVisible() = checkViewVisibile(edtSearch)
    fun searchBarIconVisible() = checkViewVisibile(imgSearchIcon)
    fun toolbarVisible() = checkViewVisibile(toolbar)
    fun pbPaginationVisible() = checkViewVisibile(pbPagination)
    fun scrollListToBottom() = scrollListToBottom(rvIssues)
    fun performIssueListItemClick() = performListItemClick(rvIssues)
    fun activityLaunched(activityName: String) = checkActivityLaunched(activityName)

}