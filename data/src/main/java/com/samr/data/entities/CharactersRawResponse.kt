package com.samr.data.entities

data class CharactersRawResponse(
    val data: ResultsRawResponse
)

data class ResultsRawResponse(
    val results: List<CharacterData>
)
