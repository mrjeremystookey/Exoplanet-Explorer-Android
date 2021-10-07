package r.stookey.exoplanetexplorer.domain

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.ScatterDataSet
import r.stookey.exoplanetexplorer.util.DataSetUtil
import timber.log.Timber


class Graph (var title: String,
             private var listOfPlanets: List<Planet>) {

   lateinit var data: ScatterDataSet
   lateinit var barData: BarDataSet

   var isScatter = true

   init {
       Timber.d("new Graph created: ${title}, number of planets in graph: ${listOfPlanets.size}")
       creatingDataSetForGraph()
   }

   private fun creatingDataSetForGraph(){
       //Should be injected
       val dataUtil = DataSetUtil()

       when(title){
            "Density - Mass Distribution" -> {
                val densityMassDataSet = dataUtil.createDensityMassDistributionDataSet(listOfPlanets, title)
                data = densityMassDataSet
            }
           "Density - Radius Distribution" -> {
               val radiusPeriodDataSSet = dataUtil.createDensityRadiusDistributionDataSet(listOfPlanets, title)
               data = radiusPeriodDataSSet
           }
           "Mass - Period Distribution" -> {
               val massPeriodDataSet = dataUtil.createMassPeriodDistributionDataSet(listOfPlanets, title)
               data = massPeriodDataSet
           }
           "Radius - Period Distribution" -> {
               val radiusPeriodDataSSet = dataUtil.radiusPeriodDistributionDataSet(listOfPlanets, title)
               data = radiusPeriodDataSSet
           }
           "Eccentricity - Period Distribution" -> {
               val radiusPeriodDataSSet = dataUtil.createEccentricityPeriodDistributionDataSet(listOfPlanets, title)
               data = radiusPeriodDataSSet
           }
           "Detections Per Year" -> {
               val detectionsPerYearDataSet = dataUtil.createDiscoveryYearDataSet(listOfPlanets, title)
               barData = detectionsPerYearDataSet
               isScatter = false
           }
        }
   }
}