package com.stefita.domain.repositories

import com.stefita.domain.entities.CharacterEntity

interface CharacterRepository {

    suspend fun getCharacters(): List<CharacterEntity>
    suspend fun insertCharacters(list: List<CharacterEntity>)
    suspend fun getLocalCharacters(): List<CharacterEntity>
    suspend fun getRemoteCharacters(): List<CharacterEntity>
}