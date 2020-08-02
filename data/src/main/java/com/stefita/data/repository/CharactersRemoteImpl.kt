package com.stefita.data.repository

import com.stefita.data.api.RemoteCharactersApi
import com.stefita.data.entities.CharacterDataEntityMapper
import com.stefita.domain.entities.CharacterEntity
import io.reactivex.Flowable

class CharactersRemoteImpl constructor(private val api: RemoteCharactersApi): CharacterDataStore{

    private val characterMapper = CharacterDataEntityMapper()

    override suspend fun getCharacters(): List<CharacterEntity> {
        val body = api.getCharacters().body() ?: return emptyList()
        return body.map { characterMapper.mapCharacterToEntity(it) }
    }
}