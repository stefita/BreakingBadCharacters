package com.stefita.presentation.characters

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.stefita.domain.common.Mapper
import com.stefita.domain.entities.CharacterEntity
import com.stefita.domain.usecases.GetCharactersUseCase
import com.stefita.presentation.common.BaseViewModel
import com.stefita.presentation.entities.CharactersSource
import com.stefita.presentation.entities.Data
import com.stefita.presentation.entities.Status
import com.stefita.presentation.entities.Error

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val mapper: Mapper<CharacterEntity, CharactersSource>
) : BaseViewModel() {

    companion object {
        private const val TAG = "viewModel"
    }

    var mNews = MutableLiveData<Data<List<CharactersSource>>>()

    fun fetchNews() {
        val disposable = getCharactersUseCase.getCharacters()
            .flatMap { mapper.Flowable(it) }
            .subscribe({ response ->
                Log.d(TAG, "On Next Called")
                mNews.value = Data(responseType = Status.SUCCESSFUL, data = response)
            }, { error ->
                Log.d(TAG, "On Error Called")
                mNews.value = Data(responseType = Status.ERROR, error = Error(error.message))
            }, {
                Log.d(TAG, "On Complete Called")
            })

        addDisposable(disposable)
    }

    fun getNewsLiveData() = mNews
}