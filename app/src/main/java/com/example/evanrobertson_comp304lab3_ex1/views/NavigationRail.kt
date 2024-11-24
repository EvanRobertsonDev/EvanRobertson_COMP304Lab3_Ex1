package com.example.evanrobertson_comp304lab3_ex1.views

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.evanrobertson_comp304lab3_ex1.navigation.Screens

@Composable
fun NavigationRail(
    onFavoriteClicked: () -> Unit,
    onHomeClicked: () -> Unit,
    onDrawerClicked: () -> Unit
) {
    val items = listOf(Screens.WeatherScreen, Screens.FavoriteLocationsScreen)
    val selectedItem = remember { mutableStateOf(items[0]) }

    NavigationRail(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        //Menu Item
        NavigationRailItem(
            selected = false,
            onClick = onDrawerClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu Icon"
                )
            }
        )

        //Home Item
        NavigationRailItem(
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
        NavigationRailItem(
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