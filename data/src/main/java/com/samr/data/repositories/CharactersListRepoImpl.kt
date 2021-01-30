package com.samr.data.repositories


import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import com.samr.data.entities.CharactersRawResponse
import com.samr.data.services.CharacterService
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.repositories.CharactersListRepo

class CharactersListRepoImpl(private val service: CharacterService): CharactersListRepo {

    override suspend fun fetchCharactersList(
        offsetFactor: Int,
        callback: (LayerResult<List<CharacterEntity>>?) -> Unit) {

        service.fetchCharactersList(offsetFactor) { result: LayerResult<CharactersRawResponse> ->

            try{
                when (result) {
                    is LayerResult.Success -> {

                        val characters = result.value?.mapToData()?.map { it.mapDataToEntity() }

                        callback(LayerResult.Success(characters))
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