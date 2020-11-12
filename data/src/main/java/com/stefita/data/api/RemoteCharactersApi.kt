package com.stefita.data.api

import com.stefita.data.entities.CharacterData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RemoteCharactersApi {

    @Headers("Accept: application/json")
    @GET("characters")
    suspend fun getCharacters(): Response<List<CharacterData>>
}