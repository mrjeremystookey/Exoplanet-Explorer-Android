package r.stookey.exoplanetexplorer.repository

import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import r.stookey.exoplanetexplorer.data.ExoplanetApiService
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