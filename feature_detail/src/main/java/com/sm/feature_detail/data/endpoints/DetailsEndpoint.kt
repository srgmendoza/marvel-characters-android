package com.sm.feature_detail.data.endpoints

import com.sm.feature_detail.data.entities.CharactersRawResponse
import com.sm.feature_detail.data.repo.DATA_LIMIT
import com.sm.feature_detail.data.repo.PUBLIC_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val CHARACTERS_ENDPOINT = "characters"
interface DetailsEndpoint {
    @GET("$CHARACTERS_ENDPOINT/{characterId}")
    suspend fun getCharacterDetailsById (
        @Path(value = "characterId")character: String,
        @Query("apikey")apiKey: String = PUBLIC_KEY,
        @Query("hash")hash: String,
        @Query("ts")ts: String,
        @Query("limit")limit: String = DATA_LIMIT
    ): CharactersRawResponse

}