package com.sm.feature_listing.data.entities

import com.sm.feature_listing.domain.models.COVER_TITLE
import com.sm.feature_listing.domain.models.CharacterDomain
import com.sm.feature_listing.domain.models.INTERIOR_STORY_TITLE
import com.sm.feature_listing.domain.models.Images
import com.sm.feature_listing.domain.models.PublishingItem
import com.sm.feature_listing.domain.models.Publishings
import com.sm.feature_listing.domain.models.StoryType
import java.util.*

data class CharacterEntity(
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
        CharacterDomain(
            id = id,
            name = name,
            description = description,
            modified = modified,
            thumbnail = getImages(thumbnail),
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
            detailUrl = getDetailUrl(urls),
            internalTS = Date().time
        )

    private fun getDetailUrl(urls: List<URLEntity>): String {
        return urls.find { it.type == "detail" }?.url ?: ""
    }

    private fun getImages(thumbnail: ThumbnailEntity): Images {
        return Images(
            thumbnail = getImageUrl(
                path = thumbnail.path,
                extension = thumbnail.extension,
                size = com.example.core_utils.AspectRatio.ImageSize.MEDIUM,
                origin = com.example.core_utils.AspectRatio.Origin.LIST),
            poster = getImageUrl(
                path = thumbnail.path,
                extension = thumbnail.extension,
                size = com.example.core_utils.AspectRatio.ImageSize.MEDIUM,
                origin = com.example.core_utils.AspectRatio.Origin.DETAIL
            )
        )
    }

    private fun getImageUrl(
        path: String,
        extension: String,
        size: com.example.core_utils.AspectRatio.ImageSize,
        origin: com.example.core_utils.AspectRatio.Origin
    ) = if (origin == com.example.core_utils.AspectRatio.Origin.LIST) "$path/${com.example.core_utils.StandardAspectRatio.getSize(size)}.$extension" else "$path.$extension"
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
        COVER_TITLE -> StoryType.COVER
        INTERIOR_STORY_TITLE -> StoryType.INTERIOR_STORY
        else -> StoryType.NA })
}

data class ThumbnailEntity(
    val path: String,
    val extension: String
)

data class URLEntity(
    val type: String,
    val url: String
)
