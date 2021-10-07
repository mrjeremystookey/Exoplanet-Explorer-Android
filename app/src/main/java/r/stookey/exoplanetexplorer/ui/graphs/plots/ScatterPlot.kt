package r.stookey.exoplanetexplorer.ui.graphs.plots

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import r.stookey.exoplanetexplorer.util.DataSetUtil
import r.stookey.exoplanetexplorer.util.MyValueFormatter


@Composable
fun ScatterPlot(dataset: ScatterDataSet) {
    var scatterDataSet = ScatterData(dataset)

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
        xAxis.labelRotationAngle = 90f
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(true)
        xAxis.valueFormatter = MyValueFormatter()

        //scatterChart.axisLeft.axisMaximum = 60f
        //xAxis.axisMaximum = 15000f


        scatterChart.data = scatterDataSet
        scatterChart.invalidate()
    }
}

@Preview()
@Composable
fun PreviewScatterPlot(){
    val massPeriodDataSet: ScatterDataSet = DataSetUtil().createMassPeriodDistributionDataSet(emptyList())
    ScatterPlot(massPeriodDataSet)
}
