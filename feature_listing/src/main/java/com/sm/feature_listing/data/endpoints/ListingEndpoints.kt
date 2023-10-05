package com.sm.feature_listing.data.endpoints

import com.sm.feature_listing.data.entities.CharactersRawResponse
import com.sm.feature_listing.data.repo.CHARACTERS_ENDPOINT
import com.sm.feature_listing.data.repo.DATA_LIMIT
import com.sm.feature_listing.data.repo.PUBLIC_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ListingEndpoints {

    @GET(CHARACTERS_ENDPOINT)
    suspend fun getCharacters(
        @Query("limit")limit: String = DATA_LIMIT,
        @Query("offset")offset: String,
        @Query("ts")ts: String,
        @Query("apikey")apiKey: String = PUBLIC_KEY,
        @Query("hash")hash: String
    ): CharactersRawResponse
}