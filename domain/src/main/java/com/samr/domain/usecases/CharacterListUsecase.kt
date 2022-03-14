package com.samr.domain.usecases

import com.samr.domain.models.CustomError
import com.samr.domain.repositories.CharacterLocalRepository
import com.samr.domain.repositories.CharacterRemoteRepository

class CharacterListUsecase(private val localRepo: CharacterLocalRepository,
                           private val remoteRepo: CharacterRemoteRepository) {

    private var offset = 1

    fun execute(onListReceived: (Result<Boolean>) -> Unit) {
        remoteRepo.fetchCharactersList(offset) { charactersResult ->
            charactersResult.onSuccess { characters ->
                offset += 1
                localRepo.insert(characters) { saveResult ->
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
                }
            }
            charactersResult.onFailure {
                val error = CustomError(
                    originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                    underLyingError = it
                )
                onListReceived(Result.failure(error))
            }
        }
    }

    fun getCharactersList() = localRepo.selectAll(offset)

}
