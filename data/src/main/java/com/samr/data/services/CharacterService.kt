package com.samr.data.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.samr.core.utils.*
import com.samr.core.utils.Utils.HASH
import com.samr.core.utils.Utils.TIMESTAMP
import com.samr.core.utils.Utils.getOffset
import com.samr.core.utils.Utils.getTimeStampPlusHash
import com.samr.data.entities.CharactersRawResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class CharacterService() {

    private var restEndpoints: CharacterEndpoints

    init {
        val retrofit =  Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_CHARACTERS_URL)
            .build()

        restEndpoints = retrofit.create(CharacterEndpoints::class.java)
    }


    suspend fun fetchCharactersList(offsetFactor: Int, callback: (LayerResult<CharactersRawResponse>?) -> Unit){


        val timeStampPlusHash = getTimeStampPlusHash()


        withContext(Dispatchers.IO){


            try{
                val result = restEndpoints.getCharacters(
                        offset = offsetFactor.getOffset(),
                        hash = timeStampPlusHash[HASH] ?: "",
                        ts = timeStampPlusHash[TIMESTAMP].toString()
                ).await()

                callback(LayerResult.Success(result))

            }catch (e: HttpException){

                callback(LayerResult.Error(DataError(errorCode = e.code())))
            }
            catch (e: Exception){

                callback(LayerResult.Error(e))
            }

        }

    }

    suspend fun fetchCharacterDetail(characterId: String, callback: (LayerResult<CharactersRawResponse>?) -> Unit){


        val timeStampPlusHash = getTimeStampPlusHash()


        withContext(Dispatchers.IO){


            try{
                val result = restEndpoints.getCharacterDetail(
                        hash = timeStampPlusHash[HASH] ?: "",
                        ts = timeStampPlusHash[TIMESTAMP].toString(),
                        character = characterId
                ).await()

                callback(LayerResult.Success(result))

            }catch (e: Exception){

                callback(LayerResult.Error(e))
            }

        }

    }
}

