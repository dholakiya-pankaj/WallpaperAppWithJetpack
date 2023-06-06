package com.hd.wallpaper.best.mobile.wallpaper.utils

import android.Manifest

object Constants {
    const val DATABASE_NAME = "my_wallpaper_db"
    const val WALLPAPER_TABLE_NAME = "my_wallpaper_table"
    const val WALLPAPER_REMOTE_KEYS_TABLE_NAME = "wallpaper_remote_keys_tale"
    const val WALLPAPER_ORIENTATION = "portrait"
    const val ITEMS_PER_PAGE = 20

    //Files Constants
    const val CHILD_DIR = "TempWallpapers"
    const val FILE_EXTENSION = ".jpg"
    const val COMPRESS_QUALITY = 95
    const val DISPLAY_NAME = "wallpaper"

    //Argument's keys
    const val PARAM_WALLPAPER = "wallpaper_position"

    //Screen Route
    const val ROUTE_HOME = "home_screen"
    const val ROUTE_WALLPAPER = "wallpaper_detail_screen/{$PARAM_WALLPAPER}"

    //Permissions
    const val READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    const val WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
}