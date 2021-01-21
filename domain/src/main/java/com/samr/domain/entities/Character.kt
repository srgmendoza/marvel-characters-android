package com.samr.domain.entities

import com.samr.data.entities.Comics
import com.samr.data.entities.ComicsItem
import com.samr.data.entities.Stories
import com.samr.data.entities.StoriesItem
import com.samr.data.entities.StoryType
import com.samr.data.entities.Thumbnail
import com.samr.data.entities.URL


data class Character (
    val id: Long,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail,
    val resourceURI: String,
    val comics: Comics,
    val series: Comics,
    val stories: Stories,
    val events: Comics,
    val urls: List<URL>
) {

    fun toCharacterUi() = CharacterUI(this.id, this.name)
}

data class Comics (
    val available: Long,
    val collectionURI: String,
    val items: List<ComicsItem>,
    val returned: Long
)

data class ComicsItem (
    val resourceURI: String,
    val name: String
)

data class Stories (
    val available: Long,
    val collectionURI: String,
    val items: List<StoriesItem>,
    val returned: Long
)

data class StoriesItem (
    val resourceURI: String,
    val name: String,
    val type: StoryType
)

enum class StoryType {
    COVER,
    INTERIOR_STORY
}

data class Thumbnail (
    val path: String,
    val extension: String
)

data class URL (
    val type: String,
    val url: String
)
