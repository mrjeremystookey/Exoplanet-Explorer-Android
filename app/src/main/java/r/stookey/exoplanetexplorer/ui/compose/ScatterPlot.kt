package r.stookey.exoplanetexplorer.ui.compose

import android.content.Context
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.utils.Utils
import r.stookey.exoplanetexplorer.domain.Planet
import timber.log.Timber


@Composable
fun ScatterPlot(listOfPlanets: List<Planet>, context: Context) {
    Utils.init(context)

    var planetEntryList = mutableListOf<Entry>()
    listOfPlanets.forEach { planet ->
        if(planet.planetaryOrbitPeriod != null && planet.planetaryMassJupiter != null){
            var planetEntry = Entry(planet.planetaryOrbitPeriod.toFloat(), planet.planetaryMassJupiter.toFloat())
            planetEntryList.add(planetEntry)
        }
    }
    Timber.d("number of Planets to be graphed: ${planetEntryList.size}")


    var dataSet = ScatterDataSet(planetEntryList, "Mass - Period Distribution")
    var scatterDataSet = ScatterData(dataSet)

    AndroidView(
    modifier = Modifier
        .fillMaxSize()
        .padding(2.dp),
    factory = { context ->
        ScatterChart(context)
    }
    ){ scatterChart ->
        val xAxis: XAxis = scatterChart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.textSize = 10f
        xAxis.textColor = Color.BLACK
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(true)
        scatterChart.data = scatterDataSet
        scatterChart.invalidate()
    }
}