package com.hd.wallpaper.best.mobile.wallpaper.di

import com.hd.wallpaper.best.mobile.wallpaper.data.GeneralErrorHandlerImpl
import com.hd.wallpaper.best.mobile.wallpaper.data.source.remote.WallpaperDataSource
import com.hd.wallpaper.best.mobile.wallpaper.data.source.remote.WallpaperDataSourceImpl
import com.hd.wallpaper.best.mobile.wallpaper.data.source.repositoryImpl.WallpaperRepositoryImpl
import com.hd.wallpaper.best.mobile.wallpaper.domain.WallpapersRepository
import com.hd.wallpaper.best.mobile.wallpaper.domain.error.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class WallpaperBindingModule {

    @Binds
    abstract fun provideWallpaperDataSource(wallpaperDataSourceImpl: WallpaperDataSourceImpl): WallpaperDataSource

    @Binds
    abstract fun provideWallpaperRepository(wallpaperRepositoryImpl: WallpaperRepositoryImpl): WallpapersRepository

    @Binds
    abstract fun bindsErrorHandler(errorHandler: GeneralErrorHandlerImpl): ErrorHandler
}