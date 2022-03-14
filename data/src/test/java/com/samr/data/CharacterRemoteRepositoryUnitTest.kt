package com.samr.data

import com.google.gson.Gson
import com.samr.data.entities.CharactersRawResponse
import com.samr.data.remote.endpoints.CharacterEndpoints
import com.samr.data.remote.repositories.CharacterRemoteRepoImpl
import com.samr.domain.models.Character
import com.samr.domain.repositories.CharacterRemoteRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.spyk
import org.junit.Before
import org.junit.Test

class CharacterRemoteRepositoryUnitTest {

    @MockK
    private lateinit var endpoints: CharacterEndpoints
    private lateinit var repo: CharacterRemoteRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repo = spyk(CharacterRemoteRepoImpl(endpoints))
    }

    @Test
    fun `should succeed calling for repo with dummy data`() {

        val characters = getDataFromJsonAsset()
        val callback = slot<(Result<List<Character>>) -> Unit>()

        every { repo.fetchCharactersList(1, capture(callback)) }answers {
            callback.captured.invoke(Result.success(characters))
        }

        repo.fetchCharactersList(1) { result ->
            result.onSuccess {
                val characters = getDataFromJsonAsset()
                assert(isCharacterEqual(it,characters))
            }
        }
    }

    private fun getDataFromJsonAsset(): List<Character> {
        val fileContent = this::class.java.classLoader.getResource("assets/rawData.json").readText()
        val charactersEntity = Gson().fromJson(fileContent, CharactersRawResponse::class.java).mapToData()

        return charactersEntity.map { c -> c.mapDataToEntity() }
    }

    private fun isCharacterEqual(first: List<Character>, second: List<Character>): Boolean {

        if (first.size != second.size) {
            return false
        }

        return first.zip(second).all { (x, y) -> x.id == y.id }
    }

}
