package com.hd.wallpaper.best.mobile.wallpaper.domain.error

interface ErrorHandler {
    fun getError(throwable: Throwable?): ErrorEntity
}