package com.sm.feature_detail.presentation.composable_ui.screens.main

import com.sm.base_core.user_intents.UiEffect
import com.sm.base_core.user_intents.UiEvent
import com.sm.base_core.user_intents.UiState
import com.sm.feature_detail.domain.models.CharacterDomain

class DetailsScreenContracts {

    sealed interface Event: UiEvent {
        class OnLoadRequested(val id: String): Event
    }

    sealed interface State: UiState {
        data object Idle: State
        data object Loading: State
        class Success(val character: CharacterDomain): State
    }

    sealed interface Effect: UiEffect {
        data object Error : Effect
        data object NA : Effect
    }
}