package com.samr.data.repositories


import com.samr.core.utils.DomainError
import com.samr.core.utils.LayerResult
import com.samr.data.services.CharacterService
import com.samr.domain.repositories.CharacterDetailRepository
import com.samr.domain.entities.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class DefaultCharacterDetailRepository: CharacterDetailRepository {

    private var service = CharacterService()

    override fun fetchCharacterDetail(characterId: String,
                                      callback: (LayerResult<CharacterEntity>?) -> Unit) {


        GlobalScope.launch(Dispatchers.IO) {

            service.fetchCharacterDetail(characterId) { result ->

                try{
                    when (result) {
                        is LayerResult.Success -> {
                            val character: CharacterEntity = result.value.mapToData()[0].mapDataToEntity()
                            callback(LayerResult.Success(character))
                        }
                        is LayerResult.Error -> {

                            throw DomainError(result.errorInfo, DomainError.Type.DATA_LAYER_ERROR)
                        }
                    }
                }catch (de: DomainError){

                    callback(LayerResult.Error(de))
                }
            }
        }
    }

}