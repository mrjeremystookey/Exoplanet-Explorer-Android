package r.stookey.exoplanetexplorer.repository

import org.json.JSONObject
import r.stookey.exoplanetexplorer.data.ExoplanetApiService

class RepositoryImpl(private val exoplanetApiService: ExoplanetApiService): Repository {

    override suspend fun getAllPlanets(): JSONObject {
        return exoplanetApiService.getAllPlanets()
    }
}