package r.stookey.exoplanetexplorer.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import r.stookey.exoplanetexplorer.domain.PlanetDtoImpl
import r.stookey.exoplanetexplorer.domain.Planet
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


    override suspend fun getPlanetsFromNetwork(query: String): List<Planet> {
        val jsonArray = exoplanetApiService.getPlanets(query)  //gets JsonArray of Planets from ApiService
        val planetList = planetMapper.convertJsonToPlanets(jsonArray)  //Converts JsonArray into List of domain model, Planet objects
        Timber.d("getAllPlanetsFromNetwork called, number of planets retrieved: " + planetList.size)
        planetList.forEach { planet ->
            //Need to add if already in cache, don't add
            insertPlanetIntoCache(planet)  //Adds the Planet object into the list
        }
        Timber.d("done adding Planets to local cache")
        return planetList
    }


    override suspend fun insertPlanetIntoCache(planet: Planet) {
        Timber.d("adding planet, "+ planet.planetName + " to local Planet Database")
        db.planetDao().insert(planet)
    }

    override suspend fun getAllPlanetsFromCache(): List<Planet> {
        Timber.d("retrieving planets from local Planet database")
        return db.planetDao().getAll()
    }

    override suspend fun removeAllPlanetsFromCache() {
        Timber.d("removing Planets from local Planet Database")
        db.planetDao().clearCache()
    }

    override suspend fun searchPlanetsFromCache(query: String): List<Planet> {
        Timber.d("searching database for $query")
        return db.planetDao().search(query)
    }

    override suspend fun searchPlanetsFullText(query: String): List<PlanetFts> {
        Timber.d("searching full text of Planets for $query")
        return db.planetDao().planetFulLTextSearch(query)
    }

    val planetFlow: Flow<List<Planet>>
        get() = db.planetDao().getAllPlanetsFlow()
            .flowOn(defaultDispatcher)
            .conflate()
}