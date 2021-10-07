package r.stookey.exoplanetexplorer.domain

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.ScatterDataSet
import r.stookey.exoplanetexplorer.util.DataSetUtil
import timber.log.Timber


class Graph (var title: String,
             private var listOfPlanets: List<Planet>) {

   var scatterData = ScatterDataSet(emptyList(), "")
   var barData = BarDataSet(emptyList(), "")

   var isScatter = true

   init {
       Timber.d("new Graph created: ${title}, number of planets in graph: ${listOfPlanets.size}")
       createDataSetForGraph()
   }


   private fun createDataSetForGraph(){
       //Should be injected
       val dataUtil = DataSetUtil()

       when(title){
            "Density - Mass Distribution" -> {
                val densityMassDataSet = dataUtil.createDensityMassDistributionDataSet(listOfPlanets, title)
                scatterData = densityMassDataSet
            }
           "Density - Radius Distribution" -> {
               val radiusPeriodDataSSet = dataUtil.createDensityRadiusDistributionDataSet(listOfPlanets, title)
               scatterData = radiusPeriodDataSSet
           }
           "Mass - Period Distribution" -> {
               val massPeriodDataSet = dataUtil.createMassPeriodDistributionDataSet(listOfPlanets, title)
               scatterData = massPeriodDataSet
           }
           "Radius - Period Distribution" -> {
               val radiusPeriodDataSSet = dataUtil.radiusPeriodDistributionDataSet(listOfPlanets, title)
               scatterData = radiusPeriodDataSSet
           }
           "Eccentricity - Period Distribution" -> {
               val radiusPeriodDataSSet = dataUtil.createEccentricityPeriodDistributionDataSet(listOfPlanets, title)
               scatterData = radiusPeriodDataSSet
           }
           "Detections Per Year" -> {
               val detectionsPerYearDataSet = dataUtil.createDiscoveryYearDataSet(listOfPlanets, title)
               barData = detectionsPerYearDataSet
               isScatter = false
           }
           /*"Custom Distribution" -> {
               val customDataSet = dataUtil.createCustomDistribution(listOfPlanets, title, )
               scatterData = customDataSet
           }*/

        }
   }
}