package com.example.evanrobertson_comp304lab3_ex1.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evanrobertson_comp304lab3_ex1.data.Location
import com.example.evanrobertson_comp304lab3_ex1.data.WeatherResponse
import com.example.evanrobertson_comp304lab3_ex1.navigation.ContentType

@Composable
fun WeatherScreenContent(
    modifier: Modifier,
    onWeatherClicked: (WeatherResponse) -> Unit,
    onFavoriteClicked: (Location) -> Unit,
    contentType: ContentType,
    weatherUIState: WeatherUIState,
    onSearch: (String) -> Unit
) {
    //Search query
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { onSearch(searchQuery) }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        )

        //Load until data is received
        AnimatedVisibility(
            visible = weatherUIState.isLoading
        ) {
            CircularProgressIndicator()
        }

        //Show weather list
        AnimatedVisibility(
            visible = weatherUIState.weather.isNotEmpty()
        ) {
            if (contentType == ContentType.List) {
                WeatherList(
                    onWeatherClicked = onWeatherClicked,
                    weather = weatherUIState.weather,
                    onFavoriteClicked = onFavoriteClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                WeatherListAndDetails(
                    weather = weatherUIState.weather,
                    onFavoriteClicked = onFavoriteClicked
                )
            }

        }
        AnimatedVisibility(
            visible = weatherUIState.error != null
        ) {
            Text(text = weatherUIState.error ?: "")
        }
    }
}