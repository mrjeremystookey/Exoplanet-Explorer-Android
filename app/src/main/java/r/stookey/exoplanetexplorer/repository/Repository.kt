package r.stookey.exoplanetexplorer.repository

import kotlinx.coroutines.flow.Flow
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetFts

interface Repository {
     suspend fun getPlanetsFromNetwork(): List<Planet>
     suspend fun searchPlanetsFullText(query: String): List<PlanetFts>
     suspend fun insertPlanetIntoCache(planet:Planet)
     suspend fun removeAllPlanetsFromCache()
     fun searchPlanetsFromCache(query: String): Flow<List<Planet>>
     val getAllPlanetsFromCache: Flow<List<Planet>>

}