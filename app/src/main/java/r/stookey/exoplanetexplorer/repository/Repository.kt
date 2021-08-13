package r.stookey.exoplanetexplorer.repository

import org.json.JSONObject

interface Repository {
    suspend fun getAllPlanets(): JSONObject
}