package com.stefita.data.repository

import com.stefita.data.db.CharactersDao
import com.stefita.data.db.CharactersDatabase
import com.stefita.data.entities.CharacterDataEntityMapper
import com.stefita.data.entities.CharacterEntityDataMapper
import com.stefita.domain.entities.CharacterEntity
import io.reactivex.Flowable

class CharactersCacheImpl(private val database: CharactersDatabase,
                          private val entityToDataMapper: CharacterEntityDataMapper,
                          private val dataToEntityMapper: CharacterDataEntityMapper): CharacterDataStore {

    private val dao: CharactersDao = database.getCharactersDao()

    override fun getCharacters(): Flowable<List<CharacterEntity>> {
        return dao.getAllCharacters().map { list ->
            list.map { dataToEntityMapper.mapCharacterToEntity(it) }
        }
    }

    fun saveCharacters(it: List<CharacterEntity>) {
        dao.clear()
        dao.saveAllCharacters(it.map { character ->
            entityToDataMapper.mapCharacterToData(character)
        } )
    }
}