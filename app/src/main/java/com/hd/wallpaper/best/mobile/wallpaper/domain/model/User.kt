package com.hd.wallpaper.best.mobile.wallpaper.domain.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerializedName("links")
    @Embedded
    val userLinks: UserLinks? = null,
    val username: String
)
