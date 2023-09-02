package com.samr.domain.models

sealed class LayerResultA<out T> {

    data class Success<out R>(val value: R?) : LayerResultA<R>()

    data class Error(val error: Throwable) : LayerResultA<Nothing>()
}

class CustomErrorA(private var underLyingError: Throwable, private val originLayer: OriginLayerA) : Throwable() {

    enum class OriginLayerA {
        DATA_LAYER, DOMAIN_LAYER, PRESENTATION_LAYER
    }

    fun getErrorOriginLayer() = originLayer

    fun getUnderlyingError() = underLyingError

    fun getErrorOriginLayerMsg() = when (originLayer) {
        OriginLayerA.DOMAIN_LAYER -> {
            "Domain Layer"
        }
        OriginLayerA.DATA_LAYER -> {
            "Data Layer"
        }
        OriginLayerA.PRESENTATION_LAYER -> {
            "Presentation Layer"
        }
    }

    fun getErrorDetailedMsg() = underLyingError.localizedMessage
}
