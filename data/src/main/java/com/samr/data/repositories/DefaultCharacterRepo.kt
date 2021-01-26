package com.samr.data.repositories



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
import com.samr.data.services.NetworkService
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.repositories.CharactersRepository
import kotlinx.coroutines.runBlocking
import kotlin.text.Charsets.UTF_8


class DefaultCharacterRepo: CharactersRepository {

    private var service = NetworkService()

    override fun fetchCharactersList(
        offsetFactor: Int,
        callback: (LayerResult<List<CharacterEntity>>?) -> Unit) {

        //TODO: Should change to global scope,
        /*GlobalScope.launch(Dispatchers.IO)*/ runBlocking {

            val offset = if(offsetFactor == 0) 40 else offsetFactor * 40

            val timestamp = (System.currentTimeMillis()/1000).toString()
            val hash =  md5(timestamp + PRIVATE_KEY + PUBLIC_KEY).toHex()



            val builder = Uri.Builder()
            builder.scheme("https")
                .authority("gateway.marvel.com")
                .appendPath("v1")
                .appendPath("public")
                .appendPath("characters")
                .appendQueryParameter("limit","40")
                .appendQueryParameter("offset",offset.toString())
                .appendQueryParameter("ts",timestamp)
                .appendQueryParameter("apikey", PUBLIC_KEY)
                .appendQueryParameter("hash", hash)

            val url = builder.build().toString()


            service.fetchData(queryUrl = url) { result ->

                try{
                    when (result) {
                        is LayerResult.Success -> {
                            val characters: List<CharacterEntity> = result.value.toCharacterData().map {
                                it.mapDataToEntity()
                            }
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

    private fun ByteArray.toCharacterData(): List<CharacterData> {

        val res = this.toString(UTF_8)
        val data = Gson().fromJson(res,CharactersRawResponse::class.java)

        return data.data.results
    }

}