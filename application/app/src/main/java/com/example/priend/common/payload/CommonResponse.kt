package com.example.priend.common.payload

data class CommonResponse<T>(
    val statusCode: String,
    val message: String,
    val data: T? = null
) {
    companion object {
        private const val OK_STATUS_CODE = "200"
        private const val OK_MESSAGE = "OK"

        fun <T> ok(message: String = OK_MESSAGE, data: T? = null): CommonResponse<T> {
            return CommonResponse(
                statusCode = OK_STATUS_CODE,
                message = message,
                data = data
            )
        }
    }
}

