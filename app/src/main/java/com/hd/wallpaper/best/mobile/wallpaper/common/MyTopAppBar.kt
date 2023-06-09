package com.hd.wallpaper.best.mobile.wallpaper.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(
    modifier: Modifier = Modifier,
    title: String,
    showDoneButton: Boolean = false,
    showBackButton: Boolean = false,
    rightButtonIcon: ImageVector = Icons.Default.Done,
    onBackButtonClicked: () -> Unit,
    onRightButtonClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(text = title, fontSize = 18.sp)
            },
            navigationIcon = {
                if (!showBackButton) return@CenterAlignedTopAppBar
                IconButton(
                    onClick = {
                        onBackButtonClicked.invoke()
                    }, content = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back button"
                        )
                    }
                )
            },
            actions = {
                if (!showDoneButton) return@CenterAlignedTopAppBar
                IconButton(
                    onClick = {
                        onRightButtonClicked.invoke()
                    }, content = {
                        Icon(
                            imageVector = rightButtonIcon,
                            contentDescription = "Right button"
                        )
                    }
                )
            }//,
//            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                containerColor = Color(0xFF018786).compositeOver(Color.White)
//                //MaterialTheme.colorScheme.onPrimaryContainer
//            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewToolbar() {
    MyToolbar(title = "My Toolbar", onBackButtonClicked = { /*TODO*/ }) {}
}