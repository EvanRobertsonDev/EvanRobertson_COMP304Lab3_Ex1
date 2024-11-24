package com.example.evanrobertson_comp304lab3_ex1.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//Favorite Locations database
@Database(
    entities = [LocationEntity::class],
    version = 2
)
@TypeConverters(LocationsTypeConverters::class)
abstract class LocationDatabase: RoomDatabase() {
    abstract fun locationDao(): LocationDao
}