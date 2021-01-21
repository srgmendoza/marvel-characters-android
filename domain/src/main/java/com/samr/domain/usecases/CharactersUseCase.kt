package com.samr.domain.usecases

import com.samr.core.utils.DomainError
import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.data.entities.CharacterDomain
import com.samr.domain.entities.CharacterUI
import com.samr.data.repositories.CharactersRepository
import com.samr.data.repositories.DefaultCharacterRepo
import java.lang.Exception


class CharactersUseCase {

    private var characterRepo: CharactersRepository = DefaultCharacterRepo()
    private var offset = 0

    fun execute(callback:(LayerResult<List<CharacterUI>>?) -> Unit) {

        characterRepo.fetchCharactersList(offset){result ->

            try{
                when (result) {
                    is LayerResult.Success -> {
                        callback(LayerResult.Success(mapEntityToUi(result.value)))
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

    private fun mapEntityToUi(dataEntity: List<CharacterDomain>): List<CharacterUI>{

        try {

            return dataEntity.map {
                CharacterUI(
                    id = it.id,
                    name = it.name)
            }

        }catch (e: Exception){

            throw UIError(e)
        }

    }
}