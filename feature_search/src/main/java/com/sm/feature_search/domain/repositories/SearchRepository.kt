package com.sm.feature_search.domain.repositories

import com.sm.feature_search.domain.models.SearchDM

interface SearchRepository {
    fun fetchByName(input: String,
                    //offsetFactor: Int,
                    onCharactersReceived: (Result<List<SearchDM>>) -> Unit)
}