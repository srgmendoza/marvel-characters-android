package com.sm.feature_listing.presentation

import androidx.lifecycle.viewModelScope
import com.sm.base_core.BaseViewModel
import com.sm.feature_listing.domain.usecases.CharacterListUsecase
import com.sm.feature_listing.presentation.models.ListedCharacter
import com.sm.feature_listing.presentation.models.Images
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
                    val listedCharacters = it.map { c ->
                        ListedCharacter(
                            id = c.id,
                            name = c.name,
                            description = c.description,
                            thumbnail = Images(
                                thumbnail = c.thumbnail.thumbnail,
                                poster = c.thumbnail.poster
                            )
                        )
                    }
                    setState {
                        copy(
                            state = CharacterListContract.CharacterListState.Success(
                                listedCharacters
                            )
                        )
                    }
                }
                listResult.onFailure {
                    setEffect { CharacterListContract.Effect.Error }
                    setState {
                        copy(
                            state = CharacterListContract.CharacterListState.Idle
                        )
                    }
                }
            }
        }
    }


}