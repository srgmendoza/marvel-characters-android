package com.sm.feature_search.data.entities

data class SearchRawResponse(
    val data: ResultsRawResponse
) {
    fun mapToData() = this.data.results
}

data class ResultsRawResponse(
    val results: List<CharacterSearchEntity>
)
