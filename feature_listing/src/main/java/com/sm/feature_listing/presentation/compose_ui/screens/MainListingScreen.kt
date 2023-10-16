package com.sm.feature_listing.presentation.compose_ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.sm.feature_detail_api.DetailsFeatureApi
import com.sm.feature_listing.navigation.ListingFeatInternalNavImpl
import com.sm.feature_listing.presentation.CharacterListContract
import com.sm.feature_listing.presentation.CharacterListViewModel
import com.sm.feature_listing.presentation.compose_ui.views.ListView
import com.sm.feature_listing.presentation.compose_ui.views.LoaderView
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent


@Composable
fun MainListingScreen(
    navController: NavHostController,
    viewModel: CharacterListViewModel = koinViewModel()
) {

    val detailsFeatNavigation: DetailsFeatureApi by KoinJavaComponent.inject(
        DetailsFeatureApi::class.java
    )

    when (val state = viewModel.uiState.collectAsState().value.state) {
        CharacterListContract.CharacterListState.Idle -> {

        }

        CharacterListContract.CharacterListState.Loading -> {
            LoaderView()
        }

        is CharacterListContract.CharacterListState.Success -> {
            ListView(
                characters = state.listedCharacters,
                onClick = {
                    val route = detailsFeatNavigation.detailsRootRoute()
                        .replace("{showAppBar}", "true")
                        .replace("{id}", "${it.id}")
                    navController.navigate(route)
                },
                onError = {
                    navController.navigate(ListingFeatInternalNavImpl.errorScreenRoute())
                }
            )
        }
    }
}