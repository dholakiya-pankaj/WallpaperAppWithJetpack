package com.hd.wallpaper.best.mobile.wallpaper.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyFloatingActionButton(
    modifier: Modifier = Modifier,
    buttonIcon: ImageVector = Icons.Default.ArrowBack,
    contentDescription: String,
    onButtonClicked: () -> Unit
) {
    FloatingActionButton(
        onClick = { onButtonClicked.invoke() },
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Icon(
            buttonIcon,
            contentDescription = contentDescription,
        )
    }
}