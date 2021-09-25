package r.stookey.exoplanetexplorer.ui.graphs.plots

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterDataSet
import r.stookey.exoplanetexplorer.domain.Planet
import timber.log.Timber

class DataSetUtil {

    fun createScatterDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet {
        var planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryOrbitPeriod != null && planet.planetaryMassJupiter != null) {
                var planetEntry = Entry(
                    planet.planetaryOrbitPeriod.toFloat(),
                    planet.planetaryMassJupiter.toFloat()
                )
                planetEntryList.add(planetEntry)
            }
        }
        return ScatterDataSet(planetEntryList, label)
    }

    fun createBarChartDataSet(listOfPlanets: List<Planet>, label: String): BarDataSet {
        var planetEntryList = mutableListOf<BarEntry>()
        var yearDiscoveryList = mutableListOf<String>()

        listOfPlanets.forEach { planet ->
            //gets list of all years in the data set
            if (planet.discoveryYear != null && !yearDiscoveryList.contains(planet.discoveryYear))
                yearDiscoveryList.add(planet.discoveryYear)


        }

        Timber.d("Planets were discovered in: $yearDiscoveryList")


        return BarDataSet(planetEntryList, label)
    }
}