package com.samr.marvelcharacterswiki.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
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
import com.samr.marvelcharacterswiki.ui.theme.ThemeStore
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.samr.marvelcharacterswiki.ui.compose_views.AppBar
import com.samr.marvelcharacterswiki.ui.compose_views.BottomBar
import com.samr.marvelcharacterswiki.ui.models.BottomTabs
import com.samr.marvelcharacterswiki.ui.theme.AppTheme
import com.sm.core_navigation.CoreNavigation
import com.sm.core_navigation.models.NavigationConfig
import com.sm.feature_detail_api.DetailsFeatureApi
import com.sm.feature_listing_api.ListingFeatureApi
import com.sm.feature_search_api.SearchFeatureApi
import org.koin.java.KoinJavaComponent

private const val SHOW_APP_BAR = true
private const val NO_SHOW_APP_BAR = false

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

    //Find destination in navConfig to know if should show app bar
    val selectedNav = navConfig.destinations.firstOrNull() { d ->
        navBackStackEntry?.destination?.route?.contains(d.second.getRoute()) ?: false
    }
    appBarState.value = selectedNav?.first ?: false

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
        NO_SHOW_APP_BAR to listingFeatNavigation,
        SHOW_APP_BAR to searchFeatNavigation,
        SHOW_APP_BAR to detailsFeatNavigation
    )

    return NavigationConfig(
        startDestinationRoute = listingFeatNavigation.listingRoute(),
        destinations = destinations
    )
}
