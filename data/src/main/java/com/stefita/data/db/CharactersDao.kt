package com.stefita.data.db

import androidx.room.*
import com.stefita.data.entities.CharacterData
import com.stefita.data.entities.CharacterEntityDataMapper
import com.stefita.domain.entities.CharacterEntity

@Dao
interface CharactersDao {

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): List<CharacterData>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllCharacters(characters: List<CharacterData>)

    @Transaction
    suspend fun saveListCharacters(
        characters: List<CharacterEntity>,
        entityToDataMapper: CharacterEntityDataMapper
    ) {
        val charList = characters.map { character ->
            entityToDataMapper.mapCharacterToData(character)
        }
        saveAllCharacters(charList)
    }

    @Query("DELETE FROM characters")
    fun clear()
}