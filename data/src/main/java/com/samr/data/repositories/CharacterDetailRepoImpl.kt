package com.samr.data.repositories


import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import com.samr.data.entities.CharacterData
import com.samr.data.services.CharacterService
import com.samr.domain.repositories.CharacterDetailRepo
import com.samr.domain.entities.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CharacterDetailRepoImpl(private val service: CharacterService): CharacterDetailRepo {


    override suspend fun fetchCharacterDetail(characterId: String,
                                      callback: (LayerResult<CharacterEntity>?) -> Unit) {


        service.fetchCharacterDetail(characterId) { result ->

            try{
                when (result) {
                    is LayerResult.Success -> {

                        val character = result.value?.mapToData()?.firstOrNull()?.mapDataToEntity()

                        callback(LayerResult.Success(character))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = (result.error as CustomError).getUnderlyingError())
                    }
                }
            }catch (e: Throwable){

                callback(LayerResult.Error(e))
            }
        }
    }
}