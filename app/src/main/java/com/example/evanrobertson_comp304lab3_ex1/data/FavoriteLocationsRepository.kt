package com.example.evanrobertson_comp304lab3_ex1.data

import kotlinx.coroutines.flow.Flow

class FavoriteLocationsRepository(private val dao: LocationDao) {
    //Gets all favorite locations from database
    val allLocations: Flow<List<Location>> = dao.getAllLocations()

    //Inserts location into database
    suspend fun insertLocation(location: Location) {

        //Convert to entity
        val entity = LocationEntity(
            0,
            location.isFavorite,
            location.name,
            location.region,
            location.country,
            location.lat,
            location.lon,
            location.tz_id,
            location.localtime_epoch,
            location.localtime
        )

        dao.insert(entity)
    }

    //Deletes location into database
    suspend fun removeLocation(location: Location, id : Int) {

        //Convert to entity
        val entity = LocationEntity(
            id,
            location.isFavorite,
            location.name,
            location.region,
            location.country,
            location.lat,
            location.lon,
            location.tz_id,
            location.localtime_epoch,
            location.localtime
        )

        dao.delete(entity)
    }

    //Gets specific location in database
    suspend fun getLocation(lon: Double, lat: Double): LocationEntity {
        return dao.getLocation(lon, lat)
    }

    //Checks if location is favorited
    fun isFavorite(location : Location): Flow<Boolean> {
        return dao.isFavorite(location.lon, location.lat)
    }
}