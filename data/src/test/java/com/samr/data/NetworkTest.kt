package com.samr.data

import com.samr.core.utils.DataError
import com.samr.core.utils.LayerResult
import com.samr.data.services.NetworkService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NetworkTest {


    private lateinit var service : NetworkService

    @Before
    fun setup(){
        service = NetworkService()
    }

    @Test
    fun `should call for network service and get a response in form of LayerResult`(){
        runBlocking {
            service.fetchData(queryUrl = TEST_URL){ data ->
                assert(data is LayerResult)
            }
        }
    }

    @Test
    fun `should call for network service and get a DataError`(){
        runBlocking {
            service.fetchData(queryUrl = ""){ data ->
                data as LayerResult.Error
                assert(data.errorInfo is DataError)
            }
        }
    }

}