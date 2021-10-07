package r.stookey.exoplanetexplorer.util

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import r.stookey.exoplanetexplorer.domain.Planet
import timber.log.Timber
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

class DataSetUtil {




    fun createCustomDataSet(listOfPlanets: List<Planet>): ScatterDataSet{
        return ScatterDataSet(emptyList(), "Custom Dataset")
    }

    private fun scaleValues(number: Double): Float {
        return log10(number).toFloat()
    }

    fun unscaleValues(number: Double): Float {
        val calcVal = 10.0.pow(number)
        return calcVal.toFloat()
    }

    fun createMassPeriodDistributionDataSet(listOfPlanets: List<Planet>): ScatterDataSet {
        val label = "Mass - Period Distribution"
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryOrbitPeriod != null && planet.planetaryMassJupiter != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryOrbitPeriod),
                    planet.planetaryMassJupiter.toFloat()
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }

    fun createPeriodRadiusDistributionDataSet(listOfPlanets: List<Planet>): ScatterDataSet {
        val label = "Radius - Period Distribution"
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryRadiusEarth != null && planet.planetaryOrbitPeriod != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryOrbitPeriod),
                    planet.planetaryRadiusEarth.toFloat()
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }

    fun createDensityRadiusDistributionDataSet(listOfPlanets: List<Planet>): ScatterDataSet {
        val label = "Density - Radius Distribution"
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetDensity != null && planet.planetaryRadiusEarth != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetDensity),
                    planet.planetaryRadiusEarth.toFloat()
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }


    fun createDiscoveryYearDataSet(listOfPlanets: List<Planet>): BarDataSet {
        val label = "Detections Per Year"
        val planetEntryList = mutableListOf<BarEntry>()
        val yearDiscoveryNumberMap = listOfPlanets.groupBy { planet -> planet.discoveryYear }
        yearDiscoveryNumberMap.keys.forEach { year ->
            val yearBarEntry: BarEntry
            if(year != null){
                val numberOfPlanets = yearDiscoveryNumberMap[year]?.size.let { it?.toFloat() }
                yearBarEntry = BarEntry(year.toFloat(), numberOfPlanets!! )
                planetEntryList.add(yearBarEntry)
            }
        }
        Timber.d("${planetEntryList.size}")
        return BarDataSet(planetEntryList, label)
    }

    fun createEccentricityPeriodDistributionDataSet(listOfPlanets: List<Planet>): ScatterDataSet{
        val label = "Eccentricity - Period Distribution"
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryOrbitPeriod != null && planet.planetaryOrbitalEccentricity != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryOrbitPeriod),
                    planet.planetaryOrbitalEccentricity.toFloat()
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }
}


class MyValueFormatter(): ValueFormatter() {
    private val format = DecimalFormat("###,##0.0")
    override fun getFormattedValue(
        value: Float,
        entry: Entry?,
        dataSetIndex: Int,
        viewPortHandler: ViewPortHandler?
    ): String {
        return format.format(entry?.x)
    }
}