package com.samr.data.services

import com.samr.core.utils.DataError
import com.samr.core.utils.LayerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.UnknownHostException

class NetworkService() {


    suspend fun fetchData(queryUrl: String,callback: (LayerResult<ByteArray>?) -> Unit){

        withContext(Dispatchers.IO){

            try {

                val url = URL(queryUrl)

                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connect()

                if(connection.responseCode in 200..399){

                    callback(LayerResult.Success(connection.inputStream.readBytes()))
                }else{

                    val errorStr = connection.errorStream.readBytes().toString(Charsets.UTF_8)
                    val errorCode = connection.responseCode


                    throw DataError(errorCode,errorStr)

                }
            }catch (dfe: DataError){ //No connection present

                callback(LayerResult.Error(dfe))
            }catch (uhe: UnknownHostException){ //No connection present

                val dataError = DataError(underLyingError = uhe)
                callback(LayerResult.Error(dataError))
            }catch(me: MalformedURLException){ //Bad Url

                val dataError = DataError(underLyingError = me)
                callback(LayerResult.Error(dataError))
            }catch (e: Exception){ //Generic error

                val dataError = DataError(underLyingError = e)
                callback(LayerResult.Error(dataError))
            }

        }

    }
}

//characters?limit=40&offset=120&ts=1&apikey=df7c00808acf1c7802e89eaf43708a75&hash=73290f23ea34fbed856880739119c830
