package r.stookey.exoplanetexplorer.domain

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.json.JSONArray
import timber.log.Timber
import javax.inject.Inject

class PlanetDtoImpl @Inject constructor(moshi: Moshi): PlanetDto {

    private var adapter: JsonAdapter<Planet>

    init {
        Timber.d("PlanetMapper initialized")
        adapter = moshi.adapter(Planet::class.java)
    }

    //Converts jsonArray returned from ExoplanetApiService to a list of Planet objects
    override fun convertJsonToPlanets(allPlanetsJson: JSONArray): List<Planet> {
        Timber.d("converting json to list of Planets")
        var planetList = mutableListOf<Planet>()
        for (i in 0 until allPlanetsJson.length()){
            var planet = adapter.fromJson(allPlanetsJson[i].toString())
            planetList.add(planet!!)
        }
        Timber.d("Number of Planets in the list: " + planetList.size)
        return planetList
    }

}