package com.sm.feature_search.domain.usecases

import com.sm.feature_search.domain.models.SearchDM

interface SearchByNameUseCase {
    fun execute(query: String, onSearchResult: (Result<List<SearchDM>>) -> Unit)
}