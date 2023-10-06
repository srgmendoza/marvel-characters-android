package com.sm.feature_listing.data.entities

import com.example.core_utils.ImageVariant
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
            images = getImages(thumbnail),
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

    private fun getImages(thumbnail: ThumbnailEntity):
            Map<ImageVariant, Map<ImageVariant.ImageSize, String>> {

        val imagesMap: MutableMap<ImageVariant, Map<ImageVariant.ImageSize, String>> =
            mutableMapOf()

        val imagesVariants = listOf(
            ImageVariant.PortraitAspectRatio,
            ImageVariant.SquareAspectRatio,
            ImageVariant.LandscapeAspectRatio,
            ImageVariant.FullSize
        )

        imagesVariants.forEach {variant ->
            imagesMap[variant] =
                variant.getAvailableSizes().associate { size ->
                    (size to getImageUrl(
                        variant = variant,
                        path = thumbnail.path,
                        extension = thumbnail.extension,
                        size = size
                    ))
                }
        }

        return imagesMap
    }

    private fun getImageUrl(
        variant: ImageVariant,
        path: String,
        extension: String,
        size: ImageVariant.ImageSize
    ): String {
        val name = variant.getSize(size)

        val url = if (name.isNotEmpty()) {
            "$path/$name.$extension"
        } else {
            "$path.$extension"
        }

        return url
    }
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
