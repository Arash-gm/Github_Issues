package com.arash.github.util

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

object Globals{

    val PAGE_SIZE: Int = 20
    val API_END_POINT = "https://api.github.com/repos/"
    val CATEGORY_LIST_SPAN = 2

    val BUNDLE_CACHE_ISSUES = "bundle_cache_issues"
    val BUNDLE_ISSUE_DETAIL = "bundle_issue_detail"

    fun <T> withSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}