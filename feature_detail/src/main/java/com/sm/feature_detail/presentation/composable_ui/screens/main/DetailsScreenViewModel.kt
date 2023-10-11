package com.sm.feature_detail.presentation.composable_ui.screens.main

import androidx.lifecycle.viewModelScope
import com.sm.base_core.BaseViewModel
import com.sm.feature_detail.domain.usecases.GetCharacterDetailByIdUseCase
import com.sm.feature_detail.presentation.mappers.PresentationMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailsScreenViewModel(private val useCase: GetCharacterDetailByIdUseCase) :
    BaseViewModel<DetailsScreenContracts.Event, DetailsScreenContracts.State, DetailsScreenContracts.Effect>() {
    override fun createInitialState(): DetailsScreenContracts.State {
        return DetailsScreenContracts.State.Idle
    }

    override fun handleEvent(event: DetailsScreenContracts.Event) {
        when(event) {
            is DetailsScreenContracts.Event.OnLoadRequested -> {
                setState { DetailsScreenContracts.State.Loading }
                getCharacterById(event.id)
            }
        }
    }


    private fun getCharacterById(id: String) {
        viewModelScope.launch {
            val result = useCase.execute(id)
            setState {
                if (result != null) {
                    DetailsScreenContracts.State.Success(
                        PresentationMapper().mapTo(result)
                    )
                } else {
                    DetailsScreenContracts.State.Idle
                }
            }

        }
    }

}