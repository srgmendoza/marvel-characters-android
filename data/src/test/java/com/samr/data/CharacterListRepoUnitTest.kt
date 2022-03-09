package com.samr.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.samr.data.entities.CustomError
import com.samr.data.entities.LayerResult
import com.samr.data.entities.CharactersRawResponse
import com.samr.data.remote.repositories.CharactersListRepoImpl
import com.samr.data.remote.repositories.CharacterRemoteRepo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharacterListRepoUnitTest {

    private lateinit var service: CharacterRemoteRepo
    private lateinit var repo: CharactersListRepoImpl

    @Before
    fun setup() {

        service = mock()
        repo = CharactersListRepoImpl(service)
    }

    @Test
    fun `should succed calling service and get LayerResult-Success`() {

        runBlocking {

            whenever(service.fetchCharactersList(eq(1), any())).thenAnswer {
                val callback = it.getArgument<((LayerResult<CharactersRawResponse>) -> Unit)>(1)
                callback.invoke(LayerResult.Success(mock()))
            }

            repo.fetchCharactersList(1) { result ->
                assert(result is LayerResult.Success)
            }
        }
    }

    @Test
    fun `should fail calling service and get LayerResult-Error`() {

        runBlocking {

            whenever(service.fetchCharactersList(eq(1), any())).thenAnswer {
                val callback = it.getArgument<((LayerResult<CharactersRawResponse>) -> Unit)>(1)
                callback.invoke(LayerResult.Error(CustomError(Throwable("TestException"), CustomError.OriginLayer.DATA_LAYER)))
            }

            repo.fetchCharactersList(1) { result ->
                assert(result is LayerResult.Error)
            }
        }
    }
}
