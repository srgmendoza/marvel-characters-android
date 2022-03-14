package com.samr.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.samr.domain.models.Character
import com.samr.domain.repositories.CharacterLocalRepository
import com.samr.domain.repositories.CharacterRemoteRepository
import com.samr.domain.usecases.CharacterListUsecase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class CharactersUseCaseTest {
    @MockK
    private lateinit var localRepo: CharacterLocalRepository
    @MockK
    private lateinit var remoteRepo: CharacterRemoteRepository

    private lateinit var useCase: CharacterListUsecase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = spyk(CharacterListUsecase(localRepo, remoteRepo), recordPrivateCalls = true)
    }

    @Test
    fun `should execute flow to get list of characters, store in db, and return success`() {
        val characters = mockk<List<Character>>()
        val remoteRepoCallback = slot<(Result<List<Character>>) -> Unit>()
        val localRepoCallback = slot<(Result<Boolean>) -> Unit>()

        every {
            remoteRepo.fetchCharactersList(1, capture(remoteRepoCallback))
        } answers {
            remoteRepoCallback.captured(Result.success(characters))
        }

        every { localRepo.insert(characters, capture(localRepoCallback)) } answers {
            localRepoCallback.captured.invoke(Result.success(true))
        }

        useCase.execute {
            assert(it.isSuccess)
        }
    }


}
