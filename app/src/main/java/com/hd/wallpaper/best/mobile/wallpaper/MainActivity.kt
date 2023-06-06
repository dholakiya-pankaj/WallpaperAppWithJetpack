package com.hd.wallpaper.best.mobile.wallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.graphics.Color
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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

                val systemUiController = rememberSystemUiController()
                val navHostController = rememberAnimatedNavController()

                SetupNavGraph(navController = navHostController)

                navHostController.addOnDestinationChangedListener {
                        _, destination, _ ->
                    when (destination.route) {
                        ROUTE_HOME -> {
                            systemUiController.isStatusBarVisible = true
                            systemUiController.setStatusBarColor(color = Color.Blue)
                        }
                        ROUTE_WALLPAPER -> {
                            systemUiController.isStatusBarVisible = false
                            systemUiController.setStatusBarColor(color = Color.Transparent)
                        }
                    }
                }
            }
        }
    }
}