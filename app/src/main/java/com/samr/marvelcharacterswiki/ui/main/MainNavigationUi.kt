package com.samr.marvelcharacterswiki.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sm.core_navigation.CoreNavigation
import com.sm.feature_listing.ListingFeatureImpl
import com.sm.feature_listing_api.ListingFeatureApi
import org.koin.java.KoinJavaComponent

@Composable
fun MainNavigationUi(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val listingFeatNavigation: ListingFeatureApi by KoinJavaComponent.inject(
        ListingFeatureApi::class.java
    )

    NavHost(
        navController = navController,
        startDestination = listingFeatNavigation.listingRoute(),
        modifier = modifier
    ) {
        register(
            featureNavigation = listingFeatNavigation,
            navController = navController,
            modifier = modifier
        )
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