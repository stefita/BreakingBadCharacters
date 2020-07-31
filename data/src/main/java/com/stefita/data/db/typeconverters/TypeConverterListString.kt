package com.stefita.data.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson

object TypeConverterListString {

    @TypeConverter
    @JvmStatic
    fun listToJson(value: List<String>?)=  Gson().toJson(value)

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String): List<String>? =
        Gson().fromJson(value, Array<String>::class.java).toList()
}