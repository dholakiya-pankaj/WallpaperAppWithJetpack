package com.hd.wallpaper.best.mobile.wallpaper.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi


fun Context.setWallpaper(type: WallpaperType, bitmap: Bitmap) {
    val wallpaperManager = WallpaperManager.getInstance(this)
    val minSdk24AndUp = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    if(minSdk24AndUp) {
        @RequiresApi(Build.VERSION_CODES.N)
        when(type) {
            WallpaperType.HOME_SCREEN -> {
                wallpaperManager.setBitmap(
                    bitmap, null, false, WallpaperManager.FLAG_SYSTEM
                )
                showToast(this)
            }
            WallpaperType.LOCK_SCREEN -> {
                wallpaperManager.setBitmap(
                    bitmap, null, false, WallpaperManager.FLAG_LOCK
                )
                showToast(this)
            }
            WallpaperType.BOTH -> {
                wallpaperManager.setBitmap(
                    bitmap, null, false,
                    WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
                )
                showToast(this)
            }
            else -> {}
        }
    } else {
        wallpaperManager.setBitmap(bitmap)
        showToast(this)
    }
}

private fun showToast(context: Context) {
    Toast.makeText(context, "Wallpaper successfully set", Toast.LENGTH_LONG).show()
}

enum class WallpaperType {
    HOME_SCREEN,
    LOCK_SCREEN,
    BOTH,
    NONE
}
