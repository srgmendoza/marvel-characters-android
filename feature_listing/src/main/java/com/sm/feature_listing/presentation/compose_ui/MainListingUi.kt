package com.sm.feature_listing.presentation.compose_ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.sm.feature_detail_api.DetailsFeatureApi
import com.sm.feature_listing.navigation.ListingFeatInternalNavImpl
import com.sm.feature_listing.presentation.CharacterListContract
import com.sm.feature_listing.presentation.CharacterListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent


@Composable
fun MainListingUi(
    navController: NavHostController,
    viewModel: CharacterListViewModel = koinViewModel()
) {

    val detailsFeatNavigation: DetailsFeatureApi by KoinJavaComponent.inject(
        DetailsFeatureApi::class.java
    )

    LaunchedEffect(Unit) {
        viewModel.setEvent(CharacterListContract.Event.OnLoadRequested)
    }

    val state = viewModel.uiState.collectAsState().value.state
    val effect = viewModel.effect.collectAsState(CharacterListContract.Effect.NA).value

    when (state) {
        CharacterListContract.CharacterListState.Idle -> {

        }

        is CharacterListContract.CharacterListState.Loading -> {
            LoaderView()
        }

        is CharacterListContract.CharacterListState.Success -> {
            ListView(
                characters = state.listedCharacters,
                onClick = {
                    Log.d("MainListing", it.thumbnail.poster)
                    val route = detailsFeatNavigation.detailsRoute().replace("{id}", "${it.id}")
                    navController.navigate(route)
                },
                onError = {
                    navController.navigate(ListingFeatInternalNavImpl.errorScreenRoute())
                }
            )
        }
    }

    when (effect) {
        is CharacterListContract.Effect.Error -> {
            navController.navigate(ListingFeatInternalNavImpl.errorScreenRoute())
        }

        else -> {
            //Nothing to do
        }
    }
}