package com.arash.github.issue

/**
 * Created by Arash Golmohammadi on 8/2/2019.
 */

interface IssueRowView {
    fun setTitle(title: String)
    fun setUser(user: String)
    fun setCreatedAt(createdAt: String)
    fun setState(state: String)
}