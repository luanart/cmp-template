package com.core.presentation.extension

import com.core.common.error.ApiError
import com.core.common.error.AppException
import com.core.common.error.ErrorCode
import com.resources.Res
import com.resources.err_expired_session
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

internal suspend fun Throwable.toSnackbarMessage() = when (this) {
    is AppException.Api -> {
        error.getTranslatedMessage()
    }
    else -> {
        message ?: "An unknown error occurred"
    }
}