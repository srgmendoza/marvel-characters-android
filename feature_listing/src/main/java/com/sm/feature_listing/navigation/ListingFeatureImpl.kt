package com.sm.feature_listing.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sm.feature_listing.presentation.compose_ui.screens.MainListingScreen
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
        //External navigation
        navGraphBuilder.composable(route) {
            MainListingScreen(navController)
        }

        //Internal navigation
        ListingFeatInternalNavImpl.registerGraph(
            navGraphBuilder = navGraphBuilder,
            navController = navController,
            modifier = modifier
        )
    }
}
