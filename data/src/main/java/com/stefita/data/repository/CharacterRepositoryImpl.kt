package com.stefita.data.repository

import com.stefita.domain.entities.CharacterEntity
import com.stefita.domain.repositories.CharacterRepository
import io.reactivex.Flowable

class CharacterRepositoryImpl(private val remote: CharactersRemoteImpl,
                              private val cache: CharactersCacheImpl): CharacterRepository {

    override fun getLocalCharacters(): Flowable<List<CharacterEntity>> {
        return cache.getCharacters()
    }

    override fun getRemoteCharacters(): Flowable<List<CharacterEntity>> {
        return remote.getCharacters()
    }

    override fun getCharacters(): Flowable<List<CharacterEntity>> {
        val updateCharactersFlowable = getRemoteCharacters()
        return getLocalCharacters().mergeWith(updateCharactersFlowable.doOnNext {
            remoteCharacters -> cache.saveCharacters(remoteCharacters)
        })
    }
}