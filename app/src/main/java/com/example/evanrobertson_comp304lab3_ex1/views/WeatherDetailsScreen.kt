package com.example.evanrobertson_comp304lab3_ex1.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherResponse
import com.example.evanrobertson_comp304lab3_ex1.viewModels.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailsScreen(onBackPressed: () -> Unit, weather: WeatherResponse) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Weather Details")
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
                }
            )
        },
        content = { paddingValues ->
            WeatherDetailsScreenContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                weather = weather
            )
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeatherDetailsScreenContent(modifier: Modifier, weather: WeatherResponse) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        //Location Name
        Text(
            text = "${weather.location.name}, ${weather.location.country}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        //Weather Condition Icon and Description
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https:${weather.current.condition.icon}",
                contentDescription = weather.current.condition.text,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = weather.current.condition.text,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        //Temperature
        Text(
            text = "Temperature: ${weather.current.temp_c}°C (${weather.current.temp_f}°F)",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        //Additional Information
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
            //Humidity
            SuggestionChip(
                onClick = { },
                label = { Text(text = "Humidity: ${weather.current.humidity}%") }
            )
            //Visibility
            SuggestionChip(
                onClick = { },
                label = { Text(text = "Visibility: ${weather.current.vis_km} km") }
            )
        }
    }
}
