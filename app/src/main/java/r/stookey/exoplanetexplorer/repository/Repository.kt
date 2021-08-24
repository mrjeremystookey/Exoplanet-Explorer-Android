package r.stookey.exoplanetexplorer.repository

import org.json.JSONArray
import org.json.JSONObject
import r.stookey.exoplanetexplorer.domain.Planet

interface Repository {
     suspend fun getPlanetsFromNetwork(query: String): List<Planet>
     suspend fun searchPlanetsFromCache(query: String): List<Planet>
     suspend fun getAllPlanetsFromCache(): List<Planet>
     suspend fun insertPlanetIntoCache(planet:Planet)
     suspend fun removeAllPlanetsFromCache()
}