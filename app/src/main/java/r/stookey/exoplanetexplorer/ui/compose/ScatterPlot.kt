package r.stookey.exoplanetexplorer.ui.compose

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


@Composable
fun ScatterPlot() {

    var entry1 = Entry(5f,15f)
    var entry2 = Entry(10f,25f)
    var entry3 = Entry(15f,45f)
    var list = listOf(entry1, entry2, entry3)
    var dataSet = ScatterDataSet(list, "test data")
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