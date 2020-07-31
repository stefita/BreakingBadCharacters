package com.stefita.data.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson

object TypeConverterListInt {

    @TypeConverter
    @JvmStatic
    fun listToJson(value: List<Int>?)=  Gson().toJson(value)

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String): List<Int>? =
        Gson().fromJson(value, Array<Int>::class.java).toList()
}