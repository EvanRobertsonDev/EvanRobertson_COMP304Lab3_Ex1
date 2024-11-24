package com.example.evanrobertson_comp304lab3_ex1

import android.app.Application
import com.example.evanrobertson_comp304lab3_ex1.di.appModules
import org.koin.core.context.startKoin

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}