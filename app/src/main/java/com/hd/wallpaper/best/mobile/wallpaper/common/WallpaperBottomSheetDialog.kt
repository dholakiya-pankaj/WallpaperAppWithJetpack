package com.hd.wallpaper.best.mobile.wallpaper.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hd.wallpaper.best.mobile.wallpaper.utils.WallpaperType
import kotlinx.coroutines.launch
import com.hd.wallpaper.best.mobile.wallpaper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperBottomSheet(
    modifier: Modifier = Modifier,
    onWallpaperOptionChoose: (WallpaperType) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = {
            onWallpaperOptionChoose.invoke(WallpaperType.NONE)
        },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Button(
            onClick = {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                    onWallpaperOptionChoose.invoke(WallpaperType.HOME_SCREEN)
                }
            },
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(ShapeDefaults.ExtraSmall)
        ) {
            Text(text = stringResource(id = R.string.home_screen))
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                    onWallpaperOptionChoose.invoke(WallpaperType.LOCK_SCREEN)
                }
            },
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(ShapeDefaults.ExtraSmall)
        ) {
            Text(text = stringResource(id = R.string.lock_screen))
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                    onWallpaperOptionChoose.invoke(WallpaperType.BOTH)
                }
            },
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
        ) {
            Text(text = stringResource(id = R.string.both_screen))
        }
    }
}