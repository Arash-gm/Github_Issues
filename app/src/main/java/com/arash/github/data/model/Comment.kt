package com.arash.github.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

@Parcelize
data class Comment(@SerializedName("body") val body:String
                   , @SerializedName("user") val user: User): BaseResponse(),Parcelable