package r.stookey.exoplanetexplorer.ui.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import r.stookey.exoplanetexplorer.ui.graphs.plots.BarChart


@AndroidEntryPoint
class GraphListFragment() : Fragment() {

    companion object {
        fun newInstance() = GraphListFragment()
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
                        content = { GraphContent(graphUIState = graphUIState) },
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
                ListOfGraphs()
            }
            GraphUIState.GraphSelected -> {
                SelectedGraph()
            }
            GraphUIState.Empty -> {

            }
        }

    }

    @Composable
    fun ListOfGraphs(){
        var rowModifier = Modifier
            .padding(4.dp)
            .background(MaterialTheme.colors.primary)
        Column {
            Row(rowModifier){
                Text("Discoveries Per Year")
            }
            Row(rowModifier){
                Text("Mass-Period Distribution")
            }
        }
    }

    @Composable
    fun SelectedGraph(){
        val discoveryYearDataSet = DataSetUtil().createDiscoveryYearDataSet(graphViewModel.planetsList.value)
        BarChart(discoveryYearDataSet)
    }


}


