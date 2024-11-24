package com.example.evanrobertson_comp304lab3_ex1.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.evanrobertson_comp304lab3_ex1.data.Location
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherResponse
import com.example.evanrobertson_comp304lab3_ex1.viewModels.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherList(
    onWeatherClicked: (WeatherResponse) -> Unit,
    onFavoriteClicked: (Location) -> Unit,
    weather: List<WeatherResponse>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(weather) { weather ->
            WeatherListItem(
                weather = weather,
                onWeatherClicked = onWeatherClicked,
                onFavoriteClicked = onFavoriteClicked
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeatherListItem(weather: WeatherResponse, onWeatherClicked: (WeatherResponse) -> Unit, onFavoriteClicked: (Location) -> Unit) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    val isFavorite by weatherViewModel.isFavorite(weather.location).collectAsState(initial = false)
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { onWeatherClicked(weather) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            //Display Weather Icon
            AsyncImage(
                model = "https:${weather.current.condition.icon}", // Use the condition icon URL
                contentDescription = weather.current.condition.text, // Use the condition description
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.FillWidth
            )

            //Display Location Name
            Text(
                text = weather.location.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(6.dp)
            )

            //Display Temperature and Condition
            Text(
                text = "${weather.current.temp_c}°C - ${weather.current.condition.text}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(6.dp)
            )

            //Display Additional Information in Chips
            FlowRow(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            ) {
                //Humidity
                SuggestionChip(
                    onClick = { },
                    label = { Text(text = "Humidity: ${weather.current.humidity}%") }
                )
                //Wind
                SuggestionChip(
                    onClick = { },
                    label = { Text(text = "Wind: ${weather.current.wind_kph} kph") }
                )
                //Pressure
                SuggestionChip(
                    onClick = { },
                    label = { Text(text = "Pressure: ${weather.current.pressure_mb} mb") }
                )
            }
            //Favorite Button
            Icon(
                modifier = Modifier
                    .clickable {
                        onFavoriteClicked(weather.location)
                    },
                imageVector = if (isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = "Favorite",
                tint = if (isFavorite) {
                    Color.Red
                } else {
                    Color.Gray
                }
            )
        }
    }
}