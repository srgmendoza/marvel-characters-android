package com.samr.marvelcharacterswiki.ui.charactersList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samr.domain.models.CustomError
import com.samr.domain.usecases.CharacterListUsecase

class CharacterListViewModel(
    private val characterUseCase: CharacterListUsecase
) : ViewModel() {

    val onError: LiveData<CustomError>
    get() {
        return onErrorLD
    }
    private val onErrorLD: MutableLiveData<CustomError> = MutableLiveData()

    fun getCharacters() {
        characterUseCase.execute { listResult ->
            listResult.onSuccess {
                //Todo: Maybe Log this
            }
            listResult.onFailure {
                onErrorLD.postValue(it as CustomError)
            }
        }
    }

    fun onCharactersListReady() = characterUseCase.getCharactersList()

}
