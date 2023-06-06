package com.hd.wallpaper.best.mobile.wallpaper.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Urls(
    @SerializedName("regular")
    val regularImage : String? = null,
    @SerializedName("small")
    val smallImage: String? = null
)