package com.sm.feature_listing.domain.usecases

import com.samr.domain.models.CustomErrorQ
import com.sm.feature_listing.domain.models.Character
import com.sm.feature_listing.domain.repositories.CharacterRemoteRepository

class CharacterListUsecase(private val remoteRepo: CharacterRemoteRepository
) {

    private var offset = 1

    fun execute(onListReceived: (Result<List<Character>>) -> Unit) {
        remoteRepo.fetchCharactersList(offset) { charactersResult ->
            charactersResult.onSuccess { characters ->
                offset += 1
                onListReceived(Result.success(characters))

/*                localRepo.insert(characters) { saveResult ->
                    saveResult.onSuccess {
                        onListReceived(Result.success(true))
                    }
                    saveResult.onFailure {
                        val error = CustomError(
                            originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                            underLyingError = it
                        )
                        onListReceived(Result.failure(error))
                    }
                }*/
            }
            charactersResult.onFailure {
                val error = CustomErrorQ(
                    originLayer = CustomErrorQ.OriginLayerQ.DOMAIN_LAYER,
                    underLyingError = it
                )
                onListReceived(Result.failure(error))
            }
        }
    }

    //fun getCharactersList() = localRepo.selectAll(offset)

}
