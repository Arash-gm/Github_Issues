package com.arash.github.data.repository

import com.arash.github.data.api.ApiCall
import com.arash.github.data.model.IssueFilter
import com.arash.github.data.model.Issue
import com.arash.github.data.model.State
import com.arash.github.data.model.User
import com.arash.github.util.Globals
import com.orhanobut.hawk.Hawk
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Arash Golmohammadi on 8/2/2019.
 */

class IssueRepository @Inject constructor(val api: ApiCall) {

    private var cachedResult: ArrayList<Issue>? = null

    fun getIssues(issueFilter: IssueFilter): Observable<ArrayList<Issue>> {
        return loadOnline(issueFilter)
    }

    private fun loadOnline(issueFilter: IssueFilter): Observable<ArrayList<Issue>>{
        return api.getIssues(issueFilter.state,issueFilter.page,issueFilter.perPage,issueFilter.phrase).doOnNext {
            saveData(it)
        }
        .onErrorReturn{ arrayListOf(Issue(state = State.OPEN.state,title = "Error On Issue",user = User(userName = "",avatarUrl = "",id = ""),body = "empty",createdAt = "1900-00-00T00:00:00Z",commentsUrl = "")) }
    }

    private fun saveData(it: ArrayList<Issue>) {
        cachedResult = it
        Hawk.put(Globals.BUNDLE_CACHE_ISSUES,cachedResult)
    }
}