package com.sm.feature_listing.presentation.compose_ui

import android.util.Log
import android.widget.Toast

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.sm.feature_listing.presentation.CharacterListContract
import com.sm.feature_listing.presentation.CharacterListViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainListingUi(navController: NavHostController,
                  viewModel : CharacterListViewModel = koinViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.setEvent(CharacterListContract.Event.OnLoadRequested)
    }

    val state = viewModel.uiState.collectAsState().value.state
    Toast.makeText(LocalContext.current, state.javaClass.simpleName.toString(), Toast.LENGTH_SHORT).show()

    val effect = viewModel.effect.collectAsState(CharacterListContract.Effect.NA).value

    when (state) {
        CharacterListContract.CharacterListState.Idle -> {

        }

        is CharacterListContract.CharacterListState.Loading -> {
            LoaderView()
        }

        is CharacterListContract.CharacterListState.Success -> {
            ListView(characters = state.listedCharacters) {
                Log.d("MainListing", it.thumbnail.poster)
            }
        }
    }

    when(effect) {
        is CharacterListContract.Effect.Error -> {
            ErrorView {
                viewModel.setEvent(CharacterListContract.Event.OnLoadRequested)
            }
        }
        else -> {
            //Nothing to do
        }
    }
}