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
import r.stookey.exoplanetexplorer.domain.SortStatus
import r.stookey.exoplanetexplorer.repository.GraphRepositoryImpl
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class GraphViewModel @Inject constructor(private val repo: GraphRepositoryImpl) : ViewModel() {

    private val planetsList: MutableState<List<Planet>> = mutableStateOf(listOf())

    private val _graphList: MutableState<List<String>> = mutableStateOf(listOf(
        "Detections Per Year",
        "Mass - Period",
        "Radius - Period",
        "Density - Radius",
        "Eccentricity - Period",
        "Density - Mass",
        "EarthMass - EarthRadius")
    )
    val graphList: State<List<String>> = _graphList

    //Data to be sent to Fragment, Scatter or Bar Data
    private var _selectedBarData: MutableState<BarDataSet> = mutableStateOf(BarDataSet(listOf(), ""))
    val selectedBarData: State<BarDataSet> = _selectedBarData

    private var _selectedScatterData: MutableState<ScatterDataSet> = mutableStateOf(ScatterDataSet(listOf(), ""))
    val selectedScatterData: State<ScatterDataSet> = _selectedScatterData

    //Graph Attributes - title,  scatter/bar chart, and custom
    private var _graphTitle: MutableState<String> = mutableStateOf("")
    val graphTitle: State<String> = _graphTitle

    private var _isBar: MutableState<Boolean> = mutableStateOf(true)
    val isBar: State<Boolean> = _isBar



    //Used for Custom Distributions
    //Updated via dropdown menus on Custom Distribution Graph page
    private var _isCustom: MutableState<Boolean> = mutableStateOf(false)
    val isCustom: State<Boolean> = _isCustom
    private var _selectedXData: MutableState<String> = mutableStateOf("")
    private var _selectedYData: MutableState<String> = mutableStateOf("")
    private var myCustomGraph: MutableState<Graph> = mutableStateOf(Graph("null", emptyList(),
        null, null))
    private val _attributeList: MutableState<List<String>> = mutableStateOf(listOf(
        SortStatus.EarthMass.units,
        SortStatus.EarthRadius.units,
        SortStatus.JupiterMass.units,
        SortStatus.JupiterRadius.units,
        SortStatus.Insolation.units,
        SortStatus.OrbitalEccentricity.units,
        SortStatus.EquilibriumTemperature.units,
        SortStatus.SemiMajorAxis.units,
        SortStatus.Period.units,
        SortStatus.Density.units)
    )
    val attributeList: State<List<String>> = _attributeList




    init {
        Timber.d("GraphViewModel initialized")
        viewModelScope.launch {
            repo.getAllPlanetsFromCache.collect { planets ->
                planetsList.value = planets
            }
        }
    }


    //Updated when new dropdown values are selected
    //Updates a mutable state of object Graph
    fun onXAxisChanged(xValue: String){
        Timber.d("x axis value changed to $xValue")
        _selectedXData.value = xValue
        myCustomGraph.value = Graph("Custom Graph",
            planetsList.value,
            _selectedXData.value,
            _selectedYData.value)
        _graphTitle.value = myCustomGraph.value.scatterData.label
        _selectedScatterData.value = myCustomGraph.value.scatterData
    }

    fun onYAxisChanged(yValue: String){
        Timber.d("y axis value changed to $yValue")
        _selectedYData.value = yValue
        myCustomGraph.value = Graph("Custom Graph",
            planetsList.value,
            _selectedXData.value,
            _selectedYData.value)
        _graphTitle.value = myCustomGraph.value.scatterData.label
        _selectedScatterData.value = myCustomGraph.value.scatterData
    }

    fun onCustomGraphSelected(){
        _isCustom.value = true
    }


    fun onGraphSelected(graphTitle: String){
        _isCustom.value = false
        val graph = Graph(graphTitle, planetsList.value, null, null)
            _graphTitle.value = graph.title
            _isBar.value = graph.isBar
            if(graph.isBar)
                _selectedBarData.value = graph.barData
            else
                _selectedScatterData.value = graph.scatterData
    }
}
