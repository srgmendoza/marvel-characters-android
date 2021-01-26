package com.samr.data.services

import com.samr.core.utils.CHARACTERS_ENDPOINT
import com.samr.core.utils.DATA_LIMIT
import com.samr.core.utils.PUBLIC_KEY
import com.samr.data.entities.CharactersRawResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterEndpoints {

    @GET(CHARACTERS_ENDPOINT)
    fun getCharacters(
            @Query("limit")limit: String = DATA_LIMIT,
            @Query("offset")offset: String,
            @Query("ts")ts: String,
            @Query("apikey")apiKey: String = PUBLIC_KEY,
            @Query("hash")hash: String)
    : Deferred<CharactersRawResponse>

    @GET("$CHARACTERS_ENDPOINT/{character}")
    fun getCharacterDetail(@Path(value = "character")character: String,
                           @Query("apikey")apiKey: String = PUBLIC_KEY,
                           @Query("hash")hash: String,
                           @Query("ts")ts: String,
                           @Query("limit")limit: String = DATA_LIMIT)
            : Deferred<CharactersRawResponse>

}