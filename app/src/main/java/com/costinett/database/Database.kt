package com.costinett.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.costinett.database.daos.Driver
import com.costinett.database.daos.DriverDao
import com.costinett.database.daos.Route
import com.costinett.database.daos.RouteDao
import com.costinett.model.DriversAndRoutes

@Database(
    entities = [Driver::class, Route::class],
    version =  1
)

abstract class Database : RoomDatabase() {
    abstract fun driverDao() : DriverDao
    abstract fun routeDao() : RouteDao
}