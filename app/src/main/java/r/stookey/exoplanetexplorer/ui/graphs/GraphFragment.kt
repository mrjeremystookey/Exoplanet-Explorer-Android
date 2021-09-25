package r.stookey.exoplanetexplorer.ui.graphs

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.graphs.plots.ScatterPlot
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import r.stookey.exoplanetexplorer.ui.graphs.plots.BarChart
import r.stookey.exoplanetexplorer.ui.graphs.plots.DataSetUtil


@AndroidEntryPoint
class GraphFragment : Fragment() {

    companion object {
        fun newInstance() = GraphFragment()
    }

    private val graphViewModel: GraphViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                ExoplanetExplorerTheme {
                    Scaffold(
                        content = { GraphContent(context) },
                        drawerContent = {}
                    )
                }
            }
        }
    }

    @Composable
    fun GraphContent(context: Context){

        var massPeriodDataSet = DataSetUtil().createScatterDataSet(graphViewModel.planetsList.value, "Mass-Period Distribution")
        var discoveryYearDataSet = DataSetUtil().createBarChartDataSet(graphViewModel.planetsList.value, "Detections Per Year")
        //ScatterPlot(massPeriodDataSet)
        BarChart(graphViewModel.planetsList.value)


    }



}


