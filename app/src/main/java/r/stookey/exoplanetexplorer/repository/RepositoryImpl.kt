package r.stookey.exoplanetexplorer.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetDtoImpl
import r.stookey.exoplanetexplorer.domain.PlanetFts
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import timber.log.Timber
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private var exoplanetApiService: ExoplanetApiService,
                                         private var planetMapper: PlanetDtoImpl,
                                         ): Repository {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default


    @Inject
    lateinit var db: PlanetDatabase

    init {
        Timber.i("Repository init running")
    }

    //Should only run when called specifically, when data is deleted from the network, or needs to be updated
    override suspend fun getPlanetsFromNetwork(): List<Planet> {
        val jsonArray = exoplanetApiService.getPlanets()  //gets JsonArray of Planets from ApiService
        val planetList = planetMapper.convertJsonToPlanets(jsonArray)  //Converts JsonArray into List of domain model, Planet objects
        Timber.d("getAllPlanetsFromNetwork called, number of planets retrieved: " + planetList.size)
        planetList.forEach { planet ->
            //Need to add if already in cache, don't add
            insertPlanetIntoCache(planet)  //Adds the Planet object into Room database
        }
        Timber.d("done adding Planets to local cache")
        return planetList
    }


    override suspend fun insertPlanetIntoCache(planet: Planet) {
        Timber.d("adding planet, "+ planet.planetName + " to local Planet Database")
        db.planetDao().insert(planet)
    }

    override suspend fun removeAllPlanetsFromCache() {
        Timber.d("removing Planets from local Planet Database")
        db.planetDao().clearCache()
    }


    override suspend fun searchPlanetsFullText(query: String): List<PlanetFts> {
        Timber.d("searching full text of Planets for $query")
        return db.planetDao().planetFulLTextSearch(query)
    }


    override fun searchPlanetsFromCache(query: String): Flow<List<Planet>>{
        Timber.d("searching database for $query")
        return db.planetDao()
            .searchWithFlow(query)
            .flowOn(defaultDispatcher)
            .conflate()
    }

    override val getAllPlanetsFromCache: Flow<List<Planet>>
        get() = db.planetDao().getAllPlanets()
            .flowOn(defaultDispatcher)
            .conflate()
}