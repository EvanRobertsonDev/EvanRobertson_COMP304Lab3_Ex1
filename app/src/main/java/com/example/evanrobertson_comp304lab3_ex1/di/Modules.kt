package com.example.evanrobertson_comp304lab3_ex1.di

import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.evanrobertson_comp304lab3_ex1.data.FavoriteLocationsRepository
import com.example.evanrobertson_comp304lab3_ex1.data.LocationDatabase
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherAPI
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherRepository
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherRepositoryImpl
import com.example.evanrobertson_comp304lab3_ex1.viewModels.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


private val json = Json

val appModules = module {
    single<WeatherRepositoryImpl> { WeatherRepositoryImpl(get(), get()) }
    single<FavoriteLocationsRepository> { FavoriteLocationsRepository(get()) }
    single { Dispatchers.IO }
    single { WeatherViewModel(get(), get()) }
    single {
        Retrofit.Builder()
            .addConverterFactory(
                json.asConverterFactory(contentType = "application/json".toMediaType())
            )
            .baseUrl("https://api.weatherapi.com/v1/")
            .build()
    }
    single { get<Retrofit>().create(WeatherAPI::class.java) }
    single {
        databaseBuilder(
            androidContext(),
            LocationDatabase::class.java,
            "location-database"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<LocationDatabase>().locationDao() }

    viewModel { WeatherViewModel(weatherRepository = get(), locRepository = get()) }
}