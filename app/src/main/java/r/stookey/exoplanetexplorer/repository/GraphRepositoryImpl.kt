package r.stookey.exoplanetexplorer.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import r.stookey.exoplanetexplorer.di.IoDispatcher
import r.stookey.exoplanetexplorer.domain.Planet
import javax.inject.Inject

class GraphRepositoryImpl @Inject constructor(private var db: PlanetDatabase,
                                              @IoDispatcher private val ioDispatcher: CoroutineDispatcher): GraphRepository {
    //used when loading list of planets
     override val getAllPlanetsFromCache: Flow<List<Planet>>
        get() = db.planetDao().getAllPlanets()
            .flowOn(ioDispatcher)
            .conflate()
}