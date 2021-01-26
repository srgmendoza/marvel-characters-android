package com.samr.data.repositories


import com.google.gson.Gson
import com.samr.core.utils.DomainError
import com.samr.core.utils.LayerResult
import com.samr.data.entities.CharacterData
import com.samr.data.entities.CharactersRawResponse
import com.samr.data.services.CharacterService
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.repositories.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.text.Charsets.UTF_8


class DefaultCharacterRepo: CharactersRepository {

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

                            throw DomainError(result.errorInfo, DomainError.Type.DATA_LAYER_ERROR)
                        }
                    }
                }catch (de: DomainError){

                    callback(LayerResult.Error(de))
                }
            }
        }

    }

    private fun ByteArray.toCharacterData(): List<CharacterData> {

        val res = this.toString(UTF_8)
        val data = Gson().fromJson(res,CharactersRawResponse::class.java)

        return data.data.results
    }

}