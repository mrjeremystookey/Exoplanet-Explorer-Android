package r.stookey.exoplanetexplorer.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import r.stookey.exoplanetexplorer.domain.Planet

@Dao
interface PlanetDao {
    @Insert
    suspend fun insert(planet: Planet)

    @Query("SELECT * FROM `planets-db`")
    suspend fun getAll(): List<Planet>

    @Query("DELETE FROM `planets-db`")
    suspend fun clearCache()

    @Query("SELECT * FROM `planets-db` WHERE planet_name = :planetName")
    suspend fun search(planetName: String): List<Planet>
}