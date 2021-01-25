package com.samr.marvelcharacterswiki.ui.presenters

import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.domain.usecases.CharactersUseCase
import com.samr.marvelcharacterswiki.ui.views.CharacterDetailView

class CharacterDetailPresenter(view: CharacterDetailView) {

    private var characterUseCase: CharactersUseCase = CharactersUseCase()

    fun fetchDataForMainScreen() {

        characterUseCase.execute{uiResult ->

//            when (uiResult){
//                is LayerResult.Success -> {
//                    view.onCharacterslistReceived(mapDataToUi(uiResult.value))
//                }
//                is LayerResult.Error -> {
//                    val error = uiResult.errorInfo as UIError
//                    view.onError(error.userMessage)
//                }
//            }

        }
    }
}