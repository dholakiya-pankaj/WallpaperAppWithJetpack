package com.hd.wallpaper.best.mobile.wallpaper.data.service

import com.hd.wallpaper.best.mobile.wallpaper.BuildConfig
import com.hd.wallpaper.best.mobile.wallpaper.data.model.UnsplashResponse
import com.hd.wallpaper.best.mobile.wallpaper.data.model.SearchWallpaperResponse
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WallpaperService {

    @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
    @GET("photos")
    suspend fun getWallpapers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashResponse>

    @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
    @GET("search/photos")
    suspend fun searchWallpaper(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("orientation") orientation: String = Constants.WALLPAPER_ORIENTATION
    ): SearchWallpaperResponse
}