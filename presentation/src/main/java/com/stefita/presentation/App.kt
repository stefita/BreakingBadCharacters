package com.stefita.presentation

import android.app.Application
import com.facebook.stetho.Stetho
import com.stefita.presentation.di.*
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        loadKoin()
        attachStetho()
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

    private fun attachStetho() {
        if (BuildConfig.DEBUG) {
            // only enable for debug builds
            Stetho.initializeWithDefaults(this)
        }
    }
}