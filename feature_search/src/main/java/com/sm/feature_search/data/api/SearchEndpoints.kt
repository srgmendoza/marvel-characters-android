package com.sm.feature_search.data.api

import com.sm.feature_search.data.entities.RawResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

private const val SEARCH_ENDPOINT = "characters"

const val DATA_LIMIT = "40"

const val PRIVATE_KEY = "342f2e0f995a9cffd4cda17b1b9e1f61e6c55da0"
const val PUBLIC_KEY = "9e37537e8b60f83fc1cb829de89cccb8"
const val TIMESTAMP = "timestamp"
const val HASH = "hash"
interface SearchEndpoints {

    @GET(SEARCH_ENDPOINT)
    fun searchCharactersByName(
        @Query("name")name: String,
        @Query("limit")limit: String = DATA_LIMIT,
        //@Query("offset")offset: String,
        @Query("ts")ts: String,
        @Query("apikey")apiKey: String = PUBLIC_KEY,
        @Query("hash")hash: String
    ): Observable<RawResponse>

    @GET(SEARCH_ENDPOINT)
    fun getCharacters(
        @Query("limit")limit: String = "4",
        //@Query("offset")offset: String,
        @Query("ts")ts: String,
        @Query("apikey")apiKey: String = PUBLIC_KEY,
        @Query("hash")hash: String
    ): Observable<RawResponse>
}