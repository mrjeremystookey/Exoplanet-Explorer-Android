package r.stookey.exoplanetexplorer.ui.graphs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.BarDataSet
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import r.stookey.exoplanetexplorer.ui.graphs.plots.BarChart
import r.stookey.exoplanetexplorer.ui.graphs.plots.DataSetUtil


//Fragment gets navigated to after a graph is selected, retrieves graph information from GraphViewModel
@AndroidEntryPoint
class GraphFragment : Fragment() {

    private val graphViewModel: GraphViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ExoplanetExplorerTheme {
                    Scaffold(
                        topBar = { TopAppBar {
                            Text(
                                text = graphViewModel.selectedData.value.label,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                                 },
                        content = { Graph() }
                    )
                }
            }
        }
    }

    @Composable
    fun Graph(){
        Column(
            Modifier
                .padding(16.dp)
                .background(MaterialTheme.colors.primary)
        ) {
            //When graph is discoveryYear show this graph
            BarChart(dataSet = graphViewModel.selectedData.value)
        }
    }

}