package com.sm.listing.data

import com.sm.listing.data.entities.CharactersRawResponse
import com.sm.listing.data.repo.CHARACTERS_ENDPOINT
import com.sm.listing.data.repo.DATA_LIMIT
import com.sm.listing.data.repo.PUBLIC_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ListingEndpoints {

    @GET(CHARACTERS_ENDPOINT)
    fun getCharacters(
        @Query("limit")limit: String = DATA_LIMIT,
        @Query("offset")offset: String,
        @Query("ts")ts: String,
        @Query("apikey")apiKey: String = PUBLIC_KEY,
        @Query("hash")hash: String
    ): Observable<CharactersRawResponse>
}