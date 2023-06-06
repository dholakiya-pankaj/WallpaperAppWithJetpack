package com.hd.wallpaper.best.mobile.wallpaper.utils

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.imageLoader
import coil.request.ImageRequest
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.CHILD_DIR
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.COMPRESS_QUALITY
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.DISPLAY_NAME
import com.hd.wallpaper.best.mobile.wallpaper.utils.Constants.FILE_EXTENSION
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


inline fun <T> sdk29AndUp(sdk29AndUp: () -> T): T? {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        sdk29AndUp()
    } else null
}

fun Context.hasReadExternalStoragePermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Constants.READ_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasWriteExternalStoragePermission(): Boolean {
    val sdkVersionIsAbove29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    return ContextCompat.checkSelfPermission(
        this,
        Constants.READ_STORAGE
    ) == PackageManager.PERMISSION_GRANTED || sdkVersionIsAbove29
}

fun Context.downloadAndSaveImage(url: String, feature: Feature, state: (ImageLoadingState) -> Unit) {
    val request = ImageRequest.Builder(this)
        .data(url)
        .target(
            onStart = {
                state(ImageLoadingState.Loading)
            },
            onSuccess = { drawable ->
                // Handle the successful result.
                val bitmap = (drawable as? BitmapDrawable)?.bitmap
                bitmap?.let {
                    when(feature) {
                        Feature.DOWNLOAD -> {
                            val isImageSaved = saveInStorage(this, it)
                            if (isImageSaved) state(ImageLoadingState.Success)
                            else state(ImageLoadingState.Error)
                        }
                        Feature.SET -> {
                            state(ImageLoadingState.SetWallpaper(it))
                        }
                        Feature.SHARE -> {
                            val imageUri = saveImgToCache(this, it)
                            state(ImageLoadingState.ShareWallpaper(imageUri))
                        }
                    }
                } ?: run {
                    Log.e("SavingError", "Bitmap error")
                    state(ImageLoadingState.Error)
                }
            },
            onError = {
                Log.e("SavingError", "Bitmap error")
                state(ImageLoadingState.Error)
            }
        )
        .build()
    imageLoader.enqueue(request)
}

private fun saveInStorage(context: Context, bitmap: Bitmap): Boolean {
    val imageCollectionUri = sdk29AndUp {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, DISPLAY_NAME + FILE_EXTENSION)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.WIDTH, bitmap.width)
        put(MediaStore.Images.Media.HEIGHT, bitmap.height)
    }

    return try {
        context.contentResolver.insert(imageCollectionUri, contentValues)?.also {
            context.contentResolver.openOutputStream(it).use { outputStream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
                    throw IOException("Couldn't save image")
                }
            }
        } ?: throw IOException("Couldn't create MediaStore entry")
        true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
}

fun saveImgToCache(context: Context, bitmap: Bitmap): Uri? {
    //    val imagesFolder = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), CHILD_DIR)
    val cachePath = File(context.cacheDir, CHILD_DIR)
    return try {
        cachePath.mkdirs()
        val file = File(cachePath, DISPLAY_NAME + FILE_EXTENSION)
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, stream)
        stream.close()
        FileProvider.getUriForFile(context, context.packageName + ".provider", file)
    } catch (e: IOException) {
        Log.e("SavingError", "IOException error: ${e.message}")
        null
    } catch (e: FileNotFoundException) {
        Log.e("SavingError", "FileNotFoundException error: ${e.message}")
        null
    }
}

sealed class ImageLoadingState {
    object Loading : ImageLoadingState()
    object Success : ImageLoadingState()
    object Error : ImageLoadingState()
    class ShareWallpaper(val uri: Uri?): ImageLoadingState()
    class SetWallpaper(val bitmap: Bitmap): ImageLoadingState()
}

enum class Feature {
    DOWNLOAD, SET, SHARE
}