package com.sm.feature_detail

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sm.feature_detail.presentation.composable_ui.screens.main.MainDetailsScreen
import com.sm.feature_detail.presentation.navigation.DetailsFeatInternalNavImpl
import com.sm.feature_detail_api.DetailsFeatureApi

class DetailFeatureImpl: DetailsFeatureApi {
    
    private val route = "details/{showAppBar}/{id}"
    override fun detailsRootRoute(): String {
        return route
    }



    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = route) {
            MainDetailsScreen(navController = navController)
        }

        DetailsFeatInternalNavImpl.registerGraph(
            navGraphBuilder = navGraphBuilder,
            navController = navController,
            modifier = modifier
        )
    }
}