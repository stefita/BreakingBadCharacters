package com.stefita.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stefita.domain.entities.CharacterEntity

interface CharacterDataStore {
    suspend fun getCharacters(): List<CharacterEntity>
}