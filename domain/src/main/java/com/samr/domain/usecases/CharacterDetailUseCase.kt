package com.samr.domain.usecases

import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.domain.entities.*
import com.samr.domain.repositories.CharacterDetailRepository

class CharacterDetailUseCase(private val characterDetailRepo: CharacterDetailRepository) {

    fun execute(characterId: String,callback:(LayerResult<CharacterEntity>?) -> Unit) {

        characterDetailRepo.fetchCharacterDetail(characterId){result ->

            try{
                when (result) {
                    is LayerResult.Success -> {

                        callback(LayerResult.Success(result.value))
                    }
                    is LayerResult.Error -> {
                        throw UIError(result.errorInfo)
                    }
                }
            }catch (e: UIError){
                callback(LayerResult.Error(e))
            }

        }
    }

}