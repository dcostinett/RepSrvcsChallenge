package com.costinett.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Dao
interface DriverDao {
    @Query("SELECT * FROM drivers")
    fun getAllDrivers() : List<Driver>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(drivers: List<Driver>)

    @Query("DELETE FROM drivers")
    fun deleteAll()
}

@Entity(tableName =  "drivers", indices =  [Index("id")])
data class Driver(
    @PrimaryKey val id: String,
    val name: String
)