package r.stookey.exoplanetexplorer.ui.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import r.stookey.exoplanetexplorer.ui.graphs.plots.BarChart
import r.stookey.exoplanetexplorer.ui.graphs.plots.ScatterPlot


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
                                text = graphViewModel.graphTitle.value,
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
            when {
                graphViewModel.isCustom.value -> {
                    Text("Chose values")
                    //Show Dropdowns
                    ScatterPlot(dataset = graphViewModel.selectedScatterData.value)
                }
                graphViewModel.isScatter.value -> {
                    ScatterPlot(dataset = graphViewModel.selectedScatterData.value)
                }
                else -> BarChart(dataSet = graphViewModel.selectedBarData.value)
            }

        }
    }

    @Composable
    fun PlanetAttributeDropdowns(colorSelected: Color = MaterialTheme.colors.primary,
                                 colorBackground: Color = MaterialTheme.colors.onSurface,
                                 expanded: Boolean,
                                 selectedIndex: Int,
                                 items: List<String>,
                                 onSelect: (Int) -> Unit,
                                 onDismissRequest: () -> Unit,
                                 content: @Composable () -> Unit){
        Box {
            content()
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .background(
                        color = colorBackground,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                items.forEachIndexed { index, s ->
                    if (selectedIndex == index) {
                        DropdownMenuItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = colorSelected,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            onClick = { onSelect(index) }
                        ) {
                            Text(
                                text = s,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    } else {
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onSelect(index) }
                        ) {
                            Text(
                                text = s,
                                color = Color.DarkGray,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}


