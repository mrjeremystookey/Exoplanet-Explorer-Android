package r.stookey.exoplanetexplorer.ui.graphs.plots

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterDataSet
import r.stookey.exoplanetexplorer.domain.Planet
import timber.log.Timber

class DataSetUtil {

    fun createMassPeriodDistributionDataSet(listOfPlanets: List<Planet>): ScatterDataSet {
        val label = "Mass - Period Distribution"
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryOrbitPeriod != null && planet.planetaryMassJupiter != null) {
                val planetEntry = Entry(
                    planet.planetaryOrbitPeriod.toFloat(),
                    planet.planetaryMassJupiter.toFloat()
                )
                planetEntryList.add(planetEntry)
            }
        }
        return ScatterDataSet(planetEntryList, label)
    }

    fun createPeriodRadiusDistributionDataSet(listOfPlanets: List<Planet>): ScatterDataSet {
        val label = "Radius - Period Distribution"
        val planetEntryList = mutableListOf<Entry>()
        return ScatterDataSet(planetEntryList, label)
    }

    fun createDiscoveryYearDataSet(listOfPlanets: List<Planet>): BarDataSet {
        val label = "Detections Per Year"
        val planetEntryList = mutableListOf<BarEntry>()
        val yearDiscoveryNumberMap = listOfPlanets.groupBy { planet -> planet.discoveryYear }
        yearDiscoveryNumberMap.keys.forEach { year ->
            val yearBarEntry: BarEntry
            if(year != null){
                Timber.d("year: $year, planets discovered: ${yearDiscoveryNumberMap[year]?.size}")
                val numberOfPlanets = yearDiscoveryNumberMap[year]?.size.let { it?.toFloat() }
                yearBarEntry = BarEntry(year.toFloat(), numberOfPlanets!! )
                Timber.d("yearBarEntry: $yearBarEntry")
                planetEntryList.add(yearBarEntry)
            }
        }
        return BarDataSet(planetEntryList, label)
    }


}