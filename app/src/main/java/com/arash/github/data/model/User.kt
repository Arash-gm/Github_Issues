package com.arash.github.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

@Parcelize
data class User(@SerializedName("login") val userName:String
                ,@SerializedName("id") val id:String
                ,@SerializedName("avatar_url") val avatarUrl:String): Parcelable