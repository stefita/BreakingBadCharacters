package com.stefita.data.repository

import com.stefita.domain.entities.CharacterEntity

interface CharacterDataStore {
    suspend fun getCharacters(): List<CharacterEntity>
}