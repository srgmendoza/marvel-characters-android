package com.samr.data.remote.repositories

import com.samr.data.entities.CustomError
import com.samr.data.entities.LayerResult
import com.samr.domain.models.Character
import com.samr.domain.repositories.CharacterDetailRepo

class CharacterDetailRepoImpl(private val service: CharacterRemoteRepo) : CharacterDetailRepo {

    override suspend fun fetchCharacterDetail(
        characterId: String,
        callback: (LayerResult<Character>?) -> Unit
    ) {

        service.fetchCharacterDetail(characterId) { result ->

            try {
                when (result) {
                    is LayerResult.Success -> {

                        val character = result.value?.mapToData()?.firstOrNull()?.mapDataToEntity()

                        callback(LayerResult.Success(character))
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
