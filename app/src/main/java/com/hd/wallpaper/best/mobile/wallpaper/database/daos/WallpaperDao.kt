package com.hd.wallpaper.best.mobile.wallpaper.database.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity

@Dao
interface WallpaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpapers(wallpapers: List<WallpaperEntity>)

    @Query("SELECT * FROM my_wallpaper_table")
    fun getAllWallpapers(): PagingSource<Int, WallpaperEntity>

    @Query("DELETE FROM my_wallpaper_table")
    suspend fun deleteAllWallpapers()

}