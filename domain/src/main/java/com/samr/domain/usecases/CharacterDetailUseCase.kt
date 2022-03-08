package com.samr.domain.usecases

import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import com.samr.domain.entities.*
import com.samr.domain.repositories.CharacterDetailRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CharacterDetailUseCase(private val characterDetailRepo: CharacterDetailRepo) {

    fun execute(characterId: String, callback: (LayerResult<CharacterEntity>?) -> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            characterDetailRepo.fetchCharacterDetail(characterId) { result ->

                try {
                    when (result) {
                        is LayerResult.Success -> {

                            callback(LayerResult.Success(result.value))
                        }
                        is LayerResult.Error -> {

                            throw CustomError(
                                originLayer = (result.error as CustomError).getErrorOriginLayer(),
                                underLyingError = (result.error as CustomError).getUnderlyingError()
                            )
                        }
                    }
                } catch (e: Throwable) {

                    callback(
                        LayerResult.Error(
                            CustomError(
                                originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                                underLyingError = e
                            )
                        )
                    )
                } catch (ce: CustomError) {

                    callback(LayerResult.Error(ce))
                }
            }
        }
    }
}
