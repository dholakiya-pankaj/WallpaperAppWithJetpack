package com.hd.wallpaper.best.mobile.wallpaper.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hd.wallpaper.best.mobile.wallpaper.data.mapper.toDomain
import com.hd.wallpaper.best.mobile.wallpaper.data.service.WallpaperService
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.ITEMS_PER_PAGE

class SearchPagingSource(
    private val wallpaperService: WallpaperService,
    private val query: String //Ex.: Nature, Love, Girls etc.
) : PagingSource<Int, WallpaperEntity>() {

    override fun getRefreshKey(state: PagingState<Int, WallpaperEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WallpaperEntity> {
        val currentPage = params.key ?: 1
        return try {
            val response = wallpaperService.searchWallpaper(
                query = query,
                page = currentPage,
                perPage = ITEMS_PER_PAGE
            )
            val endOfPaginationReached = response.wallpapers.isEmpty()
            if (response.wallpapers.isNotEmpty()) {
                LoadResult.Page(
                    data = response.toDomain(),
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}