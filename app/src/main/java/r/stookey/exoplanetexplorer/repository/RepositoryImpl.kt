package r.stookey.exoplanetexplorer.repository

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

    @Inject
    lateinit var db: PlanetDatabase

    init {
        Timber.i("Repository init running")
    }


    override suspend fun getPlanetsFromNetwork(query: String): List<Planet> {
        val jsonArray = exoplanetApiService.getPlanets(query)
        val planetList = planetMapper.convertJsonToPlanets(jsonArray)
        Timber.d("getAllPlanetsFromNetwork called, number of planets retrieved: " + planetList.size)
        planetList.forEach { planet ->
            //Need to add if already in cache, don't add
            insertPlanetIntoCache(planet)
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
}