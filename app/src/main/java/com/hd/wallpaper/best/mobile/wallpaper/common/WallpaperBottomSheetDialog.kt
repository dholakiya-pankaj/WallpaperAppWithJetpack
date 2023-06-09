package com.hd.wallpaper.best.mobile.wallpaper.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.hd.wallpaper.best.mobile.wallpaper.utils.WallpaperType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperBottomSheet(
    onWallpaperOptionChoose: (WallpaperType) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = {
            onWallpaperOptionChoose
        },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Button(onClick = {
            coroutineScope.launch {
                onWallpaperOptionChoose.invoke(WallpaperType.HOME_SCREEN)
                modalBottomSheetState.hide()
            }
        }) {
            Text(text = "Home Screen")
        }

        Button(onClick = {
            coroutineScope.launch {
                onWallpaperOptionChoose.invoke(WallpaperType.LOCK_SCREEN)
                modalBottomSheetState.hide()
            }
        }) {
            Text(text = "Lock Screen")
        }

        Button(onClick = {
            coroutineScope.launch {
                onWallpaperOptionChoose.invoke(WallpaperType.BOTH)
                modalBottomSheetState.hide()
            }
        }) {
            Text(text = "Both")
        }
    }
}