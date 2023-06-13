package com.hd.wallpaper.best.mobile.wallpaper.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi


fun Context.setWallpaper(type: WallpaperType, bitmap: Bitmap): Boolean {
    val wallpaperManager = WallpaperManager.getInstance(this)
    val minSdk24AndUp = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    if(minSdk24AndUp) {
        @RequiresApi(Build.VERSION_CODES.N)
        when(type) {
            WallpaperType.HOME_SCREEN -> {
                wallpaperManager.setBitmap(
                    bitmap, null, false, WallpaperManager.FLAG_SYSTEM
                )
                return true
            }
            WallpaperType.LOCK_SCREEN -> {
                wallpaperManager.setBitmap(
                    bitmap, null, false, WallpaperManager.FLAG_LOCK
                )
                return true
            }
            WallpaperType.BOTH -> {
                wallpaperManager.setBitmap(
                    bitmap, null, false,
                    WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
                )
                return true
            }
            else -> { return false }
        }
    } else {
        wallpaperManager.setBitmap(bitmap)
        return true
    }
}

enum class WallpaperType {
    HOME_SCREEN,
    LOCK_SCREEN,
    BOTH,
    NONE
}
