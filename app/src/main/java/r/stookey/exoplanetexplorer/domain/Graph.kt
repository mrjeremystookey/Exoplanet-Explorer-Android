package r.stookey.exoplanetexplorer.domain

import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.ScatterDataSet


class Graph (var title: String,
             var scatterDataSet: ScatterDataSet? = null,
             var barDataSet: BarDataSet? = null) {

}