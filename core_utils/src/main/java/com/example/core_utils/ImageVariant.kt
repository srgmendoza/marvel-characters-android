package com.example.core_utils

sealed interface ImageVariant {
    enum class ImageSize(isDefault: Boolean = false) {
        FULL(true),
        SMALL,
        MEDIUM,
        LARGE,
        XLARGE,
        FANTASTIC,
        UNCANNY,
        INCREDIBLE,
        AMAZING
    }

    enum class Origin {
        LIST, DETAIL
    }

    fun getSize(type: ImageSize): String

    fun getAvailableSizes(): List<ImageSize>

    data object FullSize : ImageVariant {
        override fun getAvailableSizes() = listOf(ImageSize.FULL)
        override fun getSize(type: ImageSize) = ""
    }

    data object PortraitAspectRatio : ImageVariant {
        override fun getAvailableSizes() = listOf(
                ImageSize.SMALL,
                ImageSize.MEDIUM,
                ImageSize.XLARGE,
                ImageSize.FANTASTIC,
                ImageSize.UNCANNY,
                ImageSize.INCREDIBLE
            )
        override fun getSize(type: ImageSize) =
            when (type) {
                ImageSize.SMALL -> "portrait_small"
                ImageSize.MEDIUM -> "portrait_medium"
                ImageSize.XLARGE -> "portrait_xlarge"
                ImageSize.FANTASTIC -> "portrait_fantastic"
                ImageSize.UNCANNY -> "portrait_uncanny"
                ImageSize.INCREDIBLE -> "portrait_incredible"
                else -> ""
            }
    }

    data object SquareAspectRatio : ImageVariant {
        override fun getAvailableSizes() = listOf(
                ImageSize.SMALL,
                ImageSize.MEDIUM,
                ImageSize.LARGE,
                ImageSize.XLARGE,
                ImageSize.FANTASTIC,
                ImageSize.AMAZING
            )
        override fun getSize(type: ImageSize) =
            when (type) {
                ImageSize.SMALL -> "standard_small"
                ImageSize.MEDIUM -> "standard_medium"
                ImageSize.LARGE -> "standard_large"
                ImageSize.XLARGE -> "standard_xlarge"
                ImageSize.FANTASTIC -> "standard_fantastic"
                ImageSize.AMAZING -> "standard_amazing"
                else -> ""
            }
    }

    data object LandscapeAspectRatio : ImageVariant {
        override fun getAvailableSizes() = listOf(
                ImageSize.SMALL,
                ImageSize.MEDIUM,
                ImageSize.LARGE,
                ImageSize.XLARGE,
                ImageSize.AMAZING,
                ImageSize.INCREDIBLE
            )
        override fun getSize(type: ImageSize) =
            when (type) {
                ImageSize.SMALL -> "landscape_small"
                ImageSize.MEDIUM -> "landscape_medium"
                ImageSize.LARGE -> "landscape_large"
                ImageSize.XLARGE -> "landscape_xlarge"
                ImageSize.AMAZING -> "landscape_amazing"
                ImageSize.INCREDIBLE -> "landscape_incredible"
                else -> ""
            }
    }
}

