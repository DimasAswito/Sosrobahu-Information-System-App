package com.polije.sosrobahufactoryapp.utils

enum class HttpErrorCode(val code: Int) {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    TIMEOUT(408),
    INTERNAL_SERVER_ERROR(500),
    UNKNOWN(-1)
}