package com.hd.wallpaper.best.mobile.wallpaper.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.hd.wallpaper.best.mobile.wallpaper.R
import com.hd.wallpaper.best.mobile.wallpaper.common.WallpaperBottomSheet
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import com.hd.wallpaper.best.mobile.wallpaper.utils.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WallpaperDetailScreen(
    navController: NavHostController,
    wallpaperPosition: Int,
    wallpapers: LazyPagingItems<WallpaperEntity>
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val type = remember {
        mutableStateOf(WallpaperType.NONE)
    }
    var bitmap: Bitmap? = null

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        /*Provide ager size*/
        wallpapers.itemCount
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (it[Constants.WRITE_STORAGE] == true) {
            context.downloadAndSaveImage(
                wallpapers[pagerState.currentPage]?.urls?.regularImage.toString(),
                Feature.DOWNLOAD
            ) { state ->
                handleImageState(context, state) { bmp ->
                    bitmap = bmp
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        scope.launch {
            pagerState.scrollToPage(wallpaperPosition)
        }

        HorizontalPager(state = pagerState) { position ->
            PagerScreen(wallpaper = wallpapers[position])
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
        )
        {
            FloatingActionButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Icon(
                    Icons.Outlined.ArrowBack,
                    contentDescription = "Back Button",
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .navigationBarsPadding()
                .align(Alignment.BottomCenter)
        )
        {
            FloatingActionButton(
                onClick = {
                    if (context.hasWriteExternalStoragePermission()) {
                        context.downloadAndSaveImage(
                            wallpapers[pagerState.currentPage]?.urls?.regularImage.toString(),
                            Feature.DOWNLOAD
                        ) {
                            handleImageState(context, it) { bmp ->
                                bitmap = bmp
                            }
                        }
                    } else {
                        requestPermissions(context, launcher)
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Icon(
                    Icons.Outlined.Download,
                    contentDescription = "Download Wallpaper",
                )
            }

            FilledIconButton(
                onClick = {
                    context.downloadAndSaveImage(
                        wallpapers[pagerState.currentPage]?.urls?.regularImage.toString(),
                        Feature.SET
                    ) {
                        handleImageState(context, it) { bmp ->
                            bitmap = bmp
                            type.value = WallpaperType.HOME_SCREEN
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                Text(text = "Set wallpaper")
            }

            FloatingActionButton(
                onClick = {
                    context.downloadAndSaveImage(
                        wallpapers[pagerState.currentPage]?.urls?.regularImage.toString(),
                        Feature.SHARE
                    ) {
                        handleImageState(context, it) { bmp ->
                            bitmap = bmp
                        }
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Icon(
                    Icons.Outlined.Share,
                    contentDescription = "Share Wallpaper",
                )
            }
        }
    }

    when (type.value) {
        WallpaperType.HOME_SCREEN -> {
            WallpaperBottomSheet(
                onWallpaperOptionChoose = {
                    bitmap?.let { bmp -> context.setWallpaper(it, bmp) }
                })
        }
        WallpaperType.LOCK_SCREEN -> {
            WallpaperBottomSheet(
                onWallpaperOptionChoose = {
                    bitmap?.let { bmp -> context.setWallpaper(it, bmp) }
                })
        }
        WallpaperType.BOTH -> {
            WallpaperBottomSheet(
                onWallpaperOptionChoose = {
                    bitmap?.let { bmp -> context.setWallpaper(it, bmp) }
                })
        }
        else -> {}
    }
}

fun handleImageState(
    context: Context,
    state: ImageLoadingState,
    wallpaper: (Bitmap?) -> Unit
) {
    when (state) {
        is ImageLoadingState.Loading -> {
            Log.e("SavingError", "WallpaperDetailScreen: Loading Started...")
        }
        is ImageLoadingState.Success -> {
            Toast.makeText(context, "Image saved successfully", Toast.LENGTH_LONG).show()
        }
        is ImageLoadingState.SetWallpaper -> {
            context.setWallpaper(WallpaperType.HOME_SCREEN, state.bitmap)
//            WallpaperBottomSheet(
//                onWallpaperOptionChoose = {
//                    context.setWallpaper(it, state.bitmap)
//                }
//            )
        }
        is ImageLoadingState.ShareWallpaper -> {
            if (state.uri != null) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_STREAM, state.uri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.type = "image/*"
                context.startActivity(intent)
            } else {
                Toast.makeText(
                    context,
                    "Oops! Something went wrong! Please try again.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        is ImageLoadingState.Error -> {
            Toast.makeText(context, "Image saving problem", Toast.LENGTH_LONG).show()
        }
    }
}

fun requestPermissions(
    context: Context,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    val permissions = mutableListOf<String>()
    val hasReadPermission = context.hasReadExternalStoragePermission()
    val hasWritePermission = context.hasWriteExternalStoragePermission()

    if (!hasReadPermission)
        permissions.add(Constants.READ_STORAGE)
    if (!hasWritePermission)
        permissions.add(Constants.WRITE_STORAGE)

    launcher.launch(permissions.toTypedArray())
}

@Composable
fun PagerScreen(wallpaper: WallpaperEntity?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = wallpaper?.urls?.regularImage,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_placeholder)
        )
    }
}

