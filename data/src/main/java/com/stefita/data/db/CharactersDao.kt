package com.stefita.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stefita.data.entities.CharacterData
import io.reactivex.Flowable

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flowable<List<CharacterData>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllCharacters(characters: List<CharacterData>)

    @Query("DELETE FROM characters")
    fun clear()
}