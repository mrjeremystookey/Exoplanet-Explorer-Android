package r.stookey.exoplanetexplorer.repository

import kotlinx.coroutines.flow.Flow
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetFts

interface Repository {
     suspend fun checkAndInsertPlanetIntoCache(planet:Planet)
     suspend fun removeAllPlanetsFromCache()

     suspend fun getPlanetsFromNetwork()
     suspend fun searchPlanetsFromCacheFullText(query: String): List<PlanetFts>

     fun searchPlanetsFromCache(query: String): Flow<List<Planet>>
     val getAllPlanetsFromCache: Flow<List<Planet>>
     fun getPlanetFromCache(planetId: Int): Flow<Planet>


}