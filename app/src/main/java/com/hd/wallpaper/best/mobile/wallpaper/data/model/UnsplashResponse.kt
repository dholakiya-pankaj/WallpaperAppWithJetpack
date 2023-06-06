package com.hd.wallpaper.best.mobile.wallpaper.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashResponse(
    val id: String?,
    val urls: UrlsResponse?,
    val likes: Int?,
    val user: UserResponse?,
) {
    @Serializable
    data class UrlsResponse(
        val regular: String,
        val small: String
    )

    @Serializable
    data class UserResponse(
        val links: UserLinksResponse,
        val username: String
    ) {
        @Serializable
        data class UserLinksResponse(
            val html: String
        )
    }
}