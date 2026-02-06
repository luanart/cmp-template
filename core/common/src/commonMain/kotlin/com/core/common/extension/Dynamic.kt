package com.core.common.extension

fun <T> T?.orDefault(default: T): T {
    return this ?: default
}

fun <T> Result<T>.onFinally(action: () -> Unit): Result<T> {
    action()
    return this
}