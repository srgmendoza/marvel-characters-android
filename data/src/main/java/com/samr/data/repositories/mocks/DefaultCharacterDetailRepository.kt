package com.samr.data.repositories.mocks

import android.net.Uri
import com.google.gson.Gson
import com.samr.core.utils.DomainError
import com.samr.core.utils.LayerResult
import com.samr.core.utils.Utils.md5
import com.samr.core.utils.Utils.toHex
import com.samr.data.PRIVATE_KEY
import com.samr.data.PUBLIC_KEY
import com.samr.data.entities.CharacterData
import com.samr.data.entities.CharactersRawResponse
import com.samr.data.repositories.CharacterDetailRepository
import com.samr.data.services.NetworkService
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class DefaultCharacterDetailRepository: CharacterDetailRepository {

    private var service = NetworkService()

    override fun fetchCharacterDetail(characterId: String,
                                      callback: (LayerResult<CharacterData>?) -> Unit) {

        //TODO: Should change to global scope,
        /*GlobalScope.launch(Dispatchers.IO)*/ runBlocking {

            val timestamp = (System.currentTimeMillis()/1000).toString()
            val hash =  md5(timestamp + PRIVATE_KEY + PUBLIC_KEY).toHex()

            val builder = Uri.Builder()
            builder.scheme("https")
                .authority("gateway.marvel.com")
                .appendPath("v1")
                .appendPath("public")
                .appendPath("characters")
                .appendPath(characterId)
                .appendQueryParameter("ts",timestamp)
                .appendQueryParameter("apikey", PUBLIC_KEY)
                .appendQueryParameter("hash", hash)

            val url = builder.build().toString()


            service.fetchData(queryUrl = url) { result ->

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

    private fun mapToDomain(result: ByteArray): CharacterData{

        try{

            val res = result.toString(Charsets.UTF_8)

            val data = Gson().fromJson(res, CharactersRawResponse::class.java)

            return data.data.results[0]

        }catch (e: Exception){

            throw DomainError(e, DomainError.Type.MAPPING_ERROR)
        }

    }
}