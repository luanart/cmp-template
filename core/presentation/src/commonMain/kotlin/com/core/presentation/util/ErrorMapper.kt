package com.core.presentation.util

import com.core.common.error.ApiError
import com.core.common.error.AppException
import com.core.common.extension.orDefault
import com.core.presentation.data.AppError
import com.resources.Res
import com.resources.err_expired_session
import com.resources.err_no_internet
import com.resources.err_unknown
import org.jetbrains.compose.resources.getString

suspend fun ApiError.getTranslatedMessage() : String {
    val newMessage = mapOf(
        Pair(1, getString(Res.string.err_expired_session)),
    ).getOrElse(code.orDefault(0)) {
        getString(Res.string.err_unknown)
    }

    return if (code == null && !message.isNullOrBlank()) {
        "$message (BE)"
    } else {
        "$newMessage (BE${code})"
    }
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