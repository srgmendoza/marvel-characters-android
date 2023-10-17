package com.sm.feature_detail.presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sm.core_navigation.CoreNavigation
import com.sm.feature_detail.R
import com.sm.feature_detail.presentation.composable_ui.screens.testScreen.TestScreen

internal object DetailsFeatInternalNavImpl: CoreNavigation {
    private const val testNavScreenRoute = "detailsTest/{showAppBar}"

    fun testScreenRoute() = testNavScreenRoute
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(testNavScreenRoute) {
            TestScreen()
        }
    }
}