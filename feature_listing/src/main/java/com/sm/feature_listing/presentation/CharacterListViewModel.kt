package com.sm.feature_listing.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.sm.base_core.BaseViewModel
import com.sm.feature_listing.domain.usecases.GetPagedCharactersListUseCase
import com.sm.feature_listing.presentation.models.Images
import com.sm.feature_listing.presentation.models.ListedCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onErrorReturn
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val characterUseCase: GetPagedCharactersListUseCase
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
            characterUseCase.execute()
                .map {
                    it.map { c ->
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
                }
                .cachedIn(viewModelScope)
                .collect {
                    setState {
                        copy(
                            state = CharacterListContract.CharacterListState.Success(
                                MutableStateFlow(it)
                            )
                        )
                    }
                }
        }
    }


}