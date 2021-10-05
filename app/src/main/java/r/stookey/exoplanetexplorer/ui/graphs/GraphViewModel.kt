package r.stookey.exoplanetexplorer.ui.graphs

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import r.stookey.exoplanetexplorer.domain.Graph
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.repository.GraphRepositoryImpl
import r.stookey.exoplanetexplorer.ui.graphs.plots.DataSetUtil
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class GraphViewModel @Inject constructor(private val repo: GraphRepositoryImpl) : ViewModel() {

    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())

    private val _graphList: MutableState<List<Graph>> = mutableStateOf(listOf())
    val graphList: State<List<Graph>> = _graphList

    private var _selectedData: MutableState<BarDataSet> = mutableStateOf(BarDataSet(listOf(), ""))
    val selectedData: State<BarDataSet> = _selectedData

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
        val graph = Graph("Detections Per Year")
        val listOfGraphs = mutableListOf<Graph>()
        listOfGraphs.add(graph)
        _graphList.value = listOfGraphs
    }

    fun onGraphSelected(graph: Graph){
        when(graph.title){
            "Detections Per Year" -> {
                val discoveryYearDataSet: BarDataSet = DataSetUtil().createDiscoveryYearDataSet(_planetsList.value)
                _selectedData.value = discoveryYearDataSet
            }
        }

    }


}
