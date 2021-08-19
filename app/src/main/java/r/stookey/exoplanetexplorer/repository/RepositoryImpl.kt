package r.stookey.exoplanetexplorer.repository

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import com.squareup.moshi.Moshi
import org.json.JSONArray
import r.stookey.exoplanetexplorer.cache.PlanetDatabase
import r.stookey.exoplanetexplorer.domain.PlanetDtoImpl
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetDto
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import timber.log.Timber
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private var exoplanetApiService: ExoplanetApiService,
                                         private var planetMapper: PlanetDtoImpl): Repository {

    @Inject
    lateinit var db: PlanetDatabase

    init {
        Timber.i("Repository init running")
    }

    override suspend fun getAllPlanetsFromNetwork(): List<Planet> {
        Timber.i("getAllPlanetsFromNetwork called")
        val jsonArray = exoplanetApiService.getAllPlanets()
        val planetList = planetMapper.convertJsonToPlanets(jsonArray)
        planetList.forEach { planet ->
            insertPlanetIntoCache(planet)
        }
        return planetList
    }

    override suspend fun insertPlanetIntoCache(planet: Planet) {
        Timber.d("adding planet to local cache")
        db.planetDao().insert(planet)
    }

    override suspend fun getAllPlanetsFromCache(): List<Planet> {
        Timber.d("retrieving planets from local cache")
        return db.planetDao().getAll()
    }
}