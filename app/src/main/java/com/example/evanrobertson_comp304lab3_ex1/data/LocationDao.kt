package com.example.evanrobertson_comp304lab3_ex1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    //Inserts location into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    //Gets all favorite locations from database
    @Query("SELECT * FROM Locations")
    fun getAllLocations(): Flow<List<Location>>

    //Deletes location from database
    @Delete
    suspend fun delete(location: LocationEntity)

    //Gets specific location from database
    @Query("SELECT * FROM Locations WHERE lon = :lon AND lat = :lat")
    suspend fun getLocation(lon: Double, lat:Double): LocationEntity

    //Check if location is favorited
    @Query("SELECT EXISTS (SELECT 1 FROM Locations WHERE lon = :lon AND lat = :lat)")
    fun isFavorite(lon: Double, lat: Double): Flow<Boolean>
}