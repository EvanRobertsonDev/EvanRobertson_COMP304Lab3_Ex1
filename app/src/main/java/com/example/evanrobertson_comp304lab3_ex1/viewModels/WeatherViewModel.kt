package com.example.evanrobertson_comp304lab3_ex1.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evanrobertson_comp304lab3_ex1.data.FavoriteLocationsRepository
import com.example.evanrobertson_comp304lab3_ex1.data.Location
import com.example.evanrobertson_comp304lab3_ex1.data.LocationEntity
import com.example.evanrobertson_comp304lab3_ex1.data.NetworkResult
import com.example.evanrobertson_comp304lab3_ex1.data.SearchLocation
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherRepositoryImpl
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherResponse
import com.example.evanrobertson_comp304lab3_ex1.views.WeatherUIState
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepositoryImpl, private val locRepository: FavoriteLocationsRepository
): ViewModel() {

    //API Key
    private val apiKey : String = "48a5f37d5e314e169ea210536242111"

    //UI State
    val weatherUIState = MutableStateFlow(WeatherUIState())

    //Favorite locations
    private val _favoriteLocations = MutableStateFlow<List<Location>>(emptyList())
    val favoriteLocations: StateFlow<List<Location>> get() = _favoriteLocations

    //Gets weather for all locations in list
    private suspend fun getWeather(locations : List<SearchLocation>) {
        locations.forEach { location ->
            //Search through API using longitude and latitude
            when (val result = weatherRepository.getWeather(apiKey, "$location.lat,$location.lon")) {
                is NetworkResult.Success -> {
                    //Update UI
                    weatherUIState.update {
                        val updatedWeatherList = it.weather.toMutableList()
                        updatedWeatherList.add(result.data)
                        it.copy(weather = updatedWeatherList)
                    }
                }
                is NetworkResult.Error -> {
                    //Update UI
                    weatherUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

    //Gets weather for a single location
    fun getWeatherForLocation(lon: Double, lat: Double) {
        viewModelScope.launch {
            //Update UI
            weatherUIState.update { it.copy(isLoading = true) }

            //Search through API using longitude and latitude
            when (val result = weatherRepository.getWeather(apiKey, "$lat,$lon")) {
                is NetworkResult.Success -> {
                    //Update UI
                    weatherUIState.update {
                        it.copy(isLoading = false, weather = listOf(result.data))
                    }
                }
                is NetworkResult.Error -> {
                    //Update UI
                    weatherUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

    //Gets list of locations using API search
    fun searchLocations(search : String) {
        weatherUIState.value = WeatherUIState(isLoading = true)
        viewModelScope.launch {
            //Search API for location using query
            when (val result = weatherRepository.getLocations(apiKey, search)) {
                is NetworkResult.Success -> {
                    //Update UI
                    weatherUIState.update {
                        it.copy(isLoading = false, locations = result.data)
                    }
                    getWeather(result.data)
                }
                is NetworkResult.Error -> {
                    //Update UI
                    weatherUIState.update {
                        it.copy(isLoading = false, error = result.error)
                    }
                }
            }
        }
    }

    //Insert favorite location into local database
    private fun insertLocation(location : Location) {
        viewModelScope.launch {
            locRepository.insertLocation(location)
        }
    }

    //Remove un-favorited location from local database
    fun removeLocation(location: Location) {
        viewModelScope.launch {
            locRepository.removeLocation(location, locRepository.getLocation(location.lon, location.lat).id)
        }
    }

    //Toggles location favorite status
    fun toggleFavorite(location: Location) {
        viewModelScope.launch {
            //Update location
            val updatedLocation = location.copy(isFavorite = !location.isFavorite)

            //Update repository based on new favorite status
            if (updatedLocation.isFavorite) {
                //Add to favorites
                locRepository.insertLocation(updatedLocation)
            } else {
                //Remove from favorites
                locRepository.removeLocation(updatedLocation, locRepository.getLocation(updatedLocation.lon, updatedLocation.lat).id)
            }

            //Update the favorite locations list in the UI state
            _favoriteLocations.update { currentFavorites ->
                if (updatedLocation.isFavorite) {
                    //Add to list
                    currentFavorites + updatedLocation
                } else {
                    //Remove from list
                    currentFavorites.filter { it.lon != updatedLocation.lon || it.lat != updatedLocation.lat }
                }
            }

            //Update the weather list with the new favorite status
            weatherUIState.update { currentUIState ->
                currentUIState.copy(
                    weather = currentUIState.weather.map {
                        if (it.location.lon == location.lon && it.location.lat == location.lat) {
                            //Update weather location with new favorite status
                            it.copy(location = updatedLocation)
                        } else it
                    }
                )
            }
        }
    }

    //Gets all favorite locations in local database
    fun getFavoriteLocations() {
        viewModelScope.launch {
            locRepository.allLocations.collect {
                _favoriteLocations.value = it
            }
        }
    }

    //Checks if a location is favorited
    fun isFavorite(location : Location): Flow<Boolean> {
        return locRepository.isFavorite(location)
    }
}