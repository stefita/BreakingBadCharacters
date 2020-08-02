package com.stefita.domain.usecases

import com.stefita.domain.common.*
import com.stefita.domain.entities.CharacterEntity
import com.stefita.domain.repositories.CharacterRepository
import kotlinx.coroutines.Deferred

class GetCharactersUseCase(private val transformer: FlowableRxTransformer<List<CharacterEntity>>,
                           private val repository: CharacterRepository) :
    BaseUseCase<List<CharacterEntity>, GetCharactersUseCase.Params>() {


    override suspend fun run(params: Params): Either<Failure, List<CharacterEntity>> {
        return try {
            val charactersList = repository.getCharacters()
            Either.Right(charactersList)
        } catch (exp: Exception) {
            Either.Left(CharactersListFailure(exp))
        }
    }

    data class Params(val limit: Int, val offset: Int)

    data class CharactersListFailure(val error: Exception) : Failure.FeatureFailure(error)
}