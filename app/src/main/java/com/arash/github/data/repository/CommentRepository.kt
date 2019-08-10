package com.arash.github.data.repository

import com.arash.github.data.api.ApiCall
import com.arash.github.data.model.*
import com.arash.github.util.Globals
import com.orhanobut.hawk.Hawk
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Arash Golmohammadi on 8/9/2019.
 */

class CommentRepository @Inject constructor(val api: ApiCall) {

    private var cachedResult: ArrayList<Comment>? = null

    fun getComments(url: String): Observable<ArrayList<Comment>> {
        return loadOnline(url)
    }

    private fun loadOnline(url: String): Observable<ArrayList<Comment>> {
        return api.getComments(url).doOnNext {
            saveData(it)
        }
            .onErrorReturn{ arrayListOf(Comment(body = "", user = User(userName = "",id = "", avatarUrl = ""))) }
    }

    private fun saveData(it: ArrayList<Comment>) {
        cachedResult = it
        Hawk.put(Globals.BUNDLE_CACHE_ISSUES,cachedResult)
    }
}