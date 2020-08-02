package com.stefita.domain.usecases

import com.stefita.domain.common.Either
import com.stefita.domain.common.Failure
import com.stefita.domain.entities.CharacterEntity
import com.stefita.domain.repositories.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertCharactersUseCase(private val repository: CharacterRepository) :
    BaseUseCase<Boolean, InsertCharactersUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, Boolean> {
        return try {
            withContext(Dispatchers.IO) { repository.insertCharacters(params.list) }
            Either.Right(true)
        } catch (exp: Exception) {
            Either.Left(InsertCharactersListFailure(exp))
        }
    }

    data class Params(val list: List<CharacterEntity>)

    data class InsertCharactersListFailure(val error: Exception) : Failure.FeatureFailure(error)
}