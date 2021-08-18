package r.stookey.exoplanetexplorer.repository

import org.json.JSONArray
import r.stookey.exoplanetexplorer.domain.DomainDtoImpl
import r.stookey.exoplanetexplorer.network.ExoplanetApiService
import timber.log.Timber
import javax.inject.Inject


class RepositoryImpl @Inject constructor(var exoplanetApiService: ExoplanetApiService): Repository {

    private var planetMapper = DomainDtoImpl()

    init {
        Timber.i("Repository init running")
    }

    override suspend fun getAllPlanets(): JSONArray {
        Timber.i("getAllPlanets called")
        val jsonArray = exoplanetApiService.getAllPlanets()
        planetMapper.convertJsonToPlanets(jsonArray)
        return jsonArray
    }




}