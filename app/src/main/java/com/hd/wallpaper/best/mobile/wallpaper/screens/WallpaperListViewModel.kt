package com.hd.wallpaper.best.mobile.wallpaper.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import com.hd.wallpaper.best.mobile.wallpaper.domain.WallpapersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WallpaperListViewModel @Inject constructor(
    private val wallpapersRepository: WallpapersRepository
): ViewModel() {

    fun getAllWallpapers(): Flow<PagingData<WallpaperEntity>> {
        return wallpapersRepository.getWallpapers().cachedIn(viewModelScope)
    }

    fun searchWallpaper(query: String): Flow<PagingData<WallpaperEntity>> {
        return wallpapersRepository.searchWallpaper(query).cachedIn(viewModelScope)
    }
}