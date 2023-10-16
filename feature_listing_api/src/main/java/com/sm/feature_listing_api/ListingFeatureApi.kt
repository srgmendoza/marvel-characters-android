package com.sm.feature_listing_api

import com.sm.core_navigation.CoreNavigation

interface ListingFeatureApi: CoreNavigation {

    fun listingRoute(): String

    override fun getFeatureMainRoute() = listingRoute()
}