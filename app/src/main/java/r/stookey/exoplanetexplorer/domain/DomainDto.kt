package r.stookey.exoplanetexplorer.domain

import org.json.JSONArray

interface DomainDto {
    fun convertJsonToPlanets(returnedJson: JSONArray): List<Planet> {
        return emptyList()
    }
}