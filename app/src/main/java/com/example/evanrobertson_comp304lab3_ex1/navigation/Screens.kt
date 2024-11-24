package com.example.evanrobertson_comp304lab3_ex1.navigation

sealed class Screens(val route: String) {
    data object WeatherScreen : Screens("weather")
    data object WeatherDetailsScreen : Screens("weatherDetails")
    data object FavoriteLocationsScreen : Screens("favorites")
}