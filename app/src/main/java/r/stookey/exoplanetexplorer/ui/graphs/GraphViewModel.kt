package r.stookey.exoplanetexplorer.ui.graphs

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.ScatterDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Graph
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.GraphRepositoryImpl
import r.stookey.exoplanetexplorer.util.DataSetUtil
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class GraphViewModel @Inject constructor(private val repo: GraphRepositoryImpl) : ViewModel() {

    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())

    private val _graphList: MutableState<List<Graph>> = mutableStateOf(listOf())
    val graphList: State<List<Graph>> = _graphList

    private var _selectedBarData: MutableState<BarDataSet> = mutableStateOf(BarDataSet(listOf(), ""))
    val selectedBarData: State<BarDataSet> = _selectedBarData

    private var _selectedScatterData: MutableState<ScatterDataSet> = mutableStateOf(ScatterDataSet(listOf(), ""))
    val selectedScatterData: State<ScatterDataSet> = _selectedScatterData

    private var _graphTitle: MutableState<String> = mutableStateOf("")
    val graphTitle: State<String> = _graphTitle

    //True for Scatter Plots, false for Bar Charts, probably a better solution
    private var _graphType: MutableState<Boolean> = mutableStateOf(true)
    val graphType: State<Boolean> = _graphType


    init {
        Timber.d("GraphViewModel initialized")
        initializeListOfPlanets()
        createGraphList()
    }

    private fun initializeListOfPlanets() {
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                _planetsList.value = planets }
        }
    }

    private fun createGraphList(){
        val barGraph = Graph("Detections Per Year")
        val scatterGraph = Graph("Mass - Period Distribution")
        val listOfGraphs = mutableListOf<Graph>()
        listOfGraphs.add(barGraph)
        listOfGraphs.add(scatterGraph)
        _graphList.value = listOfGraphs
    }

    fun onGraphSelected(graph: Graph){
        when(graph.title){
            "Detections Per Year" -> {
                val discoveryYearDataSet: BarDataSet = DataSetUtil().createDiscoveryYearDataSet(_planetsList.value)
                _graphTitle.value = discoveryYearDataSet.label
                _selectedBarData.value = discoveryYearDataSet
                _graphType.value = false
            }
            "Mass - Period Distribution" -> {
                val massPeriodDataSet: ScatterDataSet = DataSetUtil().createMassPeriodDistributionDataSet(_planetsList.value)
                _graphTitle.value = massPeriodDataSet.label
                _selectedScatterData.value = massPeriodDataSet
                _graphType.value = true
            }
        }

    }
}
