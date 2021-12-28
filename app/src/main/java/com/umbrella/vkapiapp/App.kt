package com.umbrella.vkapiapp

import android.app.Application
import com.umbrella.vkapiapp.di.repositoriesModule
import com.umbrella.vkapiapp.di.retrofitModule
import com.umbrella.vkapiapp.di.useCasesModule
import com.umbrella.vkapiapp.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(viewModelsModule, retrofitModule, useCasesModule, repositoriesModule)
        }
    }
}