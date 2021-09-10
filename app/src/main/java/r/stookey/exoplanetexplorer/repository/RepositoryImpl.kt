package r.stookey.exoplanetexplorer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetDtoImpl
import r.stookey.exoplanetexplorer.domain.PlanetFts
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import timber.log.Timber
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private var exoplanetApiService: ExoplanetApiService,
                                         private var planetMapper: PlanetDtoImpl,
                                         private var db: PlanetDatabase
                                         ): Repository {

    //needs to be injected
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val externalScope: CoroutineScope = CoroutineScope(Dispatchers.IO)


    //Used to tell ViewModel when Cache is up to date
    private val _doneAdding: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val doneAdding: LiveData<Boolean>
        get() = _doneAdding


    init {
        Timber.i("Repository init running")
    }

    //Should only run when called specifically, when data is deleted from the network, or needs to be updated
    override suspend fun getPlanetsFromNetwork() {
        val jsonArray = exoplanetApiService.getPlanets()  //gets JsonArray of Planets from ApiService
        val planetList = planetMapper.convertJsonToPlanets(jsonArray)  //Converts JsonArray into List of domain model, Planet objects
         checkAndInsertPlanetIntoCache(planetList)  //Adds the Planet object into Room database
    }


    //Checks if Planets have already been cached, if not, adds them to cache
    override suspend fun checkAndInsertPlanetIntoCache(planetList: List<Planet>) {
        _doneAdding.value = false
        Timber.d("doneAdding value: ${_doneAdding.value}")
        withContext(externalScope.coroutineContext) {
            planetList.forEach { planet ->
                if (db.planetDao().isPlanetCached(planet.planetName)) {
                    Timber.d("Planet is already cached")
                } else {
                    Timber.d("adding planet, " + planet.planetName + " to local Planet Database")
                    db.planetDao().insert(planet)
                }
            }
            Timber.d("done adding Planets to local cache")
        }
        _doneAdding.value = true
        Timber.d("doneAdding value: ${_doneAdding.value}")
    }

    //used when searching for a planet
    override fun searchPlanetsFromCache(query: String): Flow<List<Planet>> {
        Timber.d("searching database for $query")
        return db.planetDao().searchPlanetByName(query)
            .flowOn(defaultDispatcher)
            .conflate()
    }

    //used when loading list of planets
    override val getAllPlanetsFromCache: Flow<List<Planet>>
        get() = db.planetDao().getAllPlanets()
            .flowOn(defaultDispatcher)
            .conflate()

    //Pretty obvious what it does
    override suspend fun removeAllPlanetsFromCache() {
        Timber.d("removing Planets from local Planet Database")
        db.planetDao().clearPlanets()
    }


    //Used for FTS, not in use
    override suspend fun searchPlanetsFromCacheFullText(query: String): List<PlanetFts> {
        Timber.d("searching full text of Planets for $query")
        return db.planetDao().planetFts(query)
    }
}