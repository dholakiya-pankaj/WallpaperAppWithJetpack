package com.hd.wallpaper.best.mobile.wallpaper.data.source.remote

import androidx.paging.*
import com.hd.wallpaper.best.mobile.wallpaper.data.service.WallpaperService
import com.hd.wallpaper.best.mobile.wallpaper.data.source.paging.SearchPagingSource
import com.hd.wallpaper.best.mobile.wallpaper.data.source.paging.WallpaperRemoteMediator
import com.hd.wallpaper.best.mobile.wallpaper.database.db.WallpaperDatabase
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WallpaperDataSourceImpl @Inject constructor(
    private val wallpaperService: WallpaperService,
    private val wallpaperDatabase: WallpaperDatabase
) : WallpaperDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getWallpapers(): Flow<PagingData<WallpaperEntity>> {
        val pagingSourceFactory = { wallpaperDatabase.getWallpaperDao().getAllWallpapers() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = WallpaperRemoteMediator(
                wallpaperService = wallpaperService,
                wallpaperDatabase = wallpaperDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun searchWallpaper(query: String): Flow<PagingData<WallpaperEntity>> {
        val pagingSourceFactory = { wallpaperDatabase.getWallpaperDao().getAllWallpapers() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = SearchPagingSource(
                query = query,
                wallpaperService = wallpaperService,
                wallpaperDatabase = wallpaperDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}