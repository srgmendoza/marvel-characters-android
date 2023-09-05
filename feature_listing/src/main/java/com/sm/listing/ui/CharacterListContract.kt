package com.sm.listing.ui

import com.sm.base_core.user_intents.UiEffect
import com.sm.base_core.user_intents.UiEvent
import com.sm.base_core.user_intents.UiState
import com.sm.listing.ui.models.Character

class CharacterListContract {

    sealed class Event: UiEvent {
        object OnLoadRequested: Event()
        class OnItemSelected(val itemId: String): Event()
    }

    sealed class CharacterListState {
        object Idle: CharacterListState()
        object Loading: CharacterListState()
        class Success(val characters: List<Character>): CharacterListState()
    }

    data class State(val state: CharacterListState): UiState

    sealed class Effect: UiEffect {
        object Error : Effect()
    }
}