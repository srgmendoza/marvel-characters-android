package com.samr.data

import com.samr.domain.models.CustomError
import com.samr.domain.models.LayerResult
import com.samr.data.remote.repositories.CharacterRemoteRepoImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class CharactersServiceUnitTest {

    private lateinit var service: CharacterRemoteRepoImpl

    @Before
    fun setup() {
        service = CharacterRemoteRepoImpl()
    }

    @Test
    fun `should call for characters List and get a response in form of LayerResult`() {
        runBlocking {
            service.fetchCharactersList(1) { data ->
                assert(data is LayerResult)
            }
        }
    }

    @Test
    fun `should call for character detail and get a response in form of LayerResult`() {
        runBlocking {
            service.fetchCharacterDetail(1.toString()) { data ->
                assert(data is LayerResult)
            }
        }
    }

    @Test
    fun `should call for character detail with inexistent id and get an httpException code 404`() {
        runBlocking {
            service.fetchCharacterDetail("2") { data ->
                val customError = (data as LayerResult.Error).error
                val httpException = (customError as CustomError).getUnderlyingError() as HttpException
                assert(httpException.code() == 404)
            }
        }
    }

    @Test
    fun `should call for character detail with valid id and get success response`() {
        runBlocking {
            service.fetchCharacterDetail("1017100") { data ->
                assert(data is LayerResult.Success)
            }
        }
    }
}
