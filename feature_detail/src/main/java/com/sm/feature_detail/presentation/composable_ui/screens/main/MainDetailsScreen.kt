package com.sm.feature_detail.presentation.composable_ui.screens.main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.sm.core_navigation.getArgumentByKey
import com.sm.feature_detail.presentation.composable_ui.views.DetailsView
import com.sm.feature_detail.presentation.composable_ui.views.ShimmerView
import com.sm.feature_detail.presentation.navigation.DetailsFeatInternalNavImpl
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainDetailsScreen(
    navController: NavHostController,
    viewModel: DetailsScreenViewModel = koinViewModel()
) {
    val id = navController.getArgumentByKey("id")

    id?.let {
        LaunchedEffect(Unit) {
            viewModel.setEvent(DetailsScreenContracts.Event.OnLoadRequested(it))
        }
    }

    when (val state = viewModel.uiState.collectAsState().value) {
        is DetailsScreenContracts.State.Idle -> {

        }

        is DetailsScreenContracts.State.Loading -> {
            ShimmerView()
        }

        is DetailsScreenContracts.State.Success -> {
            DetailsView(item = state.character,
                onSeeMoreClick = {
                    Log.d("DetailsView", "SeeMore from : $it")
                },
                onGotoTestClick = {
                    val route = DetailsFeatInternalNavImpl.testScreenRoute().replace("{showAppBar}", "true")
                    navController.navigate(route)
                }
            )
        }
    }

}