package r.stookey.exoplanetexplorer.repository

import org.json.JSONObject
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import timber.log.Timber
import javax.inject.Inject


class RepositoryImpl @Inject constructor(var exoplanetApiService: ExoplanetApiService): Repository {

    init {
        Timber.i("Repository init running")
    }

    override suspend fun getAllPlanets(): JSONObject {
        Timber.i("getAllPlanets called")
        return exoplanetApiService.getAllPlanets()
    }
}