package r.stookey.exoplanetexplorer.domain

import org.json.JSONArray

interface PlanetDto {
    fun convertJsonToPlanets(returnedJson: JSONArray): List<Planet> {
        return emptyList()
    }
}