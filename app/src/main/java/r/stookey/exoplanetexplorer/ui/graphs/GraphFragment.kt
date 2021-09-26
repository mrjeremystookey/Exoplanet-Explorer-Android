package r.stookey.exoplanetexplorer.ui.graphs

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import r.stookey.exoplanetexplorer.ui.graphs.plots.BarChart
import r.stookey.exoplanetexplorer.ui.graphs.plots.DataSetUtil
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class GraphFragment() : Fragment() {

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
                        content = { GraphContent() },
                        drawerContent = {}
                    )
                }
            }
        }
    }

    @Composable
    fun GraphContent(){
        val massPeriodDataSet = DataSetUtil().createMassPeriodDistributionDataSet(graphViewModel.planetsList.value)
        val discoveryYearDataSet = DataSetUtil().createDiscoveryYearDataSet(graphViewModel.planetsList.value)
        val radiusPeriodDataSet = DataSetUtil().createPeriodRadiusDistributionDataSet(graphViewModel.planetsList.value)
        Timber.d("discoveryDataSet: ${discoveryYearDataSet.entryCount}")
        Timber.d("massPeriodDataSet: ${massPeriodDataSet.entryCount}")
        Timber.d("radiusPeriodDataSet: ${radiusPeriodDataSet.entryCount}")
        BarChart(discoveryYearDataSet)
        //ScatterPlot(massPeriodDataSet)
        //ScatterPlot(radiusPeriodDataSet)
    }



}


