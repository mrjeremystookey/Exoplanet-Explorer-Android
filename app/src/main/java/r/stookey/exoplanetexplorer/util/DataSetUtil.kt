 package r.stookey.exoplanetexplorer.util

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import r.stookey.exoplanetexplorer.domain.Planet
import timber.log.Timber
import kotlin.math.log10
import kotlin.math.pow

 
fun unscaleValues(cbr: Double): Float {
    val calcVal = 10.0.pow(cbr)
    return calcVal.toFloat()
}

//Used for scaling values to Logarithmic
fun scaleValues(number: Double): Float {
    return log10(number).toFloat()
}

class DataSetUtil {

    //Scatter Charts
    fun massPeriodDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet {
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryOrbitPeriod != null && planet.planetaryMassJupiter != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryOrbitPeriod),
                    scaleValues(planet.planetaryMassJupiter)
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label + " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }

    fun radiusPeriodDistributionDataSet(
        listOfPlanets: List<Planet>,
        label: String
    ): ScatterDataSet {
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryRadiusEarth != null && planet.planetaryOrbitPeriod != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryOrbitPeriod),
                    scaleValues(planet.planetaryRadiusEarth)
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label + " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }

    fun densityRadiusDistributionDataSet(
        listOfPlanets: List<Planet>,
        label: String
    ): ScatterDataSet {
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetDensity != null && planet.planetaryRadiusEarth != null) {
                val planetEntry = Entry(
                    scaleValues(planet.planetaryRadiusEarth),
                    scaleValues(planet.planetDensity)
                )
                planetEntryList.add(planetEntry)
            }
        }
        Timber.d(label + " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }

    fun eccentricityPeriodDistributionDataSet(
        listOfPlanets: List<Planet>,
        label: String
    ): ScatterDataSet {
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
        Timber.d(label + " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }

    fun densityMassDistributionDataSet(listOfPlanets: List<Planet>, label: String): ScatterDataSet {
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetDensity != null && planet.planetaryMassJupiter != null) {
                planetEntryList.add(
                    Entry(
                        scaleValues(planet.planetaryMassJupiter),
                        scaleValues(planet.planetDensity)
                    )
                )
            }
        }
        Timber.d(label + " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }

    fun earthMassRadiusDistributionDataSet(
        listOfPlanets: List<Planet>,
        label: String
    ): ScatterDataSet {
        val planetEntryList = mutableListOf<Entry>()
        listOfPlanets.forEach { planet ->
            if (planet.planetaryMassEarth != null && planet.planetaryRadiusEarth != null) {
                planetEntryList.add(
                    Entry(
                        scaleValues(planet.planetaryMassEarth),
                        scaleValues(planet.planetaryRadiusEarth)
                    )
                )
            }
        }
        Timber.d(label + " " + planetEntryList.size)
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

    //Doesn't currently work
    fun createDiscoveryMethodDataSet(listOfPlanets: List<Planet>, label: String): BarDataSet{
        val systemEntryList = mutableListOf<BarEntry>()
        val planetarySystemList = listOfPlanets.groupBy { planet -> planet.isPlanetControversial }
        val discoveryMethodList = listOfPlanets.groupBy { planet -> planet.discoveryMethod }

        //Timber.d("grouped by hostname, showing keys: ${planetarySystemList.keys}")
        discoveryMethodList.keys.forEach { discoveryMethod ->
            Timber.d("discovery method: $discoveryMethod")
            Timber.d("number of planets discovered by method: ${discoveryMethod?.length}")
        }


        val dataSet = BarDataSet(systemEntryList, label)
        dataSet.valueFormatter = object: ValueFormatter(){
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                return super.getFormattedValue(value, axis)
            }
        }
        return dataSet
    }



    //Custom Scatter Plot
    fun createCustomDistribution(listOfPlanets: List<Planet>, label: String, xValue: String, yValue: String): ScatterDataSet{
        val planetEntryList = mutableListOf<Entry>()
        Timber.d("x value: $xValue")
        Timber.d("y value: $yValue")
        listOfPlanets.forEach { planet ->
            val customEntry = Entry()
            //How to add x and y value to planet.xValue and planet.yValue
            when(xValue){
                "Mass, Earth" -> {if (planet.planetaryMassEarth!= null) customEntry.x = scaleValues(planet.planetaryMassEarth)}
                "Radius, Earth" -> {if (planet.planetaryRadiusEarth!= null) customEntry.x = scaleValues(planet.planetaryRadiusEarth)}
                "Mass, Jupiter" -> {if (planet.planetaryMassJupiter!= null) customEntry.x = scaleValues(planet.planetaryMassJupiter)}
                "Radius, Jupiter" -> {if (planet.planetaryRadiusJupiter!= null) customEntry.x = scaleValues(planet.planetaryRadiusJupiter)}
                "Insolation, Earth Flux" -> {if (planet.planetaryInsolationFlux!= null) customEntry.x = scaleValues(planet.planetaryInsolationFlux)}
                "Eccentricity" -> {if (planet.planetaryOrbitalEccentricity!= null) customEntry.x = scaleValues(planet.planetaryOrbitalEccentricity)}
                "Equilibrium Temperature, K" -> {if (planet.planetaryEquilibriumTemperature!= null) customEntry.x = scaleValues(planet.planetaryEquilibriumTemperature)}
                "Semi-Major Axis, AU" -> {if (planet.orbitSemiMajorAxis!= null) customEntry.x = scaleValues(planet.orbitSemiMajorAxis)}
                "Period, Days" -> {if (planet.planetaryOrbitPeriod!= null) customEntry.x = scaleValues(planet.planetaryOrbitPeriod)}
                "Density, g/cm**3" -> {if (planet.planetDensity!= null) customEntry.x = scaleValues(planet.planetDensity)}
            }
            when(yValue){
                "Mass, Earth" -> {if (planet.planetaryMassEarth!= null) customEntry.y = scaleValues(planet.planetaryMassEarth)}
                "Radius, Earth" -> {if (planet.planetaryRadiusEarth!= null) customEntry.y = scaleValues(planet.planetaryRadiusEarth)}
                "Mass, Jupiter" -> {if (planet.planetaryMassJupiter!= null) customEntry.y = scaleValues(planet.planetaryMassJupiter)}
                "Radius, Jupiter" -> {if (planet.planetaryRadiusJupiter!= null) customEntry.y = scaleValues(planet.planetaryRadiusJupiter)}
                "Insolation, Earth Flux" -> {if (planet.planetaryInsolationFlux!= null) customEntry.y = scaleValues(planet.planetaryInsolationFlux)}
                "Eccentricity" -> {if (planet.planetaryOrbitalEccentricity!= null) customEntry.y = scaleValues(planet.planetaryOrbitalEccentricity)}
                "Equilibrium Temperature, K" -> {if (planet.planetaryEquilibriumTemperature!= null) customEntry.y = scaleValues(planet.planetaryEquilibriumTemperature)}
                "Semi-Major Axis, AU" -> {if (planet.orbitSemiMajorAxis!= null) customEntry.y = scaleValues(planet.orbitSemiMajorAxis)}
                "Period, Days" -> {if (planet.planetaryOrbitPeriod!= null) customEntry.y = scaleValues(planet.planetaryOrbitPeriod)}
                "Density, g/cm**3" -> {if (planet.planetDensity!= null) customEntry.y = scaleValues(planet.planetDensity)}
            }
            planetEntryList.add(customEntry)
        }
        Timber.d(label + " " + planetEntryList.size)
        return ScatterDataSet(planetEntryList, label)
    }



}



