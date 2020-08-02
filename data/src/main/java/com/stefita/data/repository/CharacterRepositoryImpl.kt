package com.stefita.data.repository

import com.stefita.domain.entities.CharacterEntity
import com.stefita.domain.repositories.CharacterRepository

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
        val cache = getLocalCharacters()
        return if (cache.isEmpty()) {
            getRemoteCharacters()
        } else {
            cache
        }
    }
}