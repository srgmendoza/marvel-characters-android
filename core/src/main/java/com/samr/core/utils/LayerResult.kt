package com.samr.core.utils

sealed class LayerResult<out T> {

    data class Success<out R>(val value: R): LayerResult<R>()

    data class Error (val error: Throwable): LayerResult<Nothing>()

}

class CustomError(var underLyingError: Throwable? = null, private val originLayer: OriginLayer): Throwable(){

    enum class OriginLayer{
        DATA_LAYER, DOMAIN_LAYER, PRESENTATION_LAYER
    }

    fun getErrorOriginLayer() = originLayer

    fun getUnderlyingError() = underLyingError

}

class DataError(errorCode: Int? = null, errorBody: String? = null, var underLyingError: Throwable? = null): Throwable() {

    private var errorDescription: String = if(errorCode != null && errorBody != null){
        "Error $errorCode, description: $errorBody"
    }else{
        "Check underlying error for more info"
    }

}

class DomainError(error: Throwable, type: Type): Throwable(){


    enum class Type{
        DATA_LAYER_ERROR, MAPPING_ERROR, GENERIC_ERROR
    }

    init{
        when(type){
            Type.DATA_LAYER_ERROR -> {
                if(error is DataError){

                }else{

                }
            }
            Type.MAPPING_ERROR -> {

            }
            Type.GENERIC_ERROR -> {

            }
        }

    }
}

class UIError(error: Throwable): Throwable() {

    var userMessage = ""

    enum class Type{
        DATA_LAYER_ERROR, MAPPING_ERROR, GENERIC_ERROR
    }

    init {

        userMessage = when (error){
            is DomainError -> {
                "Domain Error"
            }
            is DataError -> {
                "Data Error"
            }
            else -> {
                "Unknown Error"
            }
        }
    }
}