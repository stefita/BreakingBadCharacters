package com.stefita.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stefita.data.db.typeconverters.TypeConverterListInt
import com.stefita.data.db.typeconverters.TypeConverterListString
import com.stefita.data.entities.CharacterData

@Database(entities = [CharacterData::class], version = 1, exportSchema = false)
@TypeConverters(
    TypeConverterListString::class,
    TypeConverterListInt::class
)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
}