package r.stookey.exoplanetexplorer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import r.stookey.exoplanetexplorer.di.IoDispatcher
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetFts
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import r.stookey.exoplanetexplorer.util.PlanetDtoImpl
import timber.log.Timber
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private var exoplanetApiService: ExoplanetApiService,
                                         private var planetMapper: PlanetDtoImpl,
                                         private var db: PlanetDatabase,
                                         var workManager: WorkManager,
                                         @IoDispatcher private val ioDispatcher: CoroutineDispatcher): Repository {




    //Used to tell ViewModel when Cache is up to date
    private val _doneAdding: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val doneAdding: LiveData<Boolean>
        get() = _doneAdding



    init {
        Timber.i("Repository init running")
    }


    override suspend fun getPlanetsFromNetwork() {
        val jsonArray = exoplanetApiService.getPlanets()  //gets JsonArray of Planets from ApiService
        val planetList = planetMapper.convertJsonToPlanets(jsonArray)  //Converts JsonArray into List of domain model, Planet objects
        checkAndInsertPlanetIntoCache(planetList)  //Adds the Planet object into Room database
    }


    //Checks if Planets have already been cached, if not, adds them to cache
    override suspend fun checkAndInsertPlanetIntoCache(planetList: List<Planet>) {
        _doneAdding.value = false
        Timber.d("doneAdding value: ${_doneAdding.value}")
        withContext(ioDispatcher) {
            planetList.forEach { planet ->
                if (!db.planetDao().isPlanetCached(planet.planetName)) {
                    db.planetDao().insert(planet)
                    Timber.d("adding planet, " + planet.planetName + " to local Planet Database")
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
            .flowOn(ioDispatcher)
            .conflate()
    }

    //used when loading list of planets
    override val getAllPlanetsFromCache: Flow<List<Planet>>
        get() = db.planetDao().getAllPlanets()
            .flowOn(ioDispatcher)
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