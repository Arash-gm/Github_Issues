package com.arash.github.detail

/**
 * Created by Arash Golmohammadi on 8/9/2019.
 */

interface CommentRowView {
    fun setUserName(userName: String)
    fun loadAvatar(avatarUrl: String)
    fun setBody(body: String)
}