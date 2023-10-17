package com.sm.feature_search_api

import com.sm.core_navigation.CoreNavigation

interface SearchFeatureApi: CoreNavigation {
    fun searchRoute(): String
}