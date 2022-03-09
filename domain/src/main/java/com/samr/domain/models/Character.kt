package com.samr.domain.models

data class Character(
    val id: Long,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail,
    val resourceURI: String,
    val comics: Publishings,
    val series: Publishings,
    val stories: Publishings,
    val events: Publishings,
    val urls: List<URL>
)

data class Publishings(
    val available: Long,
    val collectionURI: String,
    val items: List<PublishingItem>,
    val returned: Long
)

data class Thumbnail(
    val path: String,
    val extension: String
)

data class URL(
    val type: String,
    val url: String
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