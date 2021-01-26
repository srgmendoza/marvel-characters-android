package com.samr.marvelcharacterswiki

import com.samr.domain.usecases.CharactersUseCase
import com.samr.marvelcharacterswiki.ui.presenters.CharactersListPresenter
import com.samr.marvelcharacterswiki.ui.views.CharactersListView
import com.samr.marvelcharacterswiki.models.CharacterModel
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class UITest {

    private lateinit var presenter : CharactersListPresenter

    @Mock
    private lateinit var view: CharactersListView
    @Mock
    private lateinit var useCase: CharactersUseCase

    @Captor
    private lateinit var captor: ArgumentCaptor<CharactersListView>
    @Captor
    private lateinit var entityCaptor: ArgumentCaptor<List<CharacterModel>>


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)


        presenter = CharactersListPresenter(view)

    }

    @Test
    @SuppressWarnings("unchecked")
    fun `should call for domain layer`(){
        presenter.fetchDataForMainScreen()

        verify(useCase, times(1)).execute {captor.capture()}
        captor.value.onCharacterslistReceived(getMockData())

        //entityCaptor =

        verify(view).onCharacterslistReceived(entityCaptor.capture())
    }

    private fun getMockData(): List<CharacterModel> {
        val list: MutableList<CharacterModel> = mutableListOf()

        list.add(CharacterModel(id = 1, name = "Jhon Doe"))
        list.add(CharacterModel(id = 2, name = "Jhon Doe2"))

        return list
    }
}