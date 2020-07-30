package com.stefita.data.repository

import com.stefita.domain.entities.CharacterEntity
import io.reactivex.Flowable

interface CharacterDataStore {
    fun getCharacters(): Flowable<List<CharacterEntity>>
}