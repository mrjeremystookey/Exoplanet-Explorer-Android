package r.stookey.exoplanetexplorer.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetFts

@Dao
interface PlanetDao {
    @Insert
    suspend fun insert(planet: Planet)

    @Query("SELECT * FROM `planets-db`")
    fun getAllPlanets(): Flow<List<Planet>>

    @Query("DELETE FROM `planets-db`")
    suspend fun clearCache()

    @Query("SELECT * FROM `planets-db` WHERE  planet_name LIKE '%' ||:planetName || '%'")
    suspend fun search(planetName: String): List<Planet>

    @Query("""SELECT * FROM `planets_fts` WHERE `planets_fts` MATCH :query""")
    suspend fun planetFulLTextSearch(query: String): List<PlanetFts>

    @Query("SELECT * FROM `planets-db` WHERE  planet_name LIKE '%' ||:planetName || '%'")
    fun searchWithFlow(planetName: String): Flow<List<Planet>>

}