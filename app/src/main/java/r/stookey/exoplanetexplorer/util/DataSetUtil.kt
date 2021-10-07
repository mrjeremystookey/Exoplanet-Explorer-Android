package r.stookey.exoplanetexplorer.util

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

class DataSetUtil {

    //Used for scaling values to Logarithmic
    private fun scaleValues(number: Double): Float {
        return log10(number).toFloat()
    }

    //Scatter Charts
    fun createMassPeriodDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet {
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

    fun radiusPeriodDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet {
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

    fun createDensityRadiusDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet {
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

    fun createEccentricityPeriodDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet{
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

    fun createDensityMassDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet{
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetDensity != null && planet.planetaryMassJupiter != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetDensity),
                    scaleValues(planet.planetaryMassJupiter)
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }


    fun createCustomDistribution(listOfPlanets: List<Planet>, label: String, xValue: Double, yValue: Double): ScatterDataSet{
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (xValue != null && yValue != null) {
                val planetEntry = Entry(
                    scaleValues(xValue),
                    scaleValues(yValue)
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label+ " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }



    //Bar Charts
    fun createDiscoveryYearDataSet(listOfPlanets: List<Planet>, label: String): BarDataSet {
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