package com.hd.wallpaper.best.mobile.wallpaper.data.source.remote

import androidx.paging.PagingData
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import kotlinx.coroutines.flow.Flow

interface WallpaperDataSource {

    fun getWallpapers(): Flow<PagingData<WallpaperEntity>>

    fun searchWallpaper(query: String): Flow<PagingData<WallpaperEntity>>
}