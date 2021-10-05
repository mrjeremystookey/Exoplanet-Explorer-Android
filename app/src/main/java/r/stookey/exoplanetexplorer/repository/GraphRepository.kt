package r.stookey.exoplanetexplorer.repository

import kotlinx.coroutines.flow.Flow
import r.stookey.exoplanetexplorer.domain.Planet

interface GraphRepository {
    val getAllPlanetsFromCache: Flow<List<Planet>>     //Gets all
}