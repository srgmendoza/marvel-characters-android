package com.samr.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samr.data.entities.*
import com.samr.domain.models.*

@ProvidedTypeConverter
object CharacterConverter {

    @TypeConverter
    @JvmStatic
    fun imagesToString(value: Images?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
    @TypeConverter
    @JvmStatic
    fun stringToImages(value: String): Images {
        return Gson().fromJson(value,Images::class.java)
    }


    @TypeConverter
    @JvmStatic
    fun publishingToString(value: Publishings?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
    @TypeConverter
    @JvmStatic
    fun stringToPublishingsEntity(value: String): Publishings {
        return Gson().fromJson(value,Publishings::class.java)
    }

}