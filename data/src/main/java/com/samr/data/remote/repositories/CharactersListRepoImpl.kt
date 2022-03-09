package com.samr.data.remote.repositories

import com.samr.data.entities.CustomError
import com.samr.data.entities.LayerResult
import com.samr.data.entities.CharactersRawResponse
import com.samr.domain.models.Character
import com.samr.domain.repositories.CharactersListRepo

class CharactersListRepoImpl(private val service: CharacterRemoteRepo) : CharactersListRepo {

    override suspend fun fetchCharactersList(
        offsetFactor: Int,
        callback: (LayerResult<List<Character>>?) -> Unit
    ) {

        service.fetchCharactersList(offsetFactor) { result: LayerResult<CharactersRawResponse> ->

            try {
                when (result) {
                    is LayerResult.Success -> {

                        val characters = result.value?.mapToData()?.map { it.mapDataToEntity() }

                        callback(LayerResult.Success(characters))
                    }
                    is LayerResult.Error -> {

                        throw CustomError(
                            originLayer = CustomError.OriginLayer.DATA_LAYER,
                            underLyingError = (result.error as CustomError).getUnderlyingError()
                        )
                    }
                }
            } catch (e: Throwable) {

                callback(LayerResult.Error(e))
            }
        }
    }
}
