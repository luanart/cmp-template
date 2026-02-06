package com.core.presentation.util

import com.core.common.error.ApiError
import com.core.common.error.AppException
import com.core.common.error.ErrorCode
import com.core.presentation.data.AppError
import com.resources.Res
import com.resources.err_expired_session
import com.resources.err_no_internet
import com.resources.err_unknown
import org.jetbrains.compose.resources.getString

suspend fun ApiError.getTranslatedMessage(): String {
    val defaultMessage = getString(Res.string.err_unknown)

    if (code == null) {
        return "${message ?: defaultMessage} (BE)"
    }

    val translatedMessage = when(ErrorCode.from(code)) {
        ErrorCode.EXPIRED_SESSION -> getString(Res.string.err_expired_session)
        else -> defaultMessage
    }

    return "$translatedMessage (BE$code)"
}

internal suspend fun Throwable.toAppError(onClear: () -> Unit, onRetry: () -> Unit) = when (this) {
    is AppException.NoInternet -> AppError(
        message = getString(Res.string.err_no_internet),
        fullPage = true,
        clearError = onClear,
        retryAction = onRetry
    )
    is AppException.Api -> AppError(
        message = error.getTranslatedMessage(),
        clearError = onClear,
    )
    else -> AppError(
        message = message ?: "An unknown error occurred",
        clearError = onClear,
    )
}