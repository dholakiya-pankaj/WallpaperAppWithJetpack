package com.hd.wallpaper.best.mobile.wallpaper.data.mapper

import com.hd.wallpaper.best.mobile.wallpaper.data.model.UnsplashResponse
import com.hd.wallpaper.best.mobile.wallpaper.data.model.SearchWallpaperResponse
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import com.hd.wallpaper.best.mobile.wallpaper.domain.model.Urls
import com.hd.wallpaper.best.mobile.wallpaper.domain.model.User
import com.hd.wallpaper.best.mobile.wallpaper.domain.model.UserLinks


fun List<UnsplashResponse>.toDomain(): List<WallpaperEntity> {
    return this.map {
        it.toDomain()
    }
}

fun SearchWallpaperResponse.toDomain(): List<WallpaperEntity> {
    return this.wallpapers.map {
        it.toDomain()
    }
}

fun UnsplashResponse.toDomain() = WallpaperEntity(
    id.toString(), urls?.toDomain(), likes, user?.toDomain()
)

fun UnsplashResponse.UrlsResponse.toDomain() = Urls(
    regular, small
)

fun UnsplashResponse.UserResponse.toDomain() = User(
    links.toDomain(), username
)

fun UnsplashResponse.UserResponse.UserLinksResponse.toDomain() = UserLinks(
    html
)