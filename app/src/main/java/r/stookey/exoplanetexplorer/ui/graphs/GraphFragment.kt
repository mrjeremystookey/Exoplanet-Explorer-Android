package r.stookey.exoplanetexplorer.ui.graphs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import r.stookey.exoplanetexplorer.ui.graphs.plots.BarChart
import timber.log.Timber


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
                val graphUIState = graphViewModel.graphUIState.observeAsState().value
                ExoplanetExplorerTheme {
                    Scaffold(
                        content = {
                            GraphContent(
                                graphUIState = graphUIState) },
                        drawerContent = {}
                    )
                }
            }
        }
    }

    @Composable
    fun GraphContent(graphUIState: GraphUIState?){
        when(graphUIState){
            GraphUIState.AllGraphs -> {
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
            GraphUIState.GraphSelected -> {

            }
            GraphUIState.Empty -> {

            }
        }

    }


    @Composable
    fun ListOfGraphs(){
        Column {

        }
    }


}


