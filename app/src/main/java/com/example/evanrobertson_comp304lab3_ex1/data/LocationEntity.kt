package com.example.evanrobertson_comp304lab3_ex1.data

import androidx.room.Entity
import androidx.room.PrimaryKey


//Database location entity
@Entity(tableName = "Locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
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
