package com.sm.feature_listing.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sm.core_navigation.CoreNavigation
import com.sm.feature_listing.presentation.compose_ui.ErrorView
import com.sm.feature_listing.presentation.compose_ui.MainListingUi

internal object ListingFeatInternalNavImpl: CoreNavigation {

    private const val errorScreenRoute = "onError"

    fun errorScreenRoute(): String {
        return errorScreenRoute
    }
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(errorScreenRoute) {
            ErrorView(navController)
        }
    }
}