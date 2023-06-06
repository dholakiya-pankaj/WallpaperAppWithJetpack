package com.hd.wallpaper.best.mobile.wallpaper.navigation

import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants

sealed class Screen(val route: String) {
    object Home: Screen(Constants.ROUTE_HOME)
    object WallpaperDetail: Screen(Constants.ROUTE_WALLPAPER)
}
