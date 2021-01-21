package com.samr.data.entities

class DataLayerError (val errorType: Type) {

    sealed class Type {

        class HttpRequestError(private val code: Int): Type(){

            fun getHttpErrorMessage() = "HttpError: $code while attempting request"

            fun getHttpErrorType() = when(code){
                in 400..499 -> {
                    HttpErrorType.HTTP_CLIENT_ERROR
                }
                in 500..599 -> {
                    HttpErrorType.HTTP_SERVER_ERROR
                }
                else -> {
                    HttpErrorType.HTTP_UNKNOWN_ERROR
                }
            }

            enum class HttpErrorType {
                HTTP_CLIENT_ERROR, HTTP_SERVER_ERROR, HTTP_UNKNOWN_ERROR
            }
        }

        object NoInternetConnection: Type()

        object BadUrl: Type()

        class UnknownError(private val msg: String): Type(){

            fun getErrorMessage() = "Unknown error: $msg while attempting request"

        }
    }



}