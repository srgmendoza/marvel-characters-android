package com.samr.data.entities

import com.samr.core.utils.StoryType
import com.samr.domain.entities.*

data class CharacterData(
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

    fun mapDataToEntity() =
        CharacterEntity(
            id = this.id,
            name = this.name,
            description = this.description,
            modified = this.modified,
            thumbnail = com.samr.domain.entities.Thumbnail(
                this.thumbnail.path,
                this.thumbnail.extension
            ),
            resourceURI = this.resourceURI,
            comics = com.samr.domain.entities.Publishings(
                available = this.comics.available,
                collectionURI = this.comics.collectionURI,
                items = this.comics.items.map { it.mapComicItemsToDomain() },
                returned = this.comics.returned
            ),
            series = com.samr.domain.entities.Publishings(
                available = this.series.available,
                collectionURI = this.series.collectionURI,
                items = this.series.items.map { it.mapComicItemsToDomain() },
                returned = this.series.returned
            ),
            stories = com.samr.domain.entities.Publishings(
                available = this.stories.available,
                collectionURI = this.stories.collectionURI,
                items = this.stories.items.map { it.mapStoriesItemsToDomain() },
                returned = this.stories.returned
            ),
            events = com.samr.domain.entities.Publishings(
                available = this.events.available,
                collectionURI = this.events.collectionURI,
                items = this.events.items.map { it.mapComicItemsToDomain() },
                returned = this.events.returned
            ),
            urls = this.urls.map { it.mapUrls() }
        )
}

data class Comics(
    val available: Long,
    val collectionURI: String,
    val items: List<ComicsItem>,
    val returned: Long
)

data class ComicsItem(
    val resourceURI: String,
    val name: String
) {
    fun mapComicItemsToDomain() = PublishingItem(this.resourceURI, this.name)
}

data class Stories(
    val available: Long,
    val collectionURI: String,
    val items: List<StoriesItem>,
    val returned: Long
)

data class StoriesItem(
    val resourceURI: String,
    val name: String,
    val type: StoryType
) {
    fun mapStoriesItemsToDomain() = PublishingItem(this.resourceURI, this.name, this.type)
}

data class Thumbnail(
    val path: String,
    val extension: String
)

data class URL(
    val type: String,
    val url: String
) {

    fun mapUrls() =
        com.samr.domain.entities.URL(
            type = this.type,
            url = this.url
        )
}
