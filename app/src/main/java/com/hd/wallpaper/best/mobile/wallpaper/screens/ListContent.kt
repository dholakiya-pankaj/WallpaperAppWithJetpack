package com.hd.wallpaper.best.mobile.wallpaper.screens

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.hd.wallpaper.best.mobile.wallpaper.R
import com.hd.wallpaper.best.mobile.wallpaper.database.entity.WallpaperEntity

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ListContent(
    wallpapers: LazyPagingItems<WallpaperEntity>,
    innerPadding: PaddingValues,
    onWallpaperClicked: (Int) -> Unit
) {
    val cellConfiguration = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
        StaggeredGridCells.Adaptive(minSize = 150.dp)
    } else StaggeredGridCells.Fixed(2)

    LazyVerticalStaggeredGrid(
        columns = cellConfiguration,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        items(
            count = wallpapers.itemCount,
            key = wallpapers.itemKey(
                key = { wallpaper -> wallpaper.wallpaperId }
            ),
            contentType = wallpapers.itemContentType()
        ) { index ->
            val item = wallpapers[index]
            item?.let { wallpaper ->
                MyWallpaper(wallpaper = wallpaper) {
                    onWallpaperClicked.invoke(index)
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun MyWallpaper(wallpaper: WallpaperEntity, onWallpaperClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable {
                onWallpaperClicked()
                /*val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://unsplash.com/@${wallpaper.user?.username}?utm_source=DemoApp&utm_medium=referral")
                )
                startActivity(context, browserIntent, null)*/
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = wallpaper.urls?.smallImage,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .clip(RoundedCornerShape(16.dp)),
            placeholder = painterResource(id = R.drawable.ic_placeholder)
        )
    }
}