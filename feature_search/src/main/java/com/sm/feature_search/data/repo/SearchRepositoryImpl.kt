package com.sm.feature_search.data.repo

import com.sm.feature_search.data.api.SearchEndpoint
import com.sm.feature_search.domain.repositories.SearchRepository

class SearchRepositoryImpl(private val searchEndpoint: SearchEndpoint): SearchRepository {
}