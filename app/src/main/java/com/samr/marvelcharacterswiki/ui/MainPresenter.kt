package com.samr.marvelcharacterswiki.ui

import com.samr.core.utils.LayerResult
import com.samr.core.utils.UIError
import com.samr.domain.usecases.CharactersUseCase


class MainPresenter(private val view: MainView) {

    private var characterUseCase: CharactersUseCase = CharactersUseCase()

    fun fetchDataForMainScreen() {

        characterUseCase.execute{uiResult ->

            when (uiResult){
                is LayerResult.Success -> {
                    view.onCharacterslistReceived(uiResult.value)
                }
                is LayerResult.Error -> {
                    val error = uiResult as UIError
                    view.onError(error.userMessage)
                }
            }

        }
    }

}