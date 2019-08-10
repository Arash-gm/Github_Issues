package com.arash.github.data.api

import com.arash.github.data.model.Comment
import com.arash.github.data.model.Issue
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

interface ApiCall {

    @GET("tensorflow/tensorflow/issues")
    fun getIssues(@Query("state") state: String
                  ,@Query("page") page: Int
                  ,@Query("per_page") perPage: Int
                  ,@Query("q") phrase: String): Observable<ArrayList<Issue>>

    @GET
    fun getComments(@Url url:String): Observable<ArrayList<Comment>>
}