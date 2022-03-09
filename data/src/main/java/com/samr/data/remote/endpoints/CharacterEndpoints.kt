package com.samr.data.remote.endpoints

import com.samr.data.entities.CharactersRawResponse
import com.samr.data.utils.CHARACTERS_ENDPOINT
import com.samr.data.utils.DATA_LIMIT
import com.samr.data.utils.PUBLIC_KEY
import io.reactivex.Observable
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
        @Query("hash")hash: String
    ): Observable<CharactersRawResponse>

    @GET("$CHARACTERS_ENDPOINT/{character}")
    fun getCharacterDetail(
        @Path(value = "character")character: String,
        @Query("apikey")apiKey: String = PUBLIC_KEY,
        @Query("hash")hash: String,
        @Query("ts")ts: String,
        @Query("limit")limit: String = DATA_LIMIT
    ): Observable<CharactersRawResponse>
}
