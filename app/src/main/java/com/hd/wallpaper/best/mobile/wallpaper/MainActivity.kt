package com.hd.wallpaper.best.mobile.wallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hd.wallpaper.best.mobile.wallpaper.navigation.SetupNavGraph
import com.hd.wallpaper.best.mobile.wallpaper.ui.theme.WallpaperAppWithJetpackTheme
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.ROUTE_HOME
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.ROUTE_WALLPAPER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallpaperAppWithJetpackTheme {

                val navHostController = rememberAnimatedNavController()

                SetupNavGraph(navController = navHostController)

                navHostController.addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.route) {
                        ROUTE_HOME -> {}
                        ROUTE_WALLPAPER -> {}
                    }
                }
            }
        }
    }
}