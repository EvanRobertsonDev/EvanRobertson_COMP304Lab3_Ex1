package com.example.evanrobertson_comp304lab3_ex1.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.evanrobertson_comp304lab3_ex1.data.Location
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherResponse
import com.example.evanrobertson_comp304lab3_ex1.navigation.ContentType
import com.example.evanrobertson_comp304lab3_ex1.viewModels.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    onWeatherClicked: (WeatherResponse) -> Unit,
    contentType: ContentType
) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    val weatherUIState by weatherViewModel.weatherUIState.collectAsStateWithLifecycle()
    WeatherScreenContent(
        modifier = Modifier
            .fillMaxSize(),
        onWeatherClicked = onWeatherClicked,
        contentType = contentType,
        weatherUIState = weatherUIState,
        onFavoriteClicked = { location ->
            weatherViewModel.toggleFavorite(location)
        },
        onSearch = { query ->
            weatherViewModel.searchLocations(query)
        }
    )
}