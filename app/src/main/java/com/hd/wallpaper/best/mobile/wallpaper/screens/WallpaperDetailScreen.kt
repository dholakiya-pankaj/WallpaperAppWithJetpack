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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.hd.wallpaper.best.mobile.wallpaper.R
import com.hd.wallpaper.best.mobile.wallpaper.common.MyFloatingActionButton
import com.hd.wallpaper.best.mobile.wallpaper.common.WallpaperBottomSheet
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity
import com.hd.wallpaper.best.mobile.wallpaper.utils.*
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.IMAGE_SHARING_TYPE
import kotlinx.coroutines.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WallpaperDetailScreen(
    navController: NavHostController,
    wallpaperPosition: Int,
    wallpapers: LazyPagingItems<WallpaperEntity>
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val position by rememberSaveable { mutableStateOf(wallpaperPosition) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showLoader by remember { mutableStateOf(false) }

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
            showLoader = true
            context.downloadAndSaveImage(
                wallpapers[pagerState.currentPage]?.urls?.regularImage.toString(),
                Feature.DOWNLOAD
            ) { state ->
                showLoader = false
                handleImageState(context, state)
            }
        }

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        HorizontalPager(state = pagerState) { position ->
            PagerScreen(wallpaper = wallpapers[position])
        }

        LaunchedEffect(key1 = position) {
            scope.launch {
                Log.d("PagerPosition", "WallpaperDetailScreen: $position")
                pagerState.scrollToPage(position)
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
        )
        {
            MyFloatingActionButton(
                buttonIcon = Icons.Outlined.ArrowBack,
                contentDescription = stringResource(id = R.string.back_button),
                onButtonClicked = {
                    navController.navigateUp()
                }
            )
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .navigationBarsPadding()
                .align(Alignment.BottomCenter)
        )
        {
            MyFloatingActionButton(
                buttonIcon = Icons.Outlined.Download,
                contentDescription = stringResource(id = R.string.download_wallpaper),
            ) {
                if (context.hasWriteExternalStoragePermission()) {
                    showLoader = true
                    context.downloadAndSaveImage(
                        wallpapers[pagerState.currentPage]?.urls?.regularImage.toString(),
                        Feature.DOWNLOAD
                    ) {
                        showLoader = false
                        handleImageState(context, it)
                    }
                } else {
                    requestPermissions(context, launcher)
                }
            }

            FilledIconButton(
                onClick = {
                    context.downloadAndSaveImage(
                        wallpapers[pagerState.currentPage]?.urls?.regularImage.toString(),
                        Feature.SET
                    ) {
                        when (it) {
                            is ImageLoadingState.SetWallpaper -> {
                                bitmap = it.bitmap
                                showBottomSheet = true
                            }
                            else -> {}
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                Text(text = stringResource(id = R.string.set_wallpaper))
            }

            MyFloatingActionButton(
                buttonIcon = Icons.Outlined.Share,
                contentDescription = stringResource(id = R.string.share_wallpaper),
            ) {
                showLoader = true
                context.downloadAndSaveImage(
                    wallpapers[pagerState.currentPage]?.urls?.regularImage.toString(),
                    Feature.SHARE
                ) {
                    showLoader = false
                    handleImageState(context, it)
                }
            }
        }
    }

    if (showBottomSheet) {
        OpenBottomSheetDialog(context, bitmap, scope, showLoader = {
            showLoader = it
        }, onClose = {
            showBottomSheet = false
        })
    }

    if(showLoader) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

@Composable
fun OpenBottomSheetDialog(
    context: Context,
    bitmap: Bitmap?,
    scope: CoroutineScope,
    showLoader: (Boolean) -> Unit,
    onClose: () -> Unit
) {
    WallpaperBottomSheet(
        onWallpaperOptionChoose = {
            onClose.invoke()
            bitmap?.let { bmp ->
                showLoader(true)
                scope.launch {
                   val result = withContext(Dispatchers.IO) {
                       context.setWallpaper(it, bmp)
                   }
                    showLoader(false)
                   if(result)
                       Toast.makeText(context, R.string.wallpaper_set, Toast.LENGTH_LONG).show()
                }
            }
        }
    )
}

fun handleImageState(
    context: Context,
    state: ImageLoadingState
) {
    when (state) {
        is ImageLoadingState.Loading -> {
            Log.e("SavingError", "WallpaperDetailScreen: Loading Started...")
        }
        is ImageLoadingState.Success -> {
            Toast.makeText(context, R.string.image_saved, Toast.LENGTH_LONG).show()
        }
        is ImageLoadingState.ShareWallpaper -> {
            if (state.uri != null) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_STREAM, state.uri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.type = IMAGE_SHARING_TYPE
                context.startActivity(intent)
            } else {
                Toast.makeText(
                    context,
                    R.string.share_wallpaper_error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        is ImageLoadingState.Error -> {
            Toast.makeText(context, R.string.image_saving_error, Toast.LENGTH_LONG).show()
        }
        else -> {}
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

