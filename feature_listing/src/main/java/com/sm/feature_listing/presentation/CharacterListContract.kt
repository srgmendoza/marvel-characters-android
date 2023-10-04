package com.sm.feature_listing.presentation

import com.sm.base_core.user_intents.UiEffect
import com.sm.base_core.user_intents.UiEvent
import com.sm.base_core.user_intents.UiState
import com.sm.feature_listing.presentation.models.ListedCharacter

class CharacterListContract {

    sealed class Event: UiEvent {
        data object OnLoadRequested: Event()
        class OnItemSelected(val itemId: String): Event()
    }

    sealed class CharacterListState {
        data object Idle: CharacterListState()
        data object Loading: CharacterListState()
        class Success(val listedCharacters: List<ListedCharacter>): CharacterListState()
    }

    data class State(val state: CharacterListState): UiState

    sealed class Effect: UiEffect {
        data object Error : Effect()
        data object NA : Effect()
    }
}