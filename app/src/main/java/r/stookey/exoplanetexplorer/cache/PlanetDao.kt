package r.stookey.exoplanetexplorer.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetFts

@Dao
interface PlanetDao {
    @Insert
    suspend fun insert(planet: Planet)

    @Query("SELECT * FROM `planets-db`")
    suspend fun getAll(): List<Planet>

    @Query("DELETE FROM `planets-db`")
    suspend fun clearCache()

    @Query("SELECT * FROM `planets-db` WHERE  planet_name LIKE '%' ||:planetName || '%'")
    suspend fun search(planetName: String): List<Planet>

    @Query("""SELECT * FROM `planets_fts` WHERE `planets_fts` MATCH :query""")
    suspend fun planetFulLTextSearch(query: String): List<PlanetFts>
}