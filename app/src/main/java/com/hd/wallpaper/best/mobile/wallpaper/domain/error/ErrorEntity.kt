package com.hd.wallpaper.best.mobile.wallpaper.domain.error

import com.hd.wallpaper.best.mobile.wallpaper.data.GeneralErrorHandlerImpl

sealed class ErrorEntity {
    object Network : ErrorEntity()

    object NotFound : ErrorEntity()

    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Authentication : ErrorEntity()

    data class BadRequest(val errorResponse: GeneralErrorHandlerImpl.ErrorResponse) : ErrorEntity()

    object Unknown : ErrorEntity()
}
