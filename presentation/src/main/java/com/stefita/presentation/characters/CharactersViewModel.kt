package com.stefita.presentation.characters

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stefita.domain.common.Failure
import com.stefita.domain.common.Mapper
import com.stefita.domain.entities.CharacterEntity
import com.stefita.domain.usecases.GetCharactersUseCase
import com.stefita.domain.usecases.InsertCharactersUseCase
import com.stefita.presentation.common.BaseViewModel
import com.stefita.presentation.entities.CharactersSource

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val insertCharactersUseCase: InsertCharactersUseCase,
    private val mapper: Mapper<CharacterEntity, CharactersSource>
) : BaseViewModel() {

    companion object {
        private const val TAG = "viewModel"
    }

    var savedQuery = ""
    var savedSeason = 0

    sealed class ListState {
        object Loading : ListState()
        object Empty : ListState()
        data class Success(
            val characters: List<CharactersSource>,
            val availableSeasons: List<Int>
        ) : ListState()
    }

    val state = MutableLiveData<ListState>().apply {
        this.value = ListState.Loading
    }

    fun loadData() {
        val params = GetCharactersUseCase.Params(100, 0)
        getCharactersUseCase.invoke(viewModelScope, params) {
            it.fold(
                ::handleFailure,
                ::handleSuccess
            )
        }
    }

    private fun handleFailure(error: Failure) {
        Log.d(TAG, error.exception.message)
    }

    private fun handleSuccess(list: List<CharacterEntity>) {
        Log.d(TAG, "Success")
        when {
            list.isEmpty() -> state.value = ListState.Empty
            list.isNotEmpty() -> {
                insertCharactersUseCase.invoke(viewModelScope, InsertCharactersUseCase.Params(list))
                val charactersSourceList = mapToPresentation(list)
                val seasonsList = extractAvailableSeasons(charactersSourceList)
                state.value = ListState.Success(charactersSourceList, seasonsList)
            }
        }
    }

    private fun extractAvailableSeasons(list: List<CharactersSource>): MutableList<Int> {
        return list.map { it.appearance }.flatten().distinct().sorted().toMutableList()
    }

    private fun mapToPresentation(characters: List<CharacterEntity>): List<CharactersSource> {
        return characters.map { mapper.mapFrom(it) }
    }
}