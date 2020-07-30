package com.stefita.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stefita.data.entities.CharacterData

@Database(entities = arrayOf(CharacterData::class), version = 1)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
}