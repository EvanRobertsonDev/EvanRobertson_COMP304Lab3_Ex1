package com.example.evanrobertson_comp304lab3_ex1.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evanrobertson_comp304lab3_ex1.data.Location
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherResponse

@Composable
fun WeatherListAndDetails(weather: List<WeatherResponse>, onFavoriteClicked: (Location) -> Unit) {

    var currentWeather by remember {
        mutableStateOf(weather.first())
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherList(
            onWeatherClicked = {
                currentWeather = it
            },
            weather = weather,
            onFavoriteClicked = onFavoriteClicked,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        WeatherDetailsScreenContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f),
            weather = currentWeather
        )
    }
}