package com.sm.feature_listing.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.core_utils.ImageVariant
import com.sm.base_core.BaseViewModel
import com.sm.feature_listing.domain.usecases.GetPagedCharactersListUseCase
import com.sm.feature_listing.presentation.models.ListedCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val characterUseCase: GetPagedCharactersListUseCase
) : BaseViewModel<CharacterListContract.Event, CharacterListContract.State, CharacterListContract.Effect>() {

    init {
        setEvent(CharacterListContract.Event.OnLoadRequested)
    }

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
                            imageUrl = getProperImageUrl(c.images)
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

    private fun getProperImageUrl(
        images: Map<ImageVariant, Map<ImageVariant.ImageSize, String>>): String {

        //TODO: Select the proper image based on device aspect ratio
        return images[ImageVariant.SquareAspectRatio]?.get(ImageVariant.ImageSize.XLARGE).orEmpty()
    }


}