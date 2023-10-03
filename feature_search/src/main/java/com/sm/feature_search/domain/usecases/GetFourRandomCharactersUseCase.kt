package com.sm.feature_search.domain.usecases

import com.sm.feature_search.domain.models.SearchDM

interface GetFourRandomCharactersUseCase {
    fun execute(onSearchResult: (Result<List<SearchDM>>) -> Unit)
}