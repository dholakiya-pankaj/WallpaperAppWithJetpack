package com.hd.wallpaper.best.mobile.wallpaper.di

import android.content.Context
import androidx.room.Room
import com.hd.wallpaper.best.mobile.wallpaper.database.db.WallpaperDatabase
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideWallpaperDB(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context = context,
        WallpaperDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideWallpaperDao(database: WallpaperDatabase) = database.getWallpaperDao()
}