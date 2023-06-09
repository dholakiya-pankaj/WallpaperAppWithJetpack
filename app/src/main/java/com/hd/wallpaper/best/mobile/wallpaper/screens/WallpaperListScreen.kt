package com.hd.wallpaper.best.mobile.wallpaper.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import com.hd.wallpaper.best.mobile.wallpaper.common.MyToolbar
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity

@Composable
fun WallpaperListScreen(
    navController: NavHostController,
    wallpapers: LazyPagingItems<WallpaperEntity>,
) {
    Scaffold(
        topBar = {
            MyToolbar(
                title = "Home Screen",
                onBackButtonClicked = {},
                onRightButtonClicked = {}
            )
        },
        content = { paddingValues ->
            ListContent(wallpapers = wallpapers, paddingValues) { position ->
                navController.navigate("wallpaper_detail_screen/$position")
            }
        }
    )
}
