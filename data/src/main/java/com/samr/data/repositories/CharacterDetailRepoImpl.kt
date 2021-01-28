package com.samr.data.repositories


import com.samr.core.utils.CustomError
import com.samr.core.utils.DomainError
import com.samr.core.utils.LayerResult
import com.samr.data.services.CharacterService
import com.samr.domain.repositories.CharacterDetailRepo
import com.samr.domain.entities.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CharacterDetailRepoImpl: CharacterDetailRepo {

    private var service = CharacterService()

    override fun fetchCharacterDetail(characterId: String,
                                      callback: (LayerResult<CharacterEntity>?) -> Unit) {


        GlobalScope.launch(Dispatchers.IO) {

            service.fetchCharacterDetail(characterId) { result ->

                try{
                    when (result) {
                        is LayerResult.Success -> {

                            val character: CharacterEntity = result.value.mapToData()[0].mapDataToEntity()
                            callback(LayerResult.Success(character))
                        }
                        is LayerResult.Error -> {

                            throw CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
                                underLyingError = result.error)
                        }
                    }
                }catch (e: Exception){

                    callback(LayerResult.Error(e))
                }
            }
        }
    }

}