package com.example.evanrobertson_comp304lab3_ex1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.evanrobertson_comp304lab3_ex1.data.SearchLocation
import com.example.evanrobertson_comp304lab3_ex1.viewModels.WeatherViewModel
import com.example.evanrobertson_comp304lab3_ex1.views.FavoritesScreen
import com.example.evanrobertson_comp304lab3_ex1.views.WeatherDetailsScreen
import com.example.evanrobertson_comp304lab3_ex1.views.WeatherScreen
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel
import java.net.URLDecoder
import java.net.URLEncoder

@Composable
fun AppNavigation(
    contentType: ContentType,
    navHostController: NavHostController = rememberNavController()
) {
    val weatherViewModel: WeatherViewModel = koinViewModel()

    NavHost(
        navController = navHostController,
        startDestination = Screens.WeatherScreen.route
    ) {
        composable(Screens.WeatherScreen.route) {
            WeatherScreen(
                onWeatherClicked = { weather ->
                    //Encode the weather object to a string
                    val encodedWeather = URLEncoder.encode(Json.encodeToString(weather), "UTF-8")

                    //Navigate to the WeatherDetailsScreen with the encoded weather
                    navHostController.navigate("${Screens.WeatherDetailsScreen.route}/$encodedWeather")
                },
                contentType = contentType
            )
        }
        composable(
            route = "${Screens.WeatherDetailsScreen.route}/{weather}",
            arguments = listOf(
                navArgument("weather") {
                    type = NavType.StringType
                }
            )
        ) {
            WeatherDetailsScreen(
                onBackPressed = {
                    navHostController.popBackStack()
                },

                //Decode weather string to object
                weather = Json.decodeFromString(
                    URLDecoder.decode(it.arguments?.getString("weather") ?: "", "UTF-8")
                )
            )
        }
        composable(Screens.FavoriteLocationsScreen.route) {
            FavoritesScreen(
                onLocationClicked = { location ->
                    //Get the weather for the location
                    val weather = weatherViewModel.getWeatherForLocation(location.lon, location.lat)

                    //Encode the weather object to a string
                    val encodedWeather = URLEncoder.encode(Json.encodeToString(weather), "UTF-8")

                    //Navigate to the WeatherDetailsScreen with the encoded weather
                    navHostController.navigate("${Screens.WeatherDetailsScreen.route}/$encodedWeather")
                }
            )
        }
    }
}