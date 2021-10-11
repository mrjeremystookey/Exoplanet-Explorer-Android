package r.stookey.exoplanetexplorer.ui.graphs.plots

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import r.stookey.exoplanetexplorer.util.unscaleValues


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
        val xAxis = scatterChart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.textSize = 10f
        xAxis.textColor = Color.BLACK
        xAxis.labelRotationAngle = 0f
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(false)
        scatterChart.axisRight.isEnabled = false
        scatterChart.axisLeft.setDrawGridLines(false)
        scatterChart.axisLeft.setDrawAxisLine(true)
        scatterChart.axisLeft.axisMinimum = dataset.yMin
        xAxis.axisMinimum = dataset.xMin
        scatterChart.setScaleEnabled(true)


        scatterChart.axisLeft.valueFormatter = object: ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                return super.getFormattedValue(unscaleValues(value.toDouble()))
            }
        }
        xAxis.valueFormatter = object: ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                return super.getFormattedValue(unscaleValues(value.toDouble()))
            }
        }



        dataset.scatterShapeSize = 3f
        dataset.color = Color.YELLOW
        scatterChart.data = scatterDataSet
        scatterChart.invalidate()
    }
}

