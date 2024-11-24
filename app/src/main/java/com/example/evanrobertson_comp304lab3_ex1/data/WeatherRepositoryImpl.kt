package com.example.evanrobertson_comp304lab3_ex1.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherApi: WeatherAPI,
    private val dispatcher: CoroutineDispatcher
): WeatherRepository {

    //Gets weather using longitude and latitude
    override suspend fun getWeather(key: String, q: String): NetworkResult<WeatherResponse> {
        return withContext(dispatcher) {
            try {
                val response = weatherApi.fetchWeather(key, q)
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        NetworkResult.Success(weatherResponse)
                    } else {
                        NetworkResult.Error("Empty response body")
                    }
                } else {
                    NetworkResult.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown Error")
            }
        }
    }

    //Gets locations using search query
    override suspend fun getLocations(key: String, q: String): NetworkResult<List<SearchLocation>> {
        return withContext(dispatcher) {
            try {
                val response = weatherApi.searchLocations(key, q)
                if (response.isSuccessful) {
                    val locationResponse = response.body()
                    if (locationResponse != null) {
                        NetworkResult.Success(locationResponse)
                    } else {
                        NetworkResult.Error("Empty response body")
                    }
                } else {
                    NetworkResult.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                NetworkResult.Error(e.message ?: "Unknown Error")
            }
        }
    }
}