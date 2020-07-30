package com.stefita.data.repository

import com.stefita.data.api.RemoteCharactersApi
import com.stefita.data.entities.CharacterDataEntityMapper
import com.stefita.domain.entities.CharacterEntity
import io.reactivex.Flowable

class CharactersRemoteImpl constructor(private val api: RemoteCharactersApi): CharacterDataStore{

    private val characterMapper = CharacterDataEntityMapper()

    override fun getCharacters(): Flowable<List<CharacterEntity>> {
        return api.getCharacters().map { list ->
            list.map { characterMapper.mapCharacterToEntity(it) }
        }
    }
}