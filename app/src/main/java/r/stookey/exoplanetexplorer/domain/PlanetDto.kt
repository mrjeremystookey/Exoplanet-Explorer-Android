package r.stookey.exoplanetexplorer.domain

import org.json.JSONArray

interface PlanetDto {
    fun convertJsonToPlanets(jsonArray: JSONArray): List<Planet> {
        return emptyList()
    }
}