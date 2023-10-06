package com.sm.feature_search

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sm.feature_search.presentation.compose_ui.MainSearchUi
import com.sm.feature_search_api.SearchFeatureApi

class SearchFeatureImpl: SearchFeatureApi {

    private val route = "search"
    override fun searchRoute(): String {
        return route
    }

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route) {
            MainSearchUi(navController)
        }
    }
}