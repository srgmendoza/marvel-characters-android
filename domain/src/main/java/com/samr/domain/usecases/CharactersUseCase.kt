package com.samr.domain.usecases

import com.samr.data.utils.CustomError
import com.samr.data.utils.LayerResult
import com.samr.domain.models.*
import com.samr.domain.repositories.CharactersListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CharactersUseCase(private val characterRepo: CharactersListRepo) {

    private var offset = 1

    fun execute(callback: (com.samr.data.utils.LayerResult<List<Character>>?) -> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            characterRepo.fetchCharactersList(offset) { result ->

                try {

                    when (result) {

                        is com.samr.data.utils.LayerResult.Success -> {

                            callback(com.samr.data.utils.LayerResult.Success(result.value))
                            offset += 1
                        }
                        is com.samr.data.utils.LayerResult.Error -> {

                            throw com.samr.data.utils.CustomError(
                                originLayer = (result.error as com.samr.data.utils.CustomError).getErrorOriginLayer(),
                                underLyingError = (result.error as com.samr.data.utils.CustomError).getUnderlyingError()
                            )
                        }
                    }
                } catch (ce: com.samr.data.utils.CustomError) {

                    callback(com.samr.data.utils.LayerResult.Error(ce))
                } catch (e: Throwable) {

                    callback(
                        com.samr.data.utils.LayerResult.Error(
                            com.samr.data.utils.CustomError(
                                originLayer = com.samr.data.utils.CustomError.OriginLayer.DOMAIN_LAYER,
                                underLyingError = e
                            )
                        )
                    )
                }
            }
        }
    }
}
