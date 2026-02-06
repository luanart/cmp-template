package com.core.common.error

enum class ErrorCode(val code: Int) {
    UNKNOWN(-1),
    EXPIRED_SESSION(1);

    companion object {
        fun from(code: Int?) = entries.firstOrNull { it.code == code } ?: UNKNOWN
    }
}