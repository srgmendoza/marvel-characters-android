package com.samr.domain.usecases

import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.domain.entities.*
import com.samr.domain.repositories.CharactersListRepo


class CharactersUseCase(private val characterRepo: CharactersListRepo) {

    private var offset = 0

    fun execute(callback:(LayerResult<List<CharacterEntity>>?) -> Unit) {

        characterRepo.fetchCharactersList(offset){result ->

            try{
                when (result) {
                    is LayerResult.Success -> {
                        callback(LayerResult.Success(result.value.map{it}))
                        offset += 1
                    }
                    is LayerResult.Error -> {
                        throw CustomError(originLayer = (result.error as CustomError).getErrorOriginLayer(),
                            underLyingError = result.error)
                    }
                }
            }catch (e: Exception){

                callback(LayerResult.Error(CustomError(originLayer = CustomError.OriginLayer.DOMAIN_LAYER,
                    underLyingError = e)))

            }catch (ce: CustomError){

                callback(LayerResult.Error(ce))
            }

        }
    }


}