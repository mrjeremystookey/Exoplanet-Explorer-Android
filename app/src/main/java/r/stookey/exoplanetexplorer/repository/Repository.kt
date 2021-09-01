package r.stookey.exoplanetexplorer.repository

import kotlinx.coroutines.flow.Flow
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetFts

interface Repository {
     suspend fun checkAndInsertPlanetIntoCache(planet:List<Planet>)  //Checks if planet is already cached, if not, caches it
     suspend fun removeAllPlanetsFromCache() //Empties local database
     suspend fun getPlanetsFromNetwork()  //gets planets json from API
     fun searchPlanetsFromCache(query: String): Flow<List<Planet>>   //Searches for a planet by name
     val getAllPlanetsFromCache: Flow<List<Planet>>     //Gets all
     suspend fun searchPlanetsFromCacheFullText(query: String): List<PlanetFts>  //searches cache full text for instances of query
}