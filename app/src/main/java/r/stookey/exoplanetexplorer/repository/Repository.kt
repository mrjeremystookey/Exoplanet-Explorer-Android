package r.stookey.exoplanetexplorer.repository

import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetFts

interface Repository {
     suspend fun getPlanetsFromNetwork(query: String): List<Planet>
     suspend fun searchPlanetsFromCache(query: String): List<Planet>
     suspend fun searchPlanetsFullText(query: String): List<PlanetFts>
     suspend fun getAllPlanetsFromCache(): List<Planet>
     suspend fun insertPlanetIntoCache(planet:Planet)
     suspend fun removeAllPlanetsFromCache()
}