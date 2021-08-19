package r.stookey.exoplanetexplorer.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import r.stookey.exoplanetexplorer.domain.Planet

@Dao
interface PlanetDao {
    @Insert
    suspend fun insert(planet: Planet)

    @Query("SELECT * FROM `planets-db`")
    fun getAll(): List<Planet>
}