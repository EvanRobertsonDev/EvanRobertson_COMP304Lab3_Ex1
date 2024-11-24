package com.example.evanrobertson_comp304lab3_ex1.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.evanrobertson_comp304lab3_ex1.data.Location
import com.example.evanrobertson_comp304lab3_ex1.viewModels.WeatherViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    onLocationClicked: (Location) -> Unit
) {
    val weatherViewModel: WeatherViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        weatherViewModel.getFavoriteLocations()
    }
    val locations by weatherViewModel.favoriteLocations.collectAsStateWithLifecycle()

    if (locations.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No favorite locations")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(locations) { location ->
                LocationListItem(
                    location = location,
                    onLocationClicked = onLocationClicked,
                    onFavoriteClicked = {
                        weatherViewModel.removeLocation(it)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LocationListItem(location: Location, onLocationClicked: (Location) -> Unit, onFavoriteClicked: (Location) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { onLocationClicked(location) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {

            // Display Location Name
            Text(
                text = location.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(6.dp)
            )

            // Display Additional Information
            FlowRow(
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            ) {
                //Region
                SuggestionChip(
                    onClick = { },
                    label = { Text(text = "Region: ${location.region}") }
                )
                //Country
                SuggestionChip(
                    onClick = { },
                    label = { Text(text = "Country: ${location.country}") }
                )
                //Time
                SuggestionChip(
                    onClick = { },
                    label = { Text(text = "Time: ${location.localtime}") }
                )
            }
            Icon(
                modifier = Modifier
                    .clickable {
                        //Toggle Favorite
                        onFavoriteClicked(location.copy(isFavorite = !location.isFavorite))
                    },
                imageVector = if (location.isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = "Favorite",
                tint = if (location.isFavorite) {
                    Color.Red
                } else {
                    Color.Gray
                },
            )
        }
    }
}