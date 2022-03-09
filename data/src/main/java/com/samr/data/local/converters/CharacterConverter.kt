package com.samr.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samr.data.entities.*
import com.samr.domain.models.*

@ProvidedTypeConverter
object CharacterConverter {

    @TypeConverter
    @JvmStatic
    fun thumbEntityToString(value: ThumbnailEntity?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
    @TypeConverter
    @JvmStatic
    fun stringToThumbEntity(value: String): ThumbnailEntity {
        return Gson().fromJson(value,ThumbnailEntity::class.java)
    }


    @TypeConverter
    @JvmStatic
    fun comicEntityToString(value: ComicsEntity?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
    @TypeConverter
    @JvmStatic
    fun stringToComicsEntity(value: String): ComicsEntity {
        return Gson().fromJson(value,ComicsEntity::class.java)
    }
    @TypeConverter
    @JvmStatic
    fun comicItemListEntityToString(value: List<ComicsItem>?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
    @TypeConverter
    @JvmStatic
    fun stringToComicsItemsListEntity(value: String): List<ComicsItem> {
        return Gson().fromJson(value, object : TypeToken<List<ComicsItem>>() {}.type)
    }


    @TypeConverter
    @JvmStatic
    fun storiesEntityToString(value: StoriesEntity?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
    @TypeConverter
    @JvmStatic
    fun stringToStoriesEntity(value: String): StoriesEntity {
        return Gson().fromJson(value,StoriesEntity::class.java)
    }
    @TypeConverter
    @JvmStatic
    fun storiesItemListEntityToString(value: List<StoriesEntity>?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
    @TypeConverter
    @JvmStatic
    fun stringToStoriesItemsListEntity(value: String): List<StoriesEntity> {
        return Gson().fromJson(value, object : TypeToken<List<StoriesEntity>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun urlListEntityToString(value: List<URLEntity>?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
    @TypeConverter
    @JvmStatic
    fun stringToUrlListEntity(value: String): List<URLEntity> {
        return Gson().fromJson(value, object : TypeToken<List<URLEntity>>() {}.type)
    }



    @TypeConverter
    @JvmStatic
    fun mapToCharacterModel(entity: CharacterEntity): Character {
        return Character(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            modified = entity.modified,
            thumbnail = mapToThumbnail(entity.thumbnail),
            resourceURI = entity.resourceURI,
            comics = mapToComicPublishingModel(entity.comics),
            series = mapToComicPublishingModel(entity.series),
            stories = mapToStoriesPublishingModel(entity.stories),
            events = mapToComicPublishingModel(entity.events),
            urls = mapToUrl(entity.urls)
        )
    }

    @TypeConverter
    @JvmStatic
    fun mapToThumbnail(entity: ThumbnailEntity): Thumbnail {
        return Thumbnail(
            entity.path,
            entity.extension
        )
    }

    @TypeConverter
    @JvmStatic
    fun mapToComicPublishingModel(entity: ComicsEntity): Publishings {
        return Publishings(
            available = entity.available,
            collectionURI = entity.collectionURI,
            items = mapComicsItemsToModel(entity.items),
            returned = entity.returned
        )
    }

    @TypeConverter
    @JvmStatic
    fun mapToStoriesPublishingModel(entity: StoriesEntity): Publishings {
        return Publishings(
            available = entity.available,
            collectionURI = entity.collectionURI,
            items = mapStoriesItemsToModel(entity.items),
            returned = entity.returned
        )
    }

    @TypeConverter
    @JvmStatic
    fun mapToUrl(entity: List<URLEntity>): List<URL> {
        return entity.map {
            URL(
                type = it.type,
                url = it.url
            )
        }
    }

    @TypeConverter
    @JvmStatic
    fun mapStoriesItemsToModel(entity: List<StoriesItem>): List<PublishingItem>{
        return entity.map {
            PublishingItem (
                it.resourceURI,
                it.name,
                when(it.type) {
                    COVER_TITLE -> StoryType.COVER
                    INTERIOR_STORY_TITLE -> StoryType.INTERIOR_STORY
                    else -> StoryType.NA }
            )
        }
    }

    @TypeConverter
    @JvmStatic
    fun mapComicsItemsToModel(entity: List<ComicsItem>): List<PublishingItem>{
        return entity.map {
            PublishingItem (
                it.resourceURI,
                it.name,
                StoryType.NA
            )
        }
    }
}