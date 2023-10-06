package com.sm.feature_detail.presentation.composable_ui.screens.main

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.sm.core_navigation.getArgumentByKey
import com.sm.feature_detail.presentation.composable_ui.views.LoaderView
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainDetailsScreen(navController: NavHostController,
                      viewModel: DetailsScreenViewModel = koinViewModel()) {
    val id = navController.getArgumentByKey("id")

    id?.let {
        LaunchedEffect(Unit) {
            viewModel.setEvent(DetailsScreenContracts.Event.OnLoadRequested(it))
        }
    }

    when(val state = viewModel.uiState.collectAsState().value) {
        is DetailsScreenContracts.State.Idle -> {

        }
        is DetailsScreenContracts.State.Loading -> {
            LoaderView()
        }
        is DetailsScreenContracts.State.Success -> {
            Text(text = "Welcome to DetailsUi for character with id: ${state.character.id} and name ${state.character.name}")
        }
    }

}