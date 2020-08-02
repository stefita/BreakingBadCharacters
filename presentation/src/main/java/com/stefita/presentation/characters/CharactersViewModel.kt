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
import kotlinx.coroutines.Dispatchers

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val insertCharactersUseCase: InsertCharactersUseCase,
    private val mapper: Mapper<CharacterEntity, CharactersSource>
) : BaseViewModel() {

    companion object {
        private const val TAG = "viewModel"
    }

    sealed class ListState {
        object Loading : ListState()
        object Empty : ListState()
        data class Success(val characters: List<CharactersSource>) : ListState()
    }

    val state = MutableLiveData<ListState>().apply {
        this.value = ListState.Loading
    }

    fun loadData() {
        val params = GetCharactersUseCase.Params(100, 0)
        getCharactersUseCase.invoke(viewModelScope, params) { it.fold(::handleFailure, ::handleSuccess) }
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
                state.value = ListState.Success(mapToPresentation(list))
            }
        }
    }

    private fun mapToPresentation(characters: List<CharacterEntity>): List<CharactersSource> {
        return characters.map { mapper.mapFrom(it) }
    }
}