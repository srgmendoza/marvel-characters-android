package com.samr.domain.usecases

import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.domain.entities.*
import com.samr.domain.repositories.CharactersRepository
import java.lang.Exception


class CharactersUseCase(private val characterRepo: CharactersRepository) {

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
                        throw UIError(result.errorInfo)
                    }
                }
            }catch (e: UIError){
                callback(LayerResult.Error(e))
            }

        }
    }


}