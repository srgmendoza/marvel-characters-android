package com.samr.marvelcharacterswiki.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sm.core_navigation.CoreNavigation
import com.sm.core_navigation.models.NavigationConfig
import com.sm.feature_detail_api.DetailsFeatureApi
import com.sm.feature_listing_api.ListingFeatureApi
import com.sm.feature_search_api.SearchFeatureApi
import org.koin.java.KoinJavaComponent

@Composable
fun MainNavigationUi(
    navController: NavHostController,
    navConfig:  NavigationConfig,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = navConfig.startDestinationRoute,
        modifier = modifier
    ) {
        navConfig.featuresConfig.forEach {destination ->
            register(
                featureNavigation = destination.navInstance,
                navController = navController,
                modifier = modifier
            )
        }
    }
}

fun NavGraphBuilder.register(
    featureNavigation: CoreNavigation,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    featureNavigation.registerGraph(
        navGraphBuilder = this,
        navController = navController,
        modifier = modifier
    )
}