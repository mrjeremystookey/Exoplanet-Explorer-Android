package r.stookey.exoplanetexplorer.ui.graphs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import r.stookey.exoplanetexplorer.ui.DropDownList
import r.stookey.exoplanetexplorer.ui.compose.theme.ExoplanetExplorerTheme
import r.stookey.exoplanetexplorer.ui.graphs.plots.BarChart
import r.stookey.exoplanetexplorer.ui.graphs.plots.ScatterPlot
import timber.log.Timber


//Fragment gets navigated to after a graph is selected, retrieves graph information from GraphViewModel
@AndroidEntryPoint
class  GraphFragment : Fragment() {

    private val graphViewModel: GraphViewModel by activityViewModels()
    private val attributeList = listOf(
        "EarthMass",
        "EarthRadius",
        "Density",
        "JupiterMass",
        "Period",
    )

    override fun onStart() {
        super.onStart()
        Timber.i("onStart called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroyView called")
    }

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
                .padding(4.dp)
                .background(MaterialTheme.colors.primary)
        ) {
            when {
                graphViewModel.isCustom.value -> {
                    Row {
                        XAxisSpinner(Modifier.weight(.5f))
                        Spacer(Modifier.padding(horizontal = 4.dp))
                        YAxisSpinner(Modifier.weight(.5f))
                    }
                    ScatterPlot(dataset = graphViewModel.selectedScatterData.value)
                }
                graphViewModel.isBar.value -> {
                    BarChart(dataSet = graphViewModel.selectedBarData.value)
                }
                else -> ScatterPlot(dataset = graphViewModel.selectedScatterData.value)
            }
        }
    }

    @Composable
    fun XAxisSpinner (modifier: Modifier) {

        val text = remember { mutableStateOf("") } // initial value
        val isOpen = remember { mutableStateOf(false) } // initial value
        val openCloseOfDropDownList: (Boolean) -> Unit = {
            isOpen.value = it
        }
        val userSelectedString: (String) -> Unit = {
            text.value = it
            graphViewModel.onXAxisChanged(text.value)
        }
        Box(modifier.padding(start = 4.dp)) {
            Column {
                OutlinedTextField(
                    value = text.value,
                    onValueChange = {
                        text.value = it
                                    },
                    label = { Text(text = "Choose X axis value") },
                    modifier = Modifier.wrapContentSize()
                )
                DropDownList(
                    requestToOpen = isOpen.value,
                    list = attributeList,
                    openCloseOfDropDownList,
                    userSelectedString
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .matchParentSize()
                    .background(Color.Transparent)
                    .clickable(
                        onClick = { isOpen.value = true }
                    )
            )
        }
    }

    @Composable
    fun YAxisSpinner (modifier: Modifier) {
        val text = remember { mutableStateOf("") } // initial value
        val isOpen = remember { mutableStateOf(false) } // initial value
        val openCloseOfDropDownList: (Boolean) -> Unit = {
            isOpen.value = it
        }
        val userSelectedString: (String) -> Unit = {
            text.value = it
            graphViewModel.onYAxisChanged(text.value)
        }
        Box(modifier.padding(end = 4.dp)) {
            Column {
                OutlinedTextField(
                    value = text.value,
                    onValueChange = {
                        text.value = it
                    },
                    label = { Text(text = "Choose Y axis value") },
                    modifier = Modifier.wrapContentSize()
                )
                DropDownList(
                    requestToOpen = isOpen.value,
                    list = attributeList,
                    openCloseOfDropDownList,
                    userSelectedString
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .matchParentSize()
                    .background(Color.Transparent)
                    .clickable(
                        onClick = { isOpen.value = true }
                    )
            )
        }
    }


}


