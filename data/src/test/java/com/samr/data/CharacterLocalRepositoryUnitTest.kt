package com.samr.data

import com.google.gson.Gson
import com.samr.data.entities.CharactersRawResponse
import com.samr.data.local.Database
import com.samr.data.local.repositories.CharacterLocalRepoImpl
import com.samr.domain.models.Character
import com.samr.domain.models.CustomError
import com.samr.domain.repositories.CharacterLocalRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.spyk
import org.junit.Before
import org.junit.Test

class CharacterLocalRepositoryUnitTest {

    @MockK
    private lateinit var database: Database
    private lateinit var repo: CharacterLocalRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        repo = spyk(CharacterLocalRepoImpl(database))
    }

    @Test
    fun `should success inserting data in database and response with true`() {
        val characters = getDataFromJsonAsset()
        val callback = slot<(Result<Boolean>) -> Unit>()

        every { repo.insert(characters, capture(callback)) } answers {
            callback.captured.invoke(Result.success(true))
        }

        repo.insert(characters) { result ->
            result.onSuccess {
                assert(it)
            }
        }
    }

    @Test
    fun `should fail inserting data in database and response with a custom error`() {
        val characters = getDataFromJsonAsset()
        val callback = slot<(Result<Boolean>) -> Unit>()

        every { repo.insert(characters, capture(callback)) } answers {
            val error = CustomError(
                Throwable("Error"),
                CustomError.OriginLayer.DATA_LAYER)
            callback.captured.invoke(Result.failure(error))
        }

        repo.insert(characters) { result ->
            result.onFailure {
                assert(it is CustomError)
            }
        }
    }

    private fun getDataFromJsonAsset(): List<Character> {
        val fileContent = this::class.java.classLoader.getResource("assets/rawData.json").readText()
        val charactersEntity = Gson().fromJson(fileContent, CharactersRawResponse::class.java).mapToData()

        return charactersEntity.map { c -> c.mapDataToEntity() }
    }
}