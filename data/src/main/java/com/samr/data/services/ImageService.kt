package com.samr.data.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.samr.core.utils.BASE_CHARACTERS_URL
import com.samr.core.utils.CustomError
import com.samr.core.utils.LayerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class ImageService {


    private var restEndpoints: ImagesEndpoint

    init {
        val retrofit =  Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://i.annihil.us")
            .build()

        restEndpoints = retrofit.create(ImagesEndpoint::class.java)
    }

   suspend fun fetchImage(url: String, callback: (LayerResult<Bitmap>) -> Unit){



       withContext(Dispatchers.IO){

           try{

               val response = restEndpoints.getImage(url).await()

               if(response != null) {

                   callback(LayerResult.Success(mapToBmp(response.bytes())))
               }else{

//                   val httpErrorResponse = retrofit2.Response.error<Response>(500,response.body())
//                   throw CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
//                           underLyingError = HttpException(httpErrorResponse))
               }
           }catch (e: Throwable){

               callback(LayerResult.Error(CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
                          underLyingError = e)))
           }
//
//           OkHttpClient().newCall(request).enqueue(object : Callback{
//               override fun onFailure(call: Call, e: IOException) {
//
//                   callback(LayerResult.Error(
//                       CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
//                           underLyingError = e))
//                   )
//               }
//
//               override fun onResponse(call: Call, response: Response) {
//                   try{
//
//                       if(response.isSuccessful){
//
//                           if(response.body() != null) {
//
//                               callback(LayerResult.Success(response.body()!!.bytes()))
//                           }else{
//
//                               val httpErrorResponse = retrofit2.Response.error<Response>(500,response.body())
//                               throw CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
//                                   underLyingError = HttpException(httpErrorResponse))
//                           }
//                       }else{
//
//                           val httpErrorResponse = retrofit2.Response.error<Response>(response.code(),response.body())
//                           throw CustomError(originLayer = CustomError.OriginLayer.DATA_LAYER,
//                               underLyingError = HttpException(httpErrorResponse))
//
//                       }
//                   }catch (e: Throwable){
//
//                       callback(LayerResult.Error(e))
//                   }
//
//               }
//
//           })

       }


   }

    private fun mapToBmp(value: ByteArray) = BitmapFactory.decodeByteArray(value,0,value.size)
}