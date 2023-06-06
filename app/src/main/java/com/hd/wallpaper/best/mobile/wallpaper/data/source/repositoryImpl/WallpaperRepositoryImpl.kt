package com.hd.wallpaper.best.mobile.wallpaper.data.source.repositoryImpl

import androidx.paging.PagingData
import com.hd.wallpaper.best.mobile.wallpaper.data.source.remote.WallpaperDataSource
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import com.hd.wallpaper.best.mobile.wallpaper.domain.WallpapersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(
   private val wallpaperDataSource: WallpaperDataSource
) : WallpapersRepository {

    override fun getWallpapers(): Flow<PagingData<WallpaperEntity>> {
        return wallpaperDataSource.getWallpapers()
    }

    override fun searchWallpaper(query: String): Flow<PagingData<WallpaperEntity>> {
        return wallpaperDataSource.searchWallpaper(query)
    }
}