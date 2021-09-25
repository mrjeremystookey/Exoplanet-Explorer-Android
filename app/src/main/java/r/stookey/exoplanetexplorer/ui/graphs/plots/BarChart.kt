package r.stookey.exoplanetexplorer.ui.graphs.plots

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.utils.Utils
import r.stookey.exoplanetexplorer.domain.Planet
import timber.log.Timber

@Composable
fun BarChart(listOfPlanets: List<Planet>) {


    var planetEntryList = mutableListOf<Entry>()
    listOfPlanets.forEach { planet ->

    }
    Timber.d("number of Planets to be graphed: ${planetEntryList.size}")


    var dataSet = ScatterDataSet(planetEntryList, "Mass - Period Distribution")
    var scatterDataSet = ScatterData(dataSet)


    AndroidView(modifier = Modifier
        .fillMaxSize()
        .padding(2.dp), factory = { context ->
            BarChart(context)
    }){ barChart ->
        val xAxis: XAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textSize = 10f
        xAxis.textColor = Color.BLACK
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(true)
        barChart.invalidate()
    }
}


@Preview
@Composable
fun PreviewBarChart(){
    BarChart(emptyList())
}