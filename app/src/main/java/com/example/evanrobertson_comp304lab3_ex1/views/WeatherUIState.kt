package com.example.evanrobertson_comp304lab3_ex1.views

import com.example.evanrobertson_comp304lab3_ex1.data.SearchLocation
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherResponse
//UI State class
data class WeatherUIState(
    val isLoading: Boolean = false,
    val weather: List<WeatherResponse> = emptyList(),
    val locations: List<SearchLocation> = emptyList(),
    val error: String? = null
)
