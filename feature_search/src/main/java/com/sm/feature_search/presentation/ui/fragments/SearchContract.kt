package com.sm.feature_search.presentation.ui.fragments

import com.sm.base_core.user_intents.UiEffect
import com.sm.base_core.user_intents.UiEvent
import com.sm.base_core.user_intents.UiState
import com.sm.feature_search.presentation.models.SearchCharacter
import com.sm.feature_search.presentation.models.RandomCharacter

class SearchContract {

    sealed interface Event: UiEvent {
        object OnLoadRequested: Event
        class OnItemSelected(val itemId: String): Event
        class OnSearchByName(val input: String): Event
    }

    sealed interface SearchState {
        object Idle: SearchState
        sealed interface Loading: SearchState {
            object Transparent: Loading
            object Opaque: Loading
        }
        object Empty: SearchState
        sealed interface Success: SearchState {
            class RandomResult(val results: List<RandomCharacter>): Success
            class SearchResult(val results: List<SearchCharacter>): Success
        }
    }

    data class State(val state: SearchState): UiState

    sealed interface Effect: UiEffect {
        object Error: Effect
    }


}