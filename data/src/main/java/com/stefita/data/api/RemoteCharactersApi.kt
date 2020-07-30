package com.stefita.data.api

import com.stefita.data.entities.CharacterData
import io.reactivex.Flowable
import retrofit2.http.GET

interface RemoteCharactersApi {

    @GET("characters")
    fun getCharacters(): Flowable<List<CharacterData>>
}