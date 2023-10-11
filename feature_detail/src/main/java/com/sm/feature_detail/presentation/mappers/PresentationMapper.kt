package com.sm.feature_detail.presentation.mappers

import com.example.core_utils.ImageVariant
import com.example.core_utils.Mapper
import com.sm.feature_detail.domain.models.CharacterDomain
import com.sm.feature_detail.presentation.models.CharacterDetail

class PresentationMapper:Mapper<CharacterDomain, CharacterDetail>() {
    override fun mapTo(input: CharacterDomain): CharacterDetail {
        return CharacterDetail(
            id = input.id,
            name = input.name,
            description = input.description,
            imageUrl = getProperImageUrl(input.images),
            moreContentUrl = input.detailUrl
        )
    }

    private fun getProperImageUrl(
        images: Map<ImageVariant, Map<ImageVariant.ImageSize, String>>): String {

        //TODO: Select the proper image based on device aspect ratio
        return images[ImageVariant.SquareAspectRatio]?.get(ImageVariant.ImageSize.AMAZING).orEmpty()
    }
}