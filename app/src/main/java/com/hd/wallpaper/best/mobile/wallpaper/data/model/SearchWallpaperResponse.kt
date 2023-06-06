package com.hd.wallpaper.best.mobile.wallpaper.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchWallpaperResponse(
    @SerialName("results")
    val wallpapers: List<UnsplashResponse>
)