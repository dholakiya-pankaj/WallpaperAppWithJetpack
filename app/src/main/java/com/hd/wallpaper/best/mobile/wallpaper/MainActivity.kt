package com.hd.wallpaper.best.mobile.wallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
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
                        }
                        ROUTE_WALLPAPER -> {
//                            systemUiController.setStatusBarColor(color = Color(
//                                ContextCompat.getColor(this, R.color.black_transparent)
//                            ))
                        }
                    }
                }
            }
        }
    }
}