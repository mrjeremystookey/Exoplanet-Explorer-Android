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
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class GraphViewModel @Inject constructor(private val repo: GraphRepositoryImpl) : ViewModel() {

    private val _planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())

    private val _graphList: MutableState<List<String>> = mutableStateOf(listOf())
    val graphList: State<List<String>> = _graphList

    private var _selectedBarData: MutableState<BarDataSet> = mutableStateOf(BarDataSet(listOf(), ""))
    val selectedBarData: State<BarDataSet> = _selectedBarData

    private var _selectedScatterData: MutableState<ScatterDataSet> = mutableStateOf(ScatterDataSet(listOf(), ""))
    val selectedScatterData: State<ScatterDataSet> = _selectedScatterData

    private var _graphTitle: MutableState<String> = mutableStateOf("")
    val graphTitle: State<String> = _graphTitle



    //True for Scatter Plots, false for Bar Charts, probably a better solution
    private var _isScatter: MutableState<Boolean> = mutableStateOf(true)
    val isScatter: State<Boolean> = _isScatter


    init {
        Timber.d("GraphViewModel initialized")
        initializeListOfPlanets()
        createGraphList()

    }

    private fun initializeListOfPlanets() {
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                _planetsList.value = planets

            }
        }
        if(_planetsList.value.isNotEmpty()){
            createGraphList()
        }
    }


    private fun createGraphList(){
        val listOfGraphs = mutableListOf<String>()
        //Bar
        listOfGraphs.add("Detections Per Year")
        //Scatter
        listOfGraphs.add("Density - Mass Distribution")
        listOfGraphs.add("Density - Radius Distribution")
        listOfGraphs.add("Mass - Period Distribution")
        listOfGraphs.add("Radius - Period Distribution")
        listOfGraphs.add("Eccentricity - Period Distribution")
        _graphList.value = listOfGraphs
    }

    fun onGraphSelected(graphTitle: String){
        val graph = Graph(graphTitle, _planetsList.value)
        _graphTitle.value = graph.title
        _selectedScatterData.value = graph.data
        _isScatter.value = graph.isScatter

    }
}
