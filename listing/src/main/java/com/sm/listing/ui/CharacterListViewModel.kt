package com.sm.listing.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samr.domain.models.CustomErrorQ
import com.sm.listing.domain.usecases.CharacterListUsecase

class CharacterListViewModel(
    private val characterUseCase: CharacterListUsecase
) : ViewModel() {

    val onError: LiveData<CustomErrorQ>
    get() {
        return onErrorLD
    }
    private val onErrorLD: MutableLiveData<CustomErrorQ> = MutableLiveData()

    fun getCharacters() {
        characterUseCase.execute { listResult ->
            listResult.onSuccess {
                //Todo: Maybe Log this
            }
            listResult.onFailure {
                onErrorLD.postValue(it as CustomErrorQ)
            }
        }
    }

/*    fun onCharactersListReady(): LiveData<List<Character>>
    {
        return
    }*/

}