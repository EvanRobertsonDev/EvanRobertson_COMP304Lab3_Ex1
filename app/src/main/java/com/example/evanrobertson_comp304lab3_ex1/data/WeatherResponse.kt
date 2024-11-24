package com.example.evanrobertson_comp304lab3_ex1.data

import kotlinx.serialization.Serializable

//API friendly weather response
@Serializable
data class WeatherResponse(
    val location: Location = Location(),
    val current: Current = Current()
)

@Serializable
data class Location(
    var isFavorite: Boolean = false,
    val name: String = "",
    val region: String = "",
    val country: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val tz_id: String = "",
    val localtime_epoch: Long = 0,
    val localtime: String = ""
)

@Serializable
data class Current(
    val last_updated_epoch: Long = 0,
    val last_updated: String = "",
    val temp_c: Double = 0.0,
    val temp_f: Double = 0.0,
    val is_day: Int = 0,
    val condition: Condition = Condition(),
    val wind_mph: Double = 0.0,
    val wind_kph: Double = 0.0,
    val wind_degree: Int = 0,
    val wind_dir: String = "",
    val pressure_mb: Double = 0.0,
    val pressure_in: Double = 0.0,
    val precip_mm: Double = 0.0,
    val precip_in: Double = 0.0,
    val humidity: Int = 0,
    val cloud: Int = 0,
    val feelslike_c: Double = 0.0,
    val feelslike_f: Double = 0.0,
    val windchill_c: Double = 0.0,
    val windchill_f: Double = 0.0,
    val heatindex_c: Double = 0.0,
    val heatindex_f: Double = 0.0,
    val dewpoint_c: Double = 0.0,
    val dewpoint_f: Double = 0.0,
    val vis_km: Double = 0.0,
    val vis_miles: Double = 0.0,
    val uv: Double = 0.0,
    val gust_mph: Double = 0.0,
    val gust_kph: Double = 0.0
)

@Serializable
data class Condition(
    val text: String = "",
    val icon: String = "",
    val code: Int = 0
)