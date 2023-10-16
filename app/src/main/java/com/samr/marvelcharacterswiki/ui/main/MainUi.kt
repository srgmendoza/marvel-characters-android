package com.samr.marvelcharacterswiki.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.samr.marvelcharacterswiki.ui.compose_views.AppBar
import com.samr.marvelcharacterswiki.ui.compose_views.BottomBar
import com.samr.marvelcharacterswiki.ui.models.BottomTabs
import com.samr.marvelcharacterswiki.ui.theme.AppTheme
import com.samr.marvelcharacterswiki.ui.theme.ThemeStore
import com.sm.core_navigation.models.FeatureNavConfig
import com.sm.core_navigation.models.NavigationConfig
import com.sm.feature_detail_api.DetailsFeatureApi
import com.sm.feature_listing_api.ListingFeatureApi
import com.sm.feature_search_api.SearchFeatureApi
import org.koin.java.KoinJavaComponent

@Composable
fun MainUi(themeStore: ThemeStore) {

    val theme by themeStore.getTheme().collectAsState(initial = 0) //Initialize with default theme 0
    val systemUiController = rememberSystemUiController()
    val navController = rememberNavController()

    val appBarState = rememberSaveable {
        mutableStateOf(false)
    }

    val navConfig = remember { getNavConfig() }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    appBarState.value = navBackStackEntry?.arguments?.getString("showAppBar") == "true"

    AppTheme(themeId = theme) {
        val statusBarColor = MaterialTheme.colors.primaryVariant
        val tabs = remember { BottomTabs.values() }

        SideEffect {
            systemUiController.setSystemBarsColor(
                color = statusBarColor,
                darkIcons = false
            )
        }

        Scaffold(
            topBar = {
                AppBar(appBarState) {
                    navController.popBackStack()
                }
            },
            bottomBar = { BottomBar(navController = navController, tabs) }
        ) { innerPadding ->
            MainNavigationUi(
                navController = navController,
                navConfig = navConfig,
                modifier = Modifier.padding(innerPadding)
            )
        }

    }
}

/**
 * IMPORTANT. Add here all the features navigation apis possibilities*/
private fun getNavConfig(): NavigationConfig {
    val listingFeatNavigation: ListingFeatureApi by KoinJavaComponent.inject(
        ListingFeatureApi::class.java
    )
    val searchFeatNavigation: SearchFeatureApi by KoinJavaComponent.inject(
        SearchFeatureApi::class.java
    )
    val detailsFeatNavigation: DetailsFeatureApi by KoinJavaComponent.inject(
        DetailsFeatureApi::class.java
    )

    val destinations = listOf(
        listingFeatNavigation,
        searchFeatNavigation,
        detailsFeatNavigation
    )

    val config = NavigationConfig(
        startDestinationRoute = listingFeatNavigation.listingRoute(),
        featuresConfig = destinations.map {
            FeatureNavConfig(
                navInstance = it
            )
        }
    )

    return config
}
