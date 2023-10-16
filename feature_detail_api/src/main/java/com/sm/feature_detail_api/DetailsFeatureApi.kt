package com.sm.feature_detail_api

import com.sm.core_navigation.CoreNavigation

interface DetailsFeatureApi: CoreNavigation {
    fun detailsRootRoute(): String
    override fun getFeatureMainRoute() = detailsRootRoute()

}