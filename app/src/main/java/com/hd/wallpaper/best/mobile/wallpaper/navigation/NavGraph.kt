package com.hd.wallpaper.best.mobile.wallpaper.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.hd.wallpaper.best.mobile.wallpaper.screens.WallpaperDetailScreen
import com.hd.wallpaper.best.mobile.wallpaper.screens.WallpaperListScreen
import com.hd.wallpaper.best.mobile.wallpaper.screens.WallpaperListViewModel
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.PARAM_WALLPAPER

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    val wallpaperViewModel: WallpaperListViewModel = hiltViewModel()
    val wallpapers = wallpaperViewModel.searchWallpaper("Love").collectAsLazyPagingItems()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                     durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                slideInHorizontally (
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            }
         ) {
            WallpaperListScreen(navController = navController, wallpapers )
        }
        composable(
            route = Screen.WallpaperDetail.route,
            arguments = listOf(navArgument(PARAM_WALLPAPER) {
                type = NavType.IntType
            }),
            enterTransition = {
                slideInHorizontally (
                    initialOffsetX = { 300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(animationSpec = tween(300))
            }
        ) { navBackStackEntry ->
            WallpaperDetailScreen(
                navController = navController,
                wallpaperPosition = navBackStackEntry.arguments?.getInt(PARAM_WALLPAPER) ?: 0,
                wallpapers
            )
        }
    }
}