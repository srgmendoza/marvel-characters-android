package com.sm.feature_detail.mappers

import com.example.core_utils.ImageVariant
import com.example.core_utils.Mapper
import com.sm.feature_detail.data.entities.CharacterEntity
import com.sm.feature_detail.data.entities.ComicsItem
import com.sm.feature_detail.data.entities.StoriesItem
import com.sm.feature_detail.data.entities.ThumbnailEntity
import com.sm.feature_detail.data.entities.URLEntity
import com.sm.feature_detail.domain.models.COVER_TITLE
import com.sm.feature_detail.domain.models.CharacterDomain
import com.sm.feature_detail.domain.models.INTERIOR_STORY_TITLE
import com.sm.feature_detail.domain.models.PublishingItem
import com.sm.feature_detail.domain.models.Publishings
import com.sm.feature_detail.domain.models.StoryType
import java.util.Date

class DataToDomainMapper : Mapper<CharacterEntity, CharacterDomain>() {
    override fun mapTo(input: CharacterEntity): CharacterDomain {
        with(input) {
            return CharacterDomain(
                id = id,
                name = name,
                description = description,
                modified = modified,
                images = getImages(thumbnail),
                resourceURI = resourceURI,
                comics = Publishings(
                    available = comics.available,
                    collectionURI = comics.collectionURI,
                    items = comics.items.map { ComicsMapper().mapTo(it) },
                    returned = comics.returned
                ),
                series = Publishings(
                    available = series.available,
                    collectionURI = series.collectionURI,
                    items = series.items.map { ComicsMapper().mapTo(it) },
                    returned = series.returned
                ),
                stories = Publishings(
                    available = stories.available,
                    collectionURI = stories.collectionURI,
                    items = stories.items.map { StoriesMapper().mapTo(it) },
                    returned = stories.returned
                ),
                events = Publishings(
                    available = events.available,
                    collectionURI = events.collectionURI,
                    items = events.items.map { ComicsMapper().mapTo(it) },
                    returned = events.returned
                ),
                detailUrl = getDetailUrl(urls),
                internalTS = Date().time
            )
        }
    }

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


internal class StoriesMapper : Mapper<StoriesItem, PublishingItem>() {
    override fun mapTo(input: StoriesItem): PublishingItem {
        with(input) {
            return PublishingItem(
                this.resourceURI, this.name, when (this.type) {
                    COVER_TITLE -> StoryType.COVER
                    INTERIOR_STORY_TITLE -> StoryType.INTERIOR_STORY
                    else -> StoryType.NA
                }
            )
        }
    }
}

internal class ComicsMapper : Mapper<ComicsItem, PublishingItem>() {
    override fun mapTo(input: ComicsItem): PublishingItem {
        with(input) {
            return PublishingItem(this.resourceURI, this.name)
        }
    }
}