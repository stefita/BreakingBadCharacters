package com.stefita.data.repository

import androidx.lifecycle.LiveData
import com.stefita.domain.entities.CharacterEntity
import com.stefita.domain.repositories.CharacterRepository
import kotlinx.coroutines.Deferred

class CharacterRepositoryImpl(
    private val remote: CharactersRemoteImpl,
    private val cache: CharactersCacheImpl
) : CharacterRepository {

    override suspend fun getLocalCharacters(): List<CharacterEntity> {
        return cache.getCharacters()
    }

    override suspend fun getRemoteCharacters(): List<CharacterEntity> {
        return remote.getCharacters()
    }

    override suspend fun insertCharacters(list: List<CharacterEntity>) {
        cache.saveCharacters(list)
    }

    override suspend fun getCharacters(): List<CharacterEntity> {
        return getRemoteCharacters()
    }
}