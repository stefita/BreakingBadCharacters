package com.stefita.domain.repositories

import com.stefita.domain.entities.CharacterEntity
import io.reactivex.Flowable

interface CharacterRepository {

    fun getCharacters(): Flowable<List<CharacterEntity>>
    fun getLocalCharacters(): Flowable<List<CharacterEntity>>
    fun getRemoteCharacters(): Flowable<List<CharacterEntity>>
}