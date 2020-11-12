package com.stefita.data.db.typeconverters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object TypeConverterListInt {

    private val type = Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
    private val adapter by lazy { Moshi.Builder().build().adapter<List<Int>>(type) }

    @TypeConverter
    @JvmStatic
    fun listToJson(value: List<Int>? = emptyList()) = adapter.toJson(value)

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String = ""): List<Int>? = adapter.fromJson(value)
}