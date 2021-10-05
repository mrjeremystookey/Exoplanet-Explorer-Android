package r.stookey.exoplanetexplorer.util

import org.json.JSONArray
import r.stookey.exoplanetexplorer.domain.Planet

interface PlanetDto {
    fun convertJsonToPlanets(jsonArray: JSONArray): List<Planet> {
        return emptyList()
    }
}