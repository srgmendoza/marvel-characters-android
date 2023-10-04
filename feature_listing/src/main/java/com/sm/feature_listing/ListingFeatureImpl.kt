package com.sm.feature_listing

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sm.feature_listing.presentation.compose_ui.MainListingUi
import com.sm.feature_listing_api.ListingFeatureApi

class ListingFeatureImpl: ListingFeatureApi {

    private val route = "listing"

    override fun listingRoute(): String {
        return route
    }

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route) {
            MainListingUi(navController)
        }
    }
}
