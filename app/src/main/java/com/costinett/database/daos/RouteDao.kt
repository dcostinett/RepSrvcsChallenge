package com.costinett.database.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.costinett.model.Type

@Dao
interface RouteDao {
    @Query("SELECT * FROM routes")
    fun getAllRoutes() : List<Route>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(routes: List<Route>)

    @Query("DELETE FROM drivers")
    fun deleteAll()
}

@Entity(tableName = "routes", indices = [Index("id")])
data class Route(
    @PrimaryKey val id: Long,
    val type: Type,
    val name: String
)