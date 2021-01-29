package com.samr.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import com.samr.domain.entities.CharacterEntity
import com.samr.domain.repositories.CharactersListRepo
import com.samr.domain.usecases.CharactersUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharactersUseCaseTest {

    private lateinit var useCase : CharactersUseCase
    private lateinit var repo: CharactersListRepo

    @Before
    fun setup(){

        repo = mock()
        useCase = CharactersUseCase(repo)
    }

    @Test
    fun `should succeed calling service and get LayerResult-Success`(){

        runBlocking {

            whenever(repo.fetchCharactersList(offsetFactor = eq(1),  callback = any() )).thenAnswer {

                val callback = it.getArgument<((LayerResult<List<CharacterEntity>>) -> Unit)>(1)
                callback.invoke(LayerResult.Success(mock()))
            }

            useCase.execute{result ->
                assert(result is LayerResult.Success)
            }
        }

    }

//    @Test
//    fun `should fail calling service and get LayerResult-Error`(){
//
//        runBlocking {
//
//            whenever(service.fetchCharacterDetail(eq("someId"), any())).thenAnswer {
//                val callback = it.getArgument<((LayerResult<CharactersRawResponse>) -> Unit)>(1)
//                callback.invoke(
//                        LayerResult.Error(
//                                CustomError(Throwable("TestException"),
//                                        CustomError.OriginLayer.DATA_LAYER)
//                        ))
//            }
//
//            repo.fetchCharacterDetail("someId"){result ->
//                assert(result is LayerResult.Error)
//            }
//        }
//    }
}