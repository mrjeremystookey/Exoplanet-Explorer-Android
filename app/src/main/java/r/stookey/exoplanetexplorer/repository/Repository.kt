package r.stookey.exoplanetexplorer.repository

import org.json.JSONArray
import org.json.JSONObject
import r.stookey.exoplanetexplorer.domain.Planet

interface Repository {
     suspend fun getAllPlanetsFromNetwork(): List<Planet>
     suspend fun insertPlanetIntoCache(planet:Planet)
     suspend fun getAllPlanetsFromCache(): List<Planet>
}