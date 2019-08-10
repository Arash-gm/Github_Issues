package com.arash.github.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

@Parcelize
data class Issue(@SerializedName("state") val state:String
                 , @SerializedName("title") val title:String
                 , @SerializedName("body") val body:String
                 , @SerializedName("comments_url") val commentsUrl:String
                 , @SerializedName("created_at") val createdAt:String
                 , @SerializedName("user") val user: User): BaseResponse(),Parcelable