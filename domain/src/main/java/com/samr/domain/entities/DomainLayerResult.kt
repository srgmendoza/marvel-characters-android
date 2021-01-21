package com.samr.domain.entities


sealed class DomainLayerResult<out T> {

    data class Success<out R>(val value: R): DomainLayerResult<R>()

    data class Error (val errorInfo: Throwable? ): DomainLayerResult<Nothing>()

}

class DomainError(errorCode: Int, errorBody: String): Throwable() {

    var errorDescription = ""

    init {

        errorDescription = "Error $errorCode, description: $errorBody"
    }
}