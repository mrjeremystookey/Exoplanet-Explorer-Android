package r.stookey.exoplanetexplorer.ui.graphs.plots

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart

@Composable
fun BarChart() {
    AndroidView(modifier = Modifier
        .fillMaxSize()
        .padding(2.dp), factory = { context ->
            BarChart(context)
    }){

    }
}

@Preview
@Composable
fun PreviewBarChart(){
    BarChart()
}