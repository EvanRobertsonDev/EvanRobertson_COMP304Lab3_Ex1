package com.example.evanrobertson_comp304lab3_ex1.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.evanrobertson_comp304lab3_ex1.navigation.Screens

@Composable
fun BottomNavigationBar(
    onFavoriteClicked: () -> Unit,
    onHomeClicked: () -> Unit
) {
    val items = listOf(Screens.WeatherScreen, Screens.FavoriteLocationsScreen)
    val selectedItem = remember { mutableStateOf(items[0]) }
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        //Home Item
        NavigationBarItem(
            selected = selectedItem.value == Screens.WeatherScreen,
            onClick = {
                onHomeClicked()
                selectedItem.value = Screens.WeatherScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home Icon"
                )
            }
        )

        //Favorites Item
        NavigationBarItem(
            selected = selectedItem.value == Screens.FavoriteLocationsScreen,
            onClick = {
                onFavoriteClicked()
                selectedItem.value = Screens.FavoriteLocationsScreen
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite Icon"
                )
            }
        )
    }
}