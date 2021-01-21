package com.samr.data.repositories



import com.samr.core.utils.DomainError
import com.samr.core.utils.LayerResult
import com.samr.data.services.NetworkService
import com.samr.data.entities.CharacterDomain
import kotlinx.coroutines.runBlocking
import java.lang.Exception


open class DefaultCharacterRepo: CharactersRepository {

    private var service = NetworkService()

    override fun fetchCharactersList(
        offset: Int,
        callback: (LayerResult<List<CharacterDomain>>?) -> Unit) {

        //TODO: Should change to global scope,
        /*GlobalScope.launch(Dispatchers.IO)*/ runBlocking {

            service.fetchData(queryUrl = "") { result ->

                try{
                    when (result) {
                        is LayerResult.Success -> {
                            val characters = mapToDomain(result.value)
                            callback(LayerResult.Success(characters))
                        }
                        is LayerResult.Error -> {
                            //Raise error on domain
                            throw DomainError(result.errorInfo, DomainError.Type.DATA_LAYER_ERROR)
                        }
                    }
                }catch (de: DomainError){

                    callback(LayerResult.Error(de))
                }
            }
        }

    }

    private fun mapToDomain(result: ByteArray): List<CharacterDomain>{

        try{

            val res = result.toString(Charsets.UTF_8)
        }catch (e: Exception){

            throw DomainError(e, DomainError.Type.MAPPING_ERROR)
        }


        return listOf()
    }

}