package com.samr.domain.models

sealed class LayerResultQ<out T> {

    data class Success<out R>(val value: R?) : LayerResultQ<R>()

    data class Error(val error: Throwable) : LayerResultQ<Nothing>()
}

class CustomErrorQ(private var underLyingError: Throwable, private val originLayer: OriginLayerQ) : Throwable() {

    enum class OriginLayerQ {
        DATA_LAYER, DOMAIN_LAYER, PRESENTATION_LAYER
    }

    fun getErrorOriginLayer() = originLayer

    fun getUnderlyingError() = underLyingError

    fun getErrorOriginLayerMsg() = when (originLayer) {
        OriginLayerQ.DOMAIN_LAYER -> {
            "Domain Layer"
        }
        OriginLayerQ.DATA_LAYER -> {
            "Data Layer"
        }
        OriginLayerQ.PRESENTATION_LAYER -> {
            "Presentation Layer"
        }
    }

    fun getErrorDetailedMsg() = underLyingError.localizedMessage
}
