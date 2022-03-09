package com.samr.domain

import com.nhaarman.mockitokotlin2.*
import com.samr.data.utils.CustomError
import com.samr.data.utils.LayerResult
import com.samr.domain.models.Character
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

            val callback = it.getArgument<((com.samr.data.utils.LayerResult<List<Character>>) -> Unit)>(1)
            callback(com.samr.data.utils.LayerResult.Success(mock()))
        }

        useCase.execute { result ->
            assert(result is com.samr.data.utils.LayerResult.Success)
        }
    }

    @Test
    fun `should fail calling usecase and get LayerResult-Error`() {

        whenever(

            runBlocking { repo.fetchCharactersList(eq(1), any()) }

        ).thenAnswer {
            val callback = it.getArgument<((com.samr.data.utils.LayerResult<List<Character>>) -> Unit)>(1)
            callback(
                com.samr.data.utils.LayerResult.Error(
                    com.samr.data.utils.CustomError(
                        Throwable("TestException"),
                        com.samr.data.utils.CustomError.OriginLayer.DATA_LAYER
                    )
                )
            )
        }

        useCase.execute { result ->
            assert(result is com.samr.data.utils.LayerResult.Error)
        }
    }
}
