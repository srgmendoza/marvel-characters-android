package com.samr.domain.usecases

import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import com.samr.domain.entities.*
import com.samr.domain.repositories.CharactersListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CharactersUseCase(private val characterRepo: CharactersListRepo) {

    private var offset = 1

    fun execute(callback:(LayerResult<List<CharacterEntity>>?) -> Unit) {

        GlobalScope.launch(Dispatchers.IO) {

            characterRepo.fetchCharactersList(offset) { result ->

                try {
                    when (result) {

                        is LayerResult.Success -> {
                            callback(LayerResult.Success(result.value?.map { it }))
                            offset += 1
                        }
                        is LayerResult.Error -> {

                            throw CustomError(
                                originLayer = (result.error as CustomError).getErrorOriginLayer(),
                                underLyingError = (result.error as CustomError).getUnderlyingError()
                            )
                        }
                    }
                } catch (ce: CustomError) {

                    callback(LayerResult.Error(ce))
                } catch (e: Throwable) {

                    callback(
                        LayerResult.Error(
                            CustomError(
                                originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                                underLyingError = e
                            )
                        )
                    )
                }
            }
        }
    }
}