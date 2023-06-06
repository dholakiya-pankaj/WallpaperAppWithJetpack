package com.hd.wallpaper.best.mobile.wallpaper.data

import com.google.gson.Gson
import com.hd.wallpaper.best.mobile.wallpaper.domain.error.ErrorEntity
import com.hd.wallpaper.best.mobile.wallpaper.domain.error.ErrorHandler
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GeneralErrorHandlerImpl @Inject constructor() : ErrorHandler {

    data class ErrorResponse(val message: String, val errorCode: Int)

    override fun getError(throwable: Throwable?): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.Network
            is HttpException -> {
                when (throwable.code()) {
                    400 -> ErrorEntity.BadRequest(getErrorObject(throwable.response()?.errorBody()))
                    401 -> ErrorEntity.Authentication
                    404 -> ErrorEntity.NotFound
                    403 -> ErrorEntity.AccessDenied
                    503 -> ErrorEntity.ServiceUnavailable
                    else -> ErrorEntity.Unknown
                }
            }
            else -> ErrorEntity.Unknown
        }
    }

    private fun getErrorObject(responseBody: ResponseBody?): ErrorResponse {
        return Gson().fromJson(responseBody?.string(), ErrorResponse::class.java)
    }
}