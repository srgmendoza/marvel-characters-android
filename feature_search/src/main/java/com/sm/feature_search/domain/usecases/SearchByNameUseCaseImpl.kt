package com.sm.feature_search.domain.usecases

import com.sm.feature_search.domain.models.SearchDM
import com.sm.feature_search.domain.repositories.SearchRepository

class SearchByNameUseCaseImpl(private val repo: SearchRepository): SearchByNameUseCase {
    override fun execute(input: String, onSearchResult: (Result<List<SearchDM>>) -> Unit) {
        repo.fetchByName(input) {searchResult ->
            searchResult.onSuccess {
                onSearchResult(Result.success(it))
            }
            searchResult.onFailure {
                onSearchResult(Result.failure(it))
            }
        }
    }
}