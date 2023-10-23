package com.samr.domain

import com.nhaarman.mockitokotlin2.*
import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.repositories.CharactersListRepo
import com.samr.domain.usecases.CharactersUseCase
import com.samr.domain.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersUseCaseTest {

    private lateinit var useCase: CharactersUseCase
    private lateinit var repo: CharactersListRepo

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {

        repo = mock()
        useCase = CharactersUseCase(repo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should succeed calling Usecase and get LayerResult-Success`() {

        whenever(

            runBlocking { repo.fetchCharactersList(eq(1), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((LayerResult<List<CharacterEntity>>) -> Unit)>(1)
            callback(LayerResult.Success(mock()))
        }

        useCase.execute { result ->
            assert(result is LayerResult.Success)
        }
    }

    @Test
    fun `should fail calling usecase and get LayerResult-Error`() {

        whenever(

            runBlocking { repo.fetchCharactersList(eq(1), any()) }

        ).thenAnswer {
            val callback = it.getArgument<((LayerResult<List<CharacterEntity>>) -> Unit)>(1)
            callback(
                LayerResult.Error(
                    CustomError(
                        Throwable("TestException"),
                        CustomError.OriginLayer.DATA_LAYER
                    )
                )
            )
        }

        useCase.execute { result ->
            assert(result is LayerResult.Error)
        }
    }
}
