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
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet

@Composable
fun BarChart(dataSet: BarDataSet) {
    val barData = BarData(dataSet)

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


        barChart.data = barData
        barChart.invalidate()
    }
}

/*@Preview
@Composable
fun PreviewBarChart(){
    BarChart()
}*/
