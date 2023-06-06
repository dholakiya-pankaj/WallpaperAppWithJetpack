package com.hd.wallpaper.best.mobile.wallpaper.domain

import androidx.paging.PagingData
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import kotlinx.coroutines.flow.Flow

interface WallpapersRepository {

    fun getWallpapers(): Flow<PagingData<WallpaperEntity>>

    fun searchWallpaper(query: String): Flow<PagingData<WallpaperEntity>>
}