package com.samr.domain.usecases

import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.data.entities.CharacterData
import com.samr.data.entities.ComicsItem
import com.samr.data.entities.StoriesItem
import com.samr.data.repositories.CharacterDetailRepository
import com.samr.data.repositories.mocks.DefaultCharacterDetailRepository
import com.samr.domain.entities.*
import java.lang.Exception

class CharacterDetailUseCase {

    private var characterDetailRepo: CharacterDetailRepository = DefaultCharacterDetailRepository()
    private var offset = 0

    fun execute(characterId: String,callback:(LayerResult<CharacterEntity>?) -> Unit) {

        characterDetailRepo.fetchCharacterDetail(characterId){result ->

            try{
                when (result) {
                    is LayerResult.Success -> {
                        callback(LayerResult.Success(mapEntityToUi(result.value)))
                        offset += 1
                    }
                    is LayerResult.Error -> {
                        throw UIError(result.errorInfo)
                    }
                }
            }catch (e: UIError){
                callback(LayerResult.Error(e))
            }

        }
    }

    private fun mapEntityToUi(it: CharacterData): CharacterEntity{

        try {

            return CharacterEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    modified = it.modified,
                    thumbnail = Thumbnail(
                        it.thumbnail.path,
                        it.thumbnail.extension),
                    resourceURI = it.resourceURI,
                    comics = Publishings(
                        available = it.comics.available,
                        collectionURI = it.comics.collectionURI,
                        items = mapPublishingItem(it.comics.items),
                        returned = it.comics.returned) ,
                    series = Publishings(
                        available = it.series.available,
                        collectionURI = it.series.collectionURI,
                        items = mapPublishingItem(it.series.items),
                        returned = it.series.returned),
                    stories = Publishings(
                        available = it.stories.available,
                        collectionURI = it.stories.collectionURI,
                        items = mapPublishingItem(it.stories.items),
                        returned = it.stories.returned),
                    events = Publishings(
                        available = it.events.available,
                        collectionURI = it.events.collectionURI,
                        items = mapPublishingItem(it.events.items),
                        returned = it.events.returned),
                    urls = mapUrls(it.urls)
                )

        }catch (e: Exception){

            throw UIError(e)
        }

    }

    private fun mapUrls(urls: List<com.samr.data.entities.URL>) =
        urls.map{
            URL(type = it.type,
                url = it.url)
        }

    private fun mapPublishingItem(items: List<Any>) =
        items.map{
            when (it) {
                is StoriesItem -> {
                    PublishingItem(
                        resourceURI = it.resourceURI,
                        name = it.name,
                        type = it.type as? StoryType
                    )
                }
                is ComicsItem -> {
                    PublishingItem(
                        resourceURI = it.resourceURI,
                        name = it.name,
                        type = null
                    )
                }
                else -> {
                    listOf<PublishingItem>()
                }
            }
        }
}