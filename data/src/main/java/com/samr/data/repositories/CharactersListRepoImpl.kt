package com.samr.data.repositories


import com.google.gson.Gson
import com.samr.core.utils.CustomError
import com.samr.core.utils.DomainError
import com.samr.core.utils.LayerResult
import com.samr.data.entities.CharacterData
import com.samr.data.entities.CharactersRawResponse
import com.samr.data.services.CharacterService
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.repositories.CharactersListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.text.Charsets.UTF_8


class CharactersListRepoImpl: CharactersListRepo {

    private var service = CharacterService()

    override fun fetchCharactersList(
        offsetFactor: Int,
        callback: (LayerResult<List<CharacterEntity>>?) -> Unit) {

        GlobalScope.launch(Dispatchers.IO) {

            service.fetchCharactersList(offsetFactor) { result ->

                try{
                    when (result) {
                        is LayerResult.Success -> {
                            val characters: List<CharacterEntity> = result.value.mapToData().map {
                                it.mapDataToEntity()
                            }
                            callback(LayerResult.Success(characters))
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