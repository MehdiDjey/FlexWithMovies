package com.druide.flexwithmovies.base

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.druide.flexwithmovies.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber


class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    moviesViewModelModule,
                    movieDetailsViewModelModule,
                    serviceModule,
                    movieRepositoryModule,
                    moviesRepositoryModule,
                    networkModule
                )
            )

        }
        instance = this
    }
}