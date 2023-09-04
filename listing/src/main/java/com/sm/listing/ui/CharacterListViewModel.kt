package com.sm.listing.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samr.domain.models.CustomErrorQ
import com.sm.base_core.BaseViewModel
import com.sm.listing.domain.usecases.CharacterListUsecase
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val characterUseCase: CharacterListUsecase
) : BaseViewModel<CharacterListContract.Event, CharacterListContract.State, CharacterListContract.Effect>() {

    override fun createInitialState(): CharacterListContract.State {
        return CharacterListContract.State(CharacterListContract.CharacterListState.Idle)
    }

    override fun handleEvent(event: CharacterListContract.Event) {
        when (event) {
            is CharacterListContract.Event.OnLoadRequested -> {
                setState { copy(state = CharacterListContract.CharacterListState.Loading) }
                getCharacters()
            }

            is CharacterListContract.Event.OnItemSelected -> {
                TODO("Implement OnItemSelected flow")
            }
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            characterUseCase.execute { listResult ->
                listResult.onSuccess {
                    setState { copy(state = CharacterListContract.CharacterListState.Success(listOf())) }
                }
                listResult.onFailure {
                    setEffect { CharacterListContract.Effect.Error }
                }
            }
        }
    }


}