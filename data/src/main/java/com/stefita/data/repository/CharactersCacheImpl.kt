package com.stefita.data.repository

import com.stefita.data.db.CharactersDao
import com.stefita.data.db.CharactersDatabase
import com.stefita.data.entities.CharacterDataEntityMapper
import com.stefita.data.entities.CharacterEntityDataMapper
import com.stefita.domain.entities.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharactersCacheImpl(
    database: CharactersDatabase,
    private val entityToDataMapper: CharacterEntityDataMapper,
    private val dataToEntityMapper: CharacterDataEntityMapper
) : CharacterDataStore {

    private val dao: CharactersDao = database.getCharactersDao()

    override suspend fun getCharacters(): List<CharacterEntity> {
        return dao.getAllCharacters().map { dataToEntityMapper.mapCharacterToEntity(it) }
    }

    suspend fun saveCharacters(it: List<CharacterEntity>) {
        dao.clear()
        withContext(Dispatchers.IO) { dao.saveListCharacters(it, entityToDataMapper) }
    }
}