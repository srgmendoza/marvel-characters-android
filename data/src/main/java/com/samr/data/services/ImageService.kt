package com.samr.data.services

import android.security.KeyChainAliasCallback
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.samr.core.utils.BASE_CHARACTERS_URL
import com.samr.core.utils.CustomError
import com.samr.core.utils.DataError
import com.samr.core.utils.LayerResult
import okhttp3.*
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ImageService {

   fun fetchImage(url: String, callback: (LayerResult<ByteArray>) -> Unit){

       val request = Request.Builder()
               .url(url)
               .build()

       OkHttpClient().newCall(request).enqueue(object : Callback{
           override fun onFailure(call: Call, e: IOException) {

               callback(LayerResult.Error(
                   CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
                       underLyingError = e))
               )
           }

           override fun onResponse(call: Call, response: Response) {
               if(response.isSuccessful){

                   if(response.body() != null) {

                       callback(LayerResult.Success(response.body()!!.bytes()))
                   }else{

                       callback(LayerResult.Error(DataError()))
                   }
               }else{

                   val httpErrorResponse = retrofit2.Response.error<Response>(response.code(),response.body())

                   callback(LayerResult.Error(
                       CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
                           underLyingError = HttpException(httpErrorResponse)))
                   )
               }

           }

       })

   }
}