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

    @Query("SELECT * FROM `planets-db` WHERE  planet_name LIKE '%' ||:planetName || '%'")
    fun searchPlanetByName(planetName: String): Flow<List<Planet>>

    @Query("SELECT * FROM `planets-db` WHERE  planet_id LIKE '%' || :planetId || '%'")
    fun searchPlanetByPlanetId(planetId: Int): Flow<Planet>

    @Query("DELETE FROM `planets-db`")
    suspend fun clearPlanets()


    @Query("SELECT EXISTS(SELECT * FROM `planets-db` where planet_name = :planetName )")
    fun isPlanetCached(planetName: String?): Boolean


    //Full Text Search, not in use
    @Query("""SELECT * FROM `planets_fts` WHERE `planets_fts` MATCH :query""")
    suspend fun planetFts(query: String): List<PlanetFts>



}