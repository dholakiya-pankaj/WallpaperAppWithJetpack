package com.hd.wallpaper.best.mobile.wallpaper.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hd.wallpaper.best.mobile.wallpaper.database.daos.WallpaperDao
import com.hd.wallpaper.best.mobile.wallpaper.database.daos.WallpaperRemoteKeysDao
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperRemoteKeys

@Database(entities = [WallpaperEntity::class, WallpaperRemoteKeys::class], version = 1, exportSchema = false)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract fun getWallpaperDao(): WallpaperDao
    abstract fun getRemoteKeysDao(): WallpaperRemoteKeysDao
}