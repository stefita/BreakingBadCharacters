package com.stefita.presentation.di

import androidx.room.Room
import com.stefita.data.api.RemoteCharactersApi
import com.stefita.data.db.CharactersDatabase
import com.stefita.data.entities.CharacterDataEntityMapper
import com.stefita.data.entities.CharacterEntityDataMapper
import com.stefita.data.repository.CharacterRepositoryImpl
import com.stefita.data.repository.CharactersCacheImpl
import com.stefita.data.repository.CharactersRemoteImpl
import com.stefita.domain.repositories.CharacterRepository
import com.stefita.domain.usecases.GetCharactersUseCase
import com.stefita.presentation.characters.CharactersViewModel
import com.stefita.presentation.common.AsyncFlowableTransformer
import com.stefita.presentation.mapper.CharactersEntityMapper
import org.koin.dsl.module.module
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import retrofit2.Retrofit

val mRepositoryModules = module {
    single(name = "remote") { CharactersRemoteImpl(api = get(API))}
    single(name = "local") {
        CharactersCacheImpl(database = get(DATABASE),
            entityToDataMapper = CharacterEntityDataMapper(),
            dataToEntityMapper = CharacterDataEntityMapper()
        )
    }
    single { CharacterRepositoryImpl(remote = get("remote"), cache = get("local")) as CharacterRepository }
}

val mUseCaseModules = module {
    factory(name = "getCharactersUseCase") { GetCharactersUseCase(transformer = AsyncFlowableTransformer(), repository = get()) }
}

val mNetworkModules = module {
    single(name = RETROFIT_INSTANCE) { createNetworkClient(BASE_URL) }
    single(name = API) { (get(RETROFIT_INSTANCE) as Retrofit).create(RemoteCharactersApi::class.java) }
}

val mLocalModules = module {
    single(name = DATABASE) { Room.databaseBuilder(androidApplication(), CharactersDatabase::class.java, "breaking_bad").build() }
}

val mViewModels = module {
    viewModel {
        CharactersViewModel(getCharactersUseCase = get(GET_CHARACTERS_USECASE), mapper = CharactersEntityMapper())
    }
}

private const val BASE_URL = "https://www.breakingbadapi.com/api/"
private const val RETROFIT_INSTANCE = "Retrofit"
private const val API = "Api"
private const val GET_CHARACTERS_USECASE = "getCharactersUseCase"
private const val REMOTE = "remote response"
private const val DATABASE = "database"