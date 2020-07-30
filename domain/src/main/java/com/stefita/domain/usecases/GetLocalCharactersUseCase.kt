package com.stefita.domain.usecases

import com.stefita.domain.common.BaseFlowableUseCase
import com.stefita.domain.common.FlowableRxTransformer
import com.stefita.domain.entities.CharacterEntity
import com.stefita.domain.repositories.CharacterRepository
import io.reactivex.Flowable

class GetLocalCharactersUseCase(private val transformer: FlowableRxTransformer<List<CharacterEntity>>,
                                private val repository: CharacterRepository
):
    BaseFlowableUseCase<List<CharacterEntity>>(transformer){

    override fun createFlowable(data: Map<String, Any>?): Flowable<List<CharacterEntity>> {
        return repository.getCharacters()
    }

    fun getCharacters(): Flowable<List<CharacterEntity>> {
        val data = HashMap<String, String>()
        return single(data)
    }
}