package com.example.evanrobertson_comp304lab3_ex1.data

interface WeatherRepository {
    suspend fun getWeather(key : String, q : String): NetworkResult<WeatherResponse>
    suspend fun getLocations(key: String, q: String): NetworkResult<List<SearchLocation>>
}