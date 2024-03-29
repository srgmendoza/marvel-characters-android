package com.sm.feature_detail.domain.models

import com.example.core_utils.AspectRatio
import com.example.core_utils.ImageVariant

data class CharacterDomain(
    val id: Long,
    val name: String,
    val description: String,
    val modified: String,
    val images: Map<ImageVariant, Map<ImageVariant.ImageSize,String>>,
    val resourceURI: String,
    val comics: Publishings,
    val series: Publishings,
    val stories: Publishings,
    val events: Publishings,
    val detailUrl: String,
    val internalTS: Long
)

data class Publishings(
    val available: Long,
    val collectionURI: String,
    val items: List<PublishingItem>,
    val returned: Long
)

data class Images(
    val thumbnail: String,
    val poster: String
)

data class PublishingItem(
    val resourceURI: String,
    val name: String,
    val type: StoryType? = null
)

const val COVER_TITLE = "cover"
const val INTERIOR_STORY_TITLE = "interiorStory"

enum class StoryType(name: String) {
    COVER(COVER_TITLE),
    INTERIOR_STORY(INTERIOR_STORY_TITLE),
    NA("")
}