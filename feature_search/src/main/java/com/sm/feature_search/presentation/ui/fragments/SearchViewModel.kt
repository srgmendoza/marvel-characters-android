package com.sm.feature_search.presentation.ui.fragments

import androidx.lifecycle.viewModelScope
import com.sm.base_core.BaseViewModel
import com.sm.feature_search.domain.usecases.GetFourRandomCharactersUseCase
import com.sm.feature_search.domain.usecases.SearchByNameUseCase
import com.sm.feature_search.presentation.models.SearchCharacter
import com.sm.feature_search.presentation.models.RandomCharacter
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchByNameUseCase: SearchByNameUseCase,
    private val getFourRandomCharactersUseCase: GetFourRandomCharactersUseCase
) :
    BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {
    override fun createInitialState(): SearchContract.State {
        return SearchContract.State(SearchContract.SearchState.Idle)
    }

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnLoadRequested -> {
                setState { copy(state = SearchContract.SearchState.Loading.Opaque) }
                getRandomCharacters()
            }

            is SearchContract.Event.OnSearchByName -> {
                setState { copy(state = SearchContract.SearchState.Loading.Transparent) }
                doSearch(event.input)
            }

            is SearchContract.Event.OnItemSelected -> {
                setState { copy(state = SearchContract.SearchState.Loading.Opaque) }
                //TODO: Navigate with item
            }
        }
    }

    private fun getRandomCharacters() {
        viewModelScope.launch {
            getFourRandomCharactersUseCase.execute() { randomResult ->
                randomResult.onSuccess {
                    val characters = it.map { c ->
                        RandomCharacter(
                            id = c.id,
                            name = c.name,
                            description = c.description,
                            poster = c.thumbnail.thumbnail
                        )
                    }
                    setState {
                        copy(
                            state = SearchContract.SearchState.Success.RandomResult(
                                characters
                            )
                        )
                    }
                }
                randomResult.onFailure {
                    setEffect { SearchContract.Effect.Error }
                    setState {
                        copy(
                            state = SearchContract.SearchState.Idle
                        )
                    }
                }
            }
        }
    }

    override fun isInLoadingState() =
        uiState.value.state == SearchContract.SearchState.Loading.Opaque


    //TODO: Implement all necessary stuff next

    private fun doSearch(input: String) {
        viewModelScope.launch {
            searchByNameUseCase.execute(input) { searchResult ->
                searchResult.onSuccess {
                    if (it.isNotEmpty()) {
                        val characters = it.map { c ->
                            SearchCharacter(
                                id = c.id,
                                name = c.name,
                                description = c.description,
                                thumbnail = c.thumbnail.thumbnail
                            )
                        }
                        setState {
                            copy(
                                state = SearchContract.SearchState.Success.SearchResult(
                                    characters
                                )
                            )
                        }
                    }else{
                        setState {
                            copy(
                                state = SearchContract.SearchState.Empty
                            )
                        }
                    }

                }
                searchResult.onFailure {
                    setEffect { SearchContract.Effect.Error }
                    setState {
                        copy(
                            state = SearchContract.SearchState.Idle
                        )
                    }
                }
            }
        }
    }
}