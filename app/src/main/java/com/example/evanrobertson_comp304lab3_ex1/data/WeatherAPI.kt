package com.example.evanrobertson_comp304lab3_ex1.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    //Returns current weather for given location
    @GET("current.json")
    suspend fun fetchWeather(
        @Query("key") apiKey: String,
        @Query("q") cityName: String
    ): Response<WeatherResponse>

    //Returns list of locations for given search query
    @GET("search.json")
    suspend fun searchLocations(
        @Query("key") apiKey: String,
        @Query("q") cityName: String
    ): Response<List<SearchLocation>>
}