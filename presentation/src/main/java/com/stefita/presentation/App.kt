package com.stefita.presentation

import android.app.Application
import com.stefita.presentation.di.*
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        loadKoin()
    }

    private fun loadKoin() {
        startKoin(this,
            listOf(mNetworkModules,
                mViewModels,
                mRepositoryModules,
                mUseCaseModules,
                mLocalModules)
        )
    }
}