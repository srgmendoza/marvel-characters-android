package com.samr.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samr.domain.models.*

@Entity(tableName = "Characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: ThumbnailEntity,
    val resourceURI: String,
    val comics: ComicsEntity,
    val series: ComicsEntity,
    val stories: StoriesEntity,
    val events: ComicsEntity,
    val urls: List<URLEntity>
) {

    fun mapDataToEntity() =
        Character(
            id = id,
            name = name,
            description = description,
            modified = modified,
            thumbnail = com.samr.domain.models.Thumbnail(
                thumbnail.path,
                thumbnail.extension
            ),
            resourceURI = resourceURI,
            comics = Publishings(
                available = comics.available,
                collectionURI = comics.collectionURI,
                items = comics.items.map { it.mapComicItemsToDomain() },
                returned = comics.returned
            ),
            series = Publishings(
                available = series.available,
                collectionURI = series.collectionURI,
                items = series.items.map { it.mapComicItemsToDomain() },
                returned = series.returned
            ),
            stories = Publishings(
                available = stories.available,
                collectionURI = stories.collectionURI,
                items = stories.items.map { it.mapStoriesItemsToDomain() },
                returned = stories.returned
            ),
            events = Publishings(
                available = events.available,
                collectionURI = events.collectionURI,
                items = events.items.map { it.mapComicItemsToDomain() },
                returned = events.returned
            ),
            urls = urls.map { it.mapUrls() }
        )
}

data class ComicsEntity(
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

data class StoriesEntity(
    val available: Long,
    val collectionURI: String,
    val items: List<StoriesItem>,
    val returned: Long
)

data class StoriesItem(
    val resourceURI: String,
    val name: String,
    val type: String
) {
    fun mapStoriesItemsToDomain() = PublishingItem(this.resourceURI, this.name, when(this.type) {
        COVER_TITLE -> com.samr.domain.models.StoryType.COVER
        INTERIOR_STORY_TITLE -> com.samr.domain.models.StoryType.INTERIOR_STORY
        else -> com.samr.domain.models.StoryType.NA })
}

data class ThumbnailEntity(
    val path: String,
    val extension: String
)

data class URLEntity(
    val type: String,
    val url: String
) {

    fun mapUrls() =
        com.samr.domain.models.URL(
            type = this.type,
            url = this.url
        )
}
