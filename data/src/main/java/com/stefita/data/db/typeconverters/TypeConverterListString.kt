package com.stefita.data.db.typeconverters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object TypeConverterListString {


    private val type = Types.newParameterizedType(List::class.java, String::class.java)
    private val adapter by lazy { Moshi.Builder().build().adapter<List<String>>(type) }

    @TypeConverter
    @JvmStatic
    fun listToJson(value: List<String>?): String {
        return adapter.toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String): List<String>? {
        return adapter.fromJson(value)
    }
}